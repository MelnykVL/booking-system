package com.testtask.booking_system;

import com.testtask.booking_system.props.BookingProps;
import com.testtask.booking_system.props.PricingProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({PricingProps.class, BookingProps.class})
public class BookingSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookingSystemApplication.class, args);
  }
}
