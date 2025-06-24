package com.testtask.booking_system.mapper;

import com.testtask.booking_system.dto.UnitCreateDto;
import com.testtask.booking_system.dto.UnitPatchDto;
import com.testtask.booking_system.dto.UnitResponseDto;
import com.testtask.booking_system.entity.Unit;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UnitMapper {

  Unit unitCreateDtoToUnit(UnitCreateDto unitCreateDto);

  @Mapping(source = "owner.id", target = "ownerId")
  UnitResponseDto unitToUnitResponseDto(Unit unit);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Unit patchUnit(UnitPatchDto unitPatchDto, @MappingTarget Unit unit);
}
