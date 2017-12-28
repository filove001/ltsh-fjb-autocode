package org.ltsh.generate.templete;


import org.ltsh.generate.jdbc.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * Created by fengjianbo on 2016/3/28 0028.
 */
public class DataUtil {
    public static List<Map<String, Object>> getColumnData(String tableName){
        List<Map<String, Object>> columnDatas = null;
        columnDatas = BaseDao.queryColumns(tableName);
        for (int i = 0; i < columnDatas.size(); i++) {
            Map<String, Object> stringObjectMap = columnDatas.get(i);
            String columnName = String.valueOf(stringObjectMap.get("column_name"));
            String propertyName = getPropertyName(columnName);
            String methodName = getMethodName(columnName);
            Object columnComment = stringObjectMap.get("column_comment");
            stringObjectMap.put("propertyName", propertyName);
            stringObjectMap.put("propertyNameUpper", methodName);
            if(columnComment != null) {
                stringObjectMap.put("describe", columnComment.toString().replace("\n",""));
            }

            stringObjectMap.put("dataType",getDataType(stringObjectMap.get("data_type")));
        }

        return columnDatas;
    }
    private static String getDataType(Object columnType){
        String dataType = "String";
        if(columnType != null) {
            String columnType1 = columnType.toString().toLowerCase();
            if(columnType1.startsWith("int") || columnType1.startsWith("integer")) {
                dataType = "Integer";
            } else if(columnType1.startsWith("bigint")) {
                dataType = "Long";
            } else if(columnType1.startsWith("date") || columnType1.startsWith("datetime")) {
                dataType = "java.util.Date";
            }
        }
        return dataType;
    }
    public static String getMethodName(String columnName){
        String propertyName = getPropertyName(columnName);
        propertyName = String.valueOf(propertyName.charAt(0)).toUpperCase() + propertyName.substring(1);
        return propertyName;
    }
    public static String getPropertyName(String columnName){
        if(columnName != null && columnName.indexOf("_") != -1) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < columnName.length(); i++) {

                String c = columnName.substring(i, i+1);
                if("_".equals(c)){
                    int j = i+1;
                    if(j < columnName.length()) {
                        c = columnName.substring(j, j + 1).toUpperCase();
                        i++;
                    }
                }
                sb.append(c);
            }
            return sb.toString();

        } else {
            return columnName;
        }
    }
}
