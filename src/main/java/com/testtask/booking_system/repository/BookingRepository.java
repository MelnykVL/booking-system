package com.testtask.booking_system.repository;

import com.testtask.booking_system.entity.Booking;
import com.testtask.booking_system.enums.BookingStatus;
import java.time.Instant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  @Modifying
  @Query("UPDATE Booking b SET b.status = :expiredStatus WHERE b.status = :reservedStatus AND b.expiresAt <= :cutoff")
  int expireOldBooking(BookingStatus expiredStatus, BookingStatus reservedStatus, Instant cutoff);
}
