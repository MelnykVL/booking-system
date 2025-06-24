package com.testtask.booking_system.service;

import com.testtask.booking_system.UserEmailAlreadyExistsException;
import com.testtask.booking_system.dto.UserCreateDto;
import com.testtask.booking_system.dto.UserPatchDto;
import com.testtask.booking_system.dto.UserResponseDto;
import com.testtask.booking_system.entity.User;
import com.testtask.booking_system.mapper.UserMapper;
import com.testtask.booking_system.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional(readOnly = true)
  public ResponseEntity<UserResponseDto> getUser(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> {
      String errorMessage = String.format("User with id '%d' does not exist", userId);
      return new EntityNotFoundException(errorMessage);
    });
    UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user);

    return ResponseEntity.ok(userResponseDto);
  }

  public ResponseEntity<UserResponseDto> createUser(UserCreateDto userCreateDto) {
    if (userRepository.existsByEmail(userCreateDto.email())) {
      throw new UserEmailAlreadyExistsException(userCreateDto.email());
    }
    User user = userMapper.userCreateDtoToUser(userCreateDto);
    UserResponseDto userResponseDto = userMapper.userToUserResponseDto(userRepository.save(user));

    return ResponseEntity.ok(userResponseDto);
  }

  public ResponseEntity<UserResponseDto> patchUser(Long userId, UserPatchDto userPatchDto) {
    User user = userRepository.findById(userId).orElseThrow(() -> {
      String errorMessage = String.format("User with id '%d' does not exist", userId);
      return new EntityNotFoundException(errorMessage);
    });
    String email = userPatchDto.email();
    if (StringUtils.isNotBlank(email) && userRepository.existsByEmail(email)) {
      throw new UserEmailAlreadyExistsException(email);
    }
    user = userMapper.patchUser(userPatchDto, user);
    UserResponseDto userResponseDto = userMapper.userToUserResponseDto(userRepository.save(user));
    return ResponseEntity.ok(userResponseDto);
  }
}
