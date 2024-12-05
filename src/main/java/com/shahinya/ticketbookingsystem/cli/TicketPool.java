package com.shahinya.ticketbookingsystem.cli;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> tickets = new LinkedList<>();
    private final int maxCapacity;
    private final int totalTickets;

    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
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
