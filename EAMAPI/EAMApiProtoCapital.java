package com.bwzn.xssmg.help.EAMAPI;

import org.springframework.stereotype.Service;

/**样机资产查询接口**/
@Service("EAMApiProtoCapital")
public final class EAMApiProtoCapital extends EAMApiBase {
    private String imei;
    private String pcba;
    private String sn;

    public EAMApiProtoCapital() {
        super("ifsgeamapi/jxs/lims/queryProtoCard");
    }

    public EAMApiProtoCapital setImei(String imei) {
        this.imei = imei;
        super.AddJsonParams("imei",this.imei);
        return this;
    }

    public EAMApiProtoCapital setPcba(String pcba) {
        this.pcba = pcba;
        super.AddJsonParams("pcba",this.pcba);
        return this;
    }

    public EAMApiProtoCapital setSn(String sn) {
        this.sn = sn;
        super.AddJsonParams("sn",this.sn);
        return this;
    }
}
