package org.ltsh.generate.config;

/**
 * Created by fengjianbo on 2017/12/26.
 */
public class JdbcConfig {
//    jdbcJarPath="E:\ruanjian\java\maven\repository\mysql\mysql-connector-java\5.1.32\mysql-connector-java-5.1.32.jar"
//    driverClass="com.mysql.jdbc.Driver"
//    connectionURL="jdbc:mysql://192.168.10.121:3306/signofcloud?characterEncoding=utf8"
//    userName="root"
//    password="gdca@123"
    /**
     * jdbcjar路径
     */
    private String jdbcJarPath;
    /**
     * 驱动类
     */
    private String driverClass;
    /**
     * 链接字符串
     */
    private String connectionURL;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;

    private String useJdbc;

    private String useInformationSchema;

    public String getUseInformationSchema() {
        return useInformationSchema;
    }

    public void setUseInformationSchema(String useInformationSchema) {
        this.useInformationSchema = useInformationSchema;
    }

    public String getUseJdbc() {
        return useJdbc;
    }

    public void setUseJdbc(String useJdbc) {
        this.useJdbc = useJdbc;
    }

    public String getJdbcJarPath() {
        return jdbcJarPath;
    }

    public void setJdbcJarPath(String jdbcJarPath) {
        this.jdbcJarPath = jdbcJarPath;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
