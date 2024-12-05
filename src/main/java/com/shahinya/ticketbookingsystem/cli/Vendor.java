package com.shahinya.ticketbookingsystem.cli;

import java.math.BigDecimal;

public class Vendor implements Runnable {
    private static final Object lock = new Object(); // Lock for turn-based coordination
    private static int currentTurn = 1; // Tracks which vendor's turn it is
    private static int ticketIdCounter = 1; // Shared ticket ID counter

    private final int vendorId;         // Unique ID for the vendor
    private final int ticketReleaseRate; // Frequency of ticket release (in seconds)
    private final TicketPool ticketPool; // Shared TicketPool resource
    private final int totalVendors;     // Total number of vendors

    public Vendor(int vendorId, int ticketReleaseRate, TicketPool ticketPool, int totalVendors) {
        this.vendorId = vendorId;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
        this.totalVendors = totalVendors;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (lock) {
                    // Wait for this vendor's turn
                    while (currentTurn != vendorId) {
                        lock.wait();
                    }

                    // Check if there are more tickets to add and if the pool is not full
                    if (ticketIdCounter > ticketPool.getTotalTickets() || ticketPool.isFull()) {
                        // End processing if all tickets are added or pool is full
                        lock.notifyAll(); // Notify other threads
                        break;
                    }

                    // Create a new Ticket with the unique ID
                    Ticket ticket = new Ticket(ticketIdCounter, "Simple Event", new BigDecimal("1000"), "2024-12-04", "18:00");

                    // Add the ticket to the TicketPool
                    ticketPool.addTicket(ticket);
                    System.out.println("Vendor-" + vendorId + " added Ticket-" + ticket.getTicketId());

                    // Update the turn to the next vendor
                    currentTurn = (currentTurn % totalVendors) + 1;

                    // Notify all waiting threads
                    lock.notifyAll();

                    // Increment ticket ID
                    ticketIdCounter++;
                }

                // Sleep for the specified ticket release rate
                Thread.sleep(ticketReleaseRate * 1000); // Convert seconds to milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interruption
                System.out.println("Vendor-" + vendorId + " interrupted.");
                break;
            }
        }
    }
}
