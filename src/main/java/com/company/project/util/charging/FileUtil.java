package com.company.project.util.charging;

import org.apache.commons.io.FileUtils;
import sun.net.www.protocol.file.FileURLConnection;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Lides
 * @Date: 2019/9/16 11:57
 */
public class FileUtil {

    /**
     * 写入文件
     * @param content   字符串内容
     * @param filePath  文件路径
     * @throws IOException
     */
    public static void fileWrite(String content,String filePath) throws IOException {
        File file = new File(filePath);
        FileUtils.writeStringToFile(file,content,"utf-8");
    }
    /**
     * 写入文件
     * @param content   字节数组内容
     * @param filePath  文件路径
     * @throws IOException
     */
    public static void fileWrite(byte[] content,String filePath) throws IOException {
        File file = new File(filePath);
        FileUtils.writeByteArrayToFile(file,content);
    }

    /**
     *   读取文件为字符串列表
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<String> fileRead(String filePath) throws IOException {
        File file = new File(filePath);
        return FileUtils.readLines(file, "utf-8");
    }

    public static byte[] fileReadToByteArray(String filePath) throws IOException {
        File file = new File(filePath);
        return FileUtils.readFileToByteArray(file);
    }

}
