package com.opensourceapi.server.controller;

import com.opensourceapi.server.dto.TransactionRequest;
import com.opensourceapi.server.entity.Transaction;
import com.opensourceapi.server.exception.ApiException;
import com.opensourceapi.server.repository.TransactionRepository;
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
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Mock transaction endpoints — simulate a credit or banking system")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @GetMapping
    @Operation(summary = "List all transactions (public)")
    public List<Transaction> all() {
        return transactionRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get transactions by user ID (public)")
    public List<Transaction> getByUser(@PathVariable String userId) {
        return transactionRepository.findByUserId(userId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a transaction by id (public)")
    public Transaction getOne(@PathVariable Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ApiException("Transaction not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a transaction (requires auth)")
    public ResponseEntity<Transaction> create(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = Transaction.builder()
                .userId(request.getUserId())
                .amount(request.getAmount())
                .type(request.getType())
                .status(request.getStatus())
                .description(request.getDescription())
                .timestamp(request.getTimestamp())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionRepository.save(transaction));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a transaction (requires auth)")
    public Transaction update(@PathVariable Long id, @Valid @RequestBody TransactionRequest request) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ApiException("Transaction not found", HttpStatus.NOT_FOUND));
        
        transaction.setUserId(request.getUserId());
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setStatus(request.getStatus());
        transaction.setDescription(request.getDescription());
        transaction.setTimestamp(request.getTimestamp());

        return transactionRepository.save(transaction);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a transaction (requires auth)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ApiException("Transaction not found", HttpStatus.NOT_FOUND));
        transactionRepository.delete(transaction);
        return ResponseEntity.noContent().build();
    }
}
