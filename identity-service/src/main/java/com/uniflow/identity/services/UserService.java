package com.uniflow.identity.services;

import com.uniflow.identity.dto.LoginRequestUserDto;
import com.uniflow.identity.dto.LoginResponseDto;
import com.uniflow.identity.dto.CreateRequestUserDto;
import com.uniflow.identity.dto.ResponseUserDto;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;
public interface UserService extends UserDetailsService {
    ResponseUserDto createUser(CreateRequestUserDto dto);
    ResponseUserDto getUserById(Long id);
    List<ResponseUserDto> getAllUsers();
    LoginResponseDto login(LoginRequestUserDto dto);
}
