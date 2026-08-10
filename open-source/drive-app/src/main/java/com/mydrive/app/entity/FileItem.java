package com.mydrive.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "file_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    /** null = file lives at the top level (root) for the owner */
    private Long folderId;

    @Column(nullable = false)
    private Long ownerId;

    /** Cloudinary public_id, needed to build signed URLs and to delete the asset */
    @Column(nullable = false, length = 500)
    private String cloudinaryPublicId;

    @Column(nullable = false, length = 30)
    private String resourceType; // image | video | raw

    @Column(length = 20)
    private String format;

    private Long bytes;

    @Column(length = 150)
    private String contentType;

    @Builder.Default
    private Instant createdAt = Instant.now();
}
