                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           package com.uniflow.identity.services.impl;

import com.uniflow.identity.dto.RequestUserDto;
import com.uniflow.identity.dto.ResponseUserDto;
import com.uniflow.identity.enums.Role;
import com.uniflow.identity.exception.domain.ExistingEmailException;
import com.uniflow.identity.exception.domain.ExistingUsernameException;
import com.uniflow.identity.exception.domain.UserNotFoundException;
import com.uniflow.identity.exception.domain.WrongPasswordException;
import com.uniflow.identity.model.User;
import com.uniflow.identity.repository.UserRepository;
import com.uniflow.identity.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public ResponseUserDto createUser(RequestUserDto dto) {;
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new ExistingEmailException("User with this email already exists");
            } else if (userRepository.existsByUsername(dto.getUsername())) {
             throw new ExistingUsernameException("User with this username already exists");
        }
            User user = new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getUsername(),
                    passwordEncoder.encode(dto.getPassword()), dto.getAge(), dto.getPhoneNumber(), Role.NON_ALIGNED);
        userRepository.save(user);
        return new ResponseUserDto(user.getEmail(), user.getUsername());
    }

    @Override
    public ResponseUserDto getUserById(Long id) {
       User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user with this ID"));
           return new ResponseUserDto(user.getEmail(), user.getUsername());
    }

    @Override
    public List<ResponseUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> new ResponseUserDto(u.getEmail(), u.getUsername())).toList();
    }

    @Override
    public ResponseUserDto login(RequestUserDto requestUserDto) {
        User user = userRepository.findUserByUsername(requestUserDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("No user with this username"));
        if (!passwordEncoder.matches(requestUserDto.getPassword(),user.getPassword())) {
            throw new WrongPasswordException("Wrong password");
        }
        return new ResponseUserDto(user.getEmail(), user.getUsername());
    }
}
