package com.uniflow.identity.services;

import com.uniflow.identity.dto.LoginRequestUserDto;
import com.uniflow.identity.dto.LoginResponseDto;
import com.uniflow.identity.dto.RegisterRequestUserDto;
import com.uniflow.identity.dto.ResponseUserDto;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;
public interface UserService {
    ResponseUserDto createUser(RegisterRequestUserDto dto);
    ResponseUserDto getUserById(Long id);
    List<ResponseUserDto> getAllUsers();
    LoginResponseDto login(LoginRequestUserDto dto);
    UserDetails loadUserByUsername(String username);
}
