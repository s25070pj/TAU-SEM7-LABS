package com.example.api.service.impl;

import com.example.api.domain.User;
import com.example.api.dto.UserCreateDto;
import com.example.api.dto.UserDto;
import com.example.api.dto.UserUpdateDto;
import com.example.api.error.exception.EntityNotFoundException;
import com.example.api.repository.UserRepository;
import com.example.api.service.UserService;
import com.example.api.service.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto save(UserCreateDto userCreateDto) {
        User entity = userMapper.mapCreateToEntity(userCreateDto);
        User savedEntity = userRepository.save(entity);
        return userMapper.toDto(savedEntity);
    }

    @Override
    public UserDto update(UserUpdateDto userUpdateDto, Long id) {
        User entity = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userMapper.updateUserFromDto(userUpdateDto, entity);
        return userMapper.toDto(userRepository.save(entity));

    }

    @Override
    public List<UserDto> findAll() {
        return userMapper.toDto(userRepository.findAll());
    }

    @Override
    public Optional<UserDto> findOne(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
