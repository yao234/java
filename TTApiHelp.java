package com.bwzn.xssmg.help;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bwzn.xssmg.config.EnvironmentConfig;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TTAPI帮助类
 * **/
public final class TTApiHelp {
    public static final String pubid=InitId();

    public static final String pubsercet=InitSercet();

    public static final String URL=InitUrl();

    public static final String no=InitNo();

    public static HttpHelp httpHelp=null;

    private static String InitId() {
        //EnvironmentConfig.Init();
        return false ? "XT-94b467df-2d81-467d-9b29-f9cf7830c7fb" : "XT-584c79c2-f386-472c-a5e9-5bf0b59c65a2";
    }

    private static String InitSercet() {
        //EnvironmentConfig.Init();
        return false ? "5263d97aa440550f3f9b83aea647f971" : "977cf9a81e588342cad36fed84c0e541";
    }

    private static String InitUrl() {
        //EnvironmentConfig.Init();
        return false ? "https://mtp-test.myoas.com/pubacc/pubsend" : "https://mtp.myoas.com/pubacc/pubsend";
    }

    private static String InitNo(){
        //EnvironmentConfig.Init();
        return false?"2021042801":"16110501";
    }

    private static JSONObject InitParams(){
        final String nonce= UUID.randomUUID().toString();
        return InitParams(no,pubid,pubsercet,nonce,String.valueOf(System.currentTimeMillis()));
    }

    private static JSONObject InitParams(String no, String pubId, String pubsercet, String nonce, String time) {
        JSONObject jsonFrom = new JSONObject();
        jsonFrom.put("no", no);
        jsonFrom.put("pub", pubId);
        jsonFrom.put("nonce", nonce);
        jsonFrom.put("time", time);
        String pubtoken = sha(no, pubId, pubsercet, nonce, time);
        jsonFrom.put("pubtoken", pubtoken);
        return jsonFrom;
    }

    private static String sha(String... data) {
        String join = Stream.of(data)
                .sorted()
                .collect(Collectors.joining());
        return DigestUtils.sha1Hex(join);
    }

    public static void Send(String msg, List<String> userList){
        System.out.println("发送的TT消息为:"+msg);
        MarketApiHelp marketApiHelp=new MarketApiHelp("/oppo-mtp/pubacc/pubsend",HttpMethodHelp.POST);

        JSONObject jsonData=new JSONObject();

        JSONObject msgJson=new JSONObject();
        msgJson.put("text",msg);

        JSONArray toJson=new JSONArray();

        JSONObject toChilJson=new JSONObject();
        toChilJson.put("code","2");
        toChilJson.put("no",TTApiHelp.no);
        toChilJson.put("user",userList);

        toJson.add(toChilJson);


        jsonData.put("msg",msgJson);
        jsonData.put("from",TTApiHelp.InitParams());
        jsonData.put("to",toJson);
        jsonData.put("type","2");

        System.out.println(jsonData.toJSONString());
        httpHelp=HttpHelp.Build()
                .setUrl(TTApiHelp.URL)
                //.setHeaderParams(marketApiHelp.getHeaderObject())
                .setAccessType(HttpMethodHelp.POST)
                .setRequestParams(jsonData);
        httpHelp.Run(false);

        JSONObject result=httpHelp.getResponseResult();
        System.out.println("============TT返回的消息为=============");
        System.out.println(result.toJSONString());
        System.out.println("============TT消息发送完成=============");
    }

    public static void Send(String msg, String userStr){

        List<String> userList=new ArrayList<>();
        userList.add(userStr);

        System.out.println("发送的TT消息为:"+msg);

        JSONObject jsonData=new JSONObject();

        JSONObject msgJson=new JSONObject();
        msgJson.put("text",msg);

        JSONArray toJson=new JSONArray();

        JSONObject toChilJson=new JSONObject();
        toChilJson.put("code","2");
        toChilJson.put("no",TTApiHelp.no);
        toChilJson.put("user",userList);

        toJson.add(toChilJson);


        jsonData.put("msg",msgJson);
        jsonData.put("from",TTApiHelp.InitParams());
        jsonData.put("to",toJson);
        jsonData.put("type","2");


        System.out.println(jsonData.toJSONString());
        httpHelp=HttpHelp.Build()
                .setUrl(TTApiHelp.URL)
                //.setHeaderParams(marketApiHelp.getHeaderObject())
                .setAccessType(HttpMethodHelp.POST)
                .setRequestParams(jsonData);
        httpHelp.Run(false);

        JSONObject result=httpHelp.getResponseResult();
        System.out.println("============TT返回的消息为=============");
        System.out.println(result.toJSONString());
        System.out.println("============TT消息发送完成=============");
    }

    public static String GetSendTemplate(String user_name,String Cabinet_Info,int number,String ImeiList,boolean back){

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate=dateFormat.format(date);

        return "智能柜信息提示:"+currentDate+","+user_name+"在"+Cabinet_Info+(back?"存件":"取件")+number+"个IMEI为:"+ImeiList;
    }

}
