package com.shahinya.ticketbookingsystem.controller;

import com.shahinya.ticketbookingsystem.entity.EventTicket;
import com.shahinya.ticketbookingsystem.service.EventTicketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event-tickets")
public class EventTicketController {

    private final EventTicketManager ticketManager;

    @Autowired
    public EventTicketController(EventTicketManager ticketManager) {
        this.ticketManager = ticketManager;
    }

    @GetMapping
    public List<EventTicket> getAllTickets() {
        return ticketManager.getAllTickets();
    }

    @PostMapping
    public String addTicket(@RequestBody EventTicket ticket) {
        return ticketManager.addTicket(ticket);
    }

    @GetMapping("/{ticketId}")
    public EventTicket getTicketById(@PathVariable int ticketId) {
        return ticketManager.getTicketById(ticketId);
    }

    @DeleteMapping("/{ticketId}")
    public String removeTicket(@PathVariable int ticketId) {
        return ticketManager.removeTicket(ticketId);
    }
}

