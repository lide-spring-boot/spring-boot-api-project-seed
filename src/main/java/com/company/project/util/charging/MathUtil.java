package com.company.project.util.charging;

/**
 * @Author lides
 * @Description
 * @Date 18-10-17 16:38
 **/
public class MathUtil {

    /**
     *  保留小数后几位
     * @param target    原数值
     * @param pointBit  保留位数
     * @return
     */
    public static Double  retainPointBit(Double target,int pointBit){
        double pow = Math.pow(10, pointBit);
        long round = Math.round(target*pow);
        double result = round / pow;
        return result;
    }
}
