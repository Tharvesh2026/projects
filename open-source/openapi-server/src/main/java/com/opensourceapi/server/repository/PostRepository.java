package com.opensourceapi.server.repository;

import com.opensourceapi.server.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
