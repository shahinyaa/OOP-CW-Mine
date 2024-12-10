package com.shahinya.ticketbookingsystem.cli;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> tickets = new LinkedList<>();
    private final int maxCapacity;
    private final int totalTickets;
    private final String eventName;

    public TicketPool(int maxCapacity, int totalTickets, String eventName) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        this.eventName = eventName;
    }

    public synchronized void addTicket(Ticket ticket) throws InterruptedException {
        while (tickets.size() >= maxCapacity) {
            wait();
        }
        tickets.add(ticket);
        notifyAll();
    }

    public synchronized Ticket removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) {
            wait();
        }
        Ticket ticket = tickets.poll();
        notifyAll();
        return ticket;
    }

    public synchronized boolean isFull() {
        return tickets.size() >= maxCapacity;
    }

    public synchronized boolean isEmpty() {
        return tickets.isEmpty();
    }

    public int getTotalTickets() {
        return totalTickets;
    }
}
