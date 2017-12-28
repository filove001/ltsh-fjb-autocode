package org.ltsh.generate.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengjianbo on 2017/12/26.
 */
public class TempleteConfig {
    /**
     * 模板id
     */
    private String id;
    /**
     * 模板路径
     */
    private String templetePath;
    /**
     * 输出路径
     */
    private String outPath;
    /**
     * 文件后缀
     */
    private String suffix;
    /**
     * 文件编码
     */
    private String fileEncoding = "utf-8";

    private String targetPackage;
    /**
     * 额外参数
     */
    private Map<String, Object> extraMap = new HashMap();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
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

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

    public Map<String, Object> getExtraMap() {
        return extraMap;
    }

    public void setExtraMap(Map<String, Object> extraMap) {
        this.extraMap = extraMap;
    }
}
