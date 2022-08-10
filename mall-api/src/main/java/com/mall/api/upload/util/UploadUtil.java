package com.mall.api.upload.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadUtil {
    /**
     * 获取图片后缀名
     * @param fileName
     * @return
     */
    public static String getFileTypeName(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return suffix;
    }

    /**
     * 获取上传图片的名称
     * @return
     */
    public static String getFileName(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
        return simpleDateFormat.format(new Date());
    }

    public static void main(String[] args) {
        String file="123123123.png";
        String i=UploadUtil.getFileTypeName(file);
        System.out.println(i);
    }
}
