package com.sysdao.help.qywxApiHelp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sys.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//产品立项模板

public final class ProductApprovalTemplate {

    private String creator_userid;

    private JSONObject dataJSON;

    private JSONArray approver;

    private List<String> notifyer;

    private int notify_type;

    private int use_template_approver;

    private String choose_department;

    private JSONObject apply_data;

    private JSONArray summary_list;

    //key为控件的id
    //value为contents的value
    private Map<String,JSONObject> ControlValue;

    private String ErrorMsg;

    private boolean Success;

    //产品型号申请的模板id
    private static final String TEMPLATE_ID="C4UBhJ3udDFf8UfEhabaSrfHAuB62f5RYekRhHmpa";

    private static final String TEMPLATE_STR="{\"errcode\":0,\"template_names\":[{\"text\":\"产品立项申请与发布流程\",\"lang\":\"zh_CN\"},{\"text\":\"\",\"lang\":\"en\"}],\"template_content\":{\"controls\":[{\"property\":{\"un_print\":0,\"control\":\"Selector\",\"require\":1,\"id\":\"Selector-1681365666202\",\"placeholder\":[{\"text\":\"\",\"lang\":\"zh_CN\"}],\"title\":[{\"text\":\"项目类型\",\"lang\":\"zh_CN\"}]},\"config\":{\"selector\":{\"external_option\":{\"external_url\":\"\",\"use_external_option\":false},\"options\":[{\"value\":[{\"text\":\"公司自研\",\"lang\":\"zh_CN\"}],\"key\":\"option-1681365666202\"},{\"value\":[{\"text\":\"客户定制\",\"lang\":\"zh_CN\"}],\"key\":\"option-1681365666203\"}],\"op_relations\":[],\"type\":\"single\"}}},{\"property\":{\"un_print\":0,\"control\":\"Selector\",\"require\":1,\"id\":\"Selector-1681365708082\",\"placeholder\":[{\"text\":\"\",\"lang\":\"zh_CN\"}],\"title\":[{\"text\":\"产品类型\",\"lang\":\"zh_CN\"}]},\"config\":{\"selector\":{\"external_option\":{\"external_url\":\"\",\"use_external_option\":false},\"options\":[{\"value\":[{\"text\":\"天线\",\"lang\":\"zh_CN\"}],\"key\":\"option-1681365708082\"},{\"value\":[{\"text\":\"标签\",\"lang\":\"zh_CN\"}],\"key\":\"option-1681365708083\"},{\"value\":[{\"text\":\"读写器\",\"lang\":\"zh_CN\"}],\"key\":\"option-1681365773483\"},{\"value\":[{\"text\":\"门禁\",\"lang\":\"zh_CN\"}],\"key\":\"option-1681365778138\"},{\"value\":[{\"text\":\"智能装装备\",\"lang\":\"zh_CN\"}],\"key\":\"option-1681365788539\"},{\"value\":[{\"text\":\"其他\",\"lang\":\"zh_CN\"}],\"key\":\"option-1681365801818\"}],\"op_relations\":[],\"type\":\"single\"}}},{\"property\":{\"un_print\":0,\"control\":\"Text\",\"require\":1,\"id\":\"Text-1681365832411\",\"placeholder\":[{\"text\":\"\",\"lang\":\"zh_CN\"}],\"title\":[{\"text\":\"客户名称\",\"lang\":\"zh_CN\"}]}},{\"property\":{\"un_print\":0,\"control\":\"Textarea\",\"require\":0,\"id\":\"Textarea-1681365852893\",\"placeholder\":[{\"text\":\"\",\"lang\":\"zh_CN\"}],\"title\":[{\"text\":\"功能描述\",\"lang\":\"zh_CN\"}]}},{\"property\":{\"un_print\":0,\"control\":\"File\",\"require\":1,\"id\":\"File-1681365876126\",\"placeholder\":[{\"text\":\"\",\"lang\":\"zh_CN\"}],\"title\":[{\"text\":\"立项申请表\",\"lang\":\"zh_CN\"}]},\"config\":{\"file\":{\"is_only_photo\":0}}},{\"property\":{\"un_print\":0,\"control\":\"Textarea\",\"require\":0,\"id\":\"Textarea-1681365903342\",\"placeholder\":[{\"text\":\"\",\"lang\":\"zh_CN\"}],\"title\":[{\"text\":\"其他\",\"lang\":\"zh_CN\"}]}}]},\"errmsg\":\"ok\"}";

    private static Map<String,String> ItemType=new HashMap<>();

    private static Map<String,String> ProductType=new HashMap<>();

    static {
        ItemType.put("公司自研","option-1681365666202");
        ItemType.put("客户定制","option-1681365666203");


        ProductType.put("天线","option-1681365708082");
        ProductType.put("标签","option-1681365708083");
        ProductType.put("读写器","option-1681365773483");
        ProductType.put("门禁","option-1681365778138");
        ProductType.put("智能装装备","option-1681365788539");
        ProductType.put("其他","option-1681365801818");
        ProductType.put("装备","option-1681365788539");
    }

    public static Map<String, String> getItemType() {
        return ItemType;
    }

    public static Map<String, String> getProductType() {
        return ProductType;
    }

    public ProductApprovalTemplate(String creator_userid){
        this.creator_userid=creator_userid;
        this.dataJSON=new JSONObject();
    }

    public ProductApprovalTemplate setUse_template_approver(Integer use_template_approver) {
        this.use_template_approver = use_template_approver;
        return this;
    }

    public ProductApprovalTemplate setChoose_department(String choose_department) {
        this.choose_department = choose_department;
        return this;
    }

    public ProductApprovalTemplate AddApproverItem(Integer attr,List<String> userIdList){
        if (attr!=null && userIdList!=null){
            if (this.approver==null){
                this.approver = new JSONArray();
            }
            JSONObject approveJson=new JSONObject();
            approveJson.put("attr",attr);
            approveJson.put("userid",userIdList);
            this.approver.add(approveJson);
        }
        return this;
    }

    public ProductApprovalTemplate AddApproverItem(Integer attr,String userAccount){
        List<String> userList=new ArrayList<>();
        userList.add(userAccount);
        return AddApproverItem(attr,userList);
    }

    public ProductApprovalTemplate AddNotifyerItem(String notifyer){
        if (this.notifyer==null){
            this.notifyer=new ArrayList<>();
        }
        this.notifyer.add(notifyer);
        return this;
    }

    public ProductApprovalTemplate setNotifyer(List<String> notifyer) {
        this.notifyer = notifyer;
        return this;
    }

    public ProductApprovalTemplate setNotify_type(Integer notify_type) {
        this.notify_type = notify_type;
        return this;
    }

    private void CreateControllers(){
         JSONObject templateJSON=JSONObject.parseObject(TEMPLATE_STR);
         if (templateJSON.containsKey("template_content")){
             if (this.apply_data==null){
                 this.apply_data=new JSONObject();
             }
             JSONObject contentsObj = templateJSON.getJSONObject("template_content");
             final JSONArray controlsArray = contentsObj.getJSONArray("controls");
             if (controlsArray!=null && controlsArray.size()>0 && ControlValue!=null && ControlValue.size()>0) {
                 JSONArray contentsArray=new JSONArray();
                 for (int index = 0; index < controlsArray.size();index++) {
                     JSONObject dataObject = controlsArray.getJSONObject(index);
                     JSONObject property = dataObject.getJSONObject("property");
                     String id = property.getString("id");
                     String control=property.getString("control");
                     JSONObject valueObj = ControlValue.get(id);
                     if (valueObj!=null){
                         valueObj.put("control",control);
                         valueObj.put("id",id);
                         contentsArray.add(valueObj);
                     }
                 }
                 this.apply_data.put("contents",contentsArray);
             }
         }
    }

    public ProductApprovalTemplate setControlValue(Map<String, JSONObject> controlValue) {
        ControlValue = controlValue;
        return this;
    }

    public ProductApprovalTemplate AddSummaryInfo(String text,String lang){
        if (!StringUtils.isNullOfEmpty(text)){
            if (this.summary_list==null){
                this.summary_list=new JSONArray();
            }
            JSONObject summaryInfo=new JSONObject();

            JSONArray infoArray=new JSONArray();

            JSONObject infoItem=new JSONObject();

            infoItem.put("text",text);
            infoItem.put("lang",StringUtils.isNullOfEmpty(lang)?"zh_CN":lang);

            infoArray.add(infoItem);

            summaryInfo.put("summary_info",infoArray);

            this.summary_list.add(summaryInfo);
        }
        return this;
    }


    public ProductApprovalTemplate Build(){

        if (this.ControlValue==null){
            this.ErrorMsg="控件参数不能为空";
            this.Success=false;
            return this;
        }
        if (StringUtils.isNullOfEmpty(TEMPLATE_STR)){
            this.ErrorMsg="模板不能为空";
            this.Success=false;
            return this;
        }
        if (StringUtils.isNullOfEmpty(TEMPLATE_ID)){
            this.ErrorMsg="模板ID不能为空";
            this.Success=false;
            return this;
        }
        if (this.approver==null){
            this.ErrorMsg="审批人不能为空";
            this.Success=false;
            return this;
        }
        if (this.approver.size()==0){
            this.ErrorMsg="审批人不能为空";
            this.Success=false;
            return this;
        }
        if (this.notifyer!=null){
//            1-提单时抄送（默认值）； 2-单据通过后抄送；3-提单和单据通过后抄送
            if (this.notify_type>=1 && this.notify_type<=3){
                if (this.use_template_approver!=0){
                    this.ErrorMsg="如果需要抄送，use_template_approver只能为0";
                    this.Success=false;
                    return this;
                }
            }else{
                this.ErrorMsg="抄送的值错误，参考范围1-3，1-提单时抄送（默认值）； 2-单据通过后抄送；3-提单和单据通过后抄送";
                this.Success=false;
                return this;
            }
        }
        if (this.summary_list==null && this.summary_list.size()==0){
            this.ErrorMsg="缺少summary_list参数";
            this.Success=false;
            return this;
        }

        this.CreateControllers();
        this.dataJSON=new JSONObject();
        this.dataJSON.put("creator_userid",this.creator_userid);
        this.dataJSON.put("template_id",TEMPLATE_ID);
        this.dataJSON.put("use_template_approver",this.use_template_approver);
        if (!StringUtils.isNullOfEmpty(this.choose_department)) {
            this.dataJSON.put("choose_department", this.choose_department);
        }
        this.dataJSON.put("approver",this.approver);
        if (this.notify_type>=1 && this.notify_type<=3){
            this.dataJSON.put("notify_type",this.notify_type);
        }
        if (this.notifyer!=null && this.notifyer.size()>0) {
            this.dataJSON.put("notifyer", this.notifyer);
        }
        this.dataJSON.put("apply_data",this.apply_data);
        this.dataJSON.put("summary_list",this.summary_list);

        this.Success=true;
        return this;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public boolean isSuccess() {
        return Success;
    }

    public JSONObject getDataJSON() {
        return dataJSON;
    }
}
