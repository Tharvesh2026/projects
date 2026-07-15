package com.example.MyToDo.repository;

import com.example.MyToDo.entity.ToDo;
import com.example.MyToDo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    Optional<ToDo> findByIdAndUser(Long id, User user);
    Optional<ToDo> findByTitleAndUser(String title, User user);
    Page<ToDo> findByUser(User user, Pageable pageable);
    List<ToDo> findByUser(User user);
}
