package com.example.MyToDo.service;

import com.example.MyToDo.dto.todo.ToDoRequest;
import com.example.MyToDo.dto.todo.ToDoResponse;
import com.example.MyToDo.entity.ToDo;
import com.example.MyToDo.entity.User;
import com.example.MyToDo.exception.ResourceNotFoundException;
import com.example.MyToDo.exception.UserNotFoundException;
import com.example.MyToDo.mapper.ToDoMapper;
import com.example.MyToDo.repository.ToDoRepository;
import com.example.MyToDo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;
    private final ToDoMapper mapper;

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found: " + email));
    }

    @Transactional(readOnly = true)
    public Page<ToDoResponse> getPagination(int size, int page) {
        User currentUser = getAuthenticatedUser();
        Pageable pageable = PageRequest.of(page, size);
        return toDoRepository.findByUser(currentUser, pageable)
                .map(mapper::toResponse);
    }

    @Transactional
    public ToDoResponse createOne(ToDoRequest task) {
        User currentUser = getAuthenticatedUser();
        ToDo todo = mapper.toEntity(task);
        todo.setUser(currentUser);
        todo = toDoRepository.save(todo);
        return mapper.toResponse(todo);
    }

    @Transactional(readOnly = true)
    public List<ToDoResponse> getAll() {
        User currentUser = getAuthenticatedUser();
        List<ToDo> entities = toDoRepository.findByUser(currentUser);
        return entities.stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ToDoResponse getOne(Long id) {
        User currentUser = getAuthenticatedUser();
        ToDo entity = toDoRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional
    public ToDoResponse updateOne(Long id, ToDoRequest request) {
        User currentUser = getAuthenticatedUser();
        ToDo todo = toDoRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        ToDo updated = mapper.updateEntity(request, todo);
        updated = toDoRepository.save(updated);
        return mapper.toResponse(updated);
    }

    @Transactional
    public void deleteOne(Long id) {
        User currentUser = getAuthenticatedUser();
        ToDo todo = toDoRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        toDoRepository.delete(todo);
    }

    @Transactional
    public ToDoResponse updateStatus(Long id, Boolean status) {
        User currentUser = getAuthenticatedUser();
        ToDo todo = toDoRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        todo.setIsCompleted(status);
        todo = toDoRepository.save(todo);
        return mapper.toResponse(todo);
    }
}
