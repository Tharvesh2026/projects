package com.opensourceapi.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReceiptRequest {

    @NotBlank(message = "Merchant name is required")
    private String merchantName;

    @NotNull(message = "Total amount is required")
    private BigDecimal totalAmount;

    private BigDecimal taxAmount;

    private LocalDate date;

    private String category;

    private String imageUrl;

    private String notes;
}
