package org.ltsh.generate.config;

/**
 * Created by fengjianbo on 2018/3/7.
 */

public class GenerateBatchConfig {
    private String baseTempletePath;
    private String tableName;

    public String getBaseTempletePath() {
        return baseTempletePath;
    }

    public void setBaseTempletePath(String baseTempletePath) {
        this.baseTempletePath = baseTempletePath;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
