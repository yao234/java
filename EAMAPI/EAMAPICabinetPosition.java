package com.bwzn.xssmg.help.EAMAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bwzn.xssmg.help.EAMApiHelp;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//查询位置信息接口
@Service("EAMAPICabinetPosition")
public class EAMAPICabinetPosition extends EAMApiBase {
    private String siteCode;
    private String siteName;
    private String start;
    private String limit;

    public EAMAPICabinetPosition() {
        super("ifsgeamapi/jxs/kunlun/querySiteInfo");
    }

    public EAMAPICabinetPosition setSiteCode(String siteCode){
        this.siteCode=siteCode;
        super.AddJsonParams("siteCode",this.siteCode);
        return this;
    }

    public EAMAPICabinetPosition setSiteName(String siteName){
        this.siteName=siteName;
        super.AddJsonParams("siteName",this.siteName);
        return this;
    }

    public EAMAPICabinetPosition setStart(String start){
        this.start=start;
        super.AddJsonParams("start",this.start);
        return this;
    }

    public EAMAPICabinetPosition setLimit(String limit){
        this.limit=limit;
        super.AddJsonParams("limit",this.limit);
        return this;
    }

    public Map<String,String> queryCabinetPosition(){
        Map<String,String> map=new HashMap<>();
        loopQueryPosition(1,500,map);
        return map;

    }

    private void loopQueryPosition(int start,int limit,Map<String,String> map){
        this.siteCode="";
        this.siteName="";
        this.start=String.valueOf(start);
        this.limit=String.valueOf(limit);
        JSONObject jsonParams= this.getJsonParams();
        JSONObject result=this.Post();
        System.out.println(result.toJSONString());
        if (result!=null && result.containsKey("success")){
            boolean status=result.getBoolean("success");
            if (status){
                Integer total = result.getInteger("total");
                if (total>0){
                    JSONArray dataArray=result.getJSONArray("data");
                    for (int index=0;index<dataArray.size();index++){
                        JSONObject childData = dataArray.getJSONObject(index);
                        String siteCode = childData.getString("siteCode");
                        String siteName = childData.getString("siteName");
                        //将查询出来的编码和名称保存在map中，当全部查询完毕后用key的集合去查询数据库是否存在该编码，如果存在则更新，不存在则新增
                        map.put(siteCode,siteName);
                    }
                    loopQueryPosition(start+1,limit,map);
                }
                else{
                    return;
                }
            }
            else{return;}
        }else{
            return;
        }
    }

    @Override
    public JSONObject getJsonParams() {
        JSONObject jsonParams=new JSONObject();
        jsonParams.put("siteCode",this.siteCode);
        jsonParams.put("siteName",this.siteName);
        jsonParams.put("start",this.start);
        jsonParams.put("limit",this.limit);
        return jsonParams;
    }

    @Override
    public JSONObject Post() {
        return super.Post();
    }
}
