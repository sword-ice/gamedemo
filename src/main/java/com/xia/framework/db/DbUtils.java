package com.xia.framework.db;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtils {

   private static Logger logger = LoggerFactory.getLogger(DruidUtils.class);


    public static Map<String,Object> queryRow(String sql, Object... msg){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            conn = DruidUtils.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            if (rs.next()){
                int cols = rsmd.getColumnCount();
                for(int i = 1;i<= cols;i++){
                    String columnLabel = rsmd.getColumnLabel(i);
                    if(columnLabel == null || columnLabel.length() == 0){
                        columnLabel = rsmd.getColumnName(i);
                    }
                    result.put(columnLabel,rs.getObject(i));
                }
            }
        } catch (SQLException e) {
            logger.error("DB query row fail",e);
            e.printStackTrace();
        }finally {
            DruidUtils.close(conn,st,rs);
        }
        return result;
    }

    public static List<Map<String,Object>> queryRows(String sql, Object... msg){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
        try {
            conn = DruidUtils.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()){
                int cols = rsmd.getColumnCount();
                Map<String,Object> map = new HashMap<String, Object>();
                for(int i = 1;i<= cols;i++){
                    String columnLabel = rsmd.getColumnLabel(i);
                    if(columnLabel == null || columnLabel.length() == 0){
                        columnLabel = rsmd.getColumnName(i);
                    }
                    map.put(columnLabel,rs.getObject(i));
                }
                result.add(map);
            }
        } catch (SQLException e) {
            logger.error("DB query rows fail",e);
            e.printStackTrace();
        }finally {
            DruidUtils.close(conn,st,rs);
        }
        return result;
    }

    public static int executeSql(String sql) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = DruidUtils.getConnection();
            st = conn.createStatement();
            return st.executeUpdate(sql);
        }catch (Exception e){
            logger.error("executeSql fail",e);
            throw new SQLException(e);
        }finally {
            DruidUtils.close(conn,st,rs);
        }
    }

    public static int insertSql(String sql) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = DruidUtils.getConnection();
            st = conn.createStatement();
            rs = st.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }else {
                throw new Exception("sql_insert has no autoincreament key id!", null);
            }
        }catch (Exception e){
            logger.error("executeSql fail",e);
            throw new SQLException(e);
        }finally {
            DruidUtils.close(conn,st,rs);
        }
    }


}
