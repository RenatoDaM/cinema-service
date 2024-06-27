package com.cinema.service.domain.service;

import com.cinema.ImageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.cinema.bibi"})
public class ImageService {
    private final ImageProvider imageProvider;

    public String saveImage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        return imageProvider.saveImage(uploadDirectory, imageFile);
    }
}
