package com.formoura.product.service;

import com.formoura.product.dto.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    FileUploadResponse uploadFile(MultipartFile file);

    void deleteFile(String fileUrl);

}