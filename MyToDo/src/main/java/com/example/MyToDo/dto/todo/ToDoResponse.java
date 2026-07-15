package com.example.MyToDo.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToDoResponse {
    private Long id;
    private String title;
    private String description;
    private Boolean isCompleted;
}
