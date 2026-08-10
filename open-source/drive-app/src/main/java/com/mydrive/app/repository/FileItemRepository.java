package com.mydrive.app.repository;

import com.mydrive.app.entity.FileItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileItemRepository extends JpaRepository<FileItem, Long> {
    List<FileItem> findByOwnerIdAndFolderId(Long ownerId, Long folderId);
    Optional<FileItem> findByIdAndOwnerId(Long id, Long ownerId);
    List<FileItem> findByOwnerIdAndNameContainingIgnoreCase(Long ownerId, String name);
}
