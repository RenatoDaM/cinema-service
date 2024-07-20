package com.cinema.image.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.cinema.image.ImageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;

@Component
@ConditionalOnProperty(name = "cinema.image-provider", havingValue = "simple-storage")
@RequiredArgsConstructor
public class SimpleStorageImageProvider implements ImageProvider {
    @Value("${api-accesskey}")
    private String accessKey;

    @Value("${api-secretkey}")
    private String secretKey;

    public final String BUCKET_NAME = "cinema-service-movie-images";

    // save as jfif for some reason, probably because the conversion from multipartfile
    @Override
    public String saveImage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                secretKey
        );

        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageFile.getSize());
            metadata.setContentType(imageFile.getContentType());

            s3.putObject(BUCKET_NAME, "s3-key", imageFile.getInputStream(), metadata);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            throw new IOException("Erro ao salvar a imagem no S3", e);
        }

        return "ok";
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
