package com.bwzn.xssmg.help;

public final class ThirdHelp {

    /**
     * 此类无用
     *
     *
     * ***/
    public static final String serverAddress  = "http://opm-uat.wanyol.com/api/";

    public static final String userName = "cabinet_user";

    public static final String userPwd  = "Oppo123!";

    private String serverMethod;

    public void setServerMethod(String serverMethod) {
        this.serverMethod = serverMethod;
    }

    public static String createUrl(String method){
        return String.join(serverAddress,method);
    }

    public String createurl(String method){
        return String.join(serverAddress,serverMethod);
    }
}
