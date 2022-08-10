package com.mall.file.preview.entity;

import java.util.zip.ZipEntry;

public class ZipInfo {
    private String fileName;

    private String url;

    public ZipInfo(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ZipInfo{" +
                "fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
