package com.bwzn.xssmg.help;

import com.alibaba.fastjson.JSONObject;
import com.bwzn.xssmg.Result;
import com.bwzn.xssmg.config.EnvironmentConfig;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

@Service("SSOHelp")
public final class SSOHelp {
    public static final Map<Integer, String> ResponseStatus = InitResponseStatus();

    public static final int SuccessCode = 1010;

//    private final String RedirectUrl = "http://10.177.159.82:19090";   测试环境
    //private final String RequestUrl = //"https://test-4a.wanyol.com/"; Ini
    private final String RedirectUrl = InitRedirectUrl();  //生产环境

    private final String RequestUrl=InitAuthorizeUrl();
    /**
     * token
     **/
    public String Access_Token;
    /**
     * 过期时间
     **/
    public String expires;

    public String uid;

    public String smart_type;

    public String mail;

    public String orgPath;

    public String mobile;

    public String userCn;

    public String userOrgId;


    public static Map<Integer, String> InitResponseStatus() {
        Map<Integer, String> AllResponseStatus = new HashMap<>();
        AllResponseStatus.put(1001, "缺少服务标识");
        AllResponseStatus.put(1002, "缺少应用地址");
        AllResponseStatus.put(1003, "缺少服务秘钥");
        AllResponseStatus.put(1004, "缺少授权码");
        AllResponseStatus.put(1005, "无法识别服务标识");
        AllResponseStatus.put(1006, "不支持的引用地址");
        AllResponseStatus.put(1007, "错误的服务秘钥");
        AllResponseStatus.put(1008, "过期的授权码");
        AllResponseStatus.put(1010, "接口调取成功");
        AllResponseStatus.put(1011, "过期的accessToken");
        AllResponseStatus.put(1012, "缺少refreshToken");
        AllResponseStatus.put(1013, "refreshToken超时或者不存在");
        AllResponseStatus.put(1014, "refreshToken功能已禁用");
        return AllResponseStatus;
    }


    /**初始化认证接口地址
     *
     * http://sso.oppo.local/ 生产环境的备用地址 4a推荐使用
     * https://sso.myoas.com/ 生产环境的地址 如果是外网只能用此网址
     * **/
    public static String InitAuthorizeUrl(){
        EnvironmentConfig.Init();
        return EnvironmentConfig.getIsDubg()?"https://test-4a.wanyol.com/":"http://sso.oppo.local/";
    }


    public  static String InitRedirectUrl(){
        EnvironmentConfig.Init();
        return EnvironmentConfig.getIsDubg()?"http://10.177.159.82:19090":"http://10.177.111.132:19090";
    }


    /**获取4A接口**/

    /**
     * 认证接口
     **/
    public void AuthCode() {
        String requestParams = "?response_type=code&client_id=" + MarketApiHelp.APPID + "&redirect_uri=" + this.RedirectUrl + "index.html";
        HttpHelp httpHelp = HttpHelp.Build()
                .setUrl(this.RequestUrl + "siam/oauth2.0/authorize" + requestParams)
                .setAccessType(HttpMethodHelp.GET);

        httpHelp.Run(false);


        //JSONObject Result=httpHelp.getResponseResult();
    }


    /**
     * 获取Token
     **/
    public void GetToken(String code) {
        System.out.println("根据code 获取SSO平台 token:" + code);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("client_id", MarketApiHelp.APPID);
        jsonObject.put("client_secret", MarketApiHelp.APPSECRET);
        jsonObject.put("redirect_uri", this.RedirectUrl);
        jsonObject.put("grant_type", "authorization_code");

        HttpHelp httpHelp = HttpHelp.Build()
                .setUrl(this.RequestUrl + "siam/oauth2.0/accessTokenByJson")
                .setRequestParams(jsonObject)
                .setAccessType(HttpMethodHelp.POST);

        httpHelp.Run(false);

        JSONObject result = httpHelp.getResponseResult();
        System.out.println("--------------------获取到token 结果 ---------------------");
        System.out.println(result.toJSONString());
        System.out.println("-----------------------------------------");
        this.Access_Token = result.getString("access_token");

        this.expires = result.getString("expires");


    }


    public void GetUserInfomation(String accessToken) {
        String requestParams = "?access_token=" + accessToken;
        HttpHelp httpHelp = HttpHelp.Build()
                .setAccessType(HttpMethodHelp.GET)
                .setUrl(this.RequestUrl + "siam/oauth2.0/profileByJson" + requestParams);

        httpHelp.Run(false);

        JSONObject JsonResult = httpHelp.getResponseResult();

        JSONObject user = JsonResult.getJSONObject("attributes");

        this.uid = user.getString("uid");
        this.smart_type = user.getString("smart-type");
        this.mail = user.getString("mail");
        this.orgPath = user.getString("orgPath");
        this.mobile = user.getString("mobile");
        this.userCn = user.getString("userCn");
        this.userOrgId = user.getString("userOrgId");
    }


    public void LogOut(String redirect_url) {
        String requestParams = "";

        HttpHelp httpHelp = HttpHelp.Build()
                .setUrl(this.RequestUrl + "siam/logout" + requestParams)
                .setAccessType(HttpMethodHelp.GET);

        httpHelp.Run(false);

        JSONObject jsonObject = httpHelp.getResponseResult();
    }

    public void Run(String code) {
        this.GetToken(code);
        //this.GetUserInfomation(this.Access_Token);
    }


}
