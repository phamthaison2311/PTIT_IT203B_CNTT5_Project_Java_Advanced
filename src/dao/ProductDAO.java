package dao;

import model.Product;
import util.DataConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void insert(Product p) throws Exception {
        String sql = "INSERT INTO Products(product_name, brand, capacity, color, price, stock, description, id_category) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, p.getProductName());
        ps.setString(2, p.getBrand());
        ps.setString(3, p.getCapacity());
        ps.setString(4, p.getColor());
        ps.setDouble(5, p.getPrice());
        ps.setInt(6, p.getStock());
        ps.setString(7, p.getDescription());
        ps.setInt(8, p.getIdCategory());

        ps.executeUpdate();
        conn.close();
    }


    public List<Product> getAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products";

        Connection conn = DataConnect.openConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Product p = new Product();
            p.setIdProduct(rs.getInt("id_product"));
            p.setProductName(rs.getString("product_name"));
            p.setBrand(rs.getString("brand"));
            p.setCapacity(rs.getString("capacity"));
            p.setColor(rs.getString("color"));
            p.setPrice(rs.getDouble("price"));
            p.setStock(rs.getInt("stock"));
            p.setDescription(rs.getString("description"));
            p.setIdCategory(rs.getInt("id_category"));

            list.add(p);
        }

        conn.close();
        return list;
    }


    public void update(Product p) throws Exception {
        String sql = "UPDATE Products SET product_name=?, brand=?, capacity=?, color=?, price=?, stock=?, description=?, id_category=? WHERE id_product=?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, p.getProductName());
        ps.setString(2, p.getBrand());
        ps.setString(3, p.getCapacity());
        ps.setString(4, p.getColor());
        ps.setDouble(5, p.getPrice());
        ps.setInt(6, p.getStock());
        ps.setString(7, p.getDescription());
        ps.setInt(8, p.getIdCategory());
        ps.setInt(9, p.getIdProduct());

        ps.executeUpdate();
        conn.close();
    }


    public void delete(int id) throws Exception {
        String sql = "DELETE FROM Products WHERE id_product=?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);
        ps.executeUpdate();

        conn.close();
    }


    public List<Product> searchByName(String keyword) throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE product_name LIKE ?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, "%" + keyword + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Product p = new Product();
            p.setIdProduct(rs.getInt("id_product"));
            p.setProductName(rs.getString("product_name"));
            p.setBrand(rs.getString("brand"));
            p.setCapacity(rs.getString("capacity"));
            p.setColor(rs.getString("color"));
            p.setPrice(rs.getDouble("price"));
            p.setStock(rs.getInt("stock"));
            p.setDescription(rs.getString("description"));
            p.setIdCategory(rs.getInt("id_category"));

            list.add(p);
        }

        conn.close();
        return list;
    }

    public static Product getById(int id) throws Exception {
        String sql = "SELECT * FROM Products WHERE id_product=?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Product p = new Product();
            p.setIdProduct(rs.getInt("id_product"));
            p.setProductName(rs.getString("product_name"));
            p.setBrand(rs.getString("brand"));
            p.setCapacity(rs.getString("capacity"));
            p.setColor(rs.getString("color"));
            p.setPrice(rs.getDouble("price"));
            p.setStock(rs.getInt("stock"));
            p.setDescription(rs.getString("description"));
            p.setIdCategory(rs.getInt("id_category"));
            return p;
        }

        conn.close();
        return null;
    }

    // sắp xếp sản phẩm theo giá
    public List<Product> sortByPrice(boolean asc) throws Exception {
        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM Products ORDER BY price " + (asc ? "ASC" : "DESC");

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Product p = new Product();
            p.setIdProduct(rs.getInt("id_product"));
            p.setProductName(rs.getString("product_name"));
            p.setBrand(rs.getString("brand"));
            p.setCapacity(rs.getString("capacity"));
            p.setColor(rs.getString("color"));
            p.setPrice(rs.getDouble("price"));
            p.setStock(rs.getInt("stock"));

            list.add(p);
        }

        conn.close();
        return list;
    }
}
