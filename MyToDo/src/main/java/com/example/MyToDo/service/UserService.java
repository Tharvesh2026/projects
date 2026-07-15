package com.example.MyToDo.service;

import com.example.MyToDo.dto.user.UserRequest;
import com.example.MyToDo.dto.user.UserResponse;
import com.example.MyToDo.entity.User;
import com.example.MyToDo.exception.UserNotFoundException;
import com.example.MyToDo.mapper.UserMapper;
import com.example.MyToDo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Transactional
    public UserResponse createOne(UserRequest userRequest) {
        User entity = mapper.toEntity(userRequest);
        entity.setPassword(encoder.encode(entity.getPassword()));
        entity = repository.save(entity);
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public UserResponse getOne(String email) {
        User entity = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User is not available with email: " + email));
        return mapper.toResponse(entity);
    }
}
