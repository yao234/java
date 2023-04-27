package com.bwzn.xssmg.help.Manager;

import org.apache.poi.openxml4j.opc.PackagingURIHelper;

import java.util.HashMap;
import java.util.HashSet;

//接口管理
public final class ApiManager {
    //接口管理,键代表接口名,值代表是否可用
    private static HashMap<String,Boolean> apiMap=new HashMap<>();

    private static HashMap<String, HashSet<String>> apiManagerList=new HashMap<>();

//    //本地 和 oppo正式环境
    private static final String  WEB_AREA_API_KEY = "7BAEC83DB88D177145F3BFEED5C4B961";

    private static final String  WEB_ABNORMAL_API_KEY = "BD6F895F0F9D8B2ECCD1378C8786F1D8";

    private static final String  WEB_CABINET_TYPE_API_KEY = "D829488F6C6334993751D5DB50E7B4B8";

    private static final String  WEB_PROTOTYPE_API_KEY = "445DD26A86379F6EB6547952AE2AD311";

    private static final String  WEB_CABINET_API_KEY = "98E0C96BADAD1BBBC9EE6D0BFD17000F";

    private static final String  WEB_DICTIONARY_API_KEY = "B8AF357EB159CA7AA72A1402EB5D477B";

    private static final String  WEB_CABINET_USE_API_KEY = "982F5F4535477759C797C2E9D04EE0F4";

    private static final String  WEB_PROTOTYPE_USE_API_KEY = "8E15EDD6914AA1623EA2118FD5F90F53";

    private static final String  WEB_STAFF_USE_API_KEY = "1C272026C68A6A3E3C2CAE70ED09B891";

    private static final String  WEB_PROTO_MONITOR_API_KEY = "C5F97C076A1175A3B8409BFA1637703F";

    private static final String  WEB_SUBSCRIBE_API_KEY = "69711BCB4C1BB875EC8DE9B48BA66374";

    private static final String  WEB_CABINET_DAY_USE_API_KEY = "51E10A90C5791CAF01BFE5BC51A33732";

    private static final String  WEB_PROTO_USETOTAL_API_KEY = "35E18B7F2B9126D4C141423EF17CBFE0";

    private static final String  WEB_LOGINNOTE_API_KEY = "F055754D879B6F4623B0AF2D77E1D3D0";

    private static final String  WEB_ERRRORPUSH_API_KEY = "F56066FB025E03C325D8ED8B8E995BB4";

    private static final String  WEB_ROLE_MANAGER_API_KEY = "02E4F36B4ACE41022CA19B8C034CF683";

    private static final String  WEB_DEPT_MANAGER_API_KEY = "897AD64DB69F2E5004BE6352522316CE";

    private static final String  WEB_STAFF_MANAGER_API_KEY = "FB807569B779372617E6BF3DD065B4C6";

//    //oppo 测试环境
//    private static final String  WEB_AREA_API_KEY = "9E3F0D307020DEADA157EBD164DFA131";
//
//    private static final String  WEB_ABNORMAL_API_KEY = "9A7D689E48517913C39CB9BBE843AAA3";
//
//    private static final String  WEB_CABINET_TYPE_API_KEY = "DD7B47F10560895FF5A4FE2B7A6D2EE3";
//
//    private static final String  WEB_PROTOTYPE_API_KEY = "0FD3DB5628974CEEB8C61B13E81347F7";
//
//    private static final String  WEB_CABINET_API_KEY = "04B4C4E06DB42FB6819B20BB64905C01";
//    private static final String  WEB_DICTIONARY_API_KEY = "B8AF357EB159CA7AA72A1402EB5D477B";
//
//    private static final String  WEB_CABINET_USE_API_KEY = "B738953225828ABEF37B97275BEC1A5C";
//    private static final String  WEB_PROTOTYPE_USE_API_KEY = "23115D17110AD4BBEF9CFF8165AAEFC4";
//    private static final String  WEB_STAFF_USE_API_KEY = "0508DD0FB5FF86D6DD227819B43A3EB0";
//
//    private static final String  WEB_PROTO_MONITOR_API_KEY = "DA4CC61209B6EBB7C0EC2770A13BBE3C";
//
//    private static final String  WEB_SUBSCRIBE_API_KEY = "69711BCB4C1BB875EC8DE9B48BA66374";
//
//    private static final String  WEB_CABINET_DAY_USE_API_KEY = "C7CB740CC70A4F0CA3A1ACFD769E72AC";
//
//    private static final String  WEB_PROTO_USETOTAL_API_KEY = "68EC9C534BD4CC8709ED30BA4B4F8D5A";
//
//    private static final String  WEB_LOGINNOTE_API_KEY = "76C20C2B07E4E1DACA31D33E642D50C3";
//
//    private static final String  WEB_ERRRORPUSH_API_KEY = "D8E5ACA4B5068B390DB49B13CAB3D800";
//
//    private static final String  WEB_ROLE_MANAGER_API_KEY = "85025B364093F0B7D08843718FA95353";
//
//    private static final String  WEB_DEPT_MANAGER_API_KEY = "41B4926A24015EC080515309AC005AFC";
//
//    private static final String  WEB_STAFF_MANAGER_API_KEY = "4ED83F2378A9F500AA9765180BFC9161";

    static {
        //初始化API
        InitAreaApi();
        InitCabinetApi();
        InitCabinetTypeApi();
        InitDepartApi();
        InitDictionaryApi();
        InitAuthorityApi();
        InitProtoTypeApi();
        InitStaffApi();
        InitTakeNoteApi();
        //InitLoginNotesApi();
        InitAbnormalApi();
        InitUserLoginApi();
        InitLoginNotesApi();
        InitThridApi();
        InitRoleApi();
        InitSubScribeApi();
        InitHomeApi();
        InitTestItemApi();


        //初始化网页可调用的接口
        InitWebApi();
        //初始化通用接口
        InitCommonApi();
        //初始化安卓端调用接口
        InitAndroidApi();
        //初始化发卡软件
        InitSendCardApi();
    }
    //初始化测试项
    static void InitTestItemApi(){
        String root="/api/testItem";
        apiMap.put(root+"/listTestItems",true);
        apiMap.put(root+"/listTestItemsMoblie",true);
        apiMap.put(root+"/addTestItem",true);
        apiMap.put(root+"/updateProtoTypeTestItem",true);
        apiMap.put(root+"/updateTestItem",true);

    }
    //区域管理
    static void InitAreaApi(){
        String root="/api/area";
        apiMap.put(root+"/updateArea",true);
        apiMap.put(root+"/delArea",true);
        apiMap.put(root+"/getAllAreas",true);
        apiMap.put(root+"/getAllAreasCabinets",true);
        apiMap.put(root+"/getChildAreas",true);
        apiMap.put(root+"/getAreasBySome",true);
    }
    //柜体管理
    static void InitCabinetApi(){
        String root="/api/secretcabinet";
        apiMap.put(root+"/queryIsSetPower",true);
        apiMap.put(root+"/getAllCabinets",true);
        apiMap.put(root+"/exportAllCabinets",true);
        apiMap.put(root+"/addOrUpdateCabinet",true);
        apiMap.put(root+"/addCabinetManager",true);
        apiMap.put(root+"/findCabinetManager",true);
        apiMap.put(root+"/updateCabinetManager",true);
        apiMap.put(root+"/delCabinet",true);


        apiMap.put(root+"/CellInventory",true);
        apiMap.put(root+"/startAbnormal",true);
        apiMap.put(root+"/GetSPESEPrototypePageList",true);
        apiMap.put(root+"/getAllCellInfosByCabinetId",true);
        apiMap.put(root+"/getAllCellInfosByCabinetFId",true);




        apiMap.put(root+"/assginCell",true);
        apiMap.put(root+"/assginCellByIdNo",true);
        apiMap.put(root+"/assginCells",true);


        apiMap.put(root+"/getAllCellsByStaff",true);
        apiMap.put(root+"/getAllByStaffByCell",true);
        apiMap.put(root+"/getCabinetInfo",true);

        apiMap.put(root+"/getStaffCellInfo",true);
        apiMap.put(root+"/heartbeat",true);
        apiMap.put(root+"/sendTT",true);
    }
    //柜体类型管理
    static void InitCabinetTypeApi(){
        String root="/api/cabinettype";
        apiMap.put(root+"/addCabinetType",true);
        apiMap.put(root+"/delCabinetType",true);
        apiMap.put(root+"/updateCabinetType",true);
        apiMap.put(root+"/findCabinetTypeBySome",true);
        apiMap.put(root+"/getAreasBySome",true);

    }
    //数据字典
    static void InitDictionaryApi(){
        String root="/api/dictionary";
        apiMap.put(root+"/addDictionary",true);
        apiMap.put(root+"/deleteDictionaryItem",true);
        apiMap.put(root+"/listItems",true);
    }
    //样机管理
    static void InitProtoTypeApi(){
        String root="/api/prototype";
        apiMap.put(root+"/addProtoType",true);
        apiMap.put(root+"/delProtoType",true);
        apiMap.put(root+"/exportAllProtoTypes",true);



        apiMap.put(root+"/findAllInProtoTypes",true);
        apiMap.put(root+"/findAllProtoTypes",true);
        apiMap.put(root+"/findAllProtoTypes2",true);




        apiMap.put(root+"/findProtoTypesByCellId",true);
        apiMap.put(root+"/import",true);
        apiMap.put(root+"/queryDepartUnClockCount",true);

        apiMap.put(root+"/queryDepartUsingCount",true);

        apiMap.put(root+"/queryDepartUsingTime",true);
        apiMap.put(root+"/queryFreeProtoType",true);
        apiMap.put(root+"/queryProtoUsingCount",true);


        apiMap.put(root+"/queryProtoUsingTime",true);
        apiMap.put(root+"/queryUnclockProtoCount",true);
        apiMap.put(root+"/updateProtoType",true);

        apiMap.put("/api/system/deviceList/push",true);
    }
    //人员管理
    static void InitStaffApi(){

        apiMap.put("/api/staff/add",true);
        apiMap.put("/api/staff/changePWD",false);
        apiMap.put("/api/staff/delete",false);



        apiMap.put("/api/staff/exportAllStaff",true);
        apiMap.put("/api/staff/findOneStaff",true);
        apiMap.put("/api/staff/findStaffCell",true);


        apiMap.put("/api/staff/getAllStaff",true);
        apiMap.put("/api/staff/queryLoginInfo",true);
        apiMap.put("/api/staff/resetPWD",false);


        apiMap.put("/api/staff/roleupdate",true);
        apiMap.put("/api/staff/update",true);
        apiMap.put("/api/system/staff/import",true);
        apiMap.put("/api/system/stafflist/push",true);

        apiMap.put("/api/user/BWZNEnrollUserInfo",true);
        apiMap.put("/api/user/BWZNSetUserInfo",true);



    }
    //使用记录
    static void InitTakeNoteApi(){
        String root="/api/takenote";
        apiMap.put(root+"/cabinetDayUseList",true);
        apiMap.put(root+"/exportAllFailPush",true);
        apiMap.put(root+"/exportAllStaffUse",true);
        apiMap.put(root+"/getAllCellsUseInfo",true);
        apiMap.put(root+"/getAllNotesBySome",true);
        apiMap.put(root+"/getCabinetTakenInfo",true);
        apiMap.put(root+"/getNotesByStaffId",true);
        apiMap.put(root+"/getUseTimesByScretCabinetId",true);
        apiMap.put(root+"/listUseCountDepartment",true);
        apiMap.put(root+"/listUseCountProtoType",true);
        apiMap.put(root+"/prototypeUseList",true);
        apiMap.put(root+"/QueryFailPush",true);
        apiMap.put(root+"/UpdateOpmPushState",true);
    }
    //异常归还
    static void InitAbnormalApi(){
        String root="/api/abnormalreturn";
        apiMap.put(root+"/GetAbnormalDeptEcharts",true);
        apiMap.put(root+"/GetAbnormalPersonEcharts",true);
        apiMap.put(root+"/getAllbnormalReturnBySome",true);
        apiMap.put(root+"/handleAbnormalReturn",true);
    }
    //授权管理
    static void InitAuthorityApi(){
        String root="/api/authority";
        apiMap.put(root+"/getAllViews",true);
        apiMap.put(root+"/getViews",true);
        apiMap.put(root+"/getViewsByRoleId",true);
        apiMap.put(root+"/updateAuthority",true);
    }
    //用户登录
    static void InitUserLoginApi(){
        String root="/api";
        apiMap.put(root+"/auth/GetAuth",true);
        apiMap.put(root+"/auth/GetToken",true);
        apiMap.put(root+"/secretcabinet/GetSecretCabinetApk",true);
        apiMap.put(root+"/sso/ssoGetTokenByCode",true);

        apiMap.put(root+"/user/BWZNLogin",true);
        apiMap.put(root+"/user/LoginByCardNo",true);
        apiMap.put(root+"/user/logout",true);
    }
    //登录日志
    static void InitLoginNotesApi(){
        String root="/api/loginnote";
        apiMap.put(root+"/delLoginNote",true);
        apiMap.put(root+"/findAllNotes",true);
        apiMap.put("/api/secretcabinet/PrototypePhoto",true);
    }
    //第三方接口
    static void InitThridApi(){
        String root="/api";
        apiMap.put(root+"/autoCancelSubScribe",false);
        apiMap.put(root+"/client/login",false);

        apiMap.put(root+"/eam/getAreaInfo",true);
        apiMap.put(root+"/market/eam/queryCabinetManager",true);


        apiMap.put(root+"/market/queryPositionInformation",false);

        apiMap.put(root+"/market/queryProtoCapital",false);

        apiMap.put(root+"/op/protoType/searchProtoTypeInfo",true);

        apiMap.put(root+"/op/staff/searchStaffInfo",true);

        apiMap.put(root+"/op/staff/searchStaffInfoByIdNo",true);

        apiMap.put(root+"/opm/GetCabinetMsg",false);
        apiMap.put(root+"/staff/searchStaffInfoByCardNo",true);

        apiMap.put(root+"/system/departList/push",true);

        apiMap.put(root+"/system/users/getOppoUsers",false);

        apiMap.put(root+"/system/users/oppoTTTest",false);


        apiMap.put(root+"/testCellventroy",false);

        apiMap.put(root+"/testPosition",false);

        apiMap.put("/devicePublish/findByEpcId",true);
        apiMap.put("/devicePublish/queryDevicePublishBySI",true);
        apiMap.put("/devicePublish/reissueEpcidBySI",true);
        apiMap.put("/ext/staff/getVerificationCode",true);
        apiMap.put(root+"/handBoard/findByEpcId",true);
        apiMap.put("/issueCard/sendCardBySI",true);
        apiMap.put(root+"/performance/push",true);


        apiMap.put(root+"/protoTest/EditionProtoName",true);
        apiMap.put("/staff/staffLogin",true);

    }
    //角色管理
    static void InitRoleApi(){
        String root="/api/role";
        apiMap.put(root+"/addRole",true);
        apiMap.put(root+"/addStaffToRole",true);
        apiMap.put(root+"/assignRole",true);
        apiMap.put(root+"/cancelRoleByStaff",true);

        apiMap.put(root+"/delRole",true);
        apiMap.put(root+"/getAllRoles",true);

        apiMap.put(root+"/getRole",true);
        apiMap.put(root+"/getStaffByRoleId",true);

        apiMap.put(root+"/updateRole",true);
    }
    //部门管理
    static void InitDepartApi(){
        String root="/api/department";
        apiMap.put(root+"/addDepartment",true);
        apiMap.put(root+"/delDepartment",true);
        apiMap.put(root+"/getAllDepartments",true);
        apiMap.put(root+"/getAuthorityStaffByDepartment",true);
        apiMap.put(root+"/getDepartments",true);
        apiMap.put(root+"/getStaffByDepartment",true);
        apiMap.put(root+"/updateDepartment",true);
    }
    //预约管理
    static void InitSubScribeApi(){
        String root="/api/subscribe";
        apiMap.put(root+"/addSubscribe",true);
        apiMap.put(root+"/cancelSubscribe",true);
        apiMap.put(root+"/findSubscribeInfo",true);
        apiMap.put(root+"/listAll",true);

        apiMap.put(root+"/listDeadCountDepartment",true);
        apiMap.put(root+"/listDeadCountStaff",true);

        apiMap.put(root+"/setParams",true);

    }
    //首页接口
    static void InitHomeApi(){
        String root="/api/home";
        apiMap.put(root+"/getLatelyRecord",true);
        apiMap.put(root+"/getLatelyUseCount",true);
        apiMap.put(root+"/getSubscribeCount",true);
        apiMap.put(root+"/getSubscribeDeadCount",true);
        apiMap.put(root+"/getTakenCount",true);
    }
    //初始化网页接口 全部
    static void InitWebApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/staff/queryLoginInfo");
//        hashSet.add("/api/staff/findStaffCell");
//        hashSet.add("/api/staff/getAllStaff");
//        hashSet.add("/api/staff/findOneStaff");
//        hashSet.add("/api/staff/add");
//        hashSet.add("/api/staff/exportAllStaff");
//        hashSet.add("/api/staff/update");
//        hashSet.add("/api/staff/queryLoginInfo");
//
//        hashSet.add("/api/area/getAllAreas");
//        hashSet.add("/api/area/getChildAreas");
//        hashSet.add("/api/area/updateArea");
//        hashSet.add("/api/area/getAllAreasCabinets");
//
//        hashSet.add("/api/cabinettype/addCabinetType");
//
//        hashSet.add("/api/cabinettype/addCabinetType");
//        hashSet.add("/api/cabinettype/findCabinetTypeBySome");
//        hashSet.add("/api/cabinettype/updateCabinetType");
//
//        hashSet.add("/api/department/getDepartments");
//        hashSet.add("/api/department/getAllDepartments");
//        hashSet.add("/api/department/getpagelist");
//        hashSet.add("/api/department/getAuthorityStaffByDepartment");
//
//        hashSet.add("/api/prototype/findAllInProtoTypes");
//        hashSet.add("/api/prototype/findProtoTypesByCellId");
//        hashSet.add("/api/prototype/findAllProtoTypes2");
//        hashSet.add("/api/prototype/exportAllProtoTypes");
//        hashSet.add("/api/prototype/import");
//        hashSet.add("/api/prototype/queryProtoUsingCount");
//        hashSet.add("/api/prototype/queryProtoUsingTime");
//        hashSet.add("/api/prototype/queryUnclockProtoCount");
//
//        hashSet.add("/api/testItem/listTestItems");
//        hashSet.add("/api/testItem/addTestItem");
//        hashSet.add("/api/testItem/updateProtoTypeTestItem");
//
//        hashSet.add("/api/secretcabinet/getCabinetInfo");
//        hashSet.add("/api/secretcabinet/getAllCabinets");
//        hashSet.add("/api/secretcabinet/exportAllCabinets");
//        hashSet.add("/api/secretcabinet/findCabinetManager");
//        hashSet.add("/api/secretcabinet/delCabinet");
//        hashSet.add("/api/secretcabinet/queryIsSetPower");
//        hashSet.add("/api/secretcabinet/addOrUpdateCabinet");
//        hashSet.add("/api/secretcabinet/addCabinetManager");
//        hashSet.add("/api/secretcabinet/getAllCellInfosByCabinetId");
//        hashSet.add("/api/secretcabinet/getAllByStaffByCell");
//        hashSet.add("/api/secretcabinet/assginCellByIdNo");
//        hashSet.add("/api/secretcabinet/assginCell");
//        hashSet.add("/api/secretcabinet/getStaffCellInfo");
//
//        hashSet.add("/api/dictionary/listItems");
//        hashSet.add("/api/dictionary/deleteDictionaryItem");
//        hashSet.add("/api/dictionary/addDictionary");
//
//        hashSet.add("/api/takenote/getAllNotesBySome");
//        hashSet.add("/api/takenote/listUseCountProtoType");
//        hashSet.add("/api/takenote/listUseCountDepartment");
//        hashSet.add("/api/takenote/cabinetDayUseList");
//        hashSet.add("/api/takenote/prototypeUseList");
//        hashSet.add("/api/takenote/QueryFailPush");
//        hashSet.add("/api/takenote/exportAllFailPush");
//        hashSet.add("/api/takenote/getCabinetTakenInfo");
//        hashSet.add("/api/takenote/getUseTimesByScretCabintId");
//
//        hashSet.add("/api/loginnote/findAllNotes");
//
//        hashSet.add("/api/subscribe/listAll");
//        hashSet.add("/api/subscribe/listDeadCountStaff");
//        hashSet.add("/api/subscribe/listDeadCountDepartment");
//        hashSet.add("/api/subscribe/cancelSubscribe");
//
//        hashSet.add("/api/role/addStaffToRole");
//        hashSet.add("/api/role/getAllRoles");
//        hashSet.add("/api/role/addRole");
//        hashSet.add("/api/role/updateRole");
//        hashSet.add("/api/role/getAllRoles");
//        hashSet.add("/api/role/getStaffByRoleId");
//
        hashSet.add("/api/authority/getAllViews");
//        hashSet.add("/api/authority/updateAuthority");
//        hashSet.add("/api/authority/getViewsByRoleId");
        hashSet.add("/api/authority/getViews");
//
//        hashSet.add("/api/abnormalreturn/getAllbnormalReturnBySome");
//        hashSet.add("/api/abnormalreturn/GetAbnormalPersonEcharts");
//        hashSet.add("/api/abnormalreturn/GetAbnormalDeptEcharts");
//        hashSet.add("/api/abnormalreturn/handleAbnormalReturn");
//
//        hashSet.add("/api/op/staff/searchStaffInfo");
//        hashSet.add("/api/system/staff/import");
//
        hashSet.add("/api/home/getLatelyRecord");
        hashSet.add("/api/home/getLatelyUseCount");
        hashSet.add("/api/home/getSubscribeCount");
        hashSet.add("/api/home/getSubscribeDeadCount");
        hashSet.add("/api/home/getTakenCount");
//
       apiManagerList.put("WebServer",hashSet);
        InitWebAreaApi();
        InitWebAbnormalReturnApi();
        InitWebCabinetTypeApi();
        InitWebProtoTypeApi();
        InitWebCabinetApi();
        InitWebDictionaryApi();
        InitWebCabinetUseApi();
        InitWebProtoTypeUseApi();
        InitWebStaffUseApi();
        InitWebProtoMonitorApi();
        InitWebSubScribeTotalApi();
        InitWebCabinetDayUseApi();
        InitWebProtoUseApi();
        InitWebLoginNoteApi();
        InitWebErrorPushApi();
        InitWebRoleManagerApi();
        InitWebDeptManagerApi();
        InitWebStaffManagerApi();
    }
    //初始化通用的接口，此接口可以在没有登录的情况下调用
    static void InitCommonApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/auth/GetAuth");
        hashSet.add("/api/auth/GetToken");
        hashSet.add("/api/user/BWZNLogin");
        hashSet.add("/api/user/LoginByCardNo");
        hashSet.add("/api/sso/ssoGetTokenByCode");
        hashSet.add("/api/market/eam/queryCabinetManager");
        hashSet.add("/staff/staffLogin");
        hashSet.add("/ext/staff/getVerificationCode");
        hashSet.add("/api/secretcabinet/heartbeat");
        apiManagerList.put("Common",hashSet);
    }
    //初始化安卓端调用的接口
    static void InitAndroidApi(){
        HashSet<String> hashSet=new HashSet<>();
//        hashSet.add("/api/user/BWZNLogin");
//        hashSet.add("/api/user/LoginByCardNo");
        hashSet.add("/api/user/UserInfo");
        hashSet.add("/api/secretcabinet/GetSPESEPrototypePageList");
        hashSet.add("/api/secretcabinet/CellInventory");
        hashSet.add("/api/secretcabinet/CellInventoryEx");
        hashSet.add("/api/secretcabinet/PrototypePhoto");
        hashSet.add("/api/secretcabinet/GetSecretCabinetApk");
        hashSet.add("/api/secretcabinet/ExceptionFile");
        hashSet.add("/api/takenote/getNotesByStaffId");
        hashSet.add("/api/takenote/getAllNotesBySome");
        hashSet.add("/api/secretcabinet/heartbeat");
        hashSet.add("/api/secretcabinet/startAbnormal");
        hashSet.add("/api/testItem/listTestItems");
        hashSet.add("/api/testItem/updateTestItem");
        hashSet.add("/api/subscribe/addSubscribe");
        hashSet.add("/api/subscribe/cancelSubscribe");
        hashSet.add("/api/secretcabinet/checkCellInfo");
        hashSet.add("/api/subscribe/listAll");
        hashSet.add("/api/testItem/listTestItemsMoblie");
        hashSet.add("/api/prototype/queryFreeProtoType");
        hashSet.add("/api/secretcabinet/sendTT");


        apiManagerList.put("B48E3F8E7F30A62DF7D655FB0EAB732F",hashSet);
    }
    //初始化发卡软件调用的接口
    static void InitSendCardApi(){
        HashSet<String> hashSet=new HashSet<>();
//        hashSet.add("/ext/staff/getVerificationCode");
        hashSet.add("/issueCard/sendCardBySI");
        //hashSet.add("/api/staff/staffLogin");
        hashSet.add("/devicePublish/queryDevicePublishBySI");
        hashSet.add("/devicePublish/reissueEpcidBySI");
        hashSet.add("/secretcabinet/GetSecretCabinetApk");
        hashSet.add("/devicePublish/findByEpcId");

        apiManagerList.put("0D550B0847916C145737A2E1A9F91D3B",hashSet);
    }
    //初始化网页接口
    //初始化区域管理接口
    static void InitWebAreaApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/area/getAllAreas");
        hashSet.add("/api/area/getChildAreas");
        hashSet.add("/api/staff/getAllStaff");
        hashSet.add("/api/area/updateArea");
        apiManagerList.put(WEB_AREA_API_KEY,hashSet);
    }
    //初始化区域管理接口
    static void InitWebCabinetTypeApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/cabinettype/findCabinetTypeBySome");
        hashSet.add("/api/department/getpagelist");
        hashSet.add("/api/cabinettype/addCabinetType");
        hashSet.add("/api/cabinettype/updateCabinetType");
        apiManagerList.put(WEB_CABINET_TYPE_API_KEY,hashSet);
    }
    //初始化样机信息管理接口
    static void InitWebProtoTypeApi(){
        HashSet<String> hashSet=new HashSet<>();

        hashSet.add("/api/prototype/findAllProtoTypes2");
        hashSet.add("/api/prototype/exportAllProtoTypes");
        hashSet.add("/api/testItem/listTestItems");
        hashSet.add("/api/prototype/findAllProtoTypes2");
        hashSet.add("/api/staff/getAllStaff");
        hashSet.add("/api/testItem/updateProtoTypeTestItem");
        hashSet.add("/api/prototype/updateProtoType");
        hashSet.add("/api/testItem/addTestItem");

        apiManagerList.put(WEB_PROTOTYPE_API_KEY,hashSet);
    }
    //初始化柜体管理信息接口
    static void InitWebCabinetApi(){
        HashSet<String> hashSet=new HashSet<>();

        hashSet.add("/api/area/getAllAreas");
        hashSet.add("/api/secretcabinet/getAllCabinets");
        hashSet.add("/api/secretcabinet/getAllCellInfosByCabinetId");

        hashSet.add("/api/cabinettype/findCabinetTypeBySome");
        hashSet.add("/api/staff/getAllStaff");
        hashSet.add("/api/department/getAllDepartments");
        hashSet.add("/api/department/getAuthorityStaffByDepartment");
        hashSet.add("/api/staff/findStaffCell");

        hashSet.add("/api/secretcabinet/addOrUpdateCabinet");
        hashSet.add("/api/secretcabinet/exportAllCabinets");
        hashSet.add("/api/secretcabinet/delCabinet");
        hashSet.add("/api/secretcabinet/findCabinetManager");
        hashSet.add("/api/secretcabinet/getAllByStaffByCell");
        hashSet.add("/api/secretcabinet/addCabinetManager");
        hashSet.add("/api/secretcabinet/updateCabinetManager");
        hashSet.add("/api/secretcabinet/assginCellByIdNo");
        hashSet.add("/api/secretcabinet/assginCell");
        hashSet.add("/api/secretcabinet/queryIsSetPower");
        hashSet.add("/api/prototype/findProtoTypesByCellId");
        apiManagerList.put(WEB_CABINET_API_KEY,hashSet);
    }
    //初始数据字典接口
    static void InitWebDictionaryApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/dictionary/listItems");
        hashSet.add("/api/dictionary/deleteDictionaryItem");
        hashSet.add("/api/dictionary/addDictionary");
        apiManagerList.put(WEB_DICTIONARY_API_KEY,hashSet);
    }
    //初始化柜体使用记录
    static void InitWebCabinetUseApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/area/getAllAreasCabinets");
        hashSet.add("/api/secretcabinet/getCabinetInfo");
        hashSet.add("/api/takenote/getCabinetTakenInfo");
        hashSet.add("/api/takenote/getUseTimesByScretCabintId");
        hashSet.add("/api/prototype/findAllInProtoTypes");

        hashSet.add("/api/takenote/getAllNotesBySome");
//        hashSet.add("/api/prototype/queryUnclockProtoCount");
//        hashSet.add("/api/takenote/exportAllStaffUse");
        apiManagerList.put(WEB_CABINET_USE_API_KEY,hashSet);
    }
    //初始化样机使用记录
    static void InitWebProtoTypeUseApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/staff/getAllStaff");
        hashSet.add("/api/takenote/getAllNotesBySome");
        hashSet.add("/api/takenote/listUseCountProtoType");
        hashSet.add("/api/takenote/listUseCountDepartment");
        hashSet.add("/api/prototype/queryProtoUsingCount");
        hashSet.add("/api/prototype/queryProtoUsingTime");
        hashSet.add("/api/prototype/queryUnclockProtoCount");
        hashSet.add("/api/takenote/exportAllStaffUse");
        apiManagerList.put(WEB_PROTOTYPE_USE_API_KEY,hashSet);
    }
    //初始化员工使用记录
    static void InitWebStaffUseApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/staff/getAllStaff");
        hashSet.add("/api/takenote/getAllNotesBySome");
        hashSet.add("/api/takenote/exportAllStaffUse");
        apiManagerList.put(WEB_STAFF_USE_API_KEY,hashSet);
    }
    //初始化样机实时监控
    static void InitWebProtoMonitorApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/staff/getAllStaff");
        hashSet.add("/api/takenote/getAllNotesBySome");
        hashSet.add("/api/takenote/exportAllStaffUse");
        apiManagerList.put(WEB_PROTO_MONITOR_API_KEY,hashSet);
    }
    //初始化预约统计
    static void InitWebSubScribeTotalApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/department/getAllDepartments");
        hashSet.add("/api/subscribe/listAll");
        hashSet.add("/api/subscribe/listDeadCountStaff");
        hashSet.add("/api/subscribe/listDeadCountDepartment");
        hashSet.add("/api/subscribe/cancelSubscribe");
        apiManagerList.put(WEB_SUBSCRIBE_API_KEY,hashSet);
    }
    //初始化柜体日均使用
    static void InitWebCabinetDayUseApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/department/getDepartments");
        hashSet.add("/api/department/getAllDepartments");
        hashSet.add("/api/takenote/cabinetDayUseList");
        apiManagerList.put(WEB_CABINET_DAY_USE_API_KEY,hashSet);
    }
    //初始化样机使用统计
    static void InitWebProtoUseApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/takenote/prototypeUseList");
        apiManagerList.put(WEB_PROTO_USETOTAL_API_KEY,hashSet);
    }
    //初始化样机使用统计
    static void InitWebLoginNoteApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/loginnote/findAllNotes");
        apiManagerList.put(WEB_LOGINNOTE_API_KEY,hashSet);
    }
    //初始化异常推送记录
    static void InitWebErrorPushApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/takenote/QueryFailPush");
        hashSet.add("/api/takenote/exportAllFailPush");
        apiManagerList.put(WEB_ERRRORPUSH_API_KEY,hashSet);
    }
    //初始化角色管理
    static void InitWebRoleManagerApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/role/getAllRoles");
        hashSet.add("/api/authority/getAllViews");
        hashSet.add("/api/role/getStaffByRoleId");
        hashSet.add("/api/authority/getViewsByRoleId");
        hashSet.add("/api/role/updateRole");
        hashSet.add("/api/role/addRole");
        hashSet.add("/api/department/getAllDepartments");
        hashSet.add("/api/staff/findOneStaff");
        hashSet.add("/api/role/addStaffToRole");
        hashSet.add("/api/role/cancelRoleByStaff");
        hashSet.add("/api/staff/findOneStaff");
        hashSet.add("/api/authority/updateAuthority");
        hashSet.add("/api/department/getAuthorityStaffByDepartment");
        apiManagerList.put(WEB_ROLE_MANAGER_API_KEY,hashSet);
    }
    //初始化部门管理
    static void InitWebDeptManagerApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/department/getAllDepartments");
        apiManagerList.put(WEB_DEPT_MANAGER_API_KEY,hashSet);
    }
    //初始化人员管理
    static void InitWebStaffManagerApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/staff/getAllStaff");
        hashSet.add("/api/staff/update");
        hashSet.add("/api/staff/add");
        hashSet.add("/api/secretcabinet/getAllCabinets");

        hashSet.add("/api/secretcabinet/getStaffCellInfo");
        hashSet.add("/api/secretcabinet/assginCells");

        hashSet.add("/api/role/getAllRoles");
        hashSet.add("/api/staff/exportAllStaff");

        hashSet.add("/api/op/staff/searchStaffInfo");
        hashSet.add("/api/system/staff/import");
        apiManagerList.put(WEB_STAFF_MANAGER_API_KEY,hashSet);
    }
    //初始化异常归还接口
    static void InitWebAbnormalReturnApi(){
        HashSet<String> hashSet=new HashSet<>();
        hashSet.add("/api/abnormalreturn/getAllbnormalReturnBySome");
        hashSet.add("/api/abnormalreturn/GetAbnormalPersonEcharts");
        hashSet.add("/api/abnormalreturn/GetAbnormalDeptEcharts");
        hashSet.add("/api/abnormalreturn/handleAbnormalReturn");
        apiManagerList.put(WEB_ABNORMAL_API_KEY,hashSet);
    }
    public static HashMap<String, Boolean> getApiMap() {
        return apiMap;
    }

    public static HashMap<String, HashSet<String>> getApiManagerList() {
        return apiManagerList;
    }

    public static void AddApi(String key, Boolean value){
        if (!apiMap.containsKey(key)) {
            apiMap.put(key, value);
        }
    }

    public static void DeleteApi(String apiName){
        AddApi(apiName,false);
    }

    public static void ResumeApi(String apiName){
        AddApi(apiName,true);
    }

    public static boolean AddApiPower(String userMd5,String apiName){
        if (apiMap.containsKey(apiName)){
            Boolean isuse = apiMap.get(apiName); //如果当前api可用则可添加到资源集合中
            if (isuse){
                if (apiManagerList.containsKey(userMd5)){
                    HashSet<String> setList = apiManagerList.get(userMd5);
                    setList.add(apiName);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean DeleteApiPower(String userMd5,String apiName){
        if (apiMap.containsKey(apiName)) {
            if (apiManagerList.containsKey(userMd5)) {
                HashSet<String> setList = apiManagerList.get(userMd5);
                setList.remove(apiName);
                return true;
            }
        }
        return false;
    }

    public static boolean AccessFlag(String apikey,String apiName){
        if (apiManagerList.containsKey(apikey)){
            HashSet<String> hashSet = apiManagerList.get(apikey);
            if (hashSet!=null && hashSet.size()>0 && hashSet.contains(apiName) && apiMap.containsKey(apiName)){
                 return apiMap.get(apiName);
            }
        }
        return false;
    }
}
