package com.testtask.booking_system.repository;

import com.testtask.booking_system.entity.Unit;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {

  Page<Unit> findAllByOwnerId(Long ownerId, Pageable pageable);

  @Query(nativeQuery = true, value = """
          SELECT COUNT(*) FROM booking_system.units u
           WHERE NOT EXISTS (
                 SELECT 1 FROM booking_system.bookings b
                  WHERE b.unit_id = u.id
                    AND b.status  IN ('RESERVED','PAID')
                    AND b.period && daterange(:availableFrom, :availableTo, '[)')
           )
      """)
  int countAvailableUnits(@Param("unitId") Long unitId, @Param("availableFrom") LocalDate availableFrom,
      @Param("availableTo") LocalDate availableTo);
}
