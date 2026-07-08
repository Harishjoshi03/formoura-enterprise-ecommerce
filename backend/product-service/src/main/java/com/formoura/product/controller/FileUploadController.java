package com.formoura.product.controller;

import com.formoura.product.dto.response.FileUploadResponse;
import com.formoura.product.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "Products File API", description = "Products File Management APIs")
public class FileUploadController {

    private final S3Service service;

    @Operation(summary = " Upload Product File")
    @PostMapping("/upload")
    public FileUploadResponse uploadFile(

            @RequestParam MultipartFile file) {

        return service.uploadFile(file);
    }

    @Operation(summary = " Delete Product File")
    @DeleteMapping
    public void deleteFile(

            @RequestParam String url) {

        service.deleteFile(url);

    }

}