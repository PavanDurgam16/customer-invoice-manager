package com.devneopavan.customer_invoice_manager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TwoFactorVerificationDTO {

    private Long id;

    @NotNull
    @Size(max = 15)
    private String code;

    private LocalDateTime expirationData;

    private Long user;

}
