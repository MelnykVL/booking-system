package com.testtask.booking_system.dto;

import java.util.Collection;

public record PagingResultDto<T>(
    Integer page,
    Integer size,
    Integer totalPages,
    Long totalElements,
    boolean empty,
    Collection<T> content
) {}
