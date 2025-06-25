package com.testtask.booking_system.service;

import com.testtask.booking_system.dto.UserCreateDto;
import com.testtask.booking_system.dto.UserPatchDto;
import com.testtask.booking_system.dto.UserResponseDto;
import com.testtask.booking_system.entity.User;
import com.testtask.booking_system.enums.EventLogAction;
import com.testtask.booking_system.exception.ResourceNotFountException;
import com.testtask.booking_system.exception.UserEmailAlreadyExistsException;
import com.testtask.booking_system.mapper.UserMapper;
import com.testtask.booking_system.repository.UserRepository;
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
  private final AuditService auditService;

  @Transactional(readOnly = true)
  public ResponseEntity<UserResponseDto> findUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFountException(User.class.getSimpleName(), userId));
    UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);

    return ResponseEntity.ok(userResponseDto);
  }

  public ResponseEntity<UserResponseDto> createUser(UserCreateDto userCreateDto) {
    if (userRepository.existsByEmail(userCreateDto.email())) {
      throw new UserEmailAlreadyExistsException(userCreateDto.email());
    }
    User user = userMapper.fromUserCreateDto(userCreateDto);
    UserResponseDto userResponseDto = userMapper.toUserResponseDto(userRepository.save(user));
    auditService.log(User.class, user.getId(), EventLogAction.CREATE_USER.name(), user);

    return ResponseEntity.ok(userResponseDto);
  }

  public ResponseEntity<UserResponseDto> patchUser(Long userId, UserPatchDto userPatchDto) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFountException(User.class.getSimpleName(), userId));
    String email = userPatchDto.email();
    if (StringUtils.isNotBlank(email) && userRepository.existsByEmail(email)) {
      throw new UserEmailAlreadyExistsException(email);
    }
    user = userMapper.fromUserPatchDto(userPatchDto, user);
    UserResponseDto userResponseDto = userMapper.toUserResponseDto(userRepository.save(user));
    auditService.log(User.class, user.getId(), EventLogAction.UPDATE_USER.name(), user);

    return ResponseEntity.ok(userResponseDto);
  }
}
