package com.devneopavan.customer_invoice_manager.controller;

import com.devneopavan.customer_invoice_manager.dto.AccountVerificationDTO;
import com.devneopavan.customer_invoice_manager.service.AccountVerificationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Data
@RequestMapping(value = "/api/accountVerifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountVerificationController {

    private final AccountVerificationService accountVerificationService;

    @GetMapping
    public ResponseEntity<List<AccountVerificationDTO>> getAllAccountVerifications() {
        return ResponseEntity.ok(accountVerificationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountVerificationDTO> getAccountVerification(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(accountVerificationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAccountVerification(
            @RequestBody @Valid final AccountVerificationDTO accountVerificationDTO) {
        final Long createdId = accountVerificationService.create(accountVerificationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAccountVerification(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final AccountVerificationDTO accountVerificationDTO) {
        accountVerificationService.update(id, accountVerificationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAccountVerification(
            @PathVariable(name = "id") final Long id) {
        accountVerificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
