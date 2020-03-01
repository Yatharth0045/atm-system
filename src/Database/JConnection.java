/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Yatharth
 */
public class JConnection {
    static Connection conn;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/atm?zeroDateTimeBehavior=convertToNull";
    static final String USER = "root";
    static final String PASS = "";
       
    public static Connection getSqlConnection()
    {
        
        try {
            Class.forName(JDBC_DRIVER);
            conn=DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
