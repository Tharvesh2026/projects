package com.opensourceapi.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class RecipeRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private List<String> ingredients;

    private String instructions;

    private Integer prepTimeMinutes;

    private Integer cookTimeMinutes;

    private Integer servings;

    private String difficulty;

    private String imageUrl;
}
