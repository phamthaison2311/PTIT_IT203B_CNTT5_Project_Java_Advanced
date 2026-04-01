package dao;

import model.Order;
import model.OrderDetail;
import util.DataConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<Order> getOrdersByUser(int userId) throws Exception {
        List<Order> list = new ArrayList<>();

        String sql = "SELECT * FROM Orders WHERE user_id=? ORDER BY created_at DESC";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order o = new Order();
            o.setIdOrder(rs.getInt("id_order"));
            o.setTotalAmount(rs.getDouble("total_amount"));
            o.setStatus(rs.getString("status"));
            o.setCreatedAt(rs.getString("created_at"));

            list.add(o);
        }

        conn.close();
        return list;
    }

    // hủy đơn
    public boolean cancelOrder(int userId, int orderId) throws Exception {
        String sql = """
            UPDATE Orders 
            SET status = 'cancelled'
            WHERE id_order=? 
              AND user_id=? 
              AND status='pending'
        """;

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, orderId);
        ps.setInt(2, userId);

        int rows = ps.executeUpdate();

        conn.close();
        return rows > 0;
    }

    public List<Order> getAllOrders() throws Exception {
        List<Order> list = new ArrayList<>();

        String sql = """
        SELECT o.*, u.user_name 
        FROM Orders o
        JOIN Users u ON o.user_id = u.user_id
        ORDER BY o.created_at DESC
    """;

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order o = new Order();
            o.setIdOrder(rs.getInt("id_order"));
            o.setUserName(rs.getString("user_name"));
            o.setTotalAmount(rs.getDouble("total_amount"));
            o.setStatus(rs.getString("status"));
            o.setCreatedAt(rs.getString("created_at"));

            list.add(o);
        }

        conn.close();
        return list;
    }

    public void updateAutoShipped() throws Exception {
        String sql = """
        UPDATE Orders
        SET status = 'shipped'
        WHERE status = 'pending'
          AND TIMESTAMPDIFF(MINUTE, created_at, NOW()) >= 1
    """;

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.executeUpdate();
        conn.close();
    }

    public List<OrderDetail> getOrderDetail(int orderId) throws Exception {
        List<OrderDetail> list = new ArrayList<>();

        String sql = """
        SELECT p.product_name, od.price, od.quantity
        FROM Order_Details od
        JOIN Products p ON od.id_product = p.id_product
        WHERE od.id_order = ?
    """;

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            OrderDetail detail = new OrderDetail();
            detail.setProductName(rs.getString("product_name"));
            detail.setPrice(rs.getDouble("price"));
            detail.setQuantity(rs.getInt("quantity"));

            list.add(detail);
        }

        conn.close();
        return list;
    }

    public void checkout(int userId) throws Exception {
        Connection conn = DataConnect.openConnection();

        try {
            conn.setAutoCommit(false);

            String cartSql = """
            SELECT c.id_product, c.quantity, p.price, p.stock
            FROM Cart c
            JOIN Products p ON c.id_product = p.id_product
            WHERE c.user_id = ?
        """;

            PreparedStatement cartPs = conn.prepareStatement(cartSql);
            cartPs.setInt(1, userId);
            ResultSet rs = cartPs.executeQuery();

            List<Integer> productIds = new ArrayList<>();
            List<Integer> quantities = new ArrayList<>();
            List<Double> prices = new ArrayList<>();

            double total = 0;

            while (rs.next()) {
                int productId = rs.getInt("id_product");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");

                if (quantity > stock) {
                    throw new Exception("Sản phẩm ID " + productId + " không đủ hàng!");
                }

                productIds.add(productId);
                quantities.add(quantity);
                prices.add(price);

                total += price * quantity;
            }

            // Tạo order
            String orderSql = "INSERT INTO Orders(user_id, total_amount) VALUES (?, ?)";
            PreparedStatement orderPs = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderPs.setInt(1, userId);
            orderPs.setDouble(2, total);
            orderPs.executeUpdate();

            ResultSet key = orderPs.getGeneratedKeys();
            int orderId = 0;
            if (key.next()) {
                orderId = key.getInt(1);
            }

            // Insert order details + trừ stock
            for (int i = 0; i < productIds.size(); i++) {
                int productId = productIds.get(i);
                int quantity = quantities.get(i);
                double price = prices.get(i);

                String detailSql = "INSERT INTO Order_Details(id_order, id_product, quantity, price) VALUES (?, ?, ?, ?)";
                PreparedStatement detailPs = conn.prepareStatement(detailSql);
                detailPs.setInt(1, orderId);
                detailPs.setInt(2, productId);
                detailPs.setInt(3, quantity);
                detailPs.setDouble(4, price);
                detailPs.executeUpdate();

                String updateStock = "UPDATE Products SET stock = stock - ? WHERE id_product = ?";
                PreparedStatement stockPs = conn.prepareStatement(updateStock);
                stockPs.setInt(1, quantity);
                stockPs.setInt(2, productId);
                stockPs.executeUpdate();
            }

            // Xóa giỏ hàng sau khi xác nhận đặt
            String clearCart = "DELETE FROM Cart WHERE user_id=?";
            PreparedStatement clearPs = conn.prepareStatement(clearCart);
            clearPs.setInt(1, userId);
            clearPs.executeUpdate();

            conn.commit();

            System.out.println("Đặt hàng thành công!");

        } catch (Exception e) {
            conn.rollback();
            System.out.println("Đặt hàng thất bại!");
            throw e;
        } finally {
            conn.close();
        }
    }

    public void updateOrderStatus(int orderId, String status) throws Exception {
        String sql = "UPDATE Orders SET status=? WHERE id_order=?";

        Connection conn = DataConnect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, status);
        ps.setInt(2, orderId);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Cập nhật trạng thái thành công!");
        } else {
            System.out.println("Không tìm thấy đơn hàng!");
        }

        conn.close();
    }
}