package com.bwzn.xssmg.help.EAMAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bwzn.xssmg.entities.ProtoType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//样机业务单据查询
@Service("EAMApiQueryProtoOrderDoc")
public class EAMApiQueryProtoOrderDoc extends EAMApiBase {
    private String beginDate;//开始时间
    private String endDate;//结束时间
    private Integer pageIndex;//页号
    private String docType;//单据类型  1：apply 整机领用 2：borrow 借机申请 3：tran 仓库调拨
    private String isQualityReceive;//是否品质部代领 1：是品质部代领，0：非品质部代领
    private String docCode;//单号
    private Integer pageSize;//页面大小

    //ifsgeamapi/jxs/lims/queryProtoOrderDoc
    public EAMApiQueryProtoOrderDoc() { super("ifsgeamapi/jxs/lims/queryProtoOrderDoc"); }

    public EAMApiQueryProtoOrderDoc setDocType(String docType) {
        this.docType = docType;
        super.AddJsonParams("docType",this.docType);
        return this;
    }

    public EAMApiQueryProtoOrderDoc setBeginDate(String beginDate) {
        this.beginDate = beginDate;
        super.AddJsonParams("beginDate",this.beginDate);
        return this;
    }

    public EAMApiQueryProtoOrderDoc setEndDate(String endDate) {
        this.endDate = endDate;
        super.AddJsonParams("endDate",this.endDate);
        return this;
    }

    public EAMApiQueryProtoOrderDoc setDocCode(String docCode) {
        this.docCode = docCode;
        super.AddJsonParams("docCode",this.docCode);
        return this;
    }

    public EAMApiQueryProtoOrderDoc setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        super.AddJsonParams("pageIndex",this.pageIndex);
        return this;
    }

    public EAMApiQueryProtoOrderDoc setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        super.AddJsonParams("pageSize",this.pageSize);
        return this;
    }

    public EAMApiQueryProtoOrderDoc setIsQualityReceive(String isQualityReceive) {
        this.isQualityReceive = isQualityReceive;
        super.AddJsonParams("isQualityReceive",this.isQualityReceive);
        return this;
    }


    public void queryProtoOrder(List<String> AddProtoList,String beginDate,String endDate,String docType,String isQualityReceive){
        if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(docType)){
            return;
        }

        loopQueryProtoOrder(AddProtoList,beginDate,endDate,docType,isQualityReceive,1,10);
    }

    private void loopQueryProtoOrder(List<String> list,String beginDate,String endDate,String docType,String isQualityReceive,int currentIndex,int pageSize){

        this.setDocType(docType);
        this.setIsQualityReceive(isQualityReceive);
        this.setPageIndex(currentIndex);
        this.setPageSize(pageSize);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);

        JSONObject post = this.Post();
        if (post!=null){
            if (post.containsKey("code")){
                if (post.getInteger("code")==200){
                    if (post.getBoolean("success")){
                        if (post.containsKey("total")) {
                            Integer total = post.getInteger("total");
                            if (total != null && total>0) {
                                JSONArray data = post.getJSONArray("data");
                                if (data!=null && data.size()>0){
                                    for (int index=0;index<data.size();index++){
                                        JSONObject childData = data.getJSONObject(index);
                                        String imei = childData.getString("imei");
                                        String pcba = childData.getString("pcba");
                                        String sn = childData.getString("sn");
                                        if (StringUtils.isEmpty(imei)){
                                            continue;
                                        }
//                                        if (StringUtils.isEmpty(imei) && StringUtils.isEmpty(pcba) && StringUtils.isEmpty(sn)){
//                                           continue;
//                                        }
                                        //String docCode = childData.getString("docCode");
                                        //String docType = childData.getString("docType");
                                        String deptCode = childData.getString("deptCode");
                                        String houseCode = childData.getString("houseCode");
                                        String docDesc = childData.getString("docDesc");
                                        String spCode = childData.getString("spCode");
                                        String spName = childData.getString("spName");
                                        String projectCode = childData.getString("projectCode");
                                        String projectNumberName = childData.getString("projectNumberName");
                                        String stageName = childData.getString("stageName");
                                        String memo = childData.getString("memo");

//                                        ProtoType protoType=new ProtoType();
//                                        protoType.setF_IMEI_ID(imei);
//                                        protoType.setF_ProjectCode(projectCode);
//                                        protoType.setF_ProductSn(sn);
//                                        protoType.setF_EnCode(spCode);
//                                        protoType.setF_FullName(spName);
                                        list.add(imei);
                                    }

                                    loopQueryProtoOrder(list, beginDate, endDate, docType, isQualityReceive, currentIndex+1, pageSize);
                                    //loopQueryProtoOrder(list, docType, isQualityReceive, currentIndex+1, pageSize);
                                }
                                else{
                                    return;
                                }
                            }
                            else{
                                return;
                            }
                        }else {
                            return;
                        }
                    }else {
                        return;
                    }
                }
                else {
                    return;
                }
            }else {
                return;
            }
        }
        return;

    }


}
