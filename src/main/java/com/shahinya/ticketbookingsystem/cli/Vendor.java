package com.shahinya.ticketbookingsystem.cli;

import java.math.BigDecimal;

public class Vendor implements Runnable {
    private static final Object lock = new Object();
    private static int currentTurn = 1;
    private static int ticketIdCounter = 1;

    private final int vendorId;
    private final int ticketReleaseRate;
    private final TicketPool ticketPool;
    private final int totalVendors;
    private String eventName;

    public Vendor(int vendorId, int ticketReleaseRate, TicketPool ticketPool, int totalVendors, String eventName) {
        this.vendorId = vendorId;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
        this.totalVendors = totalVendors;
        this.eventName = eventName;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(ticketReleaseRate * 1000); // Simulate time between releases

                synchronized (lock) {
                    while (currentTurn != vendorId) {
                        lock.wait();
                    }

                    if (ticketIdCounter > ticketPool.getTotalTickets() || ticketPool.isFull()) {
                        lock.notifyAll();
                        break;
                    }

                    Ticket ticket = new Ticket(ticketIdCounter,eventName, new BigDecimal("2500"), "2024-12-25", "16:30");
                    ticketPool.addTicket(ticket);
                    System.out.println("Vendor-" + vendorId + " added Ticket-" + ticket.getTicketId());

                    currentTurn = (currentTurn % totalVendors) + 1;
                    ticketIdCounter++;
                    lock.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Vendor-" + vendorId + " interrupted.");
        }
    }
}
