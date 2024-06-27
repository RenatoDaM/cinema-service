package com.cinema;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageProvider {
    String saveImage(String uploadDirectory, MultipartFile imageFile) throws IOException;
}
