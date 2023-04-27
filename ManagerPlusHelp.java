package com.sysdao.help;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sysdao.daoservice.ManagerPlusService;
import com.sysdao.dto.ManagerPlusDetailDTO;
import com.sysdao.dto.ManagerPlusInfoDTO;
import com.sysdao.dto.ManagerPlusLoginDTO;
import com.sysdao.entity.TCustomerEntity;
import com.sysdao.entity.TManagerPlusCompanyEntity;
import com.sysdao.entity.TManagerPlusStaffEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public final class ManagerPlusHelp {

    public final static String LOGIN_URL="https://www.managerplus.cn/oauth/v2/token?grant_type=password&webflag=1&client_id=2_3t7k61w8zz28wc00w0o0swgo0ko48w08c8csk4ss444os4ccgo&client_secret=6o596th97340soswgw8ssswk8gk8cwoo40c4wwccs4g4wswk8&username=15728567633&password=aa6otMIRdVt.Q###PC";

    public final static String CRM_URL="https://www.managerplus.cn/api/v1/crm/customers";

    //登陆接口
    public ManagerPlusLoginDTO Login(){
        HttpRequest httpRequest = HttpRequest.get(ManagerPlusHelp.LOGIN_URL);
        httpRequest.header("Content-Type", "text/json;charset=utf-8");
        httpRequest.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36");
        httpRequest.header("Accept-Encoding", "gzip, deflate, br");
        httpRequest.header("Host", "www.managerplus.cn");
        httpRequest.header("Referer", "https://www.managerplus.cn/web/pc-link/pc-eb6ea3a2/");
        httpRequest.header("sec-ch-ua", "\"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"");
        httpRequest.header("sec-ch-ua-mobile", "?0");
        httpRequest.header("sec-ch-ua-platform", "Windows");
        httpRequest.header("Sec-Fetch-Dest", "empty");
        httpRequest.header("Sec-Fetch-Mode", "cors");
        httpRequest.header("Sec-Fetch-Site", "same-origin");
        String body = httpRequest.execute().body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        String access_token = jsonObject.getString("access_token");
        Integer expires_in = jsonObject.getInteger("expires_in");
        String token_type = jsonObject.getString("token_type");
        JSONObject userJson = jsonObject.getJSONObject("user");
        String contactId = userJson.getString("contactId");
        String userId = userJson.getString("userId");
        String ftokenmd5 = userJson.getString("ftokenmd5");
        String ftoken = userJson.getString("ftoken");
        String nickname = userJson.getString("nickname");

        ManagerPlusLoginDTO managerPlusLoginDTO=new ManagerPlusLoginDTO();
        managerPlusLoginDTO.setAccess_token(access_token);
        managerPlusLoginDTO.setContactId(contactId);
        managerPlusLoginDTO.setExpires_in(expires_in);
        managerPlusLoginDTO.setToken_type(token_type);
        managerPlusLoginDTO.setUserId(userId);
        managerPlusLoginDTO.setFtoken(ftoken);
        managerPlusLoginDTO.setFtokenmd5(ftokenmd5);
        managerPlusLoginDTO.setNickname(nickname);

        return managerPlusLoginDTO;
    }

    //查询客户信息
    public JSONArray QueryCRMInfo(String url,String access_token){
        HttpRequest httpRequest = HttpRequest.get(url);
        httpRequest.bearerAuth(access_token);
        httpRequest.header("Content-Type", "application/json;charset=utf-8");
        httpRequest.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36");
        httpRequest.header("Accept-Encoding", "-");
        httpRequest.header("Host", "www.managerplus.cn");
        httpRequest.header("Referer", "https://www.managerplus.cn/web/pc-link/pc-eb6ea3a2/");
        httpRequest.header("sec-ch-ua", "\"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\"");
        httpRequest.header("sec-ch-ua-mobile", "?0");
        httpRequest.header("sec-ch-ua-platform", "Windows");
        httpRequest.header("Sec-Fetch-Dest", "empty");
        httpRequest.header("Sec-Fetch-Mode", "cors");
        httpRequest.header("Sec-Fetch-Site", "same-origin");
        httpRequest.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        try{
            HttpResponse httpResponse = httpRequest.execute();
            String body = httpResponse.body();
            return JSONObject.parseArray(body);
        }catch (Exception e){
            return null;
        }
    }

    //更改客户信息接口的参数
    public String getCrmUrl(int page){
      return CRM_URL+"?page="+page+"&customerType=1&isAddEntity=1&publicType=0";
    }


    public  void AnilysData(List<TManagerPlusCompanyEntity> list, List<TManagerPlusStaffEntity> detailList, List<TCustomerEntity> customerList){
        System.out.println("开始更新大管家的客户信息");
        LoopQueryData(list,detailList,customerList,this,1);
        System.out.println("更新成功");
    }


    private void LoopQueryData(List<TManagerPlusCompanyEntity> list,List<TManagerPlusStaffEntity> detailList,List<TCustomerEntity> customerList,ManagerPlusHelp managerPlusHelp,int page){
        ManagerPlusLoginDTO login = managerPlusHelp.Login();
        System.out.println(page);
        if (login!=null){
            JSONArray jsonArray = managerPlusHelp.QueryCRMInfo(managerPlusHelp.getCrmUrl(page), login.getAccess_token());
            System.out.println(jsonArray.toString());
            if (jsonArray!=null && jsonArray.size()>0){
                for (int index=0;index<jsonArray.size();index++){
                    TManagerPlusCompanyEntity company=new TManagerPlusCompanyEntity();
                    TCustomerEntity customerEntity=new TCustomerEntity();

                    JSONObject childData = jsonArray.getJSONObject(index);
                    long companyId = childData.getLong("id"); //公司id
                    String companyName=childData.getString("name"); //公司名称
                    String crtAt = childData.getString("crtAt"); //创建时间
                    String updAt = childData.getString("updAt"); //修改时间
                    String companyAddress = childData.getString("address");
                    String companynotes=childData.getString("notes"); //客户来源描述
                    Integer status = childData.getInteger("status");

                    JSONObject createUserData = childData.getJSONObject("crtBy");
                    String createUserName="";
                    if (createUserData!=null) {
                        createUserName = createUserData.getString("name"); //创建人名称
                    }
                    JSONArray contactsUserArray = childData.getJSONArray("contacts");
                    if (contactsUserArray!=null && contactsUserArray.size()>0){


                        for (int userIndex=0;userIndex<contactsUserArray.size();userIndex++){
                            TManagerPlusStaffEntity detailDTO=new TManagerPlusStaffEntity();

                            JSONObject contactData = contactsUserArray.getJSONObject(userIndex);
                            Long customerid = contactData.getLong("id"); //客户id
                            Long conpanyId = contactData.getLong("crmCustomerId"); //客户所在公司
                            String customUserName = contactData.getString("name"); //客户姓名

                            JSONArray customerPhone = contactData.getJSONArray("mobPhones");
                            String wxchat = contactData.getString("wxchat");
                            Integer isDefaultReceiver = contactData.getInteger("isDefaultReceiver");

                            detailDTO.setCompanyid(conpanyId);
                            detailDTO.setCustomerid(customerid);
                            detailDTO.setCustomeruser(customUserName);
                            detailDTO.setIsdefaultreceiver(isDefaultReceiver==null?0:isDefaultReceiver);
                            detailDTO.setCustomerwxchat(wxchat);
                            detailDTO.setCustomerphone(customerPhone.toJSONString());

                            detailList.add(detailDTO);
                        }

                        //company.setItems(detailList);
                    }

                    Integer customerType = childData.getInteger("customerType"); //客户类型
                    Integer isPublicCustomer = childData.getInteger("isPublicCustomer"); //是否公开客户
                    Integer isStick = childData.getInteger("isStick");


                    String ls = childData.toJSONString(); //将整个json保存下来 为了后续扩展


                    company.setCompanyid(companyId);
                    company.setCompanyname(companyName);
                    company.setCreatetime(crtAt);
                    company.setUpdatetime(updAt);
                    company.setAddress(companyAddress);
                    company.setNotes(companynotes);
                    company.setCompanystatus(status);
                    company.setCreateuser(createUserName);
                    company.setCustomertype(customerType);
                    company.setIspublickcustomer(isPublicCustomer);
                    company.setIsstick(isStick);
                    company.setLs(ls);


                    customerEntity.setCustomeridId(String.valueOf(companyId));
                    customerEntity.setCustomeridName(String.valueOf(companyName));
                    customerEntity.setCustomeridNumber(String.valueOf(companyId));



                    customerList.add(customerEntity);
                    list.add(company);
                }
                LoopQueryData(list, detailList,customerList, managerPlusHelp, page+1);
            }
            else{
                return;
            }
        }
        return;
    }
}
