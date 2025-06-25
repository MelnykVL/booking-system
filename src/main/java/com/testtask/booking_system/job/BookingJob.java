package com.testtask.booking_system.job;

import com.testtask.booking_system.entity.Booking;
import com.testtask.booking_system.enums.BookingStatus;
import com.testtask.booking_system.enums.EventLogAction;
import com.testtask.booking_system.repository.BookingRepository;
import com.testtask.booking_system.service.AuditService;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookingJob {

  private static final String NUMBER_OF_COMPLETED_FIELD = "numberOfCompleted";
  private static final String NUMBER_OF_EXPIRED_FIELD = "numberOfExpired";

  private final BookingRepository bookingRepository;
  private final AuditService auditService;

  @Scheduled(cron = "0 0 0 * * *")
  public void completeFinishedBookings() {
    int completedBookings =
        bookingRepository.completeFinishedBookings(BookingStatus.COMPLETED, BookingStatus.PAID, LocalDate.now());
    if (completedBookings > 0) {
      auditService.log(Booking.class, EventLogAction.BOOKING_COMPLETED.name(),
          Map.of(NUMBER_OF_COMPLETED_FIELD, completedBookings));
      log.info("Completed {} finished bookings", completedBookings);
    }
  }

  @Scheduled(cron = "0 * * * * *")
  public void expireOldBookings() {
    int expiredBookings =
        bookingRepository.expireOldBooking(BookingStatus.EXPIRED, BookingStatus.RESERVED, Instant.now());
    if (expiredBookings > 0) {
      auditService.log(Booking.class, EventLogAction.BOOKING_COMPLETED.name(),
          Map.of(NUMBER_OF_EXPIRED_FIELD, expiredBookings));
      log.info("Canceled {} expired bookings", expiredBookings);
    }
  }
}
