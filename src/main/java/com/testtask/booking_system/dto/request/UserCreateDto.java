package com.testtask.booking_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDto(
    @NotBlank(message = "'firstName' cannot be blank.") String firstName,
    @NotBlank(message = "'lastName' cannot be blank.") String lastName,
    @Email(message = "'email' must be in email format.")
    @NotBlank(message = "'email' cannot be blank.") String email
) {}