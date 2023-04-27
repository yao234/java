package com.bwzn.xssmg.help.EAMAPI;

import com.bwzn.xssmg.help.EAMAPI.Model.ProtoBorrowOrBackDetailModel;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("EAMApiProtoBorrowOrBack")
public class EAMApiProtoBorrowOrBack  extends  EAMApiBase{

    private String docType; //单据类型 借机:borrow 还机:back
    private String protoUserCode; //样机管理员工号，责任人
    private Integer docAmount; // 借用/存入数量
    private String siteCode; //位置编码
    private String cabinetCode; //大机柜编码
    private String cabinetSubCode; //柜格编码
    private String editUserCode; //借机人/还机人工号
    private List<ProtoBorrowOrBackDetailModel> requestDetail;

    public EAMApiProtoBorrowOrBack() {
        super("ifsgeamapi/jxs/smartCabinet/protoBorrowOrBack");
    }

    public EAMApiProtoBorrowOrBack setDocType(String docType) {
        this.docType = docType;
        super.AddJsonParams("docType",this.docType);
        return this;
    }

    public EAMApiProtoBorrowOrBack setProtoUserCode(String protoUserCode) {
        this.protoUserCode = protoUserCode;
        super.AddJsonParams("protoUserCode",this.protoUserCode);
        return this;
    }

    public EAMApiProtoBorrowOrBack setDocAmount(Integer docAmount) {
        this.docAmount = docAmount;
        super.AddJsonParams("docAmount",this.docAmount);
        return this;
    }

    public EAMApiProtoBorrowOrBack setCabinetCode(String cabinetCode) {
        this.cabinetCode = cabinetCode;
        super.AddJsonParams("cabinetCode",this.cabinetCode);
        return this;
    }

    public EAMApiProtoBorrowOrBack setEditUserCode(String editUserCode) {
        this.editUserCode = editUserCode;
        super.AddJsonParams("editUserCode",this.editUserCode);
        return this;
    }

    public EAMApiProtoBorrowOrBack setCabinetSubCode(String cabinetSubCode) {
        this.cabinetSubCode = cabinetSubCode;
        super.AddJsonParams("cabinetSubCode",this.cabinetSubCode);
        return this;
    }

    public EAMApiProtoBorrowOrBack setSiteCode(String siteCode) {
        this.siteCode = siteCode;
        super.AddJsonParams("siteCode",this.siteCode);
        return this;
    }

    public EAMApiProtoBorrowOrBack setRequestDetail(List<ProtoBorrowOrBackDetailModel> requestDetail) {
        this.requestDetail = requestDetail;
        super.AddJsonParams("requestDetail",this.requestDetail);
        return this;
    }
}
