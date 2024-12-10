package com.shahinya.ticketbookingsystem.service;

import com.shahinya.ticketbookingsystem.entity.EventTicket;
import com.shahinya.ticketbookingsystem.repo.EventTicketStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTicketManager {

    private final EventTicketStorage ticketStorage;

    @Autowired
    public EventTicketManager(EventTicketStorage ticketStorage) {
        this.ticketStorage = ticketStorage;
    }

    public List<EventTicket> getAllTickets() {
        return ticketStorage.findAll();
    }

    public String addTicket(EventTicket ticket) {
        ticketStorage.save(ticket);
        return "Ticket added successfully.";
    }

    public EventTicket getTicketById(int ticketId) {
        return ticketStorage.findById(ticketId);
    }

    public String removeTicket(int ticketId) {
        EventTicket ticket = ticketStorage.findById(ticketId);
        if (ticket != null) {
            ticketStorage.deleteById(ticketId);
            return "Ticket removed successfully.";
        }
        return "Ticket not found.";
    }
}
