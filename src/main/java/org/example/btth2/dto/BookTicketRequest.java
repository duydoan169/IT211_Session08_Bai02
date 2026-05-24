package org.example.btth2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BookTicketRequest {

    @NotBlank(message = "Số hiệu chuyến bay không được để trống")
    private String flightNumber;

    @NotBlank(message = "Tên hành khách không được để trống")
    private String passengerName;
}