package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnect {
    // DRIVER
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    // URL
    private final static String URL = "jdbc:mysql://localhost:3306/phone_store?useSSL=false&serverTimezone=UTC";
    // USER
    private final static String USER = "root";
    // PASSWORD
    private final static String PASS = "123456@";

    public static Connection openConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Kết nối dữ liệu thất bại!", e);
        }
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}