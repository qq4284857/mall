package com.mall.file.upload.config;

import com.mall.api.upload.entity.FileUpload;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadConfig {
    @Bean
    public FileUpload fileUpload(){
        return new FileUpload();
    }
}
