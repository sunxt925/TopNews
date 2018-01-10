package com.didi.moon;

import java.sql.*;

/**
 *  针对线下测试环境的数据脱敏处理，数据库操作工具类
 *   via Xiaoteng
 */
public class DBUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "ktv";
    private static final String DB_USER = "root";
    private static final String DB_PWD = "123456";

    private static Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PWD);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet excuteQuery(String sql){
        if (getConnection() == null) {
            return null;
        }
        try {
            ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }


    public static ResultSet excuteQuery(String sql,Object[] obj){

        if (getConnection() == null) {
            return null;
        }
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i <obj.length ; i++) {
                ps.setObject(i+1,obj[i]);
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

public static int executeUpdate(String sql) {  
        int result = -1;  
        if (getConnection() == null) {
            return result;  
        }  
        try {  
            ps = conn.prepareStatement(sql);  
            result = ps.executeUpdate();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  


    public static int excuteUpdate(String sql,Object[] obj){
        int result = -1;

        if (getConnection() == null) {
            return 0;
        }
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i <obj.length ; i++) {
                ps.setObject(i+1,obj[i]);
            }
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static void DBclose(){

        try {

            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}