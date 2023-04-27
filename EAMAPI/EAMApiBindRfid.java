package com.bwzn.xssmg.help.EAMAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bwzn.xssmg.help.EAMAPI.Model.BindRfidItemModel;
import com.bwzn.xssmg.help.EAMApiHelp;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("EAMApiBindRfid")
public class EAMApiBindRfid extends EAMApiBase {
    public EAMApiBindRfid() {
        super("ifsgeamapi/jxs/smartCabinet/bindingRfId");
    }

    public void Add(String imei,String rfidKey,boolean isRfid){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("imei",imei);
        jsonObject.put("rfidKey",rfidKey);
        jsonObject.put("isRfid",isRfid?"1":"0"); //是否绑定rfid
        super.AddJsonItem(jsonObject);
    }
}
