package org.ltsh.generate.jdbc;

import org.ltsh.generate.config.GlobalConfig;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.*;

/**
 * Created by fengjianbo on 2017/12/27.
 */
public class BaseDao {
    private static Driver driver = null;
    public static Driver getDriver() throws Exception {
        if(BaseDao.driver == null) {
            String jdbcJarPath = GlobalConfig.jdbcConfig.getJdbcJarPath();
            System.out.println(jdbcJarPath);
            URL url1 = new URL(jdbcJarPath);
            URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { url1 }, Thread.currentThread()
                    .getContextClassLoader());
            Class<?> aClass = myClassLoader1.loadClass("com.mysql.jdbc.Driver");
            Driver driver = (Driver)aClass.newInstance();
            BaseDao.driver = driver;
        }
        return BaseDao.driver;
    }
    public static Connection getConnection() throws Exception {
        String useJdbc = GlobalConfig.jdbcConfig.getUseJdbc();
        if(useJdbc.equals("true")) {
            String connectionURL = GlobalConfig.jdbcConfig.getConnectionURL();
            Driver driver = getDriver();
            Properties properties = new Properties();
            properties.put("user", GlobalConfig.jdbcConfig.getUserName());
            properties.put("password", GlobalConfig.jdbcConfig.getPassword());
            properties.setProperty("useInformationSchema", "true");
            return driver.connect(connectionURL, properties);
        }
        return null;
    }
    public static List<Map<String, Object>> queryColumns(String tableName){
        String sql = "SELECT table_name,column_name,data_type,column_type,column_comment,is_nullable FROM information_schema.COLUMNS WHERE table_name=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tableName);
            rs = preparedStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            while(rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < columnCount; i++) {
                    map.put(metaData.getColumnName(i + 1).toLowerCase(), rs.getObject(i + 1));
                }
                list.add(map);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



}
