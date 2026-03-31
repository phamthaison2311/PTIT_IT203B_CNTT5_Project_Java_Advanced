package dao;
import model.Category;
import util.DataConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public void insert(String name) throws Exception {
        String sql = "INSERT INTO Categories(category_name) VALUES (?)";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name);
        ps.executeUpdate();

        conn.close();
    }

    public List<Category> getAll() throws Exception {
        List<Category> list = new ArrayList<>();

        String sql = "SELECT * FROM Categories";
        Connection conn = DataConnect.openConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Category c = new Category();
            c.setId(rs.getInt("id_category"));
            c.setName(rs.getString("category_name"));
            list.add(c);
        }

        conn.close();
        return list;
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM Categories WHERE id_category=?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);
        ps.executeUpdate();

        conn.close();
    }

    public void update(int id, String name) throws Exception {
        String sql = "UPDATE Categories SET category_name=? WHERE id_category=?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name);
        ps.setInt(2, id);

        ps.executeUpdate();
        conn.close();
    }

    public boolean isCategoryExists(int id) throws Exception {
        String sql = "SELECT * FROM Categories WHERE id_category = ?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        boolean exists = rs.next();
        conn.close();

        return exists;
    }

    public boolean isCategoryNameExists(String name) throws Exception {
        String sql = "SELECT * FROM Categories WHERE LOWER(category_name) = LOWER(?)";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();

        boolean exists = rs.next();
        conn.close();

        return exists;
    }
}
