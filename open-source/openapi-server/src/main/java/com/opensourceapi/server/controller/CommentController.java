package com.opensourceapi.server.controller;

import com.opensourceapi.server.dto.CommentRequest;
import com.opensourceapi.server.entity.Comment;
import com.opensourceapi.server.entity.User;
import com.opensourceapi.server.exception.ApiException;
import com.opensourceapi.server.repository.CommentRepository;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "Comments on posts. Reading is open; writing needs a JWT")
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @GetMapping("/posts/{postId}/comments")
    @Operation(summary = "List comments on a post (public)")
    public List<Comment> forPost(@PathVariable Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @PostMapping("/posts/{postId}/comments")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Add a comment to a post (requires auth)")
    public ResponseEntity<Comment> add(@AuthenticationPrincipal User user, @PathVariable Long postId,
                                        @Valid @RequestBody CommentRequest request) {
        postRepository.findById(postId)
                .orElseThrow(() -> new ApiException("Post not found", HttpStatus.NOT_FOUND));

        Comment comment = Comment.builder()
                .postId(postId)
                .authorId(user.getId())
                .content(request.getContent())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commentRepository.save(comment));
    }

    @DeleteMapping("/comments/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete your own comment (requires auth)")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal User user, @PathVariable Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ApiException("Comment not found", HttpStatus.NOT_FOUND));
        if (!comment.getAuthorId().equals(user.getId())) {
            throw new ApiException("You can only delete your own comments", HttpStatus.FORBIDDEN);
        }
        commentRepository.delete(comment);
        return ResponseEntity.noContent().build();
    }
}
