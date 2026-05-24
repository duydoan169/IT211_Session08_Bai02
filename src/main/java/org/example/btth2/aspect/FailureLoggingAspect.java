package org.example.btth2.aspect;

import org.example.btth2.entity.ErrorLog;
import org.example.btth2.repository.ErrorLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class FailureLoggingAspect {

    private final ErrorLogRepository errorLogRepository;

    @AfterThrowing(
            pointcut = "execution(* org.example.btth2.service.TicketService.*(..))",
            throwing = "ex"
    )
    public void logFailure(JoinPoint joinPoint, Exception ex) {
        ErrorLog errorLog = ErrorLog.builder()
                .timestamp(LocalDateTime.now())
                .methodName(joinPoint.getSignature().getName())
                .exceptionMessage(ex.getMessage())
                .build();
        errorLogRepository.save(errorLog);
    }
}
