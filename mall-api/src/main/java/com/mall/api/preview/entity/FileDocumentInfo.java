package com.mall.api.preview.entity;

public class FileDocumentInfo {
    private String id;

    private String host;

    private String fileName;

    private String suffix;

    private String path;

    private String createTime;

    private int status;


    public FileDocumentInfo(String id, String host, String fileName, String suffix, String path, String createTime, int status) {
        this.id = id;
        this.host = host;
        this.fileName = fileName;
        this.suffix = suffix;
        this.path = path;
        this.createTime = createTime;
        this.status = status;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
