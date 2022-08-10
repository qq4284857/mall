package com.mall.file.preview.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PreviewUtil {

    /**
     * 读取网络文件
     *
     * @param path
     * @return
     */
    public static InputStream getFileInputStream(String path) {
        path=PreviewUtil.getFileURLEncoderPath(path);
        URL url = null;
        InputStream inputStream = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            inputStream = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }



    /**
     * 判断路径是否存在
     *
     * @param pathname
     * @return
     */

    public static boolean getFilePathMkdir(String pathname) {
        boolean check = false;
        File file = new File(pathname);
        if (!file.exists()) {
            file.mkdirs(); // 不存在则创建
            check = true;
        }
        return check;
    }

    /**
     * 创建文件
     *
     * @return
     */
    public static boolean getCreateNewFile(String pathname) {
        boolean check = false;
        File file = new File(pathname);
        if (!file.exists()) {
            try {
                file.createNewFile();//创建文件
                check = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return check;
    }

    /**
     * 获取路径中的域名或IP
     *
     * @param pathname
     * @return
     */
    public static String getHost(String pathname) {
        String host = null;
        try {
            URL url = new URL(pathname);
            host = url.getHost();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return host;
    }

    /**
     * 获取文件名称
     *
     * @param path
     * @return
     * @throws MalformedURLException
     */
    public static String[] getFileName(String path) {
        String[] split = new File(path).getName().split("\\.");
        return split;
    }

    /**
     * 對文件的地址進行處理
     * @param path
     * @return
     */
    public static String getFileURLEncoderPath(String path){
        try {
            URL url =new URL(path);
            File file=new File(path);

           return url.getProtocol()+"://"+url.getAuthority()+url.getPath().replace(file.getName(),"")+URLEncoder.encode(file.getName(),"UTF-8");
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }



    /**
     * 获取服务器时间
     *
     * @return
     */
    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        return sdf.format(new Date());
    }

    /**
     * 获取32位随机UUID
     *
     * @return
     */
    public static String getUUID32() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }


    /**
     * 根据连接获取后缀名
     *
     * @param path
     * @return
     */
    public static String getParseSuffix(String path) {
        URL url = null;
        HttpURLConnection conn = null;
        BufferedInputStream bis = null;
        try {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            bis = new BufferedInputStream(conn.getInputStream());
            return HttpURLConnection.guessContentTypeFromStream(bis);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }


    public static File getResFile(String filename) {
        File file = new File(filename);
        if (!file.exists())
            file = new File(filename);
        FileSystemResource resource = new FileSystemResource(file);
        if (!resource.exists())
            try {
                file = ResourceUtils.getFile("classpath:" + filename);
            } catch (FileNotFoundException var4) {

            }
        return file;
    }


}
