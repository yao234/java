package com.sysdao.help;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sys.utils.StringUtils;
import com.sysdao.model.deviceapi.QuerySharedModel;
import com.sysdao.model.process.ModifyProductionInfomationDTO;

import java.util.ArrayList;
import java.util.List;

public class DataHelp {

    ///对ProductInfomation的Children插入数据
    public static boolean ModifyProductInfomationChildren(JSONArray root, ModifyProductionInfomationDTO dto){
        boolean isfinish = false;
        if (root.size()>0){
            for (int i = 0; i < root.size(); i++) {
                JSONObject childrenData = JSONObject.parseObject(JSONObject.toJSONString(root.get(i)));
                isfinish = LoopProductInformationSection(childrenData, dto.getFw(),dto.getAllowuser(),dto.getCreateuser());
                if (isfinish) {
                    root.remove(i);
                    root.add(i, childrenData);
                    break;
                }
            }
        }
        return isfinish;
    }

    //对指定节点加入授权人和创建人
    private static boolean LoopProductInformationSection(JSONObject jsonObject, String fileName, List<String> userList, String createUser){
        JSONArray children = jsonObject.getJSONArray("children");
        if (children.size()>0) {
            for (int i = 0; i < children.size(); i++) {
                String oldJson=JSONObject.toJSONString(children.get(i));
                JSONObject childrenData = JSONObject.parseObject(oldJson);
                if (childrenData.containsKey("children")){
                    boolean flag=LoopProductInformationSection(childrenData,fileName,userList,createUser);
                    if (flag){
                        ///将集合里的json数据进行覆盖
                        children.remove(i);
                        children.add(i, childrenData);
                        ///将jsonObject的Children进行覆盖
                        jsonObject.remove("children");
                        jsonObject.put("children",children);
                        return true;
                    }
                }
                else{
                    if (childrenData.getString("url").equals(fileName)) {
                        if (childrenData.containsKey("allow")) {
                            childrenData.remove("allow");
                        }
                        if (childrenData.containsKey("createUser")) {
                            childrenData.remove("createUser");
                        }
                        childrenData.put("allow", userList);
                        childrenData.put("createUser", createUser);

                        ///将集合的数据进行覆盖
                        children.remove(i);
                        children.add(i, childrenData);
                        return true;
                    }

                }
            }
        }else{
            return false;
        }
        return false;
    }


    ///初始化共享资料中下的指定目录
    public static void InitShare(QuerySharedModel model,JSONArray root,List<String> dirList){
        if (root.size()>0){
            for (int i = 0; i < root.size(); i++) {
                JSONObject childrenData = JSONObject.parseObject(JSONObject.toJSONString(root.get(i)));
                //循环获取指定节点下的children
                if (childrenData.containsKey("children")) {
                    LoopShareChildren(childrenData, model,dirList);
                }
                else{
                    List<QuerySharedModel> list = model.getList();
                    if (list==null ){
                        list=new ArrayList<>();
                    }
                    QuerySharedModel childModel=new QuerySharedModel();
                    childModel.setList(new ArrayList<>());
                    childModel.setName(childrenData.getString("url"));
                    childModel.setIsdir(false);
                    childModel.setTotal(childModel.getList().size());
                    childModel.setConfig(model.getConfig()+"/"+childrenData.getString("url"));
                    list.add(childModel);
                    model.setList(list);
                    model.setTotal(list.size());
                }
            }
        }

    }

    //遍历树
    private static void LoopShareChildren(JSONObject jsonObject,QuerySharedModel model,List<String> dirList) {
        List<QuerySharedModel> list = model.getList();
        if (list == null) {
            list = new ArrayList<>();
        }
        if (jsonObject.containsKey("children")) {
            JSONArray children = jsonObject.getJSONArray("children");
            if (children.size() > 0) {
                //只要当前节点包含children 就说明此节点是个目录  将参数model的子节点设置为当前节点
                QuerySharedModel childModel = new QuerySharedModel();
                childModel.setIsdir(true);
                childModel.setName(jsonObject.getString("title"));
                childModel.setConfig(model.getConfig()+"/"+jsonObject.getString("title"));
                if (!dirList.contains(childModel.getConfig())){
                    dirList.add(childModel.getConfig());
                }
                list.add(childModel);
                model.setList(list);
                //初始化当前节点的子节点
                List<QuerySharedModel> list1 = childModel.getList();

                if (list1 == null) {
                    list1 = new ArrayList<>();
                }

                for (int i = 0; i < children.size(); i++) {
                    String oldJson = JSONObject.toJSONString(children.get(i));
                    JSONObject childrenData = JSONObject.parseObject(oldJson);
                    if (childrenData.containsKey("children")) {
                        LoopShareChildren(childrenData, childModel,dirList);
                    }else{
                        QuerySharedModel childModelFile = new QuerySharedModel();
                        childModelFile.setIsdir(false);
                        childModelFile.setName(childrenData.getString("url"));
                        childModelFile.setList(new ArrayList<>());
                        childModelFile.setTotal(childModelFile.getList().size());
                        childModelFile.setConfig(childModel.getConfig()+"/"+childrenData.getString("url"));
                        list1.add(childModelFile);
                        childModel.setList(list1);
                        childModel.setTotal(childModel.getList().size());
                    }
                }

                model.setTotal(model.getList().size());
            }
            else{
                QuerySharedModel childModel=new QuerySharedModel();
                childModel.setList(new ArrayList<>());
                childModel.setName(jsonObject.getString("title"));
                childModel.setIsdir(true);
                childModel.setTotal(childModel.getList().size());
                childModel.setConfig(model.getConfig()+"/"+jsonObject.getString("title"));
                if (!dirList.contains(childModel.getConfig())){
                    dirList.add(childModel.getConfig());
                }
                list.add(childModel);
                model.setList(list);
                model.setTotal(model.getList().size());
            }
        }else{
            QuerySharedModel childModel=new QuerySharedModel();
            childModel.setList(new ArrayList<>());
            childModel.setTotal(childModel.getList().size());
            childModel.setName(jsonObject.getString("url"));
            childModel.setIsdir(false);
            childModel.setConfig(model.getConfig()+"/"+jsonObject.getString("url"));
            list.add(childModel);
            model.setList(list);
            model.setTotal(model.getList().size());
        }
    }



    public static JSONObject findLatelyNode(JSONObject jsonObject,List<String> listPath,int currentIndex){
        if (jsonObject.containsKey("children")){
            String title=jsonObject.getString("title");
            if (title.equals(listPath.get(currentIndex).trim())){
                //如果当前坐标等于数组的长度则则说明传进来的节点就是正确的节点
                if (currentIndex+1==listPath.size()){
                    return jsonObject;
                }
                JSONArray children = jsonObject.getJSONArray("children");
                for (int i=0;i<children.size();i++){
                    JSONObject jsonObject1 = children.getJSONObject(i);
                    JSONObject latelyNode = findLatelyNode(jsonObject1, listPath, currentIndex + 1);
                    if (latelyNode!=null){
                        return latelyNode;
                    }
                }
            }else{
                return null;
            }
        }
            return null;

    }

}



