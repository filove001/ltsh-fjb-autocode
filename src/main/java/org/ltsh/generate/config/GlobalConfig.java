package org.ltsh.generate.config;


import java.util.List;

/**
 * Created by fengjianbo on 2017/12/26.
 */
public class GlobalConfig {
    /**
     * 数据库配置
     */
    public static JdbcConfig jdbcConfig;
    /**
     * 模版配置
     */
    public static List<TempleteConfig> templeteConfigList;
    /**
     * 生成配置
     */
    public static List<GenerateConfig> generateConfigList;
}
