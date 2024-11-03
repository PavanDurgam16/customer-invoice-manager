package com.devneopavan.customer_invoice_manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Size(max = 120)
    private String email;

    @Size(max = 255)
    private String password;

    @Size(max = 255)
    private String address;

    @NotNull
    @Size(max = 50)
    private String phone;

    @NotNull
    @Size(max = 60)
    private String title;

    @NotNull
    @Size(max = 50)
    private String bio;

    @NotNull
    @Size(max = 255)
    private String imageUrl;

    private Boolean enabled;

    @JsonProperty("isNotLocked")
    private Boolean isNotLocked;

    @JsonProperty("isUsingMfa")
    private Boolean isUsingMfa;

    private LocalDateTime createdAt;

}
