package com.shahinya.ticketbookingsystem.cli;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take inputs from the user for configuration
        System.out.print("Enter total number of tickets: ");
        int totalTickets = scanner.nextInt();

        int maxCapacity;
        while (true) {
            System.out.print("Enter max capacity of the pool: ");
            maxCapacity = scanner.nextInt();

            if (maxCapacity > totalTickets) {
                System.out.println("Error: Max capacity of the pool cannot be greater than total number of tickets. Please try again.");
            } else {
                break;
            }
        }

        System.out.print("Enter ticket release rate (seconds per ticket): ");
        int ticketReleaseRateSeconds = scanner.nextInt();

        System.out.print("Enter number of vendors: ");
        int vendorCount = scanner.nextInt();

        System.out.print("Enter number of customers: ");
        int customerCount = scanner.nextInt();

        System.out.print("Enter customer retrieval rate (seconds per ticket): ");
        int customerRetrievalRateSeconds = scanner.nextInt();

        System.out.print("Enter quantity of tickets each customer will buy: ");
        int customerTicketQuantity = scanner.nextInt();

        // Create a ticket pool with the max capacity
        TicketPool ticketPool = new TicketPool(maxCapacity, totalTickets);

        // Create vendor threads
        Thread[] vendorThreads = new Thread[vendorCount];
        for (int i = 0; i < vendorCount; i++) {
            Vendor vendor = new Vendor(i + 1, ticketReleaseRateSeconds, ticketPool, vendorCount);
            vendorThreads[i] = new Thread(vendor, "Vendor-" + (i + 1));
            vendorThreads[i].start();
        }

        // Create customer threads
        Thread[] customerThreads = new Thread[customerCount];
        for (int i = 0; i < customerCount; i++) {
            Customer customer = new Customer(i + 1, ticketPool, customerRetrievalRateSeconds, customerTicketQuantity);
            customerThreads[i] = new Thread(customer, "Customer-" + (i + 1));
            customerThreads[i].start();
        }

        // Wait for all vendor and customer threads to finish
        try {
            for (Thread vendorThread : vendorThreads) {
                vendorThread.join();
            }
            for (Thread customerThread : customerThreads) {
                customerThread.join();
            }
            System.out.println("All tickets sold. System shutting down...");
        } catch (InterruptedException e) {
            System.out.println("System interrupted!");
        }

        scanner.close();
        System.exit(0);
    }
}