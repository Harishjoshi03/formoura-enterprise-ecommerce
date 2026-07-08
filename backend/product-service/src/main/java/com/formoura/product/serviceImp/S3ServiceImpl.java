package com.formoura.product.serviceImp;

import com.formoura.product.dto.response.FileUploadResponse;
import com.formoura.product.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    @Override
    public FileUploadResponse uploadFile(MultipartFile file) {

        try {

            String extension =
                    FilenameUtils.getExtension(
                            file.getOriginalFilename());

            String fileName =
                    UUID.randomUUID() + "." + extension;

            PutObjectRequest request =
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(file.getContentType())
                            .build();

            s3Client.putObject(
                    request,
                    RequestBody.fromBytes(file.getBytes()));

            String url =
                    "https://" +
                            bucketName +
                            ".s3." +
                            region +
                            ".amazonaws.com/" +
                            fileName;

            return FileUploadResponse.builder()
                    .fileName(fileName)
                    .url(url)
                    .build();

        } catch (IOException ex) {

            throw new RuntimeException("File Upload Failed");

        }

    }

    @Override
    public void deleteFile(String fileUrl) {

    }

}