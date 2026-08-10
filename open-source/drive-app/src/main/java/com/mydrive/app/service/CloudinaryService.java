package com.mydrive.app.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Thin wrapper around the Cloudinary SDK. Files are uploaded as "authenticated" delivery type,
 * meaning they are NOT publicly reachable by guessing the URL — every download goes through a
 * freshly generated, time-limited signed URL that our backend controls access to.
 */
@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public record UploadResult(String publicId, String resourceType, String format, Long bytes) {}

    public UploadResult upload(MultipartFile file, Long ownerId) throws IOException {
        String publicId = "drive/" + ownerId + "/" + UUID.randomUUID();

        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "public_id", publicId,
                "resource_type", "auto",
                "type", "authenticated",
                "use_filename", false,
                "overwrite", false
        ));

        String resourceType = String.valueOf(result.get("resource_type"));
        String format = result.get("format") == null ? null : String.valueOf(result.get("format"));
        Number bytes = (Number) result.get("bytes");
        String returnedPublicId = String.valueOf(result.get("public_id"));

        return new UploadResult(returnedPublicId, resourceType, format, bytes == null ? null : bytes.longValue());
    }

    /** Generates a signed, time-limited download URL for a previously uploaded asset. */
    public String signedDownloadUrl(String publicId, String resourceType, String format) {
        var builder = cloudinary.url()
                .resourceType(resourceType)
                .type("authenticated")
                .signed(true);

        if (format != null) {
            builder = builder.format(format);
        }

        return builder.generate(publicId);
    }

    public void delete(String publicId, String resourceType) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.asMap(
                "resource_type", resourceType,
                "type", "authenticated"
        ));
    }
}
