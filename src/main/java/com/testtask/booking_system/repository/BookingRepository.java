package com.testtask.booking_system.repository;

import com.testtask.booking_system.entity.Booking;
import com.testtask.booking_system.enums.BookingStatus;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  @Query("UPDATE Booking b SET b.status = :completed WHERE b.status = :paid AND b.checkOutOn <= :today")
  @Modifying
  int completeFinishedBookings(BookingStatus completed, BookingStatus paid, LocalDate today);

  @Query("UPDATE Booking b SET b.status = :expired WHERE b.status = :reserved AND b.expiresAt <= :cutoff")
  @Modifying
  int expireOldBooking(BookingStatus expired, BookingStatus reserved, Instant cutoff);
}
