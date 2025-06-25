package com.testtask.booking_system.job;

import com.testtask.booking_system.repository.BookingRepository;
import java.time.Instant;
import java.time.LocalDate;
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

  private final BookingRepository bookingRepository;

  @Scheduled(cron = "0 0 0 * * *")
  public void completeFinishedBookings() {
    int completedBookings = bookingRepository.completeFinishedBookings(LocalDate.now());
    if (completedBookings > 0) {
      log.info("Completed {} bookings", completedBookings);
    }
  }

  @Scheduled(cron = "0 * * * * *")
  public void expireOldBookings() {
    int expiredBookings = bookingRepository.expireOldBooking(Instant.now());
    if (expiredBookings > 0) {
      log.info("Completed {} bookings", expiredBookings);
    }
  }
}
