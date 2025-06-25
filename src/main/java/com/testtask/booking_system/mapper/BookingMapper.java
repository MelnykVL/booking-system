package com.testtask.booking_system.mapper;

import com.testtask.booking_system.dto.BookingCreateDto;
import com.testtask.booking_system.dto.BookingResponseDto;
import com.testtask.booking_system.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, UnitMapper.class})
public interface BookingMapper {

  BookingResponseDto toBookingResponseDto(Booking booking);

  Booking fromBookingCreateDto(BookingCreateDto bookingCreateDto);
}
