package com.devneopavan.customer_invoice_manager.controller;

import com.devneopavan.customer_invoice_manager.dto.UserEventDTO;
import com.devneopavan.customer_invoice_manager.service.UserEventService;
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
@RequestMapping(value = "/api/userEvents", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEventController {

    private final UserEventService userEventService;


    @GetMapping
    public ResponseEntity<List<UserEventDTO>> getAllUserEvents() {
        return ResponseEntity.ok(userEventService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEventDTO> getUserEvent(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(userEventService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUserEvent(
            @RequestBody @Valid final UserEventDTO userEventDTO) {
        final Long createdId = userEventService.create(userEventDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUserEvent(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final UserEventDTO userEventDTO) {
        userEventService.update(id, userEventDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUserEvent(@PathVariable(name = "id") final Long id) {
        userEventService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
