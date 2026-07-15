package com.example.MyToDo.mapper;

import com.example.MyToDo.dto.todo.ToDoRequest;
import com.example.MyToDo.dto.todo.ToDoResponse;
import com.example.MyToDo.entity.ToDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ToDoMapper {

    public ToDoResponse toResponse(ToDo entity) {
        if (entity == null) {
            return null;
        }
        log.info("Convert ToDo Entity to ToDoResponse");
        return ToDoResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .isCompleted(entity.getIsCompleted())
                .build();
    }

    public ToDo toEntity(ToDoRequest req) {
        if (req == null) {
            return null;
        }
        log.info("Convert ToDoRequest to ToDo Entity");
        ToDo entity = new ToDo();
        entity.setTitle(req.getTitle());
        entity.setDescription(req.getDescription());
        entity.setIsCompleted(req.getIsCompleted() != null ? req.getIsCompleted() : false);
        return entity;
    }

    public ToDo updateEntity(ToDoRequest req, ToDo todo) {
        if (req == null || todo == null) {
            return todo;
        }
        log.info("Update ToDo Entity from ToDoRequest");
        todo.setTitle(req.getTitle());
        todo.setDescription(req.getDescription());
        if (req.getIsCompleted() != null) {
            todo.setIsCompleted(req.getIsCompleted());
        }
        return todo;
    }
}
