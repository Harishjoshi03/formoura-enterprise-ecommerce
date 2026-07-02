package com.formoura.user.controller;

import com.formoura.user.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileUploadController {

    private final StorageService storageService;

    @PostMapping("/upload")
    public String upload(
            @RequestParam MultipartFile file) {

        return storageService.uploadFile(file);

    }

}