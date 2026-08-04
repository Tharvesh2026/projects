package com.opensourceapi.server.controller;

import com.opensourceapi.server.dto.RecipeRequest;
import com.opensourceapi.server.entity.Recipe;
import com.opensourceapi.server.exception.ApiException;
import com.opensourceapi.server.repository.RecipeRepository;
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
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Tag(name = "Recipes", description = "Mock recipe endpoints — good for cooking apps, blogs, or menu planners")
public class RecipeController {

    private final RecipeRepository recipeRepository;

    @GetMapping
    @Operation(summary = "List all recipes (public)")
    public List<Recipe> all() {
        return recipeRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a recipe by id (public)")
    public Recipe getOne(@PathVariable Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ApiException("Recipe not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a recipe (requires auth)")
    public ResponseEntity<Recipe> create(@Valid @RequestBody RecipeRequest request) {
        Recipe recipe = Recipe.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .ingredients(request.getIngredients())
                .instructions(request.getInstructions())
                .prepTimeMinutes(request.getPrepTimeMinutes())
                .cookTimeMinutes(request.getCookTimeMinutes())
                .servings(request.getServings())
                .difficulty(request.getDifficulty())
                .imageUrl(request.getImageUrl())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeRepository.save(recipe));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a recipe (requires auth)")
    public Recipe update(@PathVariable Long id, @Valid @RequestBody RecipeRequest request) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ApiException("Recipe not found", HttpStatus.NOT_FOUND));
        
        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setIngredients(request.getIngredients());
        recipe.setInstructions(request.getInstructions());
        recipe.setPrepTimeMinutes(request.getPrepTimeMinutes());
        recipe.setCookTimeMinutes(request.getCookTimeMinutes());
        recipe.setServings(request.getServings());
        recipe.setDifficulty(request.getDifficulty());
        recipe.setImageUrl(request.getImageUrl());

        return recipeRepository.save(recipe);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a recipe (requires auth)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ApiException("Recipe not found", HttpStatus.NOT_FOUND));
        recipeRepository.delete(recipe);
        return ResponseEntity.noContent().build();
    }
}
