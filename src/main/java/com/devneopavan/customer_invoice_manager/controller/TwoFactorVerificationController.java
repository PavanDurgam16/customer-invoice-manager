package com.devneopavan.customer_invoice_manager.controller;

import com.devneopavan.customer_invoice_manager.dto.TwoFactorVerificationDTO;
import com.devneopavan.customer_invoice_manager.service.TwoFactorVerificationService;
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
@RequestMapping(value = "/api/twoFactorVerifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class TwoFactorVerificationController {

    private final TwoFactorVerificationService twoFactorVerificationService;


    @GetMapping
    public ResponseEntity<List<TwoFactorVerificationDTO>> getAllTwoFactorVerifications() {
        return ResponseEntity.ok(twoFactorVerificationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TwoFactorVerificationDTO> getTwoFactorVerification(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(twoFactorVerificationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createTwoFactorVerification(
            @RequestBody @Valid final TwoFactorVerificationDTO twoFactorVerificationDTO) {
        final Long createdId = twoFactorVerificationService.create(twoFactorVerificationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateTwoFactorVerification(
            @PathVariable(name = "id") final Long id,
            @RequestBody @Valid final TwoFactorVerificationDTO twoFactorVerificationDTO) {
        twoFactorVerificationService.update(id, twoFactorVerificationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTwoFactorVerification(
            @PathVariable(name = "id") final Long id) {
        twoFactorVerificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
