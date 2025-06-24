package com.testtask.booking_system.dto;

import java.time.Instant;

public record UserResponseDto(Long id,
                              String firstName,
                              String lastName,
                              String email,
                              Instant createdAt) {}
