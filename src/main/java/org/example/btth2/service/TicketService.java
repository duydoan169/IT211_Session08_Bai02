package org.example.btth2.service;

import org.example.btth2.entity.Flight;
import org.example.btth2.entity.Ticket;
import org.example.btth2.repository.FlightRepository;
import org.example.btth2.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public Ticket bookTicket(String flightNumber, String passengerName) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay: " + flightNumber));

        if (flight.getAvailableSeats() <= 0) {
            throw new RuntimeException("Chuyến bay " + flightNumber + " đã hết chỗ");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Ticket ticket = Ticket.builder()
                .passengerName(passengerName)
                .flightId(flight.getId())
                .status("BOOKED")
                .build();

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé với ID: " + ticketId));

        if ("CANCELED".equals(ticket.getStatus())) {
            throw new RuntimeException("Vé ID " + ticketId + " đã bị hủy trước đó");
        }

        ticket.setStatus("CANCELED");
        return ticketRepository.save(ticket);
    }
}
