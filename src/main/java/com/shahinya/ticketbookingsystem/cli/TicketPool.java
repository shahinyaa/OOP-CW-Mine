package com.shahinya.ticketbookingsystem.cli;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Ticket> tickets = new LinkedList<>(); // Use Ticket type instead of String
    private final int maxCapacity; // Maximum capacity of the ticket pool
    private final int totalTickets; // Total number of tickets
    private int ticketsSold = 0;   // Counter for tickets sold

    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
    }

    // Method for vendors to add tickets to the pool
    public synchronized void addTicket(Ticket ticket) throws InterruptedException {
        while (tickets.size() >= maxCapacity) { // Wait if the queue is full
            wait();
        }
        tickets.add(ticket);
        notifyAll(); // Notify customers waiting to buy tickets
    }

    // Method for customers to remove tickets from the pool
    public synchronized Ticket removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) { // Wait if the queue is empty
            wait();
        }
        Ticket ticket = tickets.poll();
        ticketsSold++;
        notifyAll(); // Notify vendors waiting to add tickets
        return ticket;
    }

    // Method to check if the pool is full
    public synchronized boolean isFull() {
        return tickets.size() >= maxCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public synchronized boolean isEmpty() {
        return tickets.isEmpty();
    }
}
