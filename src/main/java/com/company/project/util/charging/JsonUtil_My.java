package com.company.project.util.charging;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

/**
 * @Author lides
 * @Description
 * @Date 18-9-19 12:47
 **/
public class JsonUtil_My {
    
    public static HashMap<String,Object> toMap(String jsonStr){
        HashMap <String,Object>hashMap = null;
        try {
            hashMap = JSON.parseObject(jsonStr, HashMap.class);
        } catch (Exception e) {
            return null;
        }
        return hashMap;
    }
    public static <T> T parseObject(String text, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(text, clazz);
        } catch (Exception e) {
            t= null;
        }
        return t;
    }
}
