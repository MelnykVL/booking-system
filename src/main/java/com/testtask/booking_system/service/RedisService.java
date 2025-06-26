package com.testtask.booking_system.service;

import com.testtask.booking_system.repository.UnitRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

  private static final String AVAILABLE_UNITS_KEY = "numAvailUnits:%s:%s:%s";

  private final RedisTemplate<String, Integer> redis;
  private final UnitRepository unitRepository;

  public int countAvailableUnits(Long unitId, LocalDate availableFrom, LocalDate availableTo) {
    String key = String.format(AVAILABLE_UNITS_KEY, unitId, availableFrom, availableTo);
    int numberAvailableUnits = unitRepository.countAvailableUnits(unitId, availableFrom, availableTo);
    redis.opsForValue().set(key, numberAvailableUnits);

    return numberAvailableUnits;
  }

  public void addAvailableUnit(Long unitId, LocalDate availableFrom, LocalDate availableTo) {
    String key = String.format(AVAILABLE_UNITS_KEY, unitId, availableFrom, availableTo);
    redis.opsForValue().increment(key, 1);
  }

  public void removeAvailableUnits(Long unitId, LocalDate availableFrom, LocalDate availableTo) {
    String key = String.format(AVAILABLE_UNITS_KEY, unitId, availableFrom, availableTo);
    redis.opsForValue().decrement(key, 1);
  }
}
