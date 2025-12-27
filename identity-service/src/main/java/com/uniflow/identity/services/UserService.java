package com.uniflow.identity.services;

import com.uniflow.identity.dto.LoginResponseDto;
import com.uniflow.identity.dto.RequestUserDto;
import com.uniflow.identity.dto.ResponseUserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;
public interface UserService {
    ResponseUserDto createUser(RequestUserDto dto);
    ResponseUserDto getUserById(Long id);
    List<ResponseUserDto> getAllUsers();
    LoginResponseDto login(RequestUserDto dto);
    UserDetails loadUserByUsername(String username);
}
