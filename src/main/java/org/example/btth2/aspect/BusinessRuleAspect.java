package org.example.btth2.aspect;

import org.example.btth2.entity.Flight;
import org.example.btth2.entity.Ticket;
import org.example.btth2.repository.FlightRepository;
import org.example.btth2.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class BusinessRuleAspect {

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;

    @Before("execution(* org.example.btth2.service.TicketService.cancelTicket(..)) && args(ticketId)")
    public void checkCancellationWindow(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé với ID: " + ticketId));

        Flight flight = flightRepository.findById(ticket.getFlightId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin chuyến bay"));

        long hoursLeft = ChronoUnit.HOURS.between(LocalDateTime.now(), flight.getDepartureTime());

        if (hoursLeft < 24) {
            throw new RuntimeException("Không thể hủy vé. Chuyến bay khởi hành trong " + hoursLeft + " giờ nữa (tối thiểu phải còn 24 giờ)");
        }
    }
}