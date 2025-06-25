package com.testtask.booking_system.dto.response;

import java.util.Collection;

public record PagingResultDto<T>(
    Collection<T> content,
    Integer page,
    Integer size,
    Integer totalPages,
    Long totalElements,
    boolean empty
) {}
