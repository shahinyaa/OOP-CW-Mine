package com.shahinya.ticketbookingsystem;

import com.shahinya.ticketbookingsystem.cli.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketbookingsystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TicketbookingsystemApplication.class, args);

	}

	public void run(String... args) throws Exception {
		Main.main(args);
	}

}