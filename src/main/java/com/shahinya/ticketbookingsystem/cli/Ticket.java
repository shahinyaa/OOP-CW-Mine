package com.shahinya.ticketbookingsystem.cli;

import java.math.BigDecimal;

public class Ticket {
    private final int ticketId;
    private final String eventName;
    private final BigDecimal ticketPrice;
    private final String eventDate;
    private final String eventTime;

    public Ticket(int ticketId, String eventName, BigDecimal ticketPrice, String eventDate, String eventTime) {
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    public int getTicketId() {
        return ticketId;
    }

    @Override
    public String toString() {
        return "Ticket Details:\n" +
                "Ticket ID: " + ticketId + "\n" +
                "Event Name: " + eventName + "\n" +
                "Price: $" + ticketPrice + "\n" +
                "Event Date: " + eventDate + "\n" +
                "Event Time: " + eventTime;
    }
}

