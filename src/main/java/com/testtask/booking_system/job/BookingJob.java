package com.testtask.booking_system.job;

import com.testtask.booking_system.dto.notification.BookingNotificationDto;
import com.testtask.booking_system.entity.Booking;
import com.testtask.booking_system.enums.BookingStatus;
import com.testtask.booking_system.enums.EventLogAction;
import com.testtask.booking_system.repository.BookingRepository;
import com.testtask.booking_system.service.AuditService;
import com.testtask.booking_system.service.EmailBookingNotificationService;
import com.testtask.booking_system.view.ExpiredBookingView;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
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
  private final AuditService auditService;
  private final EmailBookingNotificationService emailBookingNotificationService;

  @Scheduled(cron = "0 0 0 * * *")
  public void completeFinishedBookings() {
    List<ExpiredBookingView> completedBookingViews =
        bookingRepository.completeFinishedBookings(BookingStatus.COMPLETED, BookingStatus.PAID, LocalDate.now());
    if (!completedBookingViews.isEmpty()) {
      auditService.logBatch(Booking.class, EventLogAction.BOOKING_COMPLETED.name(),
          Collections.singletonList(completedBookingViews));
      log.info("Completed {} finished bookings", completedBookingViews.size());
    }
  }

  @Scheduled(cron = "0 * * * * *")
  public void expireOldBookings() {
    List<ExpiredBookingView> expiredBookingViews =
        bookingRepository.expireOldBooking(BookingStatus.EXPIRED, BookingStatus.RESERVED, Instant.now());
    if (!expiredBookingViews.isEmpty()) {
      auditService.logBatch(Booking.class, EventLogAction.BOOKING_EXPIRED.name(),
          Collections.singletonList(expiredBookingViews));
      log.info("Expired {} bookings", expiredBookingViews.size());
      expiredBookingViews.forEach(ebv -> {
        BookingNotificationDto bookingNotificationDto = new BookingNotificationDto();
        bookingNotificationDto.setUserId(ebv.getUserId());
        bookingNotificationDto.setUnitId(ebv.getUnitId());
        bookingNotificationDto.setBookingId(ebv.getId());
        emailBookingNotificationService.sendNotification(bookingNotificationDto);
      });
    }
  }
}
