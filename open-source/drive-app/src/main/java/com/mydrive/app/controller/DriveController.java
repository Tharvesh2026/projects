package com.mydrive.app.controller;

import com.mydrive.app.entity.FileItem;
import com.mydrive.app.entity.Folder;
import com.mydrive.app.entity.ShareLink;
import com.mydrive.app.entity.User;
import com.mydrive.app.service.FileService;
import com.mydrive.app.service.FolderService;
import com.mydrive.app.service.ShareService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/drive")
public class DriveController {

    private final FolderService folderService;
    private final FileService fileService;
    private final ShareService shareService;

    @GetMapping
    public String root(@AuthenticationPrincipal User user, Model model) {
        return browse(null, user, model);
    }

    @GetMapping("/folder/{id}")
    public String folder(@PathVariable Long id, @AuthenticationPrincipal User user, Model model) {
        return browse(id, user, model);
    }

    private String browse(Long folderId, User user, Model model) {
        List<Folder> subfolders = folderService.listChildren(user.getId(), folderId);
        List<FileItem> files = fileService.listChildren(user.getId(), folderId);
        List<Folder> breadcrumb = folderId == null ? List.of() : folderService.breadcrumb(folderId, user.getId());

        model.addAttribute("currentFolderId", folderId);
        model.addAttribute("subfolders", subfolders);
        model.addAttribute("files", files);
        model.addAttribute("breadcrumb", breadcrumb);
        return "drive";
    }

    @PostMapping("/folder")
    public String createFolder(@RequestParam String name,
                                @RequestParam(required = false) Long parentId,
                                @AuthenticationPrincipal User user) {
        folderService.create(name, parentId, user.getId());
        return parentId == null ? "redirect:/drive" : "redirect:/drive/folder/" + parentId;
    }

    @PostMapping("/folder/{id}/delete")
    public String deleteFolder(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Folder folder = folderService.get(id, user.getId());
        Long parentId = folder.getParentId();
        folderService.delete(id, user.getId());
        return parentId == null ? "redirect:/drive" : "redirect:/drive/folder/" + parentId;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file,
                          @RequestParam(required = false) Long folderId,
                          @AuthenticationPrincipal User user,
                          Model model) throws IOException {
        if (!file.isEmpty()) {
            fileService.upload(file, folderId, user.getId());
        }
        return folderId == null ? "redirect:/drive" : "redirect:/drive/folder/" + folderId;
    }

    @GetMapping("/file/{id}/download")
    public RedirectView download(@PathVariable Long id, @AuthenticationPrincipal User user) {
        String url = fileService.downloadUrl(id, user.getId());
        return new RedirectView(url);
    }

    @PostMapping("/file/{id}/delete")
    public String deleteFile(@PathVariable Long id, @AuthenticationPrincipal User user) throws IOException {
        FileItem item = fileService.get(id, user.getId());
        Long folderId = item.getFolderId();
        fileService.delete(id, user.getId());
        return folderId == null ? "redirect:/drive" : "redirect:/drive/folder/" + folderId;
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("query", q);
        model.addAttribute("fileResults", fileService.search(user.getId(), q));
        model.addAttribute("folderResults", folderService.listChildren(user.getId(), null).stream()
                .filter(f -> f.getName().toLowerCase().contains(q.toLowerCase()))
                .toList());
        return "search";
    }

    @PostMapping("/file/{id}/share")
    public String shareFile(@PathVariable Long id, @AuthenticationPrincipal User user,
                             HttpServletRequest request, Model model) {
        FileItem item = fileService.get(id, user.getId()); // ownership check
        ShareLink link = shareService.createForFile(item.getId(), user.getId());
        model.addAttribute("shareUrl", buildShareUrl(request, link.getToken()));
        model.addAttribute("itemName", item.getName());
        return "share-created";
    }

    @PostMapping("/folder/{id}/share")
    public String shareFolder(@PathVariable Long id, @AuthenticationPrincipal User user,
                               HttpServletRequest request, Model model) {
        Folder folder = folderService.get(id, user.getId()); // ownership check
        ShareLink link = shareService.createForFolder(folder.getId(), user.getId());
        model.addAttribute("shareUrl", buildShareUrl(request, link.getToken()));
        model.addAttribute("itemName", folder.getName());
        return "share-created";
    }

    private String buildShareUrl(HttpServletRequest request, String token) {
        String scheme = request.getScheme();
        String host = request.getServerName();
        int port = request.getServerPort();
        boolean defaultPort = (scheme.equals("http") && port == 80) || (scheme.equals("https") && port == 443);
        String base = scheme + "://" + host + (defaultPort ? "" : ":" + port);
        return base + "/share/" + token;
    }
}
