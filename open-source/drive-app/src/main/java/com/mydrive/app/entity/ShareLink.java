package com.mydrive.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "share_link")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShareLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ItemType itemType;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private Long ownerId;

    private Instant expiresAt; // null = never expires

    @Builder.Default
    private Instant createdAt = Instant.now();

    public enum ItemType { FILE, FOLDER }
}
