package com.cinema.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageProvider {
    String saveImage(String uploadDirectory, MultipartFile imageFile) throws IOException;

    byte[] getImage(String imageIdentifier) throws IOException;

    void deleteImage(String imageIdentifier) throws IOException;
}
