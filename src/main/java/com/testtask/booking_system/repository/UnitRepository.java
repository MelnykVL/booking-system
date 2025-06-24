package com.testtask.booking_system.repository;

import com.testtask.booking_system.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

  Page<Unit> findAllByOwnerId(Long ownerId, Pageable pageable);
}
