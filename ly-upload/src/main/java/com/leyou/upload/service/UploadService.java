package com.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @outhor Mr.JK
 * @create 2020-05-14  20:08
 */
public interface UploadService {
    String uploadImage(MultipartFile file);
}
