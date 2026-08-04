package com.opensourceapi.server.repository;

import com.opensourceapi.server.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByOwnerId(Long ownerId);
}
