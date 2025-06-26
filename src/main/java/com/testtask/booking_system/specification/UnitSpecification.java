package com.testtask.booking_system.specification;

import com.testtask.booking_system.entity.Booking;
import com.testtask.booking_system.entity.Unit;
import com.testtask.booking_system.enums.AccommodationType;
import com.testtask.booking_system.enums.BookingStatus;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class UnitSpecification {

  public Specification<Unit> hasNumberOfRooms(Integer numberOfRooms) {
    return (root, q, cb) -> {
      if (numberOfRooms == null) {
        return null;
      }
      return cb.equal(root.get(Unit.Fields.NUMBER_OF_ROOM), numberOfRooms);
    };
  }

  public Specification<Unit> hasFloor(Integer floor) {
    return (root, q, cb) -> {
      if (floor == null) {
        return null;
      }
      return cb.equal(root.get(Unit.Fields.FLOOR), floor);
    };
  }

  public Specification<Unit> hasType(AccommodationType type) {
    return (root, q, cb) -> {
      if (type == null) {
        return null;
      }
      return cb.equal(root.get(Unit.Fields.TYPE), type);
    };
  }

  public Specification<Unit> betweenPrice(BigDecimal minTotalPrice, BigDecimal maxTotalPrice,
      LocalDate availableFrom,
      LocalDate availableTo) {
    return (root, q, cb) -> {
      long nights = ChronoUnit.DAYS.between(availableFrom, availableTo);
      if (nights <= 0) {
        return cb.disjunction();
      }
      Expression<BigDecimal> total = cb.prod(root.get(Unit.Fields.PRICE_PER_NIGHT), BigDecimal.valueOf(nights));
      Predicate predicate = cb.conjunction();
      if (minTotalPrice != null) {
        predicate = cb.and(predicate, cb.ge(total, minTotalPrice));
      }
      if (maxTotalPrice != null) {
        predicate = cb.and(predicate, cb.le(total, maxTotalPrice));
      }

      return predicate;
    };
  }

  public Specification<Unit> availableBetween(LocalDate availableFrom, LocalDate availableTo) {
    return (root, q, cb) -> {
      Subquery<Long> sq = q.subquery(Long.class);
      Root<Booking> b = sq.from(Booking.class);

      String sql = "period && daterange";

      sq.select(cb.literal(1L))
          .where(cb.equal(b.get(Booking.Fields.UNIT).get(Booking.Fields.ID), root.get(Booking.Fields.ID)),
              b.get(Booking.Fields.STATUS).in(BookingStatus.RESERVED, BookingStatus.PAID),
              cb.isTrue(
                  cb.function(sql, Boolean.class, cb.literal(availableFrom), cb.literal(availableTo), cb.literal("[)"))));

      return cb.not(cb.exists(sq));
    };
  }
}
