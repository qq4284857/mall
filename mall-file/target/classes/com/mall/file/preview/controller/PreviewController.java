package com.mall.file.preview.controller;

import com.aspose.pdf.Document;
import com.aspose.pdf.Image;
import com.aspose.pdf.Page;
import com.aspose.pdf.Rectangle;
import com.aspose.slides.Presentation;
import com.aspose.slides.SaveFormat;
import com.mall.api.preview.entity.FileDocumentInfo;
import com.mall.api.upload.UploadResult;
import com.mall.file.preview.config.PreviewConfig;
import com.mall.file.preview.entity.ZipInfo;
import com.mall.file.preview.service.PreviewService;
import com.mall.file.preview.util.PreviewSuffix;
import com.mall.file.preview.util.PreviewUtil;
import com.mall.file.upload.service.UploadService;
import io.swagger.annotations.Api;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Api(tags = "在线预览")
@Controller
@RequestMapping("/words")
public class PreviewController {
    @Autowired
    PreviewService previewService;
    @Autowired
    UploadService uploadService;


    @GetMapping("/file")
    public void getFiles(String path, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean finished = false;
        String[] type = PreviewUtil.getFileName(path);
        System.out.println("接受的參數=" + path);
        String fileUrl = null;
        if ((type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_DOCX) || (type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_DOC)) {
            fileUrl = getWord(path, type);
        } else if ((type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_XLS) || (type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_XLSX)) {
            fileUrl = getExcel(path, type);
        } else if ((type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_PPT) || (type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_PPTX)) {
            fileUrl = getPPT(path, type);
        } else if ((type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_PNG) || (type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_JPG) || (type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_JPEG)
                || (type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_BMP) || (type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_GIF)) {
            fileUrl = getImages(path, type);
        } else if ((type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_ZIP) || (type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_RAR)) {
            readDataZip(path, request, response, type[1].toUpperCase());
            finished = true;
        } else if ((type[1].toUpperCase()).equals(PreviewSuffix.PRO_EXC_PDF)) {
            request.setAttribute("file", path);
            request.getRequestDispatcher("/words/view").forward(request, response);
            finished = true;
        }
        if (!finished) {
            request.setAttribute("file", fileUrl);
            request.getRequestDispatcher("/words/view").forward(request, response);
        }
    }

    @RequestMapping("/view")
    public String queryViewPdf() {

        return "pdfh5/pdf";
    }

    /**
     * word转pdf
     *
     * @param path
     */

    public String getWord(String path, String[] type) {

        if (!PreviewConfig.getWordsLicense()) {
            return null;
        }
        // 获取输入流
        InputStream inputStream = PreviewUtil.getFileInputStream(path);
        String filePath = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            com.aspose.words.Document doc = new com.aspose.words.Document(inputStream);
            doc.save(byteArrayOutputStream, com.aspose.words.SaveFormat.PDF);
            filePath = getResult(type, byteArrayOutputStream, path);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return filePath;
    }

    public String getResult(String[] type, ByteArrayOutputStream byteArrayOutputStream, String path) {
        // 获去链接中的IP或者域名
        String host = PreviewUtil.getHost(path);
        UploadResult result = null;
        byte[] bytes = byteArrayOutputStream.toByteArray();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        result = uploadService.querySaveFileDocument(byteArrayInputStream, type[0] + PreviewSuffix.PRO_EXO_PDF);
        previewService.createFileSave(new FileDocumentInfo(PreviewUtil.getUUID32(), host, type[0], type[1], result.getFileUrl(), PreviewUtil.getTime(), 0));
        return result.getFileUrl();
    }

    /**
     * excel转pdf
     *
     * @param path
     */
    public String getExcel(String path, String[] type) {
        if (!PreviewConfig.getCellsLicense()) {
            return null;
        }
        String fileName = null;
        InputStream input = PreviewUtil.getFileInputStream(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            com.aspose.cells.Workbook work = new com.aspose.cells.Workbook(input);
            work.save(byteArrayOutputStream, com.aspose.cells.SaveFormat.PDF);
            fileName = getResult(type, byteArrayOutputStream, path);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * PPT转pdf
     *
     * @param path
     */
    public String getPPT(String path, String[] type) {
        if (!PreviewConfig.getSlidesLicense()) {
            return null;
        }
        InputStream input = PreviewUtil.getFileInputStream(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Presentation pre = new Presentation(input);
        pre.save(byteArrayOutputStream, SaveFormat.Pdf);
        String fileName = getResult(type, byteArrayOutputStream, path);
        return fileName;
    }

    /**
     * 图片转pdf
     *
     * @param path
     * @param type
     * @return
     */
    public String getImages(String path, String[] type) {
        if (!PreviewConfig.getLicenseImage()) {
            return null;
        }
        String fileName = null;
        try {
            Document doc = new Document();
            Page page = doc.getPages().add();
            InputStream input = PreviewUtil.getFileInputStream(path);
            page.getPageInfo().getMargin().setBottom(0);
            page.getPageInfo().getMargin().setTop(0);
            page.getPageInfo().getMargin().setLeft(0);
            page.getPageInfo().getMargin().setRight(0);
            page.setCropBox(new Rectangle(0, 0, 800, 400));

            Image image = new Image();
            page.getParagraphs().add(image);
            image.setImageStream(input);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            doc.save(byteArrayOutputStream, com.aspose.pdf.SaveFormat.Pdf);
            fileName = getResult(type, byteArrayOutputStream, path);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;

    }

    /**
     * 压缩包
     * @return
     */
    @RequestMapping("/list")
    public String queryZipList(){
        return "zip_list";
    }

    @RequestMapping("/zip")
    public void queryZipInfo(String path,String fileName,String type,HttpServletResponse response,HttpServletRequest request) throws IOException {
        path=PreviewUtil.getFileURLEncoderPath(path);
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        InputStream inputStream= conn.getInputStream();
        //得到输入流
        ZipInputStream zin = new ZipInputStream(inputStream, Charset.forName("GBK"));
        BufferedInputStream bs = new BufferedInputStream(zin);
        byte[] bytes = null;
        ZipEntry ze;
        type=type.toUpperCase();

        //循环读取压缩包里面的文件
        while ((ze = zin.getNextEntry()) != null) {

            if (ze.toString().endsWith(fileName)) {
                //读取每个文件的字节，并放进数组
                bytes = new byte[(int) ze.getSize()];
                bs.read(bytes, 0, (int) ze.getSize());
                //将文件转成流
                InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                if(type.equals(PreviewSuffix.PRO_EXC_DOCX)||type.equals(PreviewSuffix.PRO_EXC_DOC)){
                    queryDocx(byteArrayInputStream,fileName,path,response,request);
                }else if(type.equals(PreviewSuffix.PRO_EXC_PPT)||type.equals(PreviewSuffix.PRO_EXC_PPTX)){
                    queryPptx(byteArrayInputStream,fileName,path,response,request);
                }else if(type.equals(PreviewSuffix.PRO_EXC_XLS)||type.equals(PreviewSuffix.PRO_EXC_XLSX)){
                    queryXlsx(byteArrayInputStream,fileName,path,response,request);
                }else if(type.equals(PreviewSuffix.PRO_EXC_JPG)||type.equals(PreviewSuffix.PRO_EXC_PNG)
                        ||type.equals(PreviewSuffix.PRO_EXC_JPEG)||type.equals(PreviewSuffix.PRO_EXC_BMP)
                        ||type.equals(PreviewSuffix.PRO_EXC_GIF)){
                    queryImage(byteArrayInputStream,fileName,path,response,request);
                }

            }

        }

    }

    public void queryDocx(InputStream inputStream,String fileName,String path,HttpServletResponse response,HttpServletRequest request)  {
        if (!PreviewConfig.getWordsLicense()) {
            return;
        }
        String[] str= fileName.split("\\.");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            com.aspose.words.Document doc = new com.aspose.words.Document(inputStream);
            doc.save(byteArrayOutputStream, com.aspose.words.SaveFormat.PDF);
            String filePath = getResult(str, byteArrayOutputStream, path);
            request.setAttribute("file", filePath);
            request.getRequestDispatcher("/words/view").forward(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    public void queryPptx(InputStream inputStream,String fileName,String path,HttpServletResponse response,HttpServletRequest request)  {
        if (!PreviewConfig.getSlidesLicense()) {
            return;
        }
       String[] str= fileName.split("\\.");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Presentation pre = new Presentation(inputStream);
            pre.save(byteArrayOutputStream, SaveFormat.Pdf);
            String filePath = getResult(str, byteArrayOutputStream, path);
            request.setAttribute("file", filePath);
            request.getRequestDispatcher("/words/view").forward(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }





    public void queryXlsx(InputStream inputStream,String fileName,String path,HttpServletResponse response,HttpServletRequest request)  {
        if (!PreviewConfig.getCellsLicense()) {
            return;
        }
        String[] str= fileName.split("\\.");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
        com.aspose.cells.Workbook work = new com.aspose.cells.Workbook(inputStream);
        work.save(byteArrayOutputStream, com.aspose.cells.SaveFormat.PDF);
           String filePath = getResult(str, byteArrayOutputStream, path);
            request.setAttribute("file", filePath);
            request.getRequestDispatcher("/words/view").forward(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }




    public void queryImage(InputStream inputStream,String fileName,String path,HttpServletResponse response,HttpServletRequest request)  {
            if (!PreviewConfig.getLicenseImage()) {
                return;
            }
        String[] str= fileName.split("\\.");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Document doc = new Document();
            Page page = doc.getPages().add();
            page.getPageInfo().getMargin().setBottom(0);
            page.getPageInfo().getMargin().setTop(0);
            page.getPageInfo().getMargin().setLeft(0);
            page.getPageInfo().getMargin().setRight(0);
            page.setCropBox(new Rectangle(0, 0, 800, 400));

            Image image = new Image();
            page.getParagraphs().add(image);
            image.setImageStream(inputStream);
            doc.save(byteArrayOutputStream, com.aspose.pdf.SaveFormat.Pdf);
            String filePath = getResult(str, byteArrayOutputStream, path);

            request.setAttribute("file", filePath);
            request.getRequestDispatcher("/words/view").forward(request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }





    public  void readDataZip(String urlStr,HttpServletRequest request,HttpServletResponse response,String type) throws IOException {
        urlStr=PreviewUtil.getFileURLEncoderPath(urlStr);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //得到输入流
        InputStream inputStream = conn.getInputStream();

        ZipInputStream zin = new ZipInputStream(inputStream, Charset.forName("GBK"));

        ZipEntry ze;
        List<ZipInfo> list=new ArrayList<ZipInfo>();

        //循环读取压缩包里面的文件
        while ((ze = zin.getNextEntry()) != null) {
            String[] file = PreviewUtil.getFileName(ze.getName());
            ZipInfo zip=new ZipInfo(ze.getName(),"/file/words/zip?path="+urlStr+"&fileName="+ze.getName()+"&type="+file[1]);
            list.add(zip);
        }
        zin.closeEntry();
        inputStream.close();
        request.setAttribute("zip",list);
        request.setAttribute("path",urlStr);
        try {
            request.getRequestDispatcher("/words/list").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }





}
