package org.ltsh.generate.entity;


import java.io.Serializable;
import java.util.Map;

/**
 * Created by fengjianbo on 2017/12/22.
 */

public class GenerateEntity implements Serializable {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 模版位置
     */
    private String templetePath;
//    /**
//     * 父级包名
//     */
//    private String basePackageStr;
    /**
     * 输出路径
     */
    private String outPath;
    /**
     * 模块路径
     */
//    private String modulesPath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 模块名
     */
//    private String modulesName;
    /**
     * 额外参数
     */
    private Map<String, Object> extraParams;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTempletePath() {
        return templetePath;
    }

    public void setTempletePath(String templetePath) {
        this.templetePath = templetePath;
    }


    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public Map<String, Object> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, Object> extraParams) {
        this.extraParams = extraParams;
    }
}
