package com.testtask.booking_system.repository;

import com.testtask.booking_system.entity.Booking;
import com.testtask.booking_system.enums.BookingStatus;
import com.testtask.booking_system.view.ExpiredBookingView;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  @Modifying
  @Query(nativeQuery = true, value = """
      UPDATE booking_system.bookings SET status = CAST(:#{#completed?.name()} AS booking_status)
                             WHERE status = CAST(:#{#paid?.name()} AS booking_status) AND check_out_on <= :today
                                   RETURNING id, unit_id, user_id, check_in_on, check_out_on
      """)
  List<ExpiredBookingView> completeFinishedBookings(@Param("completed") BookingStatus completed,
      @Param("paid") BookingStatus paid, @Param("today") LocalDate today);

  @Query(nativeQuery = true, value = """
      UPDATE booking_system.bookings SET status = CAST(:#{#expired?.name()} AS booking_status)
                             WHERE status = CAST(:#{#reserved?.name()} AS booking_status) AND expires_at <= :cutoff
                                   RETURNING id, unit_id, user_id, check_in_on, check_out_on
      """)
  @Modifying
  List<ExpiredBookingView> expireOldBooking(@Param("expired") BookingStatus expired,
      @Param("reserved") BookingStatus reserved, @Param("cutoff") Instant cutoff);
}
