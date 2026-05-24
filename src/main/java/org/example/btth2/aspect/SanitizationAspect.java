package org.example.btth2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SanitizationAspect {

    @Around("execution(* org.example.btth2.service.TicketService.bookTicket(..)) && args(flightNumber, passengerName)")
    public Object sanitizePassengerName(ProceedingJoinPoint joinPoint, String flightNumber, String passengerName) throws Throwable {
        String sanitized = passengerName.trim().toUpperCase();

        Object[] args = joinPoint.getArgs();
        args[1] = sanitized;

        return joinPoint.proceed(args);
    }
}
