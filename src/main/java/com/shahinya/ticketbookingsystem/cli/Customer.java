package com.shahinya.ticketbookingsystem.cli;

public class Customer implements Runnable {
    private final int customerId;           // Unique ID for the customer
    private final TicketPool ticketPool;    // Shared TicketPool
    private final int customerRetrievalRate; // Time between each retrieval
    private final int quantity;             // Quantity of tickets the customer wants to purchase

    public Customer(int customerId, TicketPool ticketPool, int customerRetrievalRate, int quantity) {
        this.customerId = customerId;
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        for (int i = 0; i < quantity; i++) {
            try {
                // Wait for the retrieval rate before attempting to buy a ticket
                Thread.sleep(customerRetrievalRate * 1000);

                synchronized (ticketPool) {
                    if (ticketPool.isEmpty()) {
                        System.out.println("Customer-" + customerId + " is waiting for tickets...");
                    }

                    // Attempt to buy a ticket from the TicketPool
                    Ticket ticket = ticketPool.removeTicket();
                    System.out.println("Customer-" + customerId + " purchased Ticket: " + ticket);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interruption
                System.out.println("Customer-" + customerId + " interrupted.");
                break;
            }
        }
    }
}