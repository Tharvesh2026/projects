package com.example.MyToDo.service;

import com.example.MyToDo.dto.user.UserRequest;
import com.example.MyToDo.dto.user.UserResponse;
import com.example.MyToDo.entity.User;
import com.example.MyToDo.exception.InvalidPasswordException;
import com.example.MyToDo.exception.UserAlreadyExistsException;
import com.example.MyToDo.exception.UserNotFoundException;
import com.example.MyToDo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Transactional
    public UserResponse register(UserRequest req) {
        String email = req.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Mail Id Already Exists: " + email);
        }
        return userService.createOne(req);
    }

    @Transactional(readOnly = true)
    public UserResponse login(UserRequest req) {
        String email = req.getEmail();
        String password = req.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Mail Id Not Found: " + email));

        if (!encoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Wrong Password, Try Again!");
        }

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}
