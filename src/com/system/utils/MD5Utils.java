package com.system.utils;

import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类，地址如下
 * from：https://blog.csdn.net/qq_30683329/article/details/80879058
 * @Author: 枠成
 * @Data:2019-08-23
 * @Description:com.system.utils
 * @version:1.0
 */
public class MD5Utils {

    @Test
    public void test(){
        System.out.println(stringMD5("123"));
        System.out.println(stringMD5("123"));
        System.out.println(stringMD5("456"));
        System.out.println(stringMD5("456"));
        System.out.println(stringMD5("fsad"));
        System.out.println(stringMD5("fsad"));
    }

    /*
    拿出的数据，还原先前的表的
    123
    202CB962AC59075B964B07152D234B70
    456
    250CF8B51C773F3F8DC8B4BE867A9A02
    fsad
    EF3EFC883935571F210C33AAC44F6BC9
     */

    public static String stringMD5(String input) {

        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");

            // 输入的字符串转换成字节数组
            byte[] inputByteArray = input.getBytes();

            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);

            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();

            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };

        // new一个字符数组，这个就是用来组成结果字符串的
        // （解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray =new char[byteArray.length * 2];

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {

            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];

            resultCharArray[index++] = hexDigits[b& 0xf];

        }

        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

}
