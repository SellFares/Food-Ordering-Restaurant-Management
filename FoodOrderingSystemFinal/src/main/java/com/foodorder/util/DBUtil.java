package com.foodorder.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static final String URL = "jdbc:mysql://localhost:3306/food_order_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    private static final String USER = "root";
    
    private static final String PWD = "swe2409146";

    public static Connection getConnection() {
        Connection conn = null;
        
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PWD);
            
        } catch (Exception e) {
        	
            e.printStackTrace();
            
            
        }
        
        return conn;
    }

    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (pstmt != null) pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
    }
}