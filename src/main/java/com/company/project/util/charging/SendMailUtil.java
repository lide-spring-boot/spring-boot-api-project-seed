package com.company.project.util.charging;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class SendMailUtil {

    @Resource
    private JavaMailSender javaMailSender;

//    @Value("${spring.mail.username}")
    private String serverMail = "376280740@qq.com";
    
    ExecutorService executorService = Executors.newCachedThreadPool();

    private void excutor(MimeMessage message){
        Thread thread = new Thread(()->{
            javaMailSender.send(message);
        });
        executorService.execute(thread);
    }
    public void sendMail(List<String> toUser, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(serverMail);
            helper.setTo(toUser.toArray(new String[toUser.size()]));
            helper.setSubject(subject);
            helper.setText(text, true);

            excutor(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void sendMail(String toUser, String subject, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(serverMail);
            helper.setTo(toUser);
            helper.setSubject(subject);
            helper.setText(text, true);
            excutor(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    public void sendMail(String toUser, String subject, String text, File file, String fileName) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(serverMail);
        helper.setTo(toUser);
        helper.setSubject(subject);
        helper.setText(text, true);
        helper.addAttachment(fileName, file);
        excutor(message);
    }

//    @Value("${spring.profiles.active}")
    private String env = "";//当前激活的配置文件
    public void sendMailOfException(String text){
        String toUser = "376280740@qq.com";
        String subject = "发现异常";
        text = "====当前激活的配置文件:"+env+"====\n"+text;
        if(env.equals("dev")){
            return;
        }
        sendMail(toUser,subject,text);
    }
}
