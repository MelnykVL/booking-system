package com.testtask.booking_system.repository;

import com.testtask.booking_system.entity.Booking;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  @Query("""
      UPDATE Booking b SET b.status = com.testtask.booking_system.enums.BookingStatus.COMPLETED 
      WHERE b.status = com.testtask.booking_system.enums.BookingStatus.PAID AND b.checkOutOn <= :today
      """)
  @Modifying
  int completeFinishedBookings(LocalDate today);

  @Query("""
      UPDATE Booking b SET b.status = com.testtask.booking_system.enums.BookingStatus.EXPIRED 
      WHERE b.status = com.testtask.booking_system.enums.BookingStatus.RESERVED AND b.expiresAt <= :cutoff
      """)
  @Modifying
  int expireOldBooking(Instant cutoff);
}
