package com.shahinya.ticketbookingsystem.cli;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Check if the user wants to load the last configuration
        BookingConfig config;
        System.out.print("Would you like to load the last configuration? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            config = BookingConfigManager.loadConfig();
            if (config != null) {
                System.out.println("Configuration loaded successfully:");
                System.out.println("Event Name: " + config.getEventName());
                System.out.println("Total Tickets: " + config.getTotalTickets());
                System.out.println("Max Capacity: " + config.getMaxCapacity());
                System.out.println("Ticket Release Rate (seconds): " + config.getTicketReleaseRateSeconds());
                System.out.println("Vendor Count: " + config.getVendorCount());
                System.out.println("Customer Count: " + config.getCustomerCount());
                System.out.println("Customer Retrieval Rate (seconds): " + config.getCustomerRetrievalRateSeconds());
                System.out.println("Customer Ticket Quantity: " + config.getCustomerTicketQuantity());
            } else {
                System.out.println("No configuration file found. Continuing with new configuration.");
                config = getNewConfig(scanner);
                BookingConfigManager.saveConfig(config);
            }
        } else {
            config = getNewConfig(scanner);
            BookingConfigManager.saveConfig(config);
        }

        // Initialize the ticket pool
        TicketPool ticketPool = new TicketPool(config.getMaxCapacity(), config.getTotalTickets(), config.getEventName());

        // Create and start vendor threads
        Thread[] vendorThreads = new Thread[config.getVendorCount()];
        for (int i = 0; i < config.getVendorCount(); i++) {
            Vendor vendor = new Vendor(i + 1, config.getTicketReleaseRateSeconds(), ticketPool, config.getVendorCount(), config.getEventName());
            vendorThreads[i] = new Thread(vendor, "Vendor-" + (i + 1));
            vendorThreads[i].start();
        }

        // Create and start customer threads
        Thread[] customerThreads = new Thread[config.getCustomerCount()];
        for (int i = 0; i < config.getCustomerCount(); i++) {
            Customer customer = new Customer(i + 1, ticketPool, config.getCustomerRetrievalRateSeconds(), config.getCustomerTicketQuantity());
            customerThreads[i] = new Thread(customer, "Customer-" + (i + 1));
            customerThreads[i].start();
        }

        // Wait for all threads to complete
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
    }

    private static BookingConfig getNewConfig(Scanner scanner) {
        System.out.println("Enter your Event name to be hosted: ");
        String eventName = scanner.nextLine();

        // Input for total tickets with error handling
        int totalTickets;
        while (true) {
            try {
                System.out.print("Enter total number of tickets: ");
                totalTickets = Integer.parseInt(scanner.nextLine());
                if (totalTickets <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer greater than zero.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer number.");
            }
        }

        // Input for maximum pool capacity with error handling
        int maxCapacity;
        while (true) {
            try {
                System.out.print("Enter max capacity of the pool: ");
                maxCapacity = Integer.parseInt(scanner.nextLine());

                if (maxCapacity <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer greater than zero.");
                } else if (maxCapacity > totalTickets) {
                    System.out.println("Error: Max capacity of the pool cannot be greater than total number of tickets. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer number.");
            }
        }

        // Input for ticket release rate
        int ticketReleaseRateSeconds;
        while (true) {
            try {
                System.out.print("Enter ticket release rate (seconds per ticket): ");
                ticketReleaseRateSeconds = Integer.parseInt(scanner.nextLine());
                if (ticketReleaseRateSeconds <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer greater than zero.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer number.");
            }
        }

        // Input for number of vendors
        int vendorCount;
        while (true) {
            try {
                System.out.print("Enter number of vendors: ");
                vendorCount = Integer.parseInt(scanner.nextLine());
                if (vendorCount <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer greater than zero.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer number.");
            }
        }

        // Input for number of customers
        int customerCount;
        while (true) {
            try {
                System.out.print("Enter number of customers: ");
                customerCount = Integer.parseInt(scanner.nextLine());
                if (customerCount <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer greater than zero.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer number.");
            }
        }

        // Input for customer retrieval rate
        int customerRetrievalRateSeconds;
        while (true) {
            try {
                System.out.print("Enter customer retrieval rate (seconds per ticket): ");
                customerRetrievalRateSeconds = Integer.parseInt(scanner.nextLine());
                if (customerRetrievalRateSeconds <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer greater than zero.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer number.");
            }
        }

        // Input for quantity of tickets each customer will buy
        int customerTicketQuantity;
        while (true) {
            try {
                System.out.print("Enter quantity of tickets each customer will buy: ");
                customerTicketQuantity = Integer.parseInt(scanner.nextLine());
                if (customerTicketQuantity <= 0) {
                    System.out.println("Invalid input. Please enter a positive integer greater than zero.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer number.");
            }
        }

        System.out.println();

        return new BookingConfig(eventName, totalTickets, maxCapacity, ticketReleaseRateSeconds,
                vendorCount, customerCount, customerRetrievalRateSeconds, customerTicketQuantity);
    }
}
