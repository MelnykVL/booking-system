package com.testtask.booking_system;

import org.springframework.boot.SpringApplication;

public class TestBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(BookingSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
