package com.opensourceapi.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QuoteRequest {

    @NotBlank(message = "Text is required")
    private String text;

    @NotBlank(message = "Author is required")
    private String author;

    private String category;
}
