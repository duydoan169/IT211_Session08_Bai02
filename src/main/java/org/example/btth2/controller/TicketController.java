package org.example.btth2.controller;

import org.example.btth2.dto.BookTicketRequest;
import org.example.btth2.entity.Ticket;
import org.example.btth2.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@Valid @RequestBody BookTicketRequest request) {
        Ticket ticket = ticketService.bookTicket(request.getFlightNumber(), request.getPassengerName());
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/cancel/{ticketId}")
    public ResponseEntity<Ticket> cancelTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.cancelTicket(ticketId);
        return ResponseEntity.ok(ticket);
    }
}
