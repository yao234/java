package com.bwzn.xssmg.help;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bwzn.xssmg.Result;
import com.bwzn.xssmg.config.EnvironmentConfig;
import com.bwzn.xssmg.utils.HttpsUtils;
import okhttp3.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

public final class HttpHelp {
    /*
    *url地址
    *
    **/
    private String url;
    /**
     * 请求类型 默认为Get
     *
     *
     *
     * **/
    private HttpMethodHelp accessType;
    /**
     *
     *
     *
     *
     *
     *
     * **/
    private String userName;
    private String userPwd;


    /**
     *
     *  请求Body
     * **/
    private JSONObject requestParams;

    /**
     *
     *  请求Body Array
     *
     * **/
    private JSONArray requestArrayParams;

    /**
     * http 头部参数
     *
     * **/
    private JSONObject headerParams;

    /**
     * 请求是否成功
     *
     *
     *
     * **/
    private boolean isSuccess;

    private JSONObject  responseResult;

    /**
     * 返回值
     *
     *
     * **/
    private Result responseValue;

    /*
    * 是否建立连接
    *
    * */
    private boolean isConnect;

    /**
     * 加密的字符串
     *
     * */
    private String basic;


    private String responseMessage;


    /***
     *
     * */
     private HttpsUtils httpsUtils;


     private OkHttpClient trustAllClient;


    public HttpHelp setUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpHelp setAccessType(HttpMethodHelp accessType) {
        this.accessType = accessType;
        return this;
    }

    public HttpMethodHelp getAccessType() {
        return accessType;
    }

    public HttpHelp setUserName(String userName) {
        this.userName = userName;
        return  this;
    }

    public HttpHelp setUserPwd(String userPwd) {
        this.userPwd = userPwd;
        return this;
    }

    public HttpHelp setRequestParams(JSONObject requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    public HttpHelp setHeaderParams(JSONObject headerParams) {
        this.headerParams = headerParams;
        return this;
    }

    public HttpHelp setRequestArrayParams(JSONArray requestArrayParams) {
        this.requestArrayParams = requestArrayParams;
        return this;
    }

    public static HttpHelp Build(){
        return new HttpHelp().setAccessType(HttpMethodHelp.GET);
    }

    public Result getResponseValue() {
        return this.responseValue;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }
    public boolean getIsSuccess(){
        boolean flag=this.isSuccess;
        this.isSuccess=false;
        return flag;
    }

    private void setConnect(boolean connect) {
        isConnect = connect;
    }

    public boolean getConnect(){
        return GetConnectState();
    }


    public JSONObject getResponseResult() {
        return this.responseResult;
    }

    /**
     * 获取连接的状态 TRUE 为可连接,False为不可连接
     *
     * */
    public boolean GetConnectState() {
        HttpUrl url = HttpUrl.parse(this.url);
        if (url == null) {
            System.out.println("无效的url");
            setConnect(false);
            return false;
        }
        /**检查环境**/
        EnvironmentConfig.Init();
        setConnect(true);
        return true;
    }

    private boolean checkAuthState() {
        if (this.userName.isEmpty() || this.userPwd.isEmpty()) {
            System.out.println("用户名和密码不能为空");
            return false;
        }
        basic = SignatureHelp.GetBasicSecret(userName, userPwd);
        return true;
    }

    /**
     *
     * 需要登录验证
     *
     * */
    public boolean createClientAuthenticator(){
        if (!checkAuthState()){
            return false;
        }
        httpsUtils = new HttpsUtils();
        System.out.println("检查代理环境");
        if (EnvironmentConfig.IsProxy()){
            System.out.println("需要代理");
            Proxy proxyTest = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("120.25.146.217", 38081));
            trustAllClient = httpsUtils.getTrustAllClientBuilder().authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    return response.request().newBuilder().header("Authorization", basic).build();
                }
            }).proxy(proxyTest).build();
        }else{
            trustAllClient = httpsUtils.getTrustAllClientBuilder().authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    return response.request().newBuilder().header("Authorization", basic).build();
                }
            }).build();
        }


        return true;
    }

    public boolean createClient() {
        try {
            /**
             * 测试环境需要用代理，正式环境不需要，且正式环境头部不需要envid参数
             *
             * **/
            System.out.println("测试环境"+EnvironmentConfig.getIsDubg());
            if (EnvironmentConfig.IsProxy()) {
                System.out.println("proxy"+EnvironmentConfig.IsProxy());
                System.out.println("Init Proxy");
                Proxy proxyTest = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("120.25.146.217", 38081));
                trustAllClient = new OkHttpClient.Builder().proxy(proxyTest).callTimeout(60, TimeUnit.SECONDS).build();
            } else {

                trustAllClient = new OkHttpClient();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally {
            return true;
        }

    }


    public boolean executeRequest() {
        RequestBody pushBody = requestParams == null ? null : RequestBody.create(MediaType.parse("application/json;charset=utf8"), requestParams.toJSONString());
        Request.Builder builder = new Request.Builder().url(this.url).method(accessType.toString(), pushBody);
        if (this.headerParams != null) {
            for (String item : this.headerParams.keySet()) {
                builder.addHeader(item, this.headerParams.getString(item));
                System.out.println(item);
                System.out.println(this.headerParams.getString(item));
            }
        }
        Request handleRequest = builder.build();
        Call call = trustAllClient.newCall(handleRequest);

        try {
            Response response = call.execute();
            if (response.isSuccessful()) {

                String content = response.body().string();
                System.out.println(content);
                this.responseMessage=content;
                this.isSuccess = true;
                this.responseValue = Result.ok(content);
                this.responseResult = JSONObject.parseObject(content);
                System.out.println(JSONObject.toJSONString(responseResult));
            } else {
                System.out.println(response.message());
                this.responseValue = Result.error(response.message());
            }
            response.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            this.responseValue = Result.error(e.getMessage());
        }
        finally {
            return true;
        }
    }


    public boolean executeRequestArray() {
        RequestBody pushBody = requestArrayParams == null ? null : RequestBody.create(MediaType.parse("application/json;charset=utf8"), requestArrayParams.toJSONString());
        Request.Builder builder = new Request.Builder().url(this.url).method(accessType.toString(), pushBody);
        if (this.headerParams != null) {
            for (String item : this.headerParams.keySet()) {
                builder.addHeader(item, this.headerParams.getString(item));
                System.out.println(item);
                System.out.println(this.headerParams.getString(item));
            }
        }
        Request handleRequest = builder.build();
        Call call = trustAllClient.newCall(handleRequest);

        try {
            Response response = call.execute();
            if (response.isSuccessful()) {

                String content = response.body().string();
                System.out.println("接口返回的数据");
                System.out.println(content);
                this.responseMessage=content;
                this.isSuccess = true;
                this.responseValue = Result.ok(content);
                this.responseResult = JSONObject.parseObject(content);
                System.out.println(JSONObject.toJSONString(responseResult));
            } else {
                System.out.println(response.message());
                this.responseValue = Result.error(response.message());
            }
            response.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            this.responseValue = Result.error(e.getMessage());
        }
        finally {
            return true;
        }
    }


    private int Error(){
        return -2;
    }

    public void Run(boolean IsNeedAuth){
        boolean flag=(this.getConnect()?(IsNeedAuth?this.createClientAuthenticator():this.createClient()):false)?this.executeRequest():false;
    }

    public void RunList(boolean IsNeedAuth){
        boolean flag=(this.getConnect()?(IsNeedAuth?this.createClientAuthenticator():this.createClient()):false)?this.executeRequestArray():false;
    }



}
