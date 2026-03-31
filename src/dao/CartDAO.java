package dao;

import model.CartItem;
import util.DataConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // thêm hoặc update
    public static void addToCart(int userId, int productId, int quantity) throws Exception {
        Connection conn = DataConnect.openConnection();

        String check = "SELECT * FROM Cart WHERE user_id=? AND id_product=?";
        PreparedStatement ps = conn.prepareStatement(check);
        ps.setInt(1, userId);
        ps.setInt(2, productId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String update = "UPDATE Cart SET quantity = quantity + ? WHERE user_id=? AND id_product=?";
            PreparedStatement ps2 = conn.prepareStatement(update);
            ps2.setInt(1, quantity);
            ps2.setInt(2, userId);
            ps2.setInt(3, productId);
            ps2.executeUpdate();
        } else {
            String insert = "INSERT INTO Cart(user_id, id_product, quantity) VALUES (?, ?, ?)";
            PreparedStatement ps2 = conn.prepareStatement(insert);
            ps2.setInt(1, userId);
            ps2.setInt(2, productId);
            ps2.setInt(3, quantity);
            ps2.executeUpdate();
        }

        conn.close();
    }

    // xem giỏ
    public void viewCart(int userId) throws Exception {
        String sql = """
            SELECT p.product_name, p.price, c.quantity,
                   (p.price * c.quantity) AS total
            FROM Cart c
            JOIN Products p ON c.id_product = p.id_product
            WHERE c.user_id = ?
        """;

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println(
                    rs.getString("product_name") + " | " +
                            rs.getDouble("price") + " | " +
                            rs.getInt("quantity") + " | " +
                            rs.getDouble("total")
            );
        }

        conn.close();
    }

    // xóa giỏ
    public void clearCart(int userId) throws Exception {
        String sql = "DELETE FROM Cart WHERE user_id=?";
        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.executeUpdate();
        conn.close();
    }


    public static List<CartItem> getCartByUser(int userId) throws Exception {
        List<CartItem> list = new ArrayList<>();

        String sql = """
        SELECT p.id_product, p.product_name, p.price, c.quantity
        FROM Cart c
        JOIN Products p ON c.id_product = p.id_product
        WHERE c.user_id = ?
    """;

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            CartItem item = new CartItem();
            item.setProductId(rs.getInt("id_product"));
            item.setProductName(rs.getString("product_name"));
            item.setPrice(rs.getDouble("price"));
            item.setQuantity(rs.getInt("quantity"));

            list.add(item);
        }

        conn.close();
        return list;
    }

    public static int getQuantityInCart(int userId, int productId) throws Exception {
        String sql = "SELECT quantity FROM Cart WHERE user_id=? AND id_product=?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, userId);
        ps.setInt(2, productId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("quantity");
        }

        conn.close();
        return 0;
    }
}