package com.devneopavan.customer_invoice_manager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String type;

    @Size(max = 255)
    private String description;

}
