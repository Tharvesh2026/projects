package com.mydrive.app.repository;

import com.mydrive.app.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByOwnerIdAndParentId(Long ownerId, Long parentId);
    Optional<Folder> findByIdAndOwnerId(Long id, Long ownerId);
    List<Folder> findByOwnerIdAndNameContainingIgnoreCase(Long ownerId, String name);
}
