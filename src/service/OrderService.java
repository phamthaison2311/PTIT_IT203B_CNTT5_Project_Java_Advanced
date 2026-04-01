package service;

import dao.OrderDAO;
import model.Order;
import model.OrderDetail;
import util.DataConnect;
import java.sql.*;
import java.util.List;

public class OrderService {

    public void checkout(int userId) {
        try {
            orderDAO.checkout(userId);
        } catch (Exception e) {
            System.out.println("Lỗi đặt hàng!");
        }
    }

    private OrderDAO orderDAO = new OrderDAO();
    public void viewOrders(int userId) {
        try {
            List<Order> list = orderDAO.getOrdersByUser(userId);

            if (list.isEmpty()) {
                System.out.println("Không có đơn hàng!");
                return;
            }

            System.out.println("\n========= DANH SÁCH ĐƠN HÀNG =========");

            System.out.printf("%-5s %-15s %-15s %-20s\n",
                    "ID", "Tổng tiền", "Trạng thái", "Ngày tạo");

            System.out.println("----------------------------------------------------------");

            for (Order o : list) {
                System.out.printf("%-5d %-15.0f %-15s %-20s\n",
                        o.getIdOrder(),
                        o.getTotalAmount(),
                        o.getStatus(),
                        o.getCreatedAt());
            }

            System.out.println("----------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Lỗi khi xem đơn hàng!");
            e.printStackTrace();
        }
    }

    public void cancelOrder(int userId, int orderId) {
        try {
            boolean success = orderDAO.cancelOrder(userId, orderId);

            if (success) {
                System.out.println("Hủy đơn thành công!");
            } else {
                System.out.println("Không thể hủy đơn (không tồn tại hoặc không phải PENDING)!");
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi hủy đơn!");
            e.printStackTrace();
        }
    }

    public void viewAllOrders() {
        try {
            orderDAO.updateAutoShipped();

            List<Order> list = orderDAO.getAllOrders();

            System.out.println("\n===== DANH SÁCH ĐƠN =====");

            System.out.printf("%-5s %-15s %-15s %-15s %-20s\n",
                    "ID", "User", "Tổng tiền", "Trạng thái", "Ngày tạo");

            System.out.println("--------------------------------------------------------------------------");

            for (Order o : list) {
                System.out.printf("%-5d %-15s %-15.0f %-15s %-20s\n",
                        o.getIdOrder(),
                        o.getUserName(),
                        o.getTotalAmount(),
                        o.getStatus(),
                        o.getCreatedAt());
            }

        } catch (Exception e) {
            System.out.println("Lỗi!");
        }
    }

    public void viewOrderDetail(int orderId) {
        try {
            List<OrderDetail> list = orderDAO.getOrderDetail(orderId);

            if (list.isEmpty()) {
                System.out.println("Không có chi tiết đơn hàng!");
                return;
            }

            System.out.println("\n===== CHI TIẾT ĐƠN HÀNG =====");

            System.out.printf("%-25s %-10s %-10s %-10s\n",
                    "Tên sản phẩm", "Giá", "SL", "Thành tiền");

            double total = 0;

            for (OrderDetail d : list) {
                double sub = d.getPrice() * d.getQuantity();
                total += sub;

                System.out.printf("%-25s %-10.0f %-10d %-10.0f\n",
                        d.getProductName(),
                        d.getPrice(),
                        d.getQuantity(),
                        sub);
            }

            System.out.println("------------------------------------------");
            System.out.printf("TỔNG: %.0f\n", total);

        } catch (Exception e) {
            System.out.println("Lỗi xem chi tiết!");
        }
    }

    public void updateOrderStatus(int orderId, String status) {
        try {
            orderDAO.updateOrderStatus(orderId, status);
        } catch (Exception e) {
            System.out.println("Lỗi cập nhật trạng thái!");
        }
    }

}