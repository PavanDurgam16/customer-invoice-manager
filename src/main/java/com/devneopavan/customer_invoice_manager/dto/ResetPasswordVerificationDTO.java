package com.devneopavan.customer_invoice_manager.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResetPasswordVerificationDTO {

    private Long id;

    @Size(max = 255)
    private String url;

    private LocalDateTime expirationDate;

    private Long user;

}
