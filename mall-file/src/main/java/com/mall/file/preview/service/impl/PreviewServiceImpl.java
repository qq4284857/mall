package com.mall.file.preview.service.impl;

import com.mall.api.preview.entity.FileDocumentInfo;
import com.mall.file.preview.dao.PreviewDao;

import com.mall.file.preview.service.PreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PreviewServiceImpl implements PreviewService {
    @Resource
    private PreviewDao previewDao;

    @Override
    public void createFileSave(FileDocumentInfo fileDocumentInfo) {
        previewDao.insert(fileDocumentInfo);
    }
}
