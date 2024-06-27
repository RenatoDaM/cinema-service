package com.cinema.adapter;

import com.cinema.service.domain.ImageProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class DiskStorageImageProvider implements ImageProvider {
    @Override
    public String saveImage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    public byte[] getImage(String imageDirectory, String imageName) throws IOException {
        Path imagePath = Path.of(imageDirectory + "/" + imageName);

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
