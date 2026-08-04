package com.opensourceapi.server.controller;

import com.opensourceapi.server.dto.TodoRequest;
import com.opensourceapi.server.entity.Todo;
import com.opensourceapi.server.entity.User;
import com.opensourceapi.server.exception.ApiException;
import com.opensourceapi.server.repository.TodoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Todos", description = "Classic beginner CRUD resource — each user's todos are private to them")
public class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping
    @Operation(summary = "List my todos")
    public List<Todo> myTodos(@AuthenticationPrincipal User user) {
        return todoRepository.findByOwnerId(user.getId());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one of my todos by id")
    public Todo getOne(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return todoRepository.findById(id)
                .filter(t -> t.getOwnerId().equals(user.getId()))
                .orElseThrow(() -> new ApiException("Todo not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Create a todo")
    public ResponseEntity<Todo> create(@AuthenticationPrincipal User user, @Valid @RequestBody TodoRequest request) {
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.isCompleted())
                .ownerId(user.getId())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(todoRepository.save(todo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a todo")
    public Todo update(@AuthenticationPrincipal User user, @PathVariable Long id, @Valid @RequestBody TodoRequest request) {
        Todo todo = todoRepository.findById(id)
                .filter(t -> t.getOwnerId().equals(user.getId()))
                .orElseThrow(() -> new ApiException("Todo not found", HttpStatus.NOT_FOUND));
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        return todoRepository.save(todo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a todo")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal User user, @PathVariable Long id) {
        Todo todo = todoRepository.findById(id)
                .filter(t -> t.getOwnerId().equals(user.getId()))
                .orElseThrow(() -> new ApiException("Todo not found", HttpStatus.NOT_FOUND));
        todoRepository.delete(todo);
        return ResponseEntity.noContent().build();
    }
}
