package com.bwzn.xssmg.help;

import okhttp3.Credentials;
import org.apache.catalina.Host;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

public final class SignatureHelp {

    public static SignatureHelp Build(){
        return new SignatureHelp();
    }
    /***
     * 魔盒wangzhi
     *
     *
     * */
    /*
    * 签名算法，以AppSecret作为秘钥，使用HmacSHA1算法先加密,再对上一步得到的结果进行Base64编码
    * */
    private  String doSign(String signString, String appSecret) throws Exception {

        byte[] data = appSecret.getBytes(StandardCharsets.UTF_8);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance("HmacSHA1");
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = signString.getBytes(StandardCharsets.UTF_8);
        byte[] sign = mac.doFinal(text);

        return new String(Base64.getEncoder().encode(sign), StandardCharsets.UTF_8);
    }
    /**
     * (魔盒加密)获取加密之后的字符串
     *
     *
     * */
    public String GetMarketSecret(HttpMethodHelp requestMethod,String host,String url,String Appid,String AppSecret,String timestamp,String nonce) throws Exception{
        String signString = String.format("%s&%s&%s&%s&%s&%s", requestMethod.toString() , host, url, Appid, timestamp,nonce);
        String signSecret= doSign(signString,AppSecret);
        System.out.println("待签名字符串：" + signString);
        System.out.println("签名结果：" + signSecret);
        return signSecret;
    }

    /**
     * 基础加密 opm加密
     * */
    public static String GetBasicSecret(String userName,String userPwd){
        return Credentials.basic(userName,userPwd);
    }
}


