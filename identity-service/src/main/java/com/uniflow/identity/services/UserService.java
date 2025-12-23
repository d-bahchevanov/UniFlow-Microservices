package com.uniflow.identity.services;

import com.uniflow.identity.dto.RequestUserDto;
import com.uniflow.identity.dto.ResponseUserDto;


import java.util.List;
public interface UserService {
    ResponseUserDto createUser(RequestUserDto dto);
    ResponseUserDto getUserById(Long id);
    List<ResponseUserDto> getAllUsers();
    ResponseUserDto login(RequestUserDto dto);
}
