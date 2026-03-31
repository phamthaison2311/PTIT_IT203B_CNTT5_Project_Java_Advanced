package dao;

import model.User;
import util.DataConnect;

import java.sql.*;

public class UserDAO {

    public void register(User user) throws Exception {
        String sql = "INSERT INTO Users(user_name, email, password, role) VALUES (?, ?, ?, ?)";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, user.getUserName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setString(4, "customer"); // đặt mặc định khi đăng ký là người dùng

        ps.executeUpdate();
        conn.close();
    }

    // check đăng nhập
    public User login(String email, String password) throws Exception {
        String sql = "SELECT * FROM Users WHERE email=? AND password=?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User u = new User();
            u.setUserId(rs.getInt("user_id"));
            u.setUserName(rs.getString("user_name"));
            u.setEmail(rs.getString("email"));
            u.setRole(rs.getString("role"));
            conn.close();
            return u;
        }

        conn.close();
        return null;
    }

    // tài khoản admin mặc định
    public void createDefaultAdmin() throws Exception {
        String checkSql = "SELECT * FROM Users WHERE email = ?";
        String insertSql = "INSERT INTO Users(user_name, email, password, role) VALUES (?, ?, ?, ?)";

        Connection conn = DataConnect.openConnection();

        // check tồn tại của tài khoản admin
        PreparedStatement check = conn.prepareStatement(checkSql);
        check.setString(1, "admin@gmail.com");
        ResultSet rs = check.executeQuery();

        if (!rs.next()) {
            PreparedStatement insert = conn.prepareStatement(insertSql);
            insert.setString(1, "adminCute");
            insert.setString(2, "admin@gmail.com");
            insert.setString(3, "123456789@");
            insert.setString(4, "admin");

            insert.executeUpdate();
            System.out.println("Đã tạo tài khoản admin mặc định!");
        }

        conn.close();
    }

    public User findByEmail(String email) throws Exception {
        String sql = "SELECT * FROM Users WHERE email=?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User u = new User();
            u.setUserId(rs.getInt("user_id"));
            u.setUserName(rs.getString("user_name"));
            u.setEmail(rs.getString("email"));
            return u;
        }

        conn.close();
        return null;
    }

    public void updateInfo(int userId, String phone, String address) throws Exception {
        String sql = "UPDATE Users SET phone=?, address=? WHERE user_id=?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, phone);
        ps.setString(2, address);
        ps.setInt(3, userId);

        ps.executeUpdate();
        conn.close();
    }
}