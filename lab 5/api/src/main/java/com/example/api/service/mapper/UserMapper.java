package com.example.api.service.mapper;

import com.example.api.domain.User;
import com.example.api.dto.UserCreateDto;
import com.example.api.dto.UserDto;
import com.example.api.dto.UserUpdateDto;
import com.example.api.service.mapper.utils.EntityMapper;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDto, User> {


    User mapCreateToEntity(UserCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateDto dto, @MappingTarget User entity);
}
