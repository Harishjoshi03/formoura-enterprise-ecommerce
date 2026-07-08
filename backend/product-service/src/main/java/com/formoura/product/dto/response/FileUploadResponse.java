package com.formoura.product.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileUploadResponse {

    private String fileName;

    private String url;

}