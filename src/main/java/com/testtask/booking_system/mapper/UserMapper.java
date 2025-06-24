package com.testtask.booking_system.mapper;

import com.testtask.booking_system.dto.UserCreateDto;
import com.testtask.booking_system.dto.UserResponseDto;
import com.testtask.booking_system.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

  User userCreateDtoToUser(UserCreateDto userCreateDto);
  UserResponseDto userToUserResponseDto(User user);
}
