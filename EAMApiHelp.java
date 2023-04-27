package com.bwzn.xssmg.help;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bwzn.xssmg.help.EAMAPI.EAMApiBase;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EAMApiHelp {

    private EAMApiBase  eamApiBase;

    public void setEamApiBase(EAMApiBase eamApiBase) {
        this.eamApiBase = eamApiBase;
    }


//    ///位置信息
//    public CabinetPosition CabinetPositionBuilder(){
//        return new CabinetPosition();
//    }
//
//    //资产信息变更
//    public ChangeCapitalInfomation ChangeCapitalInfomationBuilder(){ return new ChangeCapitalInfomation(); }
//
//
//
//
//    //位置信息查询接口
//    public class CabinetPosition{
//        private String siteCode;
//        private String siteName;
//        private String start;
//        private String limit;
//
//        public CabinetPosition setSiteCode(String siteCode){
//            this.siteCode=siteCode;
//            return this;
//        }
//
//        public CabinetPosition setSiteName(String siteName){
//            this.siteName=siteName;
//            return this;
//        }
//
//        public CabinetPosition setStart(String start){
//            this.start=start;
//            return this;
//        }
//
//        public CabinetPosition setLimit(String limit){
//            this.limit=limit;
//            return this;
//        }
//
//        public JSONObject getJSONParams(){
//            JSONObject jsonParams=new JSONObject();
//            jsonParams.put("siteCode",this.siteCode);
//            jsonParams.put("siteName",this.siteName);
//            jsonParams.put("start",this.start);
//            jsonParams.put("limit",this.limit);
//            return jsonParams;
//        }
//
//
//        public JSONObject getJSONParams(String siteCode,String siteName,String start,String limit){
//            JSONObject jsonParams=new JSONObject();
//            jsonParams.put("siteCode",siteCode);
//            jsonParams.put("siteName",siteName);
//            jsonParams.put("start",start);
//            jsonParams.put("limit",limit);
//            return jsonParams;
//        }
//
//        public String getRequestUrl(){
//            return "ifsgeamapi/jxs/kunlun/querySiteInfo";
//        }
//
//        public JSONObject Query(){
//            JSONObject jsonParams = this.getJSONParams();
//            if (jsonParams!=null && jsonParams.size()>0){
//                return this.SendRequest(jsonParams);
//            }
//            return null;
//        }
//
//        public Map<String,String> queryCabinetPosition(){
//                Map<String,String> map=new HashMap<>();
//                loopQueryPosition(1,500,map);
//                return map;
//
//        }
//
//        private void loopQueryPosition(int start,int limit,Map<String,String> map){
//            JSONObject jsonParams= this.getJSONParams("","",String.valueOf(start),String.valueOf(limit));
//            JSONObject result=SendRequest(jsonParams);
//            System.out.println(result.toJSONString());
//            if (result!=null && result.containsKey("success")){
//                boolean status=result.getBoolean("success");
//                if (status){
//                    Integer total = result.getInteger("total");
//                    if (total>0){
//                        JSONArray dataArray=result.getJSONArray("data");
//                        for (int index=0;index<dataArray.size();index++){
//                            JSONObject childData = dataArray.getJSONObject(index);
//                            String siteCode = childData.getString("siteCode");
//                            String siteName = childData.getString("siteName");
//                            //将查询出来的编码和名称保存在map中，当全部查询完毕后用key的集合去查询数据库是否存在该编码，如果存在则更新，不存在则新增
//                            map.put(siteCode,siteName);
//                        }
//                        loopQueryPosition(start+1,limit,map);
//                    }
//                }
//            }
//        }
//
//        private JSONObject SendRequest(JSONObject jsonParams){
//            MarketApiHelp marketApiHelp=new MarketApiHelp("/"+this.getRequestUrl(),HttpMethodHelp.POST);
//            HttpHelp httpHelp=HttpHelp.Build()
//                    .setUrl(MarketApiHelp.MARKETURL+this.getRequestUrl())
//                    .setAccessType(HttpMethodHelp.POST)
//                    .setHeaderParams(marketApiHelp.getHeaderObject())
//                    .setRequestParams(jsonParams);
//            httpHelp.Run(false);
//            JSONObject result=httpHelp.getResponseResult();
//            return result;
//        }
//
//    }
//
//    //服务器资产位置信息变更接口
//    public class ChangeCapitalInfomation{
//        private List<CapitalDTO> list;
//
//        public ChangeCapitalInfomation(){
//            list=new ArrayList<>();
//        }
//
//        public void Add(String installSite,String assetCode,String siteCode){
//            if (StringUtils.isEmpty(installSite) || StringUtils.isEmpty(assetCode) || StringUtils.isEmpty(siteCode)){
//                return;
//            }
//            list.add(new CapitalDTO(installSite,assetCode,siteCode));
//        }
//
//        public String getRequestUrl(){
//            return "ifsgeamapi/jxs/kunlun/changeSite";
//        }
//
//        public JSONObject SendRequest(){
//            JSONObject jsonParams=new JSONObject();
//            jsonParams.put("data",this.list);
//            MarketApiHelp marketApiHelp=new MarketApiHelp("/"+this.getRequestUrl(),HttpMethodHelp.POST);
//            HttpHelp httpHelp=HttpHelp.Build()
//                    .setUrl(MarketApiHelp.MARKETURL+this.getRequestUrl())
//                    .setAccessType(HttpMethodHelp.POST)
//                    .setHeaderParams(marketApiHelp.getHeaderObject())
//                    .setRequestParams(jsonParams);
//            httpHelp.Run(false);
//            JSONObject result=httpHelp.getResponseResult();
//            return result;
//        }
//
//        private class CapitalDTO{
//            private String installSite;
//            private String assetCode;
//            private String siteCode;
//            private String chgData;
//
//            public CapitalDTO(String installSite,String assetCode,String siteCode){
//                this.installSite=installSite;
//                this.assetCode=assetCode;
//                this.siteCode=siteCode;
//                Timestamp createTime = new Timestamp(System.currentTimeMillis());
//                this.chgData=createTime.toString();
//
//            }
//
//            public CapitalDTO(String installSite,String assetCode,String siteCode,String chgDate){
//                this.installSite=installSite;
//                this.assetCode=assetCode;
//                this.siteCode=siteCode;
//                this.chgData=chgDate;
//            }
//
//        }
//
//    }


}
