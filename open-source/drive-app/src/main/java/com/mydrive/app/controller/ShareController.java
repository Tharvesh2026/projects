package com.mydrive.app.controller;

import com.mydrive.app.entity.FileItem;
import com.mydrive.app.entity.Folder;
import com.mydrive.app.entity.ShareLink;
import com.mydrive.app.exception.ForbiddenException;
import com.mydrive.app.exception.NotFoundException;
import com.mydrive.app.repository.FileItemRepository;
import com.mydrive.app.repository.FolderRepository;
import com.mydrive.app.service.CloudinaryService;
import com.mydrive.app.service.FolderService;
import com.mydrive.app.service.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/share")
public class ShareController {

    private final ShareService shareService;
    private final FileItemRepository fileItemRepository;
    private final FolderRepository folderRepository;
    private final FolderService folderService;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/{token}")
    public String view(@PathVariable String token, Model model) {
        ShareLink link = shareService.resolve(token);

        if (link.getItemType() == ShareLink.ItemType.FILE) {
            FileItem item = fileItemRepository.findById(link.getItemId())
                    .orElseThrow(() -> new NotFoundException("Shared file no longer exists"));
            model.addAttribute("sharedFile", item);
            model.addAttribute("token", token);
            return "share-file";
        }

        // FOLDER: show its immediate children
        Folder folder = folderRepository.findById(link.getItemId())
                .orElseThrow(() -> new NotFoundException("Shared folder no longer exists"));

        List<Folder> subfolders = folderRepository.findByOwnerIdAndParentId(folder.getOwnerId(), folder.getId());
        List<FileItem> files = fileItemRepository.findByOwnerIdAndFolderId(folder.getOwnerId(), folder.getId());

        model.addAttribute("token", token);
        model.addAttribute("rootFolder", folder);
        model.addAttribute("currentFolder", folder);
        model.addAttribute("subfolders", subfolders);
        model.addAttribute("files", files);
        return "share-folder";
    }

    /** Navigate into a subfolder within a shared folder tree (must stay inside the shared subtree). */
    @GetMapping("/{token}/folder/{folderId}")
    public String browseSubfolder(@PathVariable String token, @PathVariable Long folderId, Model model) {
        ShareLink link = shareService.resolve(token);
        if (link.getItemType() != ShareLink.ItemType.FOLDER) {
            throw new NotFoundException("This link does not point to a folder");
        }

        Folder rootFolder = folderRepository.findById(link.getItemId())
                .orElseThrow(() -> new NotFoundException("Shared folder no longer exists"));

        if (!folderService.isDescendantOrSelf(folderId, rootFolder.getId(), rootFolder.getOwnerId())) {
            throw new ForbiddenException("That folder is outside the shared link");
        }

        Folder current = folderRepository.findById(folderId)
                .orElseThrow(() -> new NotFoundException("Folder not found"));

        List<Folder> subfolders = folderRepository.findByOwnerIdAndParentId(current.getOwnerId(), current.getId());
        List<FileItem> files = fileItemRepository.findByOwnerIdAndFolderId(current.getOwnerId(), current.getId());

        model.addAttribute("token", token);
        model.addAttribute("rootFolder", rootFolder);
        model.addAttribute("currentFolder", current);
        model.addAttribute("subfolders", subfolders);
        model.addAttribute("files", files);
        return "share-folder";
    }

    @GetMapping("/{token}/file/{fileId}/download")
    public RedirectView download(@PathVariable String token, @PathVariable Long fileId) {
        ShareLink link = shareService.resolve(token);

        FileItem item = fileItemRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("File not found"));

        boolean allowed = (link.getItemType() == ShareLink.ItemType.FILE && link.getItemId().equals(fileId))
                || (link.getItemType() == ShareLink.ItemType.FOLDER
                    && folderService.isDescendantOrSelf(item.getFolderId(), link.getItemId(), item.getOwnerId()));

        if (!allowed) {
            throw new ForbiddenException("That file is outside the shared link");
        }

        String url = cloudinaryService.signedDownloadUrl(item.getCloudinaryPublicId(), item.getResourceType(), item.getFormat());
        return new RedirectView(url);
    }
}
