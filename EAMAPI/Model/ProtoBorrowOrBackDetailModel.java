package com.bwzn.xssmg.help.EAMAPI.Model;

import lombok.Data;

@Data
public final class ProtoBorrowOrBackDetailModel {
    private String imei;

    public ProtoBorrowOrBackDetailModel(String imei){
        this.imei=imei;
    }
}
