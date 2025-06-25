package com.testtask.booking_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserPatchDto(
    @Pattern(regexp = ".*\\S.*", message = "'firstName' cannot be whitespace only if specified.") String firstName,
    @Pattern(regexp = ".*\\S.*", message = "'lastName' cannot be whitespace only if specified.") String lastName,
    @Pattern(regexp = ".*\\S.*", message = "'email' cannot be whitespace only if specified.")
    @Email(message = "'email' must be in email format.") String email
) {}