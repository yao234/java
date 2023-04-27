package com.sysdao.help;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelData {
    private Map<String,Integer> ExcelHead;

    private List<Map<Integer,String>> ExcelDatas;

    private Map<String,String> ConfigColumnMap; //key是实体类的字段，Value是Excel的表头

    public List<Map<Integer, String>> getExcelDatas() {
        return ExcelDatas;
    }

    public Map<String, Integer> getExcelHead() {
        return ExcelHead;
    }

    public void setExcelHead(Map<String, Integer> excelHead) {
        ExcelHead = excelHead;
    }

    public void setExcelDatas(List<Map<Integer, String>> excelDatas) {
        ExcelDatas = excelDatas;
    }

    public void setConfigColumnMap(Map<String, String> configColumnMap) {
        ConfigColumnMap = configColumnMap;
    }


    public <T> List<T> Parse(Class<T> c1) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        List<T> list=new ArrayList<>();
        if (c1==null){
            return list;
        }
        if (ConfigColumnMap!=null){
            boolean isFirst=true;
            //先找到中英文转换的字段,在通过字段间接去找具体值
            for (String item:ConfigColumnMap.keySet()){
                Field declaredField = c1.getDeclaredField(item);
                declaredField.setAccessible(true);
                //如果字段存在的话先将此字段的值保存下来
                if (declaredField!=null) {
                    //通过对应关系找到此列在excel表的列下标，再通过列下标可找到excel具体行具体列的值
                    Integer keyIndex = ExcelHead.get(ConfigColumnMap.get(item));
                    if (keyIndex==null){
                        continue;
                    }
                    ///行式存储
                    for (int index = 0; index < ExcelDatas.size(); index++) {
                        //获取每一行的数据
                        Map<Integer, String> rowData = ExcelDatas.get(index);
                        //如果当前行的列大小大于等于列名映射的下标就可以获取到列的值
                        String value="";
                        if (rowData.keySet().size()>=keyIndex){
                            value=rowData.get(keyIndex);
                        }
                        if (isFirst) {
                            T model = c1.newInstance();
                            declaredField.set(model, value);
                            list.add(model);
                        }
                        else{
                            T model= list.get(index);
                            declaredField.set(model,value);
                        }
                    }
                    isFirst=false;
                }
            }
        }
        return list;
    }

}
