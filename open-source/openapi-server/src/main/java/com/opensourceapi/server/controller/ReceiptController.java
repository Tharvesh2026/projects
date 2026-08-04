package com.opensourceapi.server.controller;

import com.opensourceapi.server.dto.ReceiptRequest;
import com.opensourceapi.server.entity.Receipt;
import com.opensourceapi.server.exception.ApiException;
import com.opensourceapi.server.repository.ReceiptRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/receipts")
@RequiredArgsConstructor
@Tag(name = "Receipts", description = "Mock receipt manager endpoints — good for expense tracking apps")
public class ReceiptController {

    private final ReceiptRepository receiptRepository;

    @GetMapping
    @Operation(summary = "List all receipts, optionally filtered by category (public)")
    public List<Receipt> all(@RequestParam(required = false) String category) {
        if (category != null && !category.isBlank()) {
            return receiptRepository.findByCategoryIgnoreCase(category);
        }
        return receiptRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a receipt by id (public)")
    public Receipt getOne(@PathVariable Long id) {
        return receiptRepository.findById(id)
                .orElseThrow(() -> new ApiException("Receipt not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a receipt (requires auth)")
    public ResponseEntity<Receipt> create(@Valid @RequestBody ReceiptRequest request) {
        Receipt receipt = Receipt.builder()
                .merchantName(request.getMerchantName())
                .totalAmount(request.getTotalAmount())
                .taxAmount(request.getTaxAmount())
                .date(request.getDate())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .notes(request.getNotes())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(receiptRepository.save(receipt));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a receipt (requires auth)")
    public Receipt update(@PathVariable Long id, @Valid @RequestBody ReceiptRequest request) {
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new ApiException("Receipt not found", HttpStatus.NOT_FOUND));
        
        receipt.setMerchantName(request.getMerchantName());
        receipt.setTotalAmount(request.getTotalAmount());
        receipt.setTaxAmount(request.getTaxAmount());
        receipt.setDate(request.getDate());
        receipt.setCategory(request.getCategory());
        receipt.setImageUrl(request.getImageUrl());
        receipt.setNotes(request.getNotes());

        return receiptRepository.save(receipt);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a receipt (requires auth)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new ApiException("Receipt not found", HttpStatus.NOT_FOUND));
        receiptRepository.delete(receipt);
        return ResponseEntity.noContent().build();
    }
}
