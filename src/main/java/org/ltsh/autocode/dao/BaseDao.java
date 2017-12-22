package org.ltsh.autocode.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * Created by fengjb-it on 2016/2/14 0014.
 */
public class BaseDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Map<String, Object>> queryColumns(String tableName){
        String sql = "SELECT table_name,column_name,data_type,column_type,column_comment,is_nullable FROM COLUMNS WHERE table_name=?";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, tableName);
        return resultList;

    }

}
