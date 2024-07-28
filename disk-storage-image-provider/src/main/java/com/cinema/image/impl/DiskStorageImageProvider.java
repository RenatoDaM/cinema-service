package com.cinema.image.impl;

import com.cinema.image.ImageProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
@ConditionalOnProperty(name = "cinema.image-provider", havingValue = "local-disk")
public class DiskStorageImageProvider implements ImageProvider {

    @Value("${cinema.movie-image-upload-directory}")
    private String movieImagesPath;

    @Override
    public String saveImage(String imageIdentifier, MultipartFile imageFile) throws IOException {
        Path uploadPath = Path.of(movieImagesPath);
        Path filePath = uploadPath.resolve(imageIdentifier);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return imageIdentifier;
    }

    public byte[] getImage(String imageIdentifier) throws IOException {
        Path imagePath = Path.of((movieImagesPath + imageIdentifier));

        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return imageBytes;
        } else {
            return null;
        }
    }

    public String deleteImage(String imageDirectory, String imageName) throws IOException {
        Path imagePath = Path.of(imageDirectory, imageName);

        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
            return "Success";
        } else {
            return "Failed";
        }
    }
}
