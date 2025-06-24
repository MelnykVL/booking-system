package com.testtask.booking_system;

import com.testtask.booking_system.props.PricingProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PricingProps.class)
public class BookingSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookingSystemApplication.class, args);
  }
}
