package com.cinema.image.impl;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.cinema.image.ImageProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;

@Component
@ConditionalOnProperty(name = "cinema.image-provider", havingValue = "simple-storage")
public class SimpleStorageImageProvider implements ImageProvider {

    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();


    @Override
    public String saveImage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        try {
            s3.putObject(bucket_name, key_name, new File(file_path));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
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
