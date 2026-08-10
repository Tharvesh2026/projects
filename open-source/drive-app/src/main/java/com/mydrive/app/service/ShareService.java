package com.mydrive.app.service;

import com.mydrive.app.entity.ShareLink;
import com.mydrive.app.exception.NotFoundException;
import com.mydrive.app.repository.ShareLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareLinkRepository shareLinkRepository;

    public ShareLink createForFile(Long fileId, Long ownerId) {
        return create(ShareLink.ItemType.FILE, fileId, ownerId);
    }

    public ShareLink createForFolder(Long folderId, Long ownerId) {
        return create(ShareLink.ItemType.FOLDER, folderId, ownerId);
    }

    private ShareLink create(ShareLink.ItemType type, Long itemId, Long ownerId) {
        ShareLink link = ShareLink.builder()
                .token(UUID.randomUUID().toString().replace("-", ""))
                .itemType(type)
                .itemId(itemId)
                .ownerId(ownerId)
                .build();
        return shareLinkRepository.save(link);
    }

    public ShareLink resolve(String token) {
        ShareLink link = shareLinkRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("This share link doesn't exist or was revoked"));
        if (link.getExpiresAt() != null && link.getExpiresAt().isBefore(Instant.now())) {
            throw new NotFoundException("This share link has expired");
        }
        return link;
    }
}
