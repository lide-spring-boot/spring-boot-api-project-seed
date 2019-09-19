package com.company.project.util.charging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


public class HttpFileUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpFileUtil.class);

    /**
     *
     * @param request   MultipartFile
     * @param uploadPath 上传路径，可包括原文件
     * @param fileName  保存文件名
     * @return
     */
    public static  String upload(HttpServletRequest request,String uploadPath,String fileName){
        List<MultipartFile> files = null;
        try {
            files = ((MultipartHttpServletRequest)request).getFiles("file");
        } catch (Exception e) {
            log.info("Upload file failed,file is empty!");
            throw new RuntimeException("上传文件失败，文件为空");
        }
        MultipartFile file = null;
        File file2 = new File(uploadPath);
            file = files.get(0);
            if (!file.isEmpty()) {
                try {
                    if(file2.exists()){
                        log.info("删除原文件："+file2.getAbsolutePath());
                        file2.delete();
                    }
                    String realFileName = "";
                    if(fileName.matches(".*\\..*")){
                        realFileName =  uploadPath.substring(0, uploadPath.lastIndexOf("/")+1)+fileName;
                    }else{
                        String getOriginalFilename = file.getOriginalFilename();
                        realFileName = uploadPath.substring(0,
                                uploadPath.lastIndexOf("/")+1) +fileName +getOriginalFilename.substring(getOriginalFilename.lastIndexOf("."));

                    }
                    file2 = new File(realFileName);
                    //防止新生文件存在相同文件名
                    if(file2.exists()){
                        log.info("删除原文件："+file2.getAbsolutePath());
                        file2.delete();
                    }
                    if(!file2.getParentFile().exists()){
                        file2.getParentFile().mkdirs();
                    }
                    file.transferTo(file2);
                    log.info("上传文件成功");
                    log.info("Upload file:"+uploadPath);
                    return realFileName.substring(realFileName.lastIndexOf("/")+1);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("You failed to upload " + uploadPath + " => "+e.getMessage());
                }
            }
        throw new RuntimeException("上传失败");
    }

    public static void downLoadLocalFile(File file, HttpServletResponse response){
        String name = file.getName();
        try {
            byte[] bytes = new byte[1024];
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition","attachement;filename=\""+name+"\"");
            int len = 0;
            while((len = bufferedInputStream.read(bytes))>0){
                response.getOutputStream().write(bytes,0,len);
            }
            bufferedInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
