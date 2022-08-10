package com.mall.api.upload;

/**
 * 上传实体类
 */
public class UploadResult {

    //上传成功
    private String success;

    //上传失败
    private String error;

    //描述信息
    private String msg;

    //上传的URL
    private String fileUrl;

    @Override
    public String toString() {
        return "UploadResult{" +
                "success='" + success + '\'' +
                ", error='" + error + '\'' +
                ", msg='" + msg + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
