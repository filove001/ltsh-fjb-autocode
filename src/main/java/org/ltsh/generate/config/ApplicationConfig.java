package org.ltsh.generate.config;

/**
 * Created by fengjianbo on 2018/3/6.
 */
public class ApplicationConfig {
    private String projectPath;
    private String baseOutPath;

    public String getBaseOutPath() {
        return baseOutPath;
    }

    public void setBaseOutPath(String baseOutPath) {
        this.baseOutPath = baseOutPath;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}
