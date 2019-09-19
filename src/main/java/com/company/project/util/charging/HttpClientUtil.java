package com.company.project.util.charging;


import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;


/**
 * <dependency>
 * <groupId>org.apache.httpcomponents</groupId>
 * <artifactId>httpclient</artifactId>
 * </dependency>
 */

public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger("HttpClientUtil");

    public static String post(String url, String json, String contentType, Map<String, Object> head) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        try {
            StringEntity s = new StringEntity(json.toString(), "UTF-8");
            s.setContentType("application/" + contentType + ";charset=utf-8");//发送json数据需要设置contentType

            if (head != null) {
                head.forEach((k, v) -> {
                    post.setHeader(k, v.toString());
                });
            }
            post.setEntity(s);
            RequestConfig requestConfig = RequestConfig.custom()

                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)

                    .setSocketTimeout(5000).build();

            post.setConfig(requestConfig);

            CloseableHttpResponse response = httpclient.execute(post);


            HttpEntity entity = response.getEntity();//得到请求回来的数据
            String result = EntityUtils.toString(entity, "UTF-8");
            log.info("得到的结果:" + result);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":400,\"message\":\"请求失败,请稍后再试\"}";
        }


    }

    public static String post(String url, String json, String contentType) {
        return post(url, json, contentType, null);
    }

    public static String post(String url, String json) {
        return post(url, json, "json");
    }

    public static String put(String url, String json) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPut put = new HttpPut(url);
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            put.setEntity(s);


            RequestConfig requestConfig = RequestConfig.custom()

                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)

                    .setSocketTimeout(5000).build();

            put.setConfig(requestConfig);

            CloseableHttpResponse response = httpclient.execute(put);


            HttpEntity entity = response.getEntity();//得到请求回来的数据
            String result = EntityUtils.toString(entity, "UTF-8");
            log.info("得到的结果:" + result);
            return result;
        } catch (Exception e) {
            return "{\"code\":400,\"message\":\"请求失败,请稍后再试\"}";
        }


    }

    public static String get(String url) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        try {

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            get.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(get);
            HttpEntity entity = response.getEntity();//得到请求回来的数据
            String result = EntityUtils.toString(entity, "UTF-8");
            log.info("得到的结果:" + result);
            return result;
        } catch (Exception e) {
            return "{\"code\":400,\"message\":\"请求失败,请稍后再试\"}";
        }
    }

    public static InputStream getForInputstem(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        get.setConfig(requestConfig);
        CloseableHttpResponse execute = httpclient.execute(get);
        HttpEntity entity = execute.getEntity();
        InputStream content = entity.getContent();
        return content;
    }

    public static byte[] getForBytes(String url) throws IOException {
        InputStream content = getForInputstem(url);
        byte[] bytes = new byte[1024];
        int len = 0;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((len = content.read(bytes)) != -1) {
            byteArrayOutputStream.write(bytes, 0, len);
        }
        byte[] bytes1 = byteArrayOutputStream.toByteArray();
        return bytes1;
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
//        __cfduid=d357d60157f1ac0a7d15e3795a49dfda01568602483; pEiq_2132_saltkey=M0ww66R3; pEiq_2132_lastvisit=1568598883; pgv_pvi=7560727552; pgv_si=s8181315584; Hm_lvt_cff719ca808774cfac9dee856b6b5d82=1568602483; pEiq_2132_seccode=2731.793a30a98181a3a77d; pEiq_2132_ulastactivity=1568602522%7C0; pEiq_2132_auth=b0c6RJgW4ZRVfAQsYMHuJddnOxN5p%2BPBJ%2BhczU6XBeh86eg8hlZ%2FpK7Z7GGtCc8Nck1EQCvUF7LXy%2Ff0LXuZdd%2Fb%2BQQH; pEiq_2132_lip=172.68.189.161%2C1568602522; pEiq_2132_nofavfid=1; pEiq_2132_visitedfid=59; pEiq_2132_smile=1D1; pEiq_2132_lastcheckfeed=1421152%7C1568602787; pEiq_2132_checkfollow=1; pEiq_2132_checkpm=1; pEiq_2132_st_t=1421152%7C1568602794%7Cdae02bade7d882134618abf946e7954a; pEiq_2132_forum_lastvisit=D_59_1568602794; Hm_lpvt_cff719ca808774cfac9dee856b6b5d82=1568602797; pEiq_2132_sendmail=1; pEiq_2132_lastact=1568602801%09forum.php%09viewthread
        String url = "https://www.baidu.com";
        String s = HttpClientUtil.get(url);
        try {
            FileUtil.fileWrite(s,"D:\\Data_Storage\\test.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
