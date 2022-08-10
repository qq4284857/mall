package com.mall.file.upload.controller;

import com.mall.api.upload.UploadResult;
import com.mall.file.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    UploadService uploadService;

    @RequestMapping("/save")
    @ResponseBody
    public UploadResult querySaveFile(MultipartFile file) throws IOException {
        return uploadService.querySaveFile(file.getInputStream(), file.getOriginalFilename());
    }

    @RequestMapping("/upFile")
    public String queryFile() {
        return "/upload";
    }
}
