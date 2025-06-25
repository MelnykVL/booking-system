package com.testtask.booking_system.service;

import com.testtask.booking_system.dto.request.BookingCreateDto;
import com.testtask.booking_system.dto.response.BookingResponseDto;
import com.testtask.booking_system.entity.Booking;
import com.testtask.booking_system.entity.Unit;
import com.testtask.booking_system.entity.User;
import com.testtask.booking_system.enums.BookingStatus;
import com.testtask.booking_system.enums.EventLogAction;
import com.testtask.booking_system.exception.BookingCancellationNotAllowedException;
import com.testtask.booking_system.exception.MaxBookingTermException;
import com.testtask.booking_system.exception.ResourceNotFountException;
import com.testtask.booking_system.exception.UserNotOwnerBookingException;
import com.testtask.booking_system.mapper.BookingMapper;
import com.testtask.booking_system.props.BookingProps;
import com.testtask.booking_system.props.PricingProps;
import com.testtask.booking_system.repository.BookingRepository;
import com.testtask.booking_system.repository.UnitRepository;
import com.testtask.booking_system.repository.UserRepository;
import com.testtask.booking_system.util.MarkupUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
  private final AuditService auditService;
  private final PricingProps pricingProps;
  private final BookingProps bookingProps;

  public ResponseEntity<BookingResponseDto> createBooking(Long userId, Long unitId, BookingCreateDto bookingCreateDto) {
    if (ChronoUnit.DAYS.between(bookingCreateDto.checkInOn(), bookingCreateDto.checkOutOn())
        > bookingProps.maxBookingTerm()) {
      throw new MaxBookingTermException(bookingProps.maxBookingTerm());
    }
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFountException(User.class.getSimpleName(), unitId));
    Unit unit = unitRepository.findById(unitId)
        .orElseThrow(() -> new ResourceNotFountException(Unit.class.getSimpleName(), unitId));
    Booking booking = bookingMapper.fromBookingCreateDto(bookingCreateDto);
    booking.setUser(user);
    booking.setUnit(unit);
    BigDecimal totalCost =
        MarkupUtils.countTotalCostForPeriod(bookingCreateDto.checkInOn(), bookingCreateDto.checkOutOn(),
            unit.getPricePerNight(), pricingProps.markupPercent());
    booking.setTotalCost(totalCost);
    booking.setExpiresAt(Instant.now().plus(bookingProps.expirationTime()));
    booking = bookingRepository.save(booking);
    BookingResponseDto bookingResponseDto = bookingMapper.toBookingResponseDto(booking);
    auditService.log(Booking.class, booking.getId(), EventLogAction.CREATE_BOOKING.name(), booking);

    return ResponseEntity.ok(bookingResponseDto);
  }

  public ResponseEntity<Void> cancelBooking(Long userId, Long unitId, Long bookingId) {
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFountException(Booking.class.getSimpleName(), bookingId));
    if (!booking.getUser().getId().equals(userId)) {
      throw new UserNotOwnerBookingException(userId, bookingId);
    }
    if (!booking.getUnit().getId().equals(unitId)) {
      throw new UserNotOwnerBookingException(userId, unitId);
    }
    if (!booking.getStatus().equals(BookingStatus.RESERVED)) {
      throw new BookingCancellationNotAllowedException(booking.getStatus());
    }
    booking.setStatus(BookingStatus.CANCELED);
    bookingRepository.save(booking);
    auditService.log(Booking.class, bookingId, EventLogAction.BOOKING_CANCELED.name(), booking);

    return ResponseEntity.noContent().build();
  }
}
