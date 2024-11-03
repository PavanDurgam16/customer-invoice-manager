package com.devneopavan.customer_invoice_manager.controller;

import com.devneopavan.customer_invoice_manager.dto.ResetPasswordVerificationDTO;
import com.devneopavan.customer_invoice_manager.service.ResetPasswordVerificationService;
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
@RequestMapping(value = "/api/resetPasswordVerifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResetPasswordVerificationController {

    private final ResetPasswordVerificationService resetPasswordVerificationService;

    @GetMapping
    public ResponseEntity<List<ResetPasswordVerificationDTO>> getAllResetPasswordVerifications() {
        return ResponseEntity.ok(resetPasswordVerificationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResetPasswordVerificationDTO> getResetPasswordVerification(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(resetPasswordVerificationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createResetPasswordVerification(
            @RequestBody @Valid final ResetPasswordVerificationDTO resetPasswordVerificationDTO) {
        final Long createdId = resetPasswordVerificationService.create(resetPasswordVerificationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateResetPasswordVerification(
            @PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ResetPasswordVerificationDTO resetPasswordVerificationDTO) {
        resetPasswordVerificationService.update(id, resetPasswordVerificationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteResetPasswordVerification(
            @PathVariable(name = "id") final Long id) {
        resetPasswordVerificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
