package org.ltsh.autocode.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fengjb-it on 2016/2/15 0015.
 */
public class TempleteUtils {
    private static Logger log = LoggerFactory.getLogger(TempleteUtils.class);
    public static final String TEMPLETE_STR = "templete";
    public static final String TEMPLETE_DATASOURCE_STR = "dataSource";
    public static final String TEMPLETE_TYPE_STR = "type";
    public static final String TEMPLETE_REGEX=".*?<templete[^>]*>((.|\\n)*?)<\\/templete>.*?";
//    public static final String TEMPLETE_REGEX="<(\\?<HtmlTag>sql)[^>]*?>((\\?<Nested><\\\\k<HtmlTag>[^>]*>)|</\\\\k<HtmlTag>>(\\?<-Nested>)|.*?)*</\\\\k<HtmlTag>>";

    public static final String TEMPLETE_REGEX_DELETE="<templete[^>]*>|</templete>";
    public static final String PRO_REGEX="\\$\\{[^}]*\\}";

    public static String replaceTemplete(String templeteStr, Map<String, Object> repleaceMap) throws Exception {
        Pattern compile = Pattern.compile(PRO_REGEX);
        Matcher matcher = compile.matcher(templeteStr);
        while(matcher.find()) {
            String tempStr = matcher.group();
            String tempStr1 = tempStr.substring(2, tempStr.length() - 1);
            String[] split = tempStr.split(".");
            if(split.length > 1) {
                Map<String, Object> tempMap = null;
                for (int i = 0; i < split.length; i++) {
                    Object obj = repleaceMap.get(split[i]);
                    if(obj != null) {
                        if(obj instanceof Map) {
                            tempMap = (Map) obj;
                        }
                    }

                }
                if(tempMap != null) {
                    for (String key : tempMap.keySet()) {
                        templeteStr = templeteStr.replace(tempStr, String.valueOf(tempMap.get(key)));
                    }
                } else {
                    throw new Exception("参数:\"" + tempStr1 + "\"未定义");
                }

            } else {
                templeteStr = templeteStr.replace(tempStr, String.valueOf(repleaceMap.get(tempStr1)));
            }
        }
        return templeteStr;
    }

    private static String getTempleteList(String templeteStr, Map<String, Object> templeteData) throws Exception {
        Pattern pattern = Pattern.compile(TEMPLETE_REGEX);
        Matcher matcher = pattern.matcher(templeteStr);
        int idx=0;
        List<Map<String, String>> templeteStrList = new ArrayList<Map<String, String>>();
        String tempStr = templeteStr;
        while (matcher.find()) {
            String group = matcher.group();
            String index = "####templeteTemp----------"+idx+"####";

            tempStr = tempStr.replace(group, index);
            Map<String, String> map = new HashMap<String, String>();
            map.put("index", index);
            map.put("oldStr", group);
            //map.put("newStr", baseStr);
            templeteStrList.add(map);
            //System.out.println(baseStr);
            idx++;

        }
        tempStr = replaceTemplete(tempStr, templeteData);
        for (int i=0; i<templeteStrList.size(); i++) {
            String oldStr = templeteStrList.get(i).get("oldStr");
            String index = templeteStrList.get(i).get("index");
            Map<String, String> propertyMap = new HashMap<String, String>();
            Pattern compile = Pattern.compile(TEMPLETE_REGEX_DELETE);
            Matcher matcher1 = compile.matcher(oldStr);
            if(matcher1.find()) {
                String group1 = matcher1.group();
                compile = Pattern.compile("\\S*=\"[^\"]*\"");
                String tempelteStr = matcher1.replaceAll("");

                matcher1 = compile.matcher(group1);

                while(matcher1.find()){
                    String group2 = matcher1.group();
                    group2 = group2.replaceAll("\"","");
                    propertyMap.put(group2.split("=")[0], group2.split("=")[1]);
                }
                if("for".equals(propertyMap.get(TEMPLETE_TYPE_STR))) {
                    Object obj = templeteData.get(propertyMap.get(TEMPLETE_DATASOURCE_STR));
                    if(obj instanceof List) {
                        List<Map> list = (List<Map>) obj;
                        StringBuffer tempSb = new StringBuffer();
                        for (int j = 0; j < list.size(); j++) {
                            String s = replaceTemplete(tempelteStr, list.get(j));
                            tempSb.append(s);
                        }
                        String trim = propertyMap.get("trim");
                        if(trim != null && tempSb.toString().trim().endsWith(trim)) {
                            tempSb = new StringBuffer(tempSb.substring(0, tempSb.lastIndexOf(",")));
                        }
                        tempStr = tempStr.replace(index, tempSb.toString());

                    }

                }
            }
        }
        return tempStr;

    }

    public Map<String, Object> getData(String tableName, Map<String, Object> map){
        if(map == null) {
            map = new HashMap<String, Object>();
        }

        map.put("className", DataUtil.getMethodName(tableName));
        map.put("tableName", tableName);

        if(map.get("columnDatas") == null) {
            List<Map<String, Object>> columnDatas = DataUtil.getColumnData(tableName);
            map.put("columnDatas", columnDatas);
        }
        return map;
    }


    public void getEntityStr(String tableName) throws Exception {

        Map<String, Object> map = getData(tableName, null);
        String templetePath = TempleteUtils.class.getClass().getResource("/templete").getPath();
        File tempFile = new File(templetePath + "/" + "entity.temp");
        FileReader fileReader = new FileReader(tempFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        while ((tempStr = bufferedReader.readLine()) != null) {
            sb.append(tempStr);
            sb.append("\n");
        }
        bufferedReader.close();
        getTempleteList(sb.toString(), map);
    }

    public String getTempleteStr(String tableName, String basePath, String path, Map<String, Object> map) throws Exception {

        map = getData(tableName, map);
        File tempFile = new File(basePath + "/" + path);
        if(!tempFile.exists()) {
            tempFile.mkdirs();
        }
        FileReader fileReader = new FileReader(tempFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        while ((tempStr = bufferedReader.readLine()) != null) {
            sb.append(tempStr);
            sb.append("\n");
        }
        bufferedReader.close();
        return getTempleteList(sb.toString(), map);
    }


    public static void main(String[] args) throws Exception {
        String templetePath = TempleteUtils.class.getClass().getResource("/templete").getPath();
        writeModules("cloud_file_push", templetePath,
                "org.ltsh.framework.modules.manager",
                "D:\\test\\xiangmu\\");
    }

    public static void writeModules(String tableName, String templetePath, String basePackageStr, String outPath) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        writeFileForPage(tableName, templetePath, basePackageStr, outPath+"\\java", DataUtil.getMethodName(tableName) + ".java", "entity", "entity.temp", map);

        writeFileForPage(tableName, templetePath, basePackageStr, outPath + "\\java", DataUtil.getMethodName(tableName) + "Mapper.java", "dao", "mapper.temp", map);

        writeFile(tableName, templetePath, basePackageStr, outPath + "\\resources", "sqlmaps", DataUtil.getMethodName(tableName) + "Mapper.xml", "sqlmaps", "mapper.xml.temp", map);
    }
    public static void writeFile(String tableName, String templetePath, String basePackageStr, String outPath, String modulesPath, String fileName, String modulesName, String templeteName, Map<String, Object> map) throws Exception{
        if(map == null) {
            map = new HashMap<String, Object>();
        }
        TempleteUtils templeteUtils = new TempleteUtils();
        map.put("packageName", basePackageStr);
        map.put("modulesName", modulesName);
        String templeteStr = templeteUtils.getTempleteStr(tableName, templetePath, templeteName, map);
        TempleteFileUtils.writeFile(templeteStr, fileName, outPath + "/" + modulesPath + "/");
    }

    public static void writeFileForPage(String tableName, String templetePath, String basePackageStr, String outPath, String fileName, String modulesName, String templeteName, Map<String, Object> map) throws Exception{
        writeFile(tableName, templetePath, basePackageStr, outPath, basePackageStr.replaceAll("\\.","/") + "/" + modulesName, fileName, modulesName, templeteName, map);
    }

}
