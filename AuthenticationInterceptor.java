package com.bwzn.xssmg.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.bwzn.xssmg.GlobalExceptionCode;
import com.bwzn.xssmg.Result;
import com.bwzn.xssmg.annotation.PassToken;
import com.bwzn.xssmg.annotation.UserLoginToken;
import com.bwzn.xssmg.entities.BaseStaff;
import com.bwzn.xssmg.entities.http.resp.CabinetInfo;
import com.bwzn.xssmg.help.AccessLimit;
import com.bwzn.xssmg.help.SSOHelp;
import com.bwzn.xssmg.service.BaseStaffService;
import com.bwzn.xssmg.service.CabinetService;
import com.bwzn.xssmg.service.RedisCacheService;
import com.bwzn.xssmg.utils.HttpsUtils;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;


/**
 * @author xby
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    BaseStaffService baseStaffService;

    @Autowired
    CabinetService cabinetService;

    //@Autowired
    private ConcurrentMap<String,AccessLimit> Relimit=new ConcurrentHashMap<>();

    public static List<CabinetInfo> cacheCabinetInfos = new ArrayList<>();

    @Autowired
    private RedisCacheService redisCacheService;

    @Value("${myconfig.whitelistip}")
    private String trustIp;


    @Autowired
    SSOHelp ssoHelp;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        System.out.println("ok");
        String requestUrl=httpServletRequest.getHeader("xs-location-href");
        System.out.println(requestUrl);
        CheckHttpRequest(httpServletRequest,httpServletResponse);


        //获取用户访问的超链接
        String token = httpServletRequest.getHeader("Authorization");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                String user_IDNO = (String) redisCacheService.get(token);//根据token获取工号
                System.out.println("读取到redis userId:" + user_IDNO);
                if (StringUtils.isEmpty(user_IDNO)) {
                    if (validateCabinetIp(GetIp(httpServletRequest))) {
                        throw new RuntimeException("401");
                    } else {
                        System.out.println("前端传来的token:" + token);
                        ssoHelp.GetUserInfomation(token);
                        user_IDNO = ssoHelp.uid;
                        System.out.println("读取到SSO userId" + user_IDNO);
                        redisCacheService.setKeyByTime(token, user_IDNO, 12L, TimeUnit.HOURS);
                    }

                }
                if (StringUtils.isEmpty(user_IDNO)){
                    throw new RuntimeException("用户不存在，请重新登录");
                }

                // 获取 token 中的 user id
                /*String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                BaseStaff staff = baseStaffService.findStaffById(userId);
                if (staff == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(staff.getF_Password())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }*/
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


    private void CheckHttpRequest(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {

        //String trustIp="192.168.0.103;192.168.0.95;127.0.0.1";
        /***
         * Http头部参数检查
         * **/
        /**检查是否为白名单ip**/
        String ip=GetIp(httpServletRequest);

        System.out.println("访问此网站的ip:"+ip);

        String refer = httpServletRequest.getHeader("Referer");

        String host = httpServletRequest.getRemoteHost();

        String currentIp=InetAddress.getLocalHost().getHostAddress();

        System.out.println("当前主机ip:"+currentIp);

        System.out.println("访问此网站的host:"+host);
        System.out.println("访问此网站的referer:"+refer);
        boolean isContinue=false;
        /**重要的页面只允许管理员访问**/

        /**先检查ip是否合法**/
        /**如果非白名单ip可能是网站登录**/
        if (cacheCabinetInfos.size() == 0){
            List<CabinetInfo> allCabinets = cabinetService.findAllCabinets();
            cacheCabinetInfos.addAll(allCabinets);
        }

        if (!trustIp.contains(ip) && !trustIp.contains(host) && !validateCabinetIp(ip)) {
            if (refer==null){
                throw new RuntimeException("非法请求");
            }
            if (!refer.contains(currentIp)) {
                throw new RuntimeException("非法链接站点");
            }
            String UserAgent=httpServletRequest.getHeader("User-Agent");
            if (UserAgent==null){
                throw new RuntimeException("非法浏览器");
            }
            else{
                isContinue=true;
            }
            if (!isContinue) {
                throw new RuntimeException("非白名单站点");
            }
        }
        //***Referer检查***//*
        String contentType=httpServletRequest.getContentType();

        System.out.println(contentType);
        if (contentType!=null && contentType.trim()!=""){
            if (!(contentType.contains("application/json") || contentType.contains("multipart/form-data"))){
                throw new RuntimeException("非法请求类型");
            }
        }
        //*//**内容长度检查**//*
        int ContentLength=httpServletRequest.getContentLength();

        if (ContentLength>20971520){
            throw new RuntimeException("单个文件大小不能超过20M");
        }

        ServletInputStream inputStream=httpServletRequest.getInputStream();
        if(inputStream.isReady()){
            byte[] contentBuff = new byte[inputStream.available()];
            inputStream.read(contentBuff);
            String content = new String(contentBuff);
            if (content.contains("@") || content.contains("=") || content.contains(":")){
                throw new RuntimeException("非法输入");
            }
        }
//
//            *//**内容检查**//*
//            ServletInputStream inputStream=httpServletRequest.getInputStream();
//           if(inputStream.isReady()){
//
//           }*/


        /**限流   1秒只允许登录请求3次 **/
        String apiName=httpServletRequest.getRequestURI();
        if (apiName.trim().toLowerCase().contains("login")) {

            if (!Relimit.containsKey(apiName)) {
                AccessLimit limit = new AccessLimit();

                Calendar ca = Calendar.getInstance();
                Date currentDate = ca.getTime();

                Calendar endca = Calendar.getInstance();
                endca.add(Calendar.SECOND, 1);
                Date endDate = endca.getTime();

                limit.setLimitCount(5);
                limit.setStartDate(currentDate);
                limit.setCurrentDate(currentDate);
                limit.setEndDate(endDate);
                limit.setAccessCount(1);
                Relimit.put(apiName, limit);
                System.out.println("访问次数:" + limit.getAccessCount());
                System.out.println("限制次数:" + limit.getLimitCount());
            } else {
                AccessLimit limit = Relimit.get(apiName);
                /**
                 * 当前时间未到开始时间则需等待
                 *
                 * **/
                if (limit.getCurrentDate().compareTo(limit.startDate) < 0) {
                    Calendar ca = Calendar.getInstance();
                    Date currentDate = ca.getTime();
                    limit.setCurrentDate(currentDate);
                    throw new RuntimeException("访问次数频繁，请稍后再试");
                }
                limit.setAccessCount(limit.getAccessCount() + 1);
                /**判断时间戳是否到时间了 到了则更新**/
                if (limit.IsReSet()) {
                    Calendar ca = Calendar.getInstance();
                    Date currentDate = ca.getTime();
                    limit.setCurrentDate(currentDate);
                    ca.add(Calendar.SECOND, 1);
                    Date endDate=ca.getTime();
                    limit.setEndDate(endDate);
                    limit.setAccessCount(1);
                } else {

                    if (limit.IsAccess()) {
                        Date newDate = new Date();
                        limit.setCurrentDate(newDate);
                    } else {
                        /**如果访问频繁可能是恶意攻击 将开始时间设长**/
                        Calendar ca = Calendar.getInstance();
                        ca.add(Calendar.SECOND, 10);
                        Date startDate = ca.getTime();
                        limit.setStartDate(startDate);
                        throw new RuntimeException("访问次数频繁，请稍后再试");
                    }

                }
                System.out.println("访问次数:" + limit.getAccessCount());
                System.out.println("限制次数:" + limit.getLimitCount());
            }

        }

    }

    public static boolean validateCabinetIp(String ip){
        for (CabinetInfo info : cacheCabinetInfos){
            if (ip.equals(info.getF_IP())){
                return true;
            }
        }
        return false;
    }


    public static String GetIp(HttpServletRequest request) {
        // 取用户ip地址
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }


        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }


        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

}
