package com.opensourceapi.server.controller;

import com.opensourceapi.server.dto.PostRequest;
import com.opensourceapi.server.entity.Post;
import com.opensourceapi.server.entity.User;
import com.opensourceapi.server.exception.ApiException;
import com.opensourceapi.server.repository.PostRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "Posts", description = "Public blog-style posts. Reading is open to everyone; writing needs a JWT")
public class PostController {

    private final PostRepository postRepository;

    @GetMapping
    @Operation(summary = "List all posts (public)")
    public List<Post> all() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a post by id (public)")
    public Post getOne(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a post (requires auth)")
    public ResponseEntity<Post> create(@AuthenticationPrincipal User user, @Valid @RequestBody PostRequest request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .authorId(user.getId())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(postRepository.save(post));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update your own post (requires auth)")
    public Post update(@AuthenticationPrincipal User user, @PathVariable Long id, @Valid @RequestBody PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        if (!post.getAuthorId().equals(user.getId())) {
            throw new ApiException("You can only edit your own posts", HttpStatus.FORBIDDEN);
        }
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete your own post (requires auth)")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal User user, @PathVariable Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));
        if (!post.getAuthorId().equals(user.getId())) {
            throw new ApiException("You can only delete your own posts", HttpStatus.FORBIDDEN);
        }
        postRepository.delete(post);
        return ResponseEntity.noContent().build();
    }
}
