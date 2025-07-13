package com.example.analyzerofanalyses.service.impl;

import com.example.analyzerofanalyses.domain.analysis.AnalysisImage;
import com.example.analyzerofanalyses.domain.exception.ImageUploadException;
import com.example.analyzerofanalyses.domain.symptom.SymptomImage;
import com.example.analyzerofanalyses.service.ImageService;
import com.example.analyzerofanalyses.service.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    // нужно сделать так, чтобы работало с любым типом картинки
    @Override
    public String upload(final SymptomImage image) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new ImageUploadException(
                    "Image upload failed: " + e.getMessage()
            );
        }

        MultipartFile file = image.getFile();

        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name.");
        }

        String fileName = generateFileName(file);
        InputStream inputStream;

        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new ImageUploadException(
                    "Image upload failed: " + e.getMessage()
            );
        }

        saveImage(inputStream, fileName);

        return fileName;
    }

    @Override
    public String upload(final AnalysisImage image) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new ImageUploadException(
                    "Image upload failed" + e.getMessage()
            );
        }

        MultipartFile file = image.getFile();

        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name.");
        }

        String fileName = generateFileName(file);
        InputStream inputStream;

        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new ImageUploadException(
                    "Image upload failed" + e.getMessage()
            );
        }

        saveImage(inputStream, fileName);

        return fileName;
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());

        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateFileName(final MultipartFile file) {
        String extension = getExtension(file);

        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(final MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null) {
            throw new ImageUploadException("Image upload failed");
        }

        return originalFilename
                .substring(originalFilename.lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveImage(
            final InputStream inputStream,
            final String fileName
    ) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }
}
