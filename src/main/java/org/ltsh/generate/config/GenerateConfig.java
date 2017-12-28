package org.ltsh.generate.config;

/**
 * Created by fengjianbo on 2017/12/27.
 */
public class GenerateConfig {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 生成class名称
     */
    private String fileName;
    /**
     * 引用模板
     */
    private String refTemplete;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRefTemplete() {
        return refTemplete;
    }

    public void setRefTemplete(String refTemplete) {
        this.refTemplete = refTemplete;
    }
}
