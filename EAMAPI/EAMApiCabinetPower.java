package com.bwzn.xssmg.help.EAMAPI;

import org.springframework.stereotype.Service;

/**查询EAM借机权限接口**/
@Service("EAMApiCabinetPower")
public final class EAMApiCabinetPower extends EAMApiBase {

    private String userCode;

    private String rightType;

    public EAMApiCabinetPower() {
        super("ifsgeamapi/jxs/smartCabinet/protoRolePower");
    }

    public EAMApiCabinetPower setRightType(String rightType) {
        this.rightType = rightType;
        super.AddJsonParams("rightType", this.rightType);
        return this;
    }

    public EAMApiCabinetPower setUserCode(String userCode) {
        this.userCode = userCode;
        super.AddJsonParams("userCode",this.userCode);
        return this;
    }

    public void setParams(String userCode){
        this.userCode=userCode;
        this.rightType="borrow_right";
        super.AddJsonParams("userCode",this.userCode);
        super.AddJsonParams("rightType",this.rightType);
    }
}
