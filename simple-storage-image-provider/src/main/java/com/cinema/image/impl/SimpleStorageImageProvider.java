package com.cinema.image.impl;


import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.cinema.exception.MovieImageNotFoundException;
import com.cinema.image.ImageProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;

@Component
@ConditionalOnProperty(name = "cinema.image-provider", havingValue = "simple-storage")
@ComponentScan(basePackages = {"com.cinema.image.config"})
@RequiredArgsConstructor
public class SimpleStorageImageProvider implements ImageProvider {

    private final AmazonS3 s3;

    private static final Logger log = LoggerFactory.getLogger(SimpleStorageImageProvider.class);

    @Value("${cinema.s3-bucket-name}")
    public String BUCKET_NAME;

    // save as jfif for some reason, probably because the conversion from multipartfile
    @Override
    public String saveImage(String imageIdentifier, MultipartFile imageFile) throws IOException {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageFile.getSize());
            metadata.setContentType(imageFile.getContentType());

            s3.putObject(BUCKET_NAME, imageIdentifier, imageFile.getInputStream(), metadata);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            throw new IOException("Error occurred while trying to save movie image on S3", e);
        }

        return imageIdentifier;
    }

    public byte[] getImage(String imageIdentifier) throws IOException {
        byte[] imageBytes = null;
        try {
            S3Object s3Object = s3.getObject(BUCKET_NAME, imageIdentifier);
            S3ObjectInputStream s3is = s3Object.getObjectContent();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] readBuf = new byte[1024];
            int readLen;

            while ((readLen = s3is.read(readBuf)) > 0) {
                byteArrayOutputStream.write(readBuf, 0, readLen);
            }

            s3is.close();
            imageBytes = byteArrayOutputStream.toByteArray();
        } catch (AmazonServiceException e) {
            if (e.getStatusCode() == 404) {
                throw new MovieImageNotFoundException("Image not found in the image provider", e);
            }
        }

        return imageBytes;
    }

    public void deleteImage(String imageIdentifier) {
        s3.deleteObject(BUCKET_NAME, imageIdentifier);
    }
}
