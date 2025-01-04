package com.cinema.service.domain.service;

import com.cinema.image.ImageProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import static com.cinema.service.domain.util.MultipartFileValidator.validateImage;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.cinema.image.impl"})
public class ImageService {

    private final ImageProvider imageProvider;

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    public String saveImage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        validateImage(imageFile);
        var imageIdentifier = imageProvider.saveImage(uploadDirectory, imageFile);
        log.info("Saved image with identifier: {}", imageIdentifier);
        return imageIdentifier;
    }

    public byte[] getImage(String imageIdentifier) throws IOException {
        return imageProvider.getImage(imageIdentifier);
    }

    public void deleteImage(String imageIdentifier) throws IOException {
        log.info("Deleting image {}", imageIdentifier);
        imageProvider.deleteImage(imageIdentifier);
    }
}
