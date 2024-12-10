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

    public String getEventName() {
        return eventName;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + ticketId + ", " +
                "Event Name: " + eventName + ", " +
                "Price: Rs." + ticketPrice + ", " +
                "Event Date: " + eventDate + ", " +
                "Event Time: " + eventTime;
    }
}
