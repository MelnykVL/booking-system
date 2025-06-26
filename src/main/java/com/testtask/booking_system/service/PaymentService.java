package com.testtask.booking_system.service;

import com.testtask.booking_system.dto.request.PaymentCreateDto;
import com.testtask.booking_system.entity.Booking;
import com.testtask.booking_system.entity.Payment;
import com.testtask.booking_system.entity.Unit;
import com.testtask.booking_system.entity.User;
import com.testtask.booking_system.enums.BookingStatus;
import com.testtask.booking_system.enums.EventLogAction;
import com.testtask.booking_system.enums.PaymentStatus;
import com.testtask.booking_system.exception.PaymentNotAllowedException;
import com.testtask.booking_system.exception.ResourceNotFountException;
import com.testtask.booking_system.mapper.PaymentMapper;
import com.testtask.booking_system.repository.BookingRepository;
import com.testtask.booking_system.repository.PaymentRepository;
import com.testtask.booking_system.repository.UnitRepository;
import com.testtask.booking_system.repository.UserRepository;
import java.util.Map;
import java.util.random.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

  private final AuditService auditService;
  private final PaymentRepository paymentRepository;
  private final UserRepository userRepository;
  private final UnitRepository unitRepository;
  private final BookingRepository bookingRepository;
  private final PaymentMapper paymentMapper;

  public ResponseEntity<String> payForBooking(Long userId, Long unitId, Long bookingId,
      PaymentCreateDto paymentCreateDto) {
    RandomGenerator rnd = RandomGenerator.getDefault();
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFountException(User.class.getSimpleName(), unitId));
    unitRepository.findById(unitId)
        .orElseThrow(() -> new ResourceNotFountException(Unit.class.getSimpleName(), unitId));
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFountException(Unit.class.getSimpleName(), unitId));
    if (!BookingStatus.RESERVED.equals(booking.getStatus())) {
      throw new PaymentNotAllowedException();
    }
    Payment payment = paymentMapper.fromUserCreateDto(paymentCreateDto);
    payment.setUser(user);
    payment.setBooking(booking);
    int random = rnd.nextInt(0, 100);
    EventLogAction eventLogAction;
    if (random < 75) {
      payment.setStatus(PaymentStatus.SUCCEEDED);
      eventLogAction = EventLogAction.PAYMENT_SUCCEEDED;
    } else {
      payment.setStatus(PaymentStatus.FAILED);
      eventLogAction = EventLogAction.PAYMENT_FAILED;
    }
    paymentRepository.save(payment);
    auditService.log(Payment.class, payment.getId(), eventLogAction.name(),
        Map.of("amount", payment.getAmount()));

    return ResponseEntity.status(HttpStatus.OK).body(PaymentStatus.SUCCEEDED.name());
  }
}
