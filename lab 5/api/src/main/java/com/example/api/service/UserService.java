package com.example.api.service;

import com.example.api.dto.UserCreateDto;
import com.example.api.dto.UserDto;
import com.example.api.dto.UserUpdateDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto save(UserCreateDto userCreateDto);

    UserDto update(UserUpdateDto userUpdateDto, Long id);

    List<UserDto> findAll();

    Optional<UserDto> findOne(Long id);

    void delete(Long id);
}
