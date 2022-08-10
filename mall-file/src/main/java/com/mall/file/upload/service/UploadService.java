package com.mall.file.upload.service;

import com.mall.api.upload.UploadResult;

import java.io.InputStream;

public interface UploadService {

    UploadResult querySaveFile(InputStream inputStream, String originalFilename);

    UploadResult querySaveFileDocument(InputStream inputStream, String originalFilename);
}
