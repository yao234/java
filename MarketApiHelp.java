package com.bwzn.xssmg.help;


import com.alibaba.fastjson.JSONObject;
import com.bwzn.xssmg.config.EnvironmentConfig;
import org.apache.catalina.Host;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ThreadLocalRandom;

/*
* 魔盒API帮助类
*
* */
public final class MarketApiHelp {
    public static final String MARKETURL=InitMarketUrl();
    public static final String HOST  = InitHost();  //apimarket.myoas.com   apimarket-sandbox.myoas.com

    public static final String ENVID="UAT";

    public static final String SIGNVERSION = "2.0.0";

    public static final String APPID  = "cabinet-manager";

    public static final String APPSECRET = "PrVPwtiGTIamvPdEnz";

    //魔盒3des密钥
    public static final String SercretKey_Oppo  = "Api_V3|86|j23YU/Pwd_o02|h7f/I83fz_5U7l|3m6";

    public static final String SercretKey_Xs  = "Api_V3|86|j23YU/Pwd_o02|";


    private JSONObject headerObject;

    public JSONObject getHeaderObject() {
        return this.headerObject;
    }

    public MarketApiHelp(String url){
        InitRequestHeader(url);
    }

    public MarketApiHelp(String url,HttpMethodHelp method) {
        InitRequestHeader(url, method);
    }


    //初始化请求头
    public void InitRequestHeader(String url){
        try {
           InitRequestHeader(url,HttpMethodHelp.GET);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    public void InitRequestHeader(String url,HttpMethodHelp method){
        try {
            /**时间搓
             *   headerObj.put("Content-Type", "application/json");
             *             headerObj.put("appid",SignatureHelp.appId);
             *             headerObj.put("nonce",signature.getNonce());
             *             headerObj.put("timestamp",signature.getTimestamp());
             *             headerObj.put("sign",signature.GetSignatureString());
             *             headerObj.put("signversion","2.0.0");
             *             headerObj.put("envid","DEV");
             * **/
            EnvironmentConfig.Init();
            final String timestamp=String.valueOf(System.currentTimeMillis()/1000);
            /** 6位随机数*/
            final String nonce=String.format("%06d", ThreadLocalRandom.current().nextInt(999999));
            headerObject = new JSONObject();
            headerObject.put("Content-Type", "application/json");
            headerObject.put("appid",APPID);
            headerObject.put("nonce",nonce);
            headerObject.put("timestamp",timestamp);
            headerObject.put("sign",SignatureHelp.Build().GetMarketSecret(method,HOST,url,APPID,APPSECRET,timestamp,nonce));
            headerObject.put("signversion",SIGNVERSION);
            //headerObject.put("host",HOST);
            if (EnvironmentConfig.getIsDubg()) {
                headerObject.put("envid", ENVID); /**正式环境不需要用到此参数，只有测试环境才需要用到**/
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    private  static String InitMarketUrl() {
        EnvironmentConfig.Init();
        return EnvironmentConfig.getIsDubg() ? "https://apimarket-sandbox.myoas.com/" : "https://apimarket.myoas.com/";
    }


    private static  String InitHost() {
        EnvironmentConfig.Init();
        return EnvironmentConfig.getIsDubg() ? "apimarket-sandbox.myoas.com" : "apimarket.myoas.com";
    }



}



