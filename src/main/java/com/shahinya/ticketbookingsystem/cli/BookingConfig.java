package com.shahinya.ticketbookingsystem.cli;

public class BookingConfig {
    private String eventName;
    private int totalTickets;
    private int maxCapacity;
    private int ticketReleaseRateSeconds;
    private int vendorCount;
    private int customerCount;
    private int customerRetrievalRateSeconds;
    private int customerTicketQuantity;

    public BookingConfig(String eventName, int totalTickets, int maxCapacity,
                         int ticketReleaseRateSeconds, int vendorCount,
                         int customerCount, int customerRetrievalRateSeconds,
                         int customerTicketQuantity) {
        this.eventName = eventName;
        this.totalTickets = totalTickets;
        this.maxCapacity = maxCapacity;
        this.ticketReleaseRateSeconds = ticketReleaseRateSeconds;
        this.vendorCount = vendorCount;
        this.customerCount = customerCount;
        this.customerRetrievalRateSeconds = customerRetrievalRateSeconds;
        this.customerTicketQuantity = customerTicketQuantity;
    }

    // Getters for each field
    public String getEventName() {
        return eventName;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getTicketReleaseRateSeconds() {
        return ticketReleaseRateSeconds;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public int getCustomerRetrievalRateSeconds() {
        return customerRetrievalRateSeconds;
    }

    public int getCustomerTicketQuantity() {
        return customerTicketQuantity;
    }
}
