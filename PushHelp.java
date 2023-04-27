package com.bwzn.xssmg.help;

import com.bwzn.xssmg.entities.BaseStaff;
import com.bwzn.xssmg.entities.PushInfo;
import com.bwzn.xssmg.entities.http.resp.BaseStaffInfo;
import com.bwzn.xssmg.entities.http.resp.CabinetInfo;
import com.bwzn.xssmg.service.BaseStaffService;
import com.bwzn.xssmg.service.RedisCacheService;
import com.bwzn.xssmg.service.TakenNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service("PushHelp")
public class PushHelp {

    @Autowired
    private TakenNoteService takenNoteService;

    @Autowired
    private BaseStaffService baseStaffService;

    private BaseStaff staff;

    private String cabinetId;

    private String cabinetInfo;

    private String ImeiList;

    private boolean ImeiState;



    public void Init(BaseStaff staff,String CabinetID,String CabinetInfo) {
        this.staff=staff;
        this.cabinetId=CabinetID;
        this.cabinetInfo=CabinetInfo;
    }

    public void ReSetImei(String ImeiList,boolean borrow) {
        this.ImeiList = ImeiList;
        this.ImeiState=borrow;
    }

    public void AddPushReport(boolean success){
        this.AddPushReport(success,success?"推送成功":"推送失败");
    }

    public void AddPushReport(boolean success,String Msg){
        this.AddPushReport(success,Msg,PushTypeHelp.TT);
    }

    public void AddPushReport(boolean success,int PushType){
        this.AddPushReport(success,success?"推送成功":"推送失败",PushType);
    }

    public void AddPushReport(boolean success,String Msg,int PushType){
        String userId="";
        if (this.staff!=null){
            userId=this.staff.getF_Id();
        }
        this.AddPushReport(success,PushType,userId,Msg);
    }

    public void AddPushReport(boolean success,int PushType,String F_UserId){
        this.AddPushReport(success,PushType,F_UserId,success?"推送成功":"推送失败");

    }

    public void AddPushReport(boolean success,int PushType,String F_UserId,String Msg){
        String user_id=StringUtils.isEmpty(F_UserId)?(this.staff!=null?this.staff.getF_Id():""):F_UserId;
        BaseStaffInfo baseStaff= baseStaffService.findStaffInfoById(user_id);
        String user_name=baseStaff!=null?baseStaff.getF_FullName():"";
        this.AddPushReport(success,PushType,user_id,user_name,Msg,this.cabinetId,this.cabinetInfo,ImeiList,ImeiState);
    }

    private void AddPushReport(boolean success,int PushType,String F_UserId,String F_UserName,String Msg,String cabinetId,String cabinetInfo,String ImeiList,boolean ImeiState){
        PushInfo pushInfo=new PushInfo();
        pushInfo.setF_Id(UUID.randomUUID().toString());
        pushInfo.setF_PushState(success);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate=dateFormat.format(date);

        pushInfo.setF_Date(currentDate);
        pushInfo.setF_UserId(F_UserId);
        pushInfo.setF_UserName(F_UserName);
        pushInfo.setF_PushType(PushType);
        pushInfo.setF_Msg(Msg);
        pushInfo.setF_CabinetId(cabinetId);
        pushInfo.setF_CabinetInfo(cabinetInfo);
        pushInfo.setF_IMEI(ImeiList);
        pushInfo.setF_ImeiState(ImeiState);

        takenNoteService.AddPushReport(pushInfo);
    }
}
