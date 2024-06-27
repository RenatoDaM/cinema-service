package com.cinema.service.domain.service;

import com.cinema.service.domain.ImageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageProvider imageProvider;

    public String saveImage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        return imageProvider.saveImage(uploadDirectory, imageFile);
    }
}
