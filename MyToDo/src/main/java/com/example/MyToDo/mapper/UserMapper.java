package com.example.MyToDo.mapper;

import com.example.MyToDo.dto.user.UserRequest;
import com.example.MyToDo.dto.user.UserResponse;
import com.example.MyToDo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserMapper {

    public UserResponse toResponse(User entity) {
        if (entity == null) {
            return null;
        }
        log.info("Convert Entity to UserResponse");
        return UserResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .build();
    }

    public User toEntity(UserRequest req) {
        if (req == null) {
            return null;
        }
        log.info("Convert UserRequest to Entity");
        return User.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .build();
    }
}
