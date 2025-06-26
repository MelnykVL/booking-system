package com.testtask.booking_system;

import com.github.javafaker.Faker;
import com.testtask.booking_system.entity.Unit;
import com.testtask.booking_system.entity.User;
import com.testtask.booking_system.enums.AccommodationType;
import com.testtask.booking_system.repository.UnitRepository;
import com.testtask.booking_system.repository.UserRepository;
import java.math.BigDecimal;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

  private final UnitRepository unitRepository;
  private final UserRepository userRepository;

  @Override
  public void run(ApplicationArguments args) {
    if (unitRepository.count() >= 100) {
      log.info("Database already contains 100 or more units. Skipping random data generation.");
      return;
    }

    User user = userRepository.findById(1L)
        .orElseThrow(() -> new IllegalStateException("Default user with ID 1 not found. Check Liquibase scripts."));
    log.info("Generating 90 random units...");
    Faker faker = new Faker();

    var units = IntStream.range(0, 90)
        .mapToObj(i -> {
          Unit unit = new Unit();
          unit.setOwner(user);
          unit.setNumberOfRooms(faker.number().randomDigitNotZero());
          unit.setFloor(faker.number().randomDigitNotZero());
          unit.setPricePerNight(BigDecimal.valueOf(faker.number().randomDigitNotZero()));
          unit.setDescription(faker.lorem().word());
          unit.setType(faker.options().option(AccommodationType.class));
          return unit;
        })
        .toList();

    unitRepository.saveAll(units);
    log.info("Successfully generated and saved 90 random units.");
  }
}