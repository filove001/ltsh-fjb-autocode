package org.ltsh.generate.config;

import com.alibaba.fastjson.JSONObject;
import org.ltsh.generate.bean.BeanUtils;
import org.ltsh.generate.templete.DataUtil;
import org.ltsh.generate.xml.ReadXml;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fengjianbo on 2017/12/27.
 */
public class ReadConfig {
    public static void main(String[] args) {
        try {
            readConfig("/templete/autoConfig.xml");
            System.out.println(JSONObject.toJSONString(GlobalConfig.jdbcConfig));
            System.out.println(JSONObject.toJSONString(GlobalConfig.templeteConfigList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void readConfig(String path) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(path);
//        InputStream resourceAsStream = ReadConfig.class.getResourceAsStream(path);
        Map configuration = ReadXml.xmlToMap(fileInputStream, "configuration");
        fileInputStream.close();
        Map applicationConfigMap = (Map)configuration.get("applicationConfig");
        if(applicationConfigMap != null) {
            ApplicationConfig applicationConfig = BeanUtils.mapToBean(applicationConfigMap, ApplicationConfig.class);
            GlobalConfig.applicationConfig = applicationConfig;
        }
        Map jdbcConfigMap = (Map)configuration.get("jdbcConfig");
        if(jdbcConfigMap == null || jdbcConfigMap.isEmpty()){
            throw new Exception("jdbc配置错误");
        } else {
            JdbcConfig jdbcConfig = BeanUtils.mapToBean(jdbcConfigMap, JdbcConfig.class);
            GlobalConfig.jdbcConfig = jdbcConfig;
        }
        List templeteList = getList(configuration, "templeteList", "templete");

        List<TempleteConfig> templeteConfigList = new ArrayList<TempleteConfig>();
        for (Object obj : templeteList) {
            Map map = (Map) obj;
            TempleteConfig templeteConfig = BeanUtils.mapToBean(map, TempleteConfig.class);
            templeteConfigList.add(templeteConfig);
            if(map.get("templete") != null) {

                List extra = getList(map, "templete", "extras");
                if(extra != null) {
                    for (Object obj1 : extra) {
                        if(((Map) obj1).get("property") != null) {
                            Map map1 = (Map)((Map) obj1).get("property");
                            templeteConfig.getExtraMap().put((String)map1.get("name"), map1.get("value"));
                        } else {
                            Map map1 = ((Map) obj1);
                            templeteConfig.getExtraMap().put((String)map1.get("name"), map1.get("value"));
                        }

                    }
                }
            }
        }
        GlobalConfig.templeteConfigList = templeteConfigList;
        List generateList = getList(configuration, "generateList", "generate");

//        List generateList = (List)configuration.get("generateList");
        List<GenerateConfig> generateConfigList = new ArrayList<GenerateConfig>();
        if(generateList != null) {
            for (Object obj : generateList) {
                Map map = (Map) obj;
                GenerateConfig generateConfig = BeanUtils.mapToBean(map, GenerateConfig.class);
                generateConfigList.add(generateConfig);
            }
        }

        GlobalConfig.generateConfigList = generateConfigList;
        List generateBatchList = getList(configuration, "generateBatchList", "generateBatch");
        for (Object obj : generateBatchList) {
            Map map = (Map) obj;
            GenerateBatchConfig generateBatchConfig = BeanUtils.mapToBean(map, GenerateBatchConfig.class);
            String baseTempletePath = generateBatchConfig.getBaseTempletePath();
            File file = new File(GlobalConfig.applicationConfig.getProjectPath() + baseTempletePath);
            if(file.exists()) {
                String[] list = file.list();
                for (String fileName : list) {
                    GenerateConfig generateConfig = new GenerateConfig();

//                <!--<generate tableName="answer_topic_detail" fileName="answerTopicDetail.sql" refTemplete="sqlTemplete"></generate>-->
                    generateConfig.setTableName(generateBatchConfig.getTableName());
                    String modelName = fileName.substring(0, fileName.lastIndexOf("."));
                    String entityName = DataUtil.getMethodName(generateConfig.getTableName());
                    if(modelName.equals("entity")) {
                        entityName = entityName + ".java";
                    }else if(modelName.equals("sql")) {
                        entityName = DataUtil.getPropertyName(generateConfig.getTableName()) + ".sql";
                    } else {
                        entityName = entityName + DataUtil.getMethodName(modelName) + ".java";
                    }
                    generateConfig.setRefTemplete(modelName + "Templete");
                    generateConfig.setFileName(entityName);
                    generateConfigList.add(generateConfig);
                }
//                System.out.println(JSONObject.toJSONString(list));

            }
        }
        System.out.println("配置文件解析完毕");
    }

    private static List getList(Map map, String parentKey, String key){
        List list = new ArrayList();
        if(map.get(parentKey) != null && map.get(parentKey) instanceof Map) {
            Object o = ((Map) map.get(parentKey)).get(key);
            if(o instanceof List) {
                list.addAll((List)((Map) map.get(parentKey)).get(key));
            } else {
                list.add((Map)((Map) map.get(parentKey)).get(key));
            }

        } else {
            list = (List)map.get(parentKey);
        }
        return list;
    }
}
