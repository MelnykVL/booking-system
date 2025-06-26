package com.testtask.booking_system.mapper;

import com.testtask.booking_system.dto.request.PaymentCreateDto;
import com.testtask.booking_system.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

  Payment fromUserCreateDto(PaymentCreateDto paymentCreateDto);
}
