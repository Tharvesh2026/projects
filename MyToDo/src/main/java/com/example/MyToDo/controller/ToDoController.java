package com.example.MyToDo.controller;

import com.example.MyToDo.dto.todo.ToDoRequest;
import com.example.MyToDo.dto.todo.ToDoResponse;
import com.example.MyToDo.service.ToDoService;
import com.example.MyToDo.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/app/v1/todo")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ToDoResponse>>> getAll() {
        List<ToDoResponse> data = service.getAll();
        ApiResponse<List<ToDoResponse>> response = ApiResponse.<List<ToDoResponse>>builder()
                .success(true)
                .message("Tasks retrieved successfully")
                .data(data)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pages")
    public ResponseEntity<ApiResponse<Page<ToDoResponse>>> getPagination(
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") int page
    ) {
        Page<ToDoResponse> data = service.getPagination(size, page);
        ApiResponse<Page<ToDoResponse>> response = ApiResponse.<Page<ToDoResponse>>builder()
                .success(true)
                .message("Paginated tasks retrieved successfully")
                .data(data)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ToDoResponse>> createOne(@Valid @RequestBody ToDoRequest task) {
        ToDoResponse data = service.createOne(task);
        ApiResponse<ToDoResponse> response = ApiResponse.<ToDoResponse>builder()
                .success(true)
                .message("Task created successfully")
                .data(data)
                .status(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ToDoResponse>> getOne(@PathVariable Long id) {
        ToDoResponse data = service.getOne(id);
        ApiResponse<ToDoResponse> response = ApiResponse.<ToDoResponse>builder()
                .success(true)
                .message("Task details retrieved successfully")
                .data(data)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ToDoResponse>> updateOne(
            @PathVariable Long id,
            @Valid @RequestBody ToDoRequest task
    ) {
        ToDoResponse data = service.updateOne(id, task);
        ApiResponse<ToDoResponse> response = ApiResponse.<ToDoResponse>builder()
                .success(true)
                .message("Task updated successfully")
                .data(data)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ToDoResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam Boolean status
    ) {
        ToDoResponse data = service.updateStatus(id, status);
        ApiResponse<ToDoResponse> response = ApiResponse.<ToDoResponse>builder()
                .success(true)
                .message("Task status updated successfully")
                .data(data)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOne(@PathVariable Long id) {
        service.deleteOne(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Task deleted successfully")
                .data(null)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }
}
