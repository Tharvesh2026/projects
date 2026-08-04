package com.opensourceapi.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotBlank(message = "Type is required")
    private String type; // CREDIT, DEBIT

    @NotBlank(message = "Status is required")
    private String status; // PENDING, COMPLETED, FAILED

    private String description;

    private LocalDateTime timestamp;
}
