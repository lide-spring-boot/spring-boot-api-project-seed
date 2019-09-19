package com.company.project.util.charging;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class QrCodeGenerator {
	
	private static final Logger log = LoggerFactory.getLogger(QrCodeGenerator.class);
	

	 private String filePath ="";
	 
	 public  String create(String content) {
		log.info("生成二维码-------------------");
		log.info("content:"+content);
		 int width = 300;
	     int height = 300;
	     String format = "png";	   
	     String fileName = content;
	     String realPath = fileName+"."+format;
	        //定义二维码的参数
	        HashMap map = new HashMap();
	        //设置编码
	        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
	        //设置纠错等级
	        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
	        map.put(EncodeHintType.MARGIN, 2);

	        try {
	            //生成二维码
	            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);
	            log.info("二维码文件路径："+filePath+realPath);
	            File files = new File(filePath+realPath);
	            if(!files.exists()) {
	            	files.mkdirs();
	            }
	            Path file = files.toPath();
	            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
	        } catch (WriterException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        return realPath;
	 }
	 public  String read(String path) {
		 log.info("读取二维码-------------------");
		 Result result = null;
		  try {
	            MultiFormatReader multiFormatReader = new MultiFormatReader();
	            File file = new File(path);
	            BufferedImage image = ImageIO.read(file);
	            //定义二维码参数
	            Map hints = new HashMap();
	            hints.put(EncodeHintType.CHARACTER_SET,"utf-8");

	            //获取读取二维码结果
	            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
	             result = multiFormatReader.decode(binaryBitmap, hints);
	            
	            System.out.println("读取二维码： " + result.toString());
	            System.out.println("二维码格式： " + result.getBarcodeFormat());
	            System.out.println("二维码内容： " + result.getText());
	        } catch (NotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  return result.getText();
	 }
}
