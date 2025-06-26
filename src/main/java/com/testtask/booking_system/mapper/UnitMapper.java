package com.testtask.booking_system.mapper;

import com.testtask.booking_system.dto.request.UnitCreateDto;
import com.testtask.booking_system.dto.request.UnitPatchDto;
import com.testtask.booking_system.dto.response.UnitResponseDto;
import com.testtask.booking_system.entity.Unit;
import com.testtask.booking_system.props.PricingProps;
import lombok.Setter;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Setter(onMethod_ = {@Autowired})
public abstract class UnitMapper {

  protected PricingProps pricingProps;

  public abstract Unit fromUnitCreateDto(UnitCreateDto unitCreateDto);

  @Mapping(source = "owner.id", target = "ownerId")
  @Mapping(target = "pricePerNight",
      expression = "java(applyMarkup(pricingProps.getTotalPrice(pricePerNight)))")
  public abstract UnitResponseDto toUnitResponseDto(Unit unit);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  public abstract Unit fromUnitPatchDto(UnitPatchDto unitPatchDto, @MappingTarget Unit unit);
}
