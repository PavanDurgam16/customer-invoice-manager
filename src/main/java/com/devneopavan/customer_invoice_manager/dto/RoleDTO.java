package com.devneopavan.customer_invoice_manager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 255)
    private String permission;

}
