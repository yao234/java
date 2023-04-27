package com.bwzn.xssmg.help.EAMAPI.Model;

import lombok.Data;

@Data
public final class BindRfidItemModel {
    private String imei;
    private String rfidKey;
    private boolean isRfid;

    public BindRfidItemModel(String imei, String rfidKey, boolean isRfid){
        this.imei = imei;;
        this.isRfid=isRfid;
        this.rfidKey=rfidKey;
    }
}
