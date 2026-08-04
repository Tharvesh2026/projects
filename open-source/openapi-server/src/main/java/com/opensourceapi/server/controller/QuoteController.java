package com.opensourceapi.server.controller;

import com.opensourceapi.server.dto.QuoteRequest;
import com.opensourceapi.server.entity.Quote;
import com.opensourceapi.server.exception.ApiException;
import com.opensourceapi.server.repository.QuoteRepository;
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
@RequestMapping("/api/v1/quotes")
@RequiredArgsConstructor
@Tag(name = "Quotes", description = "Mock quote endpoints — great for inspirational widgets or daily quote apps")
public class QuoteController {

    private final QuoteRepository quoteRepository;

    @GetMapping
    @Operation(summary = "List all quotes, optionally filtered by author or category (public)")
    public List<Quote> all(@RequestParam(required = false) String author,
                           @RequestParam(required = false) String category) {
        if (author != null && !author.isBlank()) {
            return quoteRepository.findByAuthorContainingIgnoreCase(author);
        }
        if (category != null && !category.isBlank()) {
            return quoteRepository.findByCategoryIgnoreCase(category);
        }
        return quoteRepository.findAll();
    }

    @GetMapping("/random")
    @Operation(summary = "Get a random quote (public)")
    public Quote getRandom() {
        Quote quote = quoteRepository.findRandomQuote();
        if (quote == null) {
            throw new ApiException("No quotes available", HttpStatus.NOT_FOUND);
        }
        return quote;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a quote by id (public)")
    public Quote getOne(@PathVariable Long id) {
        return quoteRepository.findById(id)
                .orElseThrow(() -> new ApiException("Quote not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a quote (requires auth)")
    public ResponseEntity<Quote> create(@Valid @RequestBody QuoteRequest request) {
        Quote quote = Quote.builder()
                .text(request.getText())
                .author(request.getAuthor())
                .category(request.getCategory())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(quoteRepository.save(quote));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a quote (requires auth)")
    public Quote update(@PathVariable Long id, @Valid @RequestBody QuoteRequest request) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ApiException("Quote not found", HttpStatus.NOT_FOUND));
        
        quote.setText(request.getText());
        quote.setAuthor(request.getAuthor());
        quote.setCategory(request.getCategory());

        return quoteRepository.save(quote);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a quote (requires auth)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ApiException("Quote not found", HttpStatus.NOT_FOUND));
        quoteRepository.delete(quote);
        return ResponseEntity.noContent().build();
    }
}
