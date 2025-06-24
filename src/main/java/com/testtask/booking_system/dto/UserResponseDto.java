package com.testtask.booking_system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;

public record UserResponseDto(Long id,
                              String firstName,
                              String lastName,
                              String email,
                              @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC") Instant createdAt) {}
