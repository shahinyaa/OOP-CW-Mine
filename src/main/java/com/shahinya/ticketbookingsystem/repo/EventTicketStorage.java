package com.shahinya.ticketbookingsystem.repo;

import com.shahinya.ticketbookingsystem.entity.EventTicket;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventTicketStorage {

    private final List<EventTicket> ticketStorage = new ArrayList<>();

    public List<EventTicket> findAll() {
        return new ArrayList<>(ticketStorage);
    }

    public void save(EventTicket ticket) {
        ticketStorage.add(ticket);
    }

    public EventTicket findById(int ticketId) {
        return ticketStorage.stream()
                .filter(ticket -> ticket.getTicketId() == ticketId)
                .findFirst()
                .orElse(null);
    }

    public void deleteById(int ticketId) {
        ticketStorage.removeIf(ticket -> ticket.getTicketId() == ticketId);
    }
}

