package org.ltsh.generate;

import com.alibaba.fastjson.JSONObject;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.ltsh.generate.config.GenerateConfig;
import org.ltsh.generate.config.GlobalConfig;
import org.ltsh.generate.config.ReadConfig;
import org.ltsh.generate.config.TempleteConfig;
import org.ltsh.generate.entity.PackageGenerateEntity;

import org.ltsh.generate.templete.TempleteUtils;

/**
 * Created by fengjianbo on 2017/12/26.
 */
@Mojo(name = "run")
public class GenerateMain extends AbstractMojo {

    @Parameter(defaultValue = "/templeteProject/autoConfig.xml")
    private String configPath;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("ConfigPath:" + configPath);
        try {
            ReadConfig.readConfig(configPath);
            for (GenerateConfig generateConfig: GlobalConfig.generateConfigList) {
                for (TempleteConfig templeteConfig: GlobalConfig.templeteConfigList) {
                    if(generateConfig.getRefTemplete().equals(templeteConfig.getId())) {
                        PackageGenerateEntity packageGenerateEntity = new PackageGenerateEntity();
                        packageGenerateEntity.setBasePackageStr(templeteConfig.getTargetPackage());
                        packageGenerateEntity.setOutPath(templeteConfig.getOutPath());
                        packageGenerateEntity.setTempletePath(templeteConfig.getTempletePath());
                        packageGenerateEntity.setOutPath(templeteConfig.getOutPath());
                        packageGenerateEntity.setExtraParams(templeteConfig.getExtraMap());
                        packageGenerateEntity.setTableName(generateConfig.getTableName());
                        packageGenerateEntity.setFileName(generateConfig.getFileName());
                        TempleteUtils.writeFileForPage(packageGenerateEntity);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
