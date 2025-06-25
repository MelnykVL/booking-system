package com.testtask.booking_system.service;

import com.testtask.booking_system.dto.BookingCreateDto;
import com.testtask.booking_system.dto.BookingResponseDto;
import com.testtask.booking_system.entity.Booking;
import com.testtask.booking_system.entity.Unit;
import com.testtask.booking_system.entity.User;
import com.testtask.booking_system.enums.BookingStatus;
import com.testtask.booking_system.exception.BookingCancellationException;
import com.testtask.booking_system.exception.ResourceNotFountException;
import com.testtask.booking_system.mapper.BookingMapper;
import com.testtask.booking_system.props.PricingProps;
import com.testtask.booking_system.repository.BookingRepository;
import com.testtask.booking_system.repository.UnitRepository;
import com.testtask.booking_system.repository.UserRepository;
import com.testtask.booking_system.util.MarkupUtils;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {

  private final BookingRepository bookingRepository;
  private final UserRepository userRepository;
  private final UnitRepository unitRepository;
  private final BookingMapper bookingMapper;
  private final PricingProps pricingProps;

  public ResponseEntity<BookingResponseDto> createBooking(Long userId, Long unitId, BookingCreateDto bookingCreateDto) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFountException(User.class.getSimpleName(), unitId));
    Unit unit = unitRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFountException(Unit.class.getSimpleName(), unitId));
    Booking booking = bookingMapper.fromBookingCreateDto(bookingCreateDto);
    booking.setUser(user);
    booking.setUnit(unit);
    BigDecimal totalCost =
        MarkupUtils.countTotalCostForPeriod(bookingCreateDto.checkInOn(), bookingCreateDto.checkOutOn(),
            unit.getPricePerNight(), pricingProps.getMarkupPercent());
    booking.setTotalCost(totalCost);
    booking.setExpiresAt(Instant.now().plus(Duration.ofMinutes(15)));
    booking = bookingRepository.save(booking);
    BookingResponseDto bookingResponseDto = bookingMapper.toBookingResponseDto(booking);
    return ResponseEntity.ok(bookingResponseDto);
  }

  public ResponseEntity<Void> cancelBooking(Long userId, Long unitId, Long bookingId) {
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFountException(Booking.class.getSimpleName(), bookingId));
    if (booking.getUser().getId().equals(userId)) {
      String errorMessage = String.format("User '%d' is not the owner of booking '%s'.", userId, bookingId);
      throw new BookingCancellationException(errorMessage);
    }
    if (booking.getUnit().getId().equals(unitId)) {
      String errorMessage = String.format("User '%d' has not booked unit '%s'.", userId, unitId);
      throw new BookingCancellationException(errorMessage);
    }
    booking.setStatus(BookingStatus.CANCELED);
    bookingRepository.save(booking);

    return ResponseEntity.noContent().build();
  }
}
