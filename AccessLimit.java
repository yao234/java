package com.bwzn.xssmg.help;

import lombok.Data;

import java.util.Date;

@Data
public class AccessLimit {

    public Date startDate;
    public Date currentDate;
    public Date endDate;

    public int AccessCount;

    public int LimitCount;

    public boolean IsAccess(){
        return this.currentDate.compareTo(this.endDate)<=0?this.getAccessCount()<=this.getLimitCount():false;
    }

    public boolean IsReSet(){
        return this.currentDate.compareTo(this.endDate)>0;
    }
}
