package com.sysdao.help;

import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelHelp {

    public static List<Map<Integer, List<String>>> readExcel(MultipartFile file) {
        //存储excel所有sheet信息
        List<Map<Integer, List<String>>> totalList = new ArrayList<>();
        //获取文件流
//        FileInputStream is = null;
        //创建工作簿
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file.getInputStream());//可兼容03、07版本
            //获取sheet数量
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                //存储单页sheet信息map
                Map<Integer, List<String>> excelMap = new HashMap<>();
                //获取当前工作表
                Sheet sheet = workbook.getSheetAt(i);
                //获取表记录
                //获取行数 TODO 关于获取行数的方法好像有坑，可以查资料确定一下
                int numOfRows = sheet.getLastRowNum();
                for (int rowNum = 0; rowNum < numOfRows; rowNum++) {
                    List<String> infoList = new ArrayList<>();
                    Row rowData = sheet.getRow(rowNum);
                    if (rowData != null) {
                        //读取列
                        int cellCount = rowData.getLastCellNum();
                        for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                            Cell cell = rowData.getCell(cellNum);
                            //匹配数据类型
                            String cellValue = "";
                            if (cell != null) {
                                if (cell.getCellTypeEnum() == CellType.STRING) {
                                    cellValue = cell.getStringCellValue();
                                }

                                if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                                    //数值型
                                    //poi读取整数会自动转化成小数，这里对整数进行还原，小数不做处理
                                    long longValue = Math.round(cell.getNumericCellValue());
                                    if (Double.parseDouble(longValue + ".0") == cell.getNumericCellValue()) {
                                        cellValue = String.valueOf(longValue);
                                    } else {
                                        cellValue = String.valueOf(cell.getNumericCellValue());
                                    }
                                } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
                                    //公式型
                                    //公式计算的值不会转化成小数，这里数值获取失败后会获取字符
                                    try {
                                        cellValue = String.valueOf(cell.getNumericCellValue());
                                    } catch (Exception e) {
                                        cellValue = cell.getStringCellValue();
                                    }
                                }
                                infoList.add(cellValue);
                            }else {
                                infoList.add(null);
                            }
                            excelMap.put(rowNum,infoList);
                        }
                    }
                }
                totalList.add(excelMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
//                if (is != null) {
//                    is.close();
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return totalList;
    }

    public static ExcelData readSheetFirst(MultipartFile file) {
        if (file==null){
            return null;
        }
        //记录excel的表头 key为表头名称,value为表头所在的位置
        Map<String,Integer> mapHead=new HashMap<>();  //一个Map为一行数据

        List<Map<Integer,String>> mapData=new ArrayList<>(); //key 为数据所在第几列  value 为具体值  list的大小为具体第几行

        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file.getInputStream());//可兼容03、07版本
            //获取sheet数量
            int numberOfSheets = workbook.getNumberOfSheets();
            if (numberOfSheets>0){
                //如果sheet数量大于0，我们只获取第一个
                Sheet sheet = workbook.getSheetAt(0);
                //获取工作表的行
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

                if (physicalNumberOfRows>0){
                    //获取列名
                    Row headRow=sheet.getRow(0);
                    ReadHeadCell(headRow,mapHead);
                    //获取其他列
                    if (physicalNumberOfRows>=2){
                        for (int rowIndex=1;rowIndex<physicalNumberOfRows;rowIndex++){
                            Map<Integer,String> data=new HashMap<>();
                            Row dataRow = sheet.getRow(rowIndex);
                            ReadDataCell(dataRow,data);
                            if (data.size()>0){
                                mapData.add(data);
                            }
                        }
                    }

                    ExcelData excelData=new ExcelData();
                    if (mapHead.size()>0){
                        excelData.setExcelHead(mapHead);
                    }
                    if (mapData.size()>0){
                        excelData.setExcelDatas(mapData);
                    }
                    return excelData;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void ReadHeadCell(Row row,Map<String,Integer> map){
        if (row!=null){
            int cellLength = row.getPhysicalNumberOfCells();
            if (cellLength>0){
                for (int cellIndex=0;cellIndex<cellLength;cellIndex++){
                    Cell cell = row.getCell(cellIndex);
                    String cellValue = "";
                    if (cell!=null){
                        if (cell.getCellTypeEnum() == CellType.STRING) {
                            cellValue = cell.getStringCellValue();
                        }
                        else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                            //数值型
                            //poi读取整数会自动转化成小数，这里对整数进行还原，小数不做处理
                            long longValue = Math.round(cell.getNumericCellValue());
                            if (Double.parseDouble(longValue + ".0") == cell.getNumericCellValue()) {
                                cellValue = String.valueOf(longValue);
                            } else {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            }
                        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
                            //公式型
                            //公式计算的值不会转化成小数，这里数值获取失败后会获取字符
                            try {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            } catch (Exception e) {
                                cellValue = cell.getStringCellValue();
                            }
                        }
                    }
                    //如果不等于空的话则将数据插入到数组中
                    //if (!StringUtils.isEmpty(cellValue)){
                    map.put(cellValue,cellIndex);
                    //}
                }
            }
        }
    }

    private static void ReadDataCell(Row row,Map<Integer,String> map){
        if (row!=null){
            int cellLength = row.getLastCellNum();
            if (cellLength>0){
                for (int cellIndex=0;cellIndex<cellLength;cellIndex++){
                    Cell cell = row.getCell(cellIndex);
                    String cellValue = "";
                    if (cell!=null){
                        if (cell.getCellTypeEnum() == CellType.STRING) {
                            cellValue = cell.getStringCellValue();
                        }
                        else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                            //数值型
                            //poi读取整数会自动转化成小数，这里对整数进行还原，小数不做处理
                            long longValue = Math.round(cell.getNumericCellValue());
                            if (Double.parseDouble(longValue + ".0") == cell.getNumericCellValue()) {
                                cellValue = String.valueOf(longValue);
                            } else {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            }
                        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
                            //公式型
                            //公式计算的值不会转化成小数，这里数值获取失败后会获取字符
                            try {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            } catch (Exception e) {
                                cellValue = cell.getStringCellValue();
                            }
                        }
                    }
                    //如果不等于空的话则将数据插入到数组中
                    //if (!StringUtils.isEmpty(cellValue)){
                    map.put(cellIndex,cellValue);
                    //}
                }
            }
        }
    }

    private static void ReadCell(Row row){
        if (row!=null){
            int cellLength = row.getPhysicalNumberOfCells();
            if (cellLength>0){
                for (int cellIndex=0;cellIndex<cellLength;cellIndex++){
                    Cell cell = row.getCell(cellIndex);
                    String cellValue = "";
                    if (cell!=null){
                        if (cell.getCellTypeEnum() == CellType.STRING) {
                            cellValue = cell.getStringCellValue();
                        }
                        else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                            //数值型
                            //poi读取整数会自动转化成小数，这里对整数进行还原，小数不做处理
                            long longValue = Math.round(cell.getNumericCellValue());
                            if (Double.parseDouble(longValue + ".0") == cell.getNumericCellValue()) {
                                cellValue = String.valueOf(longValue);
                            } else {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            }
                        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
                            //公式型
                            //公式计算的值不会转化成小数，这里数值获取失败后会获取字符
                            try {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            } catch (Exception e) {
                                cellValue = cell.getStringCellValue();
                            }
                        }
                    }

                    //如果不等于空的话则将数据插入到数组中
                    if (!StringUtils.isEmpty(cellValue)){

                    }
                }
            }
        }
    }

}
