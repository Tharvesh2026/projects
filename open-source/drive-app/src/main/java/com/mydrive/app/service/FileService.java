package com.mydrive.app.service;

import com.mydrive.app.entity.FileItem;
import com.mydrive.app.exception.NotFoundException;
import com.mydrive.app.repository.FileItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileItemRepository fileItemRepository;
    private final CloudinaryService cloudinaryService;

    public List<FileItem> listChildren(Long ownerId, Long folderId) {
        return fileItemRepository.findByOwnerIdAndFolderId(ownerId, folderId);
    }

    public FileItem get(Long id, Long ownerId) {
        return fileItemRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new NotFoundException("File not found"));
    }

    public FileItem upload(MultipartFile file, Long folderId, Long ownerId) throws IOException {
        CloudinaryService.UploadResult result = cloudinaryService.upload(file, ownerId);

        FileItem item = FileItem.builder()
                .name(file.getOriginalFilename() != null ? file.getOriginalFilename() : "untitled")
                .folderId(folderId)
                .ownerId(ownerId)
                .cloudinaryPublicId(result.publicId())
                .resourceType(result.resourceType())
                .format(result.format())
                .bytes(result.bytes())
                .contentType(file.getContentType())
                .build();

        return fileItemRepository.save(item);
    }

    public String downloadUrl(Long id, Long ownerId) {
        FileItem item = get(id, ownerId);
        return cloudinaryService.signedDownloadUrl(item.getCloudinaryPublicId(), item.getResourceType(), item.getFormat());
    }

    public void delete(Long id, Long ownerId) throws IOException {
        FileItem item = get(id, ownerId);
        cloudinaryService.delete(item.getCloudinaryPublicId(), item.getResourceType());
        fileItemRepository.delete(item);
    }

    public List<FileItem> search(Long ownerId, String query) {
        return fileItemRepository.findByOwnerIdAndNameContainingIgnoreCase(ownerId, query);
    }
}
