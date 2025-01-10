package com.cinema.core.common.util;

import org.apache.tika.Tika;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public class MultipartFileValidator {
    private static final Tika tika = new Tika();

    public static void validateImage(MultipartFile file) throws IOException {
        if (file.isEmpty())
            throw new FileUploadException("Image not found on request");

        String mimeType = tika.detect(file.getInputStream());
        if (!mimeType.startsWith("image/"))
            throw new FileUploadException("Invalid file type: " + mimeType);
    }
}
