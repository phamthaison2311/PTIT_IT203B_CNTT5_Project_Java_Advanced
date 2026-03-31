package service;

import dao.CartDAO;
import dao.ProductDAO;
import model.CartItem;
import model.Product;

import java.util.List;

public class CartService {

    private CartDAO dao = new CartDAO();

    public void addToCart(int userId, int productId, int quantity) {
        try {
            Product p = ProductDAO.getById(productId);

            if (p == null) {
                System.out.println("Sản phẩm không tồn tại!");
                return;
            }

            if (p.getStock() <= 0) {
                System.out.println("Hết hàng!");
                return;
            }

            if (quantity <= 0) {
                System.out.println("Số lượng phải > 0!");
                return;
            }

            if (quantity > p.getStock()) {
                System.out.println("Vượt quá tồn kho!");
                return;
            }

            int current = CartDAO.getQuantityInCart(userId, productId);

            if (current + quantity > p.getStock()) {
                System.out.println("Tổng số lượng trong giỏ vượt quá tồn kho!");
                return;
            }

            CartDAO.addToCart(userId, productId, quantity);
            System.out.println("Thêm vào giỏ thành công!");

        } catch (Exception e) {
            System.out.println("Lỗi!");
        }
    }

    public void viewCart(int userId) {
        try {
            List<CartItem> list = CartDAO.getCartByUser(userId);

            if (list.isEmpty()) {
                System.out.println("Giỏ hàng trống!");
                return;
            }

            System.out.println("\n===== GIỎ HÀNG =====");
            System.out.printf("%-5s %-25s %-10s %-10s %-10s\n",
                    "ID", "Tên SP", "Giá", "SL", "Tổng");

            double total = 0;

            for (CartItem item : list) {
                double sub = item.getPrice() * item.getQuantity();
                total += sub;

                System.out.printf("%-5d %-25s %-10.0f %-10d %-10.0f\n",
                        item.getProductId(),
                        item.getProductName(),
                        item.getPrice(),
                        item.getQuantity(),
                        sub);
            }

            System.out.println("-----------------------------");
            System.out.printf("TỔNG: %.0f\n", total);

        } catch (Exception e) {
            System.out.println("Lỗi hiển thị giỏ!");
        }
    }
}