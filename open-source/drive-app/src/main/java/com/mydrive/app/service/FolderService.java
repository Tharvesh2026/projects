package com.mydrive.app.service;

import com.mydrive.app.entity.Folder;
import com.mydrive.app.exception.NotFoundException;
import com.mydrive.app.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public List<Folder> listChildren(Long ownerId, Long parentId) {
        return folderRepository.findByOwnerIdAndParentId(ownerId, parentId);
    }

    public Folder get(Long id, Long ownerId) {
        return folderRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new NotFoundException("Folder not found"));
    }

    public Folder create(String name, Long parentId, Long ownerId) {
        if (parentId != null) {
            // validate the parent exists and belongs to the caller
            get(parentId, ownerId);
        }
        Folder folder = Folder.builder()
                .name(name)
                .parentId(parentId)
                .ownerId(ownerId)
                .build();
        return folderRepository.save(folder);
    }

    /** Breadcrumb trail from root down to (and including) the given folder. */
    public List<Folder> breadcrumb(Long folderId, Long ownerId) {
        List<Folder> trail = new ArrayList<>();
        Long currentId = folderId;
        int guard = 0;
        while (currentId != null && guard++ < 100) {
            Folder folder = get(currentId, ownerId);
            trail.add(0, folder);
            currentId = folder.getParentId();
        }
        return trail;
    }

    public void delete(Long id, Long ownerId) {
        Folder folder = get(id, ownerId);
        // Note: for a production system, cascade-delete children folders/files (or block
        // deletion when non-empty). Kept simple here: folder must be empty to delete.
        folderRepository.delete(folder);
    }

    public boolean isDescendantOrSelf(Long folderId, Long ancestorId, Long ownerId) {
        Long currentId = folderId;
        int guard = 0;
        while (currentId != null && guard++ < 100) {
            if (currentId.equals(ancestorId)) {
                return true;
            }
            Folder folder = folderRepository.findByIdAndOwnerId(currentId, ownerId).orElse(null);
            if (folder == null) {
                return false;
            }
            currentId = folder.getParentId();
        }
        return false;
    }
}
