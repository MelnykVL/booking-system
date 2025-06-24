package com.testtask.booking_system.mapper;

import com.testtask.booking_system.dto.UnitCreateDto;
import com.testtask.booking_system.dto.UnitPatchDto;
import com.testtask.booking_system.dto.UnitResponseDto;
import com.testtask.booking_system.entity.Unit;
import com.testtask.booking_system.props.PricingProps;
import com.testtask.booking_system.util.MarkupUtils;
import java.math.BigDecimal;
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

  public abstract Unit unitCreateDtoToUnit(UnitCreateDto unitCreateDto);

  @Mapping(source = "owner.id", target = "ownerId")
  @Mapping(target = "pricePerNight",
      expression = "java(applyMarkup(unit.getPricePerNight(), pricingProps.getMarkupPercent()))")
  public abstract UnitResponseDto unitToUnitResponseDto(Unit unit);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  public abstract Unit patchUnit(UnitPatchDto unitPatchDto, @MappingTarget Unit unit);

  public BigDecimal applyMarkup(BigDecimal price, BigDecimal markupPercent) {
    return MarkupUtils.applyMarkup(price, markupPercent);
  }
}
