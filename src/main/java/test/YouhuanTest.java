package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class YouhuanTest {
    
    public static void main(String[] args) {
        
        Map<String, Map<String, Integer>> resultMap = new HashMap();
        try {
            
            InputStream inputStream = new FileInputStream("/Users/youhuan/Downloads/100days.xlsx");
            
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            
            XSSFSheet s = workbook.getSheetAt(0);
            System.out.println(s.getPhysicalNumberOfRows());
            for (int j = 1; j < s.getPhysicalNumberOfRows(); j++) {//获取总行数
                
                Row row = s.getRow(j); // 取出第i行 getRow(index) 获取第(j)行
                Map<String, Integer> lineMap = new HashMap();
                String group = getCellFormatValue(row.getCell(0));//取出第j行第k列的值
                String name = getCellFormatValue(row.getCell(1));//取出第j行第k列的值
                String no = getCellFormatValue(row.getCell(2));//取出第j行第k列的值
                String s1 = no.split("\\.")[0];
                String uniqueKey = group + "-" + name + "-" + s1;
                for (int k = 3; k < row.getPhysicalNumberOfCells(); k++) { // getPhysicalNumberOfCells() 获取当前行的总列数
                    
                    String text = getCellFormatValue(row.getCell(k));
                    
                    // 先汇总一行数据
                    Integer integer = lineMap.get(text);
                    if (null == integer) {
                        lineMap.put(text, 1);
                    } else {
                        lineMap.put(text, integer + 1);
                    }
                    
                }
                Map<String, Integer> integerMap = resultMap.get(uniqueKey);
                if (integerMap == null) {
                    resultMap.put(uniqueKey, lineMap);
                } else {
                    mergeMap(resultMap, lineMap, uniqueKey);
                }
                
            }
            
            workbook.close();
        } catch (Exception e) {
        }
    
        Set<Map.Entry<String, Map<String, Integer>>> entries = resultMap.entrySet();
        
        // 分组，分几个组，每组的人按照工号由小到大排序
        Map<String, List<String>> integerListHashMap = new HashMap();
    
        for (Map.Entry<String, Map<String, Integer>> entry : entries) {
            String key = entry.getKey();
            String[] split = key.split("-");
            String groupNum = split[1];
            List<String> strings = integerListHashMap.get(groupNum);
            if (null == strings) {
                List<String> stuffStrings = new ArrayList<>();
                stuffStrings.add(entry.getKey() + "+" + JSON.toJSONString(entry.getValue()));
                integerListHashMap.put(groupNum, stuffStrings);
            } else {
                strings.add(entry.getKey() + "+" + JSON.toJSONString(entry.getValue()));
            }
//            System.out.println(entry.getKey() + ":" + JSON.toJSONString(entry.getValue()));
        }
    
        Set<String> busiSet = new HashSet<>();
        for (int i = 1; i <= 8; i++) {
            List<String> strings = integerListHashMap.get(i + "");
            List<String> collect = strings.stream().sorted((o1, o2) -> {
                Integer split1 = Integer.valueOf(o1.split("-")[3].split("\\+")[0]);
                Integer split2 = Integer.valueOf(o2.split("-")[3].split("\\+")[0]);
                return split1 - split2;
            }).collect(Collectors.toList());
            for (String string : collect) {
                String[] split = string.split("\\+");
                String uiqueKey = split[0];
                String data = split[1];
                JSONObject jsonObject = JSONObject.parseObject(data);
                Set<Map.Entry<String, Object>> entries1 = jsonObject.entrySet();
                Map<String, Integer> stringIntegerMap = convertResult(entries1);
                for (Map.Entry<String, Object> stringObjectEntry : entries1) {
                    busiSet.add(stringObjectEntry.getKey());
                }
                System.out.println(uiqueKey + "," + convertNullToEmptyString(stringIntegerMap.get("1")) + ",," + convertNullToEmptyString(stringIntegerMap.get("3")) + "," + convertNullToEmptyString(stringIntegerMap.get("4")) + "," + convertNullToEmptyString(stringIntegerMap.get("5")));
            }
        }
    
        System.out.println(JSON.toJSONString(busiSet));
        
        
    }
    
    public static String convertNullToEmptyString(Integer str) {
        if (null == str) {
            return "";
        }
        return str.toString();
    }
    
    public static Map<String, Integer> convertResult(Set<Map.Entry<String, Object>> set) {
    
        Map<String, Integer> objectObjectHashMap = new HashMap();
        for (Map.Entry<String, Object> stringObjectEntry : set) {
            String key = stringObjectEntry.getKey();
            if (one.contains(key)) {
                Integer integer = objectObjectHashMap.get("1");
                if (null == integer) {
                    objectObjectHashMap.put("1", (Integer)stringObjectEntry.getValue());
                } else {
                    objectObjectHashMap.put("1", objectObjectHashMap.get("1") + (Integer)stringObjectEntry.getValue());
                }
            }
            if (three.contains(key)) {
                Integer integer = objectObjectHashMap.get("3");
                if (null == integer) {
                    objectObjectHashMap.put("3", (Integer)stringObjectEntry.getValue());
                } else {
                    objectObjectHashMap.put("3", objectObjectHashMap.get("3") + (Integer)stringObjectEntry.getValue());
                }
            }
            if (four.contains(key)) {
                Integer integer = objectObjectHashMap.get("4");
                if (null == integer) {
                    objectObjectHashMap.put("4", (Integer)stringObjectEntry.getValue());
                } else {
                    objectObjectHashMap.put("4", objectObjectHashMap.get("4") + (Integer)stringObjectEntry.getValue());
                }
            }
    
            if (elseData.contains(key)) {
                Integer integer = objectObjectHashMap.get("5");
                if (null == integer) {
                    objectObjectHashMap.put("5", (Integer)stringObjectEntry.getValue());
                } else {
                    objectObjectHashMap.put("5", objectObjectHashMap.get("5") + (Integer)stringObjectEntry.getValue());
                }
            }
        }
        return objectObjectHashMap;
    }
    static Set<String> one = new HashSet<>();
    static Set<String> three = new HashSet<>();
    static Set<String> four = new HashSet<>();
    static Set<String> elseData = new HashSet<>();
    
     static {
         one.add("年");
         one.add("√");
    
         three.add("抽");
         three.add("隔");
         three.add("日");
         three.add("调");
         three.add("学");
         
         four.add("封");
    
         elseData.add("旷");
         elseData.add("病");
         elseData.add("事");
         elseData.add("儿");
         elseData.add("婚");
         elseData.add("丧");
         elseData.add("工");
         elseData.add("陪");
         elseData.add("停");
         
     }
    
    
    public static void mergeMap(Map<String, Map<String, Integer>> resultMap, Map<String, Integer> mapB, String uniqueKey) {
        Map<String, Integer> stringIntegerMap = resultMap.get(uniqueKey);
        Set<Map.Entry<String, Integer>> entries = mapB.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            if (null == stringIntegerMap.get(entry.getKey()) ) {
                stringIntegerMap.put(entry.getKey(), entry.getValue());
            } else {
                stringIntegerMap.put(entry.getKey(), stringIntegerMap.get(entry.getKey()) + entry.getValue());
            }
        }
        
    }
    
    public static String getCellFormatValue(Cell cell) {
        
        String cellValue = "";
        
        if (cell != null) {
            
            // 判断cell类型
            
            switch (cell.getCellType()) {
                
                case Cell.CELL_TYPE_NUMERIC: {
                    
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    
                    break;
                    
                }
                
                case Cell.CELL_TYPE_STRING: {
                    
                    cellValue = cell.getRichStringCellValue().getString();
                    
                    break;
                    
                }
                
                default:
                    
                    cellValue = "";
                
            }
            
        }
        return cellValue;
    }
}
