package com.uniflow.identity.services.impl;
import com.uniflow.identity.client.ProfileClient;
import com.uniflow.identity.dto.*;
import com.uniflow.identity.exception.domain.ExistingEmailException;
import com.uniflow.identity.exception.domain.ExistingUsernameException;
import com.uniflow.identity.exception.domain.UserNotFoundException;
import com.uniflow.identity.exception.domain.WrongPasswordException;
import com.uniflow.identity.kafka.event.UserRegisteredEvent;
import com.uniflow.identity.kafka.producer.UserRegisteredEventProducer;
import com.uniflow.identity.model.User;
import com.uniflow.identity.repository.UserRepository;
import com.uniflow.identity.security.jwt.service.JwtService;
import com.uniflow.identity.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.springframework.security.core.userdetails.User.builder;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ProfileClient profileClient;
    private final UserRegisteredEventProducer eventProducer;
    @Override
    @Transactional
    public ResponseUserDto createUser(CreateRequestUserDto dto) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new ExistingEmailException("User with this email already exists");
            } else if (userRepository.existsByUsername(dto.getUsername())) {
             throw new ExistingUsernameException("User with this username already exists");
        }
            User user = new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getUsername(),
                    passwordEncoder.encode(dto.getPassword()), dto.getAge(), dto.getPhoneNumber(), dto.getRole());
        userRepository.save(user);

        UserRegisteredEvent event = new UserRegisteredEvent(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
        eventProducer.sendUserRegisteredEvent(event);
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
    public LoginResponseDto login(LoginRequestUserDto loginRequestUserDto) {
        User user = userRepository.findUserByUsername(loginRequestUserDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("No user with this username"));
        if (!passwordEncoder.matches(loginRequestUserDto.getPassword(),user.getPassword())) {
            throw new WrongPasswordException("Wrong password");
        }
        String token = jwtService.generateToken(user);
        return new LoginResponseDto(token);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("No such user exists");
        }
        profileClient.deleteProfile(id);
        userRepository.deleteUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
       User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("No user with this username"));
       return builder()
               .username(user.getUsername())
               .password(user.getPassword())
               .authorities("ROLE_" + user.getRole().name())
               .build();
    }
}
