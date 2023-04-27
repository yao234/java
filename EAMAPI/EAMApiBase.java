package com.bwzn.xssmg.help.EAMAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bwzn.xssmg.help.HttpHelp;
import com.bwzn.xssmg.help.HttpMethodHelp;
import com.bwzn.xssmg.help.MarketApiHelp;

import java.util.Set;

public abstract class EAMApiBase {
    //json的参数
    private JSONObject jsonParams;
    private JSONArray jsonArray;
    private String requestUrl;

    public EAMApiBase(String requestUrl){
        this.jsonParams=new JSONObject();
        this.jsonArray=new JSONArray();
        this.requestUrl=requestUrl;
    }

    public void setJsonParams(JSONObject jsonParams){
        this.jsonParams=jsonParams;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return this.jsonArray;
    }

    public JSONObject getJsonParams(){
        return this.jsonParams;
    }

    public void AddJsonParams(String key, Object value){
        this.jsonParams.put(key, value);
    }

    public void AddJsonItem(JSONObject item){
        this.jsonArray.add(item);
    }

    public JSONObject Post(){
        JSONObject params=this.getJsonParams();
        String json=params.toJSONString();
        System.out.println("发送的json");
        System.out.println(json);
        MarketApiHelp marketApiHelp=new MarketApiHelp("/"+this.requestUrl, HttpMethodHelp.POST);
        HttpHelp httpHelp=HttpHelp.Build()
                .setUrl(MarketApiHelp.MARKETURL+this.requestUrl)
                .setAccessType(HttpMethodHelp.POST)
                .setHeaderParams(marketApiHelp.getHeaderObject())
                .setRequestParams(params);
        httpHelp.Run(false);
        JSONObject result=httpHelp.getResponseResult();
        ClearData();
        return result;
    }

    public JSONObject PostArray(){
        JSONArray params=this.getJsonArray();
        String json=params.toJSONString();
        System.out.println("发送的json");
        System.out.println(json);
        MarketApiHelp marketApiHelp=new MarketApiHelp("/"+this.requestUrl, HttpMethodHelp.POST);
        HttpHelp httpHelp=HttpHelp.Build()
                .setUrl(MarketApiHelp.MARKETURL+this.requestUrl)
                .setAccessType(HttpMethodHelp.POST)
                .setHeaderParams(marketApiHelp.getHeaderObject())
                .setRequestArrayParams(params);
        httpHelp.RunList(false);
        JSONObject result=httpHelp.getResponseResult();
        ClearData();
        return result;
    }

    public JSONObject Get(){
        StringBuilder stringBuilder=new StringBuilder();
        JSONObject jsonParams=this.getJsonParams();
        if (jsonParams!=null){
            Set<String> keyList = jsonParams.keySet();
           // Object[] objects = keyList.toArray();
            if (keyList!=null && keyList.size()>0){
                Object[] objects = keyList.toArray();
                stringBuilder.append("?"+objects[0]+"="+jsonParams.getString(objects[0].toString()));
                if (objects.length>1){
                    for (int i=1;i<objects.length;i++){
                        stringBuilder.append("&"+objects[1]+"="+jsonParams.getString(objects[i].toString()));
                    }
                }
            }

        }
        MarketApiHelp marketApiHelp=new MarketApiHelp("/"+this.requestUrl+stringBuilder, HttpMethodHelp.GET);
        HttpHelp httpHelp=HttpHelp.Build()
                .setUrl(MarketApiHelp.MARKETURL+this.requestUrl+stringBuilder)
                .setAccessType(HttpMethodHelp.GET)
                .setHeaderParams(marketApiHelp.getHeaderObject());
        httpHelp.Run(false);
        JSONObject result=httpHelp.getResponseResult();
        ClearData();
        return result;
    }

    private void ClearData(){
        this.getJsonArray().clear();
        this.getJsonParams().clear();
    }
}
