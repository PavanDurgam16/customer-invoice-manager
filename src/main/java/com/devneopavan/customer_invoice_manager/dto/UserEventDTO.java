package com.devneopavan.customer_invoice_manager.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserEventDTO {

    private Long id;

    @Size(max = 100)
    private String device;

    @Size(max = 100)
    private String ipAddress;

    private LocalDateTime createdAt;

    private Long user;

    private Long event;

}
