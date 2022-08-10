package com.mall.file.upload.service.impl;

import com.mall.api.upload.UploadResult;
import com.mall.api.upload.util.UploadUtil;
import com.mall.api.upload.entity.FileUpload;
import com.mall.file.upload.service.UploadService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    FileUpload fileUpload;


    @Override
    public UploadResult querySaveFile(InputStream inputStream, String originalFilename) {
        UploadResult result = new UploadResult();
//        FileUpload fileUpload = new FileUpload();
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = fileUpload.getAccessKey();
        String secretKey = fileUpload.getSecretKey();
        String bucket = fileUpload.getBucket();
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = UploadUtil.getFileName() + UploadUtil.getFileTypeName(originalFilename);
        try {

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                if (response.statusCode == 200) {
                    result.setSuccess("success");
                    result.setMsg("上传成功！");
                    result.setFileUrl(fileUpload.getFileUrl() + key);
                } else {
                    result.setError("error");
                    result.setMsg("上传失败！");
                }
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                result.setError("error");
                result.setMsg("上传失败！");
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }


        return result;
    }

    @Override
    public UploadResult querySaveFileDocument(InputStream inputStream, String originalFilename) {
        UploadResult result = new UploadResult();
//        FileUpload fileUpload = new FileUpload();
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = fileUpload.getAccessKey();
        String secretKey = fileUpload.getSecretKey();
        String bucket = fileUpload.getBucket();
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = UploadUtil.getFileName() + UploadUtil.getFileTypeName(originalFilename);
        try {

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                if (response.statusCode == 200) {
                    result.setSuccess("success");
                    result.setMsg("上传成功！");
                    result.setFileUrl(fileUpload.getFileUrl() + key);
                } else {
                    result.setError("error");
                    result.setMsg("上传失败！");
                }
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                result.setError("error");
                result.setMsg("上传失败！");
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }


        return result;
    }
}
