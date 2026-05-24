package org.example.btth2.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "error_logs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private String methodName;

    @Column(columnDefinition = "TEXT")
    private String exceptionMessage;
}