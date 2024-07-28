package com.cinema.service.domain.service;

import com.cinema.image.ImageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.cinema.image.impl"})
public class ImageService {

    private final ImageProvider imageProvider;

    public String saveImage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        return imageProvider.saveImage(uploadDirectory, imageFile);
    }

    public byte[] getImage(String imageIdentifier) throws IOException {
        return imageProvider.getImage(imageIdentifier);
    }
}
