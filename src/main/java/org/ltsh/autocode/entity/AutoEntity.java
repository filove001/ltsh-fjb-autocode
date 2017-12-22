package org.ltsh.autocode.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Random on 2017/12/22.
 */

public class AutoEntity implements Serializable {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 模版位置
     */
    private String templetePath;
    /**
     * 模版名称
     */
    private String templeteName;
    /**
     * 父级包名
     */
    private String basePackageStr;
    /**
     * 输出路径
     */
    private String outPath;
    /**
     * 模块路径
     */
    private String modulesPath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 模块名
     */
    private String modulesName;
    /**
     * 额外参数
     */
    private Map<String, Object> extraParams;

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

    public String getTempleteName() {
        return templeteName;
    }

    public void setTempleteName(String templeteName) {
        this.templeteName = templeteName;
    }

    public String getBasePackageStr() {
        return basePackageStr;
    }

    public void setBasePackageStr(String basePackageStr) {
        this.basePackageStr = basePackageStr;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public String getModulesPath() {
        return modulesPath;
    }

    public void setModulesPath(String modulesPath) {
        this.modulesPath = modulesPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getModulesName() {
        return modulesName;
    }

    public void setModulesName(String modulesName) {
        this.modulesName = modulesName;
    }

    public Map<String, Object> getExtraParams() {
        return extraParams;
    }

    public void setExtraParams(Map<String, Object> extraParams) {
        this.extraParams = extraParams;
    }
}
