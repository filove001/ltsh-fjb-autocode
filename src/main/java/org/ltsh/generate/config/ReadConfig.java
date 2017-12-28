package org.ltsh.generate.config;

import com.alibaba.fastjson.JSONObject;
import org.ltsh.generate.bean.BeanUtils;

import org.ltsh.generate.xml.ReadXml;

import java.io.InputStream;
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
        InputStream resourceAsStream = ReadConfig.class.getResourceAsStream("/templete/autoConfig.xml");
        Map configuration = ReadXml.xmlToMap(resourceAsStream, "configuration");
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
        for (Object obj : generateList) {
            Map map = (Map) obj;
            GenerateConfig generateConfig = BeanUtils.mapToBean(map, GenerateConfig.class);
            generateConfigList.add(generateConfig);
        }
        GlobalConfig.generateConfigList = generateConfigList;
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
