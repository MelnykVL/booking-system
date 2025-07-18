package com.testtask.booking_system.controller;

import com.testtask.booking_system.dto.request.UserCreateDto;
import com.testtask.booking_system.dto.request.UserPatchDto;
import com.testtask.booking_system.dto.response.UserResponseDto;
import com.testtask.booking_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  @GetMapping("/{userId}")
  public ResponseEntity<UserResponseDto> findUser(@PathVariable Long userId) {
    return userService.findUser(userId);
  }

  @PostMapping
  public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
    return userService.createUser(userCreateDto);
  }

  @PatchMapping("/{userId}")
  public ResponseEntity<UserResponseDto> patchUser(@PathVariable Long userId,
      @Valid @RequestBody UserPatchDto userPatchDto) {
    return userService.patchUser(userId, userPatchDto);
  }
}
