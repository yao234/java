package com.sysdao.help;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import org.omg.IOP.Encoding;

import java.beans.Encoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class FileHelp {


    public static String ReadToString(String pathname){
        /**测试读文件**/
        try {
//"E:\\yaoproject\\testConfig"
            File file = new File(pathname);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String content = new String(buffer);
            inputStream.close();
            return content;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static JSONObject ReadToJsonObj(String pathname){
        String content=ReadToString(pathname);
        JSONObject data=null;
        try {
            data=JSONObject.parseObject(content);
        }catch (Exception e){
            e.printStackTrace();
            data=new JSONObject();
        }
        return data;
    }

    public static JSONArray ReadToJsonArray(String pathname){
        String content=ReadToString(pathname);
        JSONArray array=null;
        try {
            array=JSONObject.parseArray(content);
        }catch (Exception e){
            e.printStackTrace();
            array=new JSONArray();
        }
        return array;
    }

    public static void WriteString(String pathname,String content){
        try {
            File file = new File(pathname);
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] contentBytes = content.getBytes();
            outputStream.write(contentBytes);
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
