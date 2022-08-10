package com.mall.file.preview.config;

import com.aspose.pdf.License;

import java.io.InputStream;

/**
 * 各种文档转格式配置文件加载类
 * @author tanzheng
 *
 */
public class PreviewConfig {
    /**
     * 获取Wordslicense word
     *
     * @return
     */
    public static boolean getWordsLicense() {
        boolean result = false;
        try {
            InputStream is = PreviewConfig.class.getClassLoader().getResourceAsStream("license.xml");
            com.aspose.words.License aposeLic = new com.aspose.words.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 获取cellslicense excel
     *
     * @return
     */
    public static boolean getCellsLicense() {
        boolean result = false;
        try {
            InputStream is = PreviewConfig.class.getClassLoader().getResourceAsStream("license.xml");
            com.aspose.cells.License aposeLic = new com.aspose.cells.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取Slideslicense ppt
     *
     * @return
     */
    public static boolean getSlidesLicense() {
        boolean result = false;
        try {
            InputStream is = PreviewConfig.class.getClassLoader().getResourceAsStream("license.xml");
            com.aspose.slides.License aposeLic = new com.aspose.slides.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取license  image
     *
     * @return
     */
    public static boolean getLicenseImage() {

        boolean result = false;
        try {
            InputStream license = PreviewConfig.class.getClassLoader().getResourceAsStream("license.xml");// license路径
            License aposeLic = new License();
            aposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
