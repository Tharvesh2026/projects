package com.mydrive.app.service;

import com.mydrive.app.dto.RegisterForm;
import com.mydrive.app.entity.User;
import com.mydrive.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static class UsernameTakenException extends RuntimeException {
        public UsernameTakenException(String message) { super(message); }
    }

    public User register(RegisterForm form) {
        if (userRepository.existsByUsername(form.getUsername())) {
            throw new UsernameTakenException("That username is already taken");
        }
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new UsernameTakenException("That email is already registered");
        }
        User user = User.builder()
                .username(form.getUsername())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .build();
        return userRepository.save(user);
    }
}
