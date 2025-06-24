package com.testtask.booking_system.mapper;

import com.testtask.booking_system.dto.UserCreateDto;
import com.testtask.booking_system.dto.UserPatchDto;
import com.testtask.booking_system.dto.UserResponseDto;
import com.testtask.booking_system.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

  User fromUserCreateDto(UserCreateDto userCreateDto);
  UserResponseDto toUserResponseDto(User user);
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  User fromUserPatchDto(UserPatchDto userPatchDto, @MappingTarget User user);
}
