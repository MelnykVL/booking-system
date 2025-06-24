package com.testtask.booking_system.mapper;

import com.testtask.booking_system.dto.PagingResultDto;
import java.util.List;
import java.util.function.Function;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PageMapper {

  default <S, D> PagingResultDto<D> pageToPagingResultDto(Page<S> page, Function<S, D> mapper) {
    List<D> content = page.getContent()
        .stream()
        .map(mapper)
        .toList();

    return new PagingResultDto<>(
        page.getNumber(),
        page.getSize(),
        page.getTotalPages(),
        page.getTotalElements(),
        page.isEmpty(),
        content
    );
  }
}
