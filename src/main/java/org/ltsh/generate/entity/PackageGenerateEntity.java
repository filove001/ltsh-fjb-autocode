package org.ltsh.generate.entity;

/**
 * Created by fengjianbo on 2017/12/26.
 */
public class PackageGenerateEntity extends GenerateEntity {
    /**
     * 父级包名
     */
    private String basePackageStr;

    public String getBasePackageStr() {
        return basePackageStr;
    }

    public void setBasePackageStr(String basePackageStr) {
        this.basePackageStr = basePackageStr;
    }
}
