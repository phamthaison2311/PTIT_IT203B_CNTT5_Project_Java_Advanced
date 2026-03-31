package view;

import model.User;
import service.*;

import java.util.Scanner;

import static view.MenuAdmin.*;

public class MenuUser {
    private static Scanner sc = new Scanner(System.in);
    private static AuthService authService = new AuthService();
    private static ProductService productService = new ProductService();
    private static CartService cartService = new CartService();
    private static OrderService orderService = new OrderService();

    /**
     * PHẦN MENU CHO NGƯỜI DÙNG
     */
    public static void userMenu(User user) {
        while (true) {
            System.out.println(CYAN + "    ╔══════════════════════════════════════════╗" + RESET);
            System.out.println(CYAN + "    ║" + YELLOW + "            HỆ THỐNG MUA SẮM              " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ╠══════════════════════════════════════════╣" + RESET);
            System.out.println(CYAN + "    ║" + GREEN + "  [1]. Xem danh sách sản phẩm             " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ║" + GREEN + "  [2]. Thêm sản phẩm vào giỏ hàng         " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ║" + GREEN + "  [3]. Kiểm tra giỏ hàng                  " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ║" + GREEN + "  [4]. Tiến hành đặt hàng                 " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ╠══════════════════════════════════════════╣" + RESET);
            System.out.println(CYAN + "    ║" + PURPLE + "  [5]. Cập nhật thông tin cá nhân         " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ║" + PURPLE + "  [6]. Quản lý đơn hàng đã đặt            " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ║" + PURPLE + "  [7]. Sắp xếp sản phẩm theo giá          " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ╠══════════════════════════════════════════╣" + RESET);
            System.out.println(CYAN + "    ║" + RED + "  [0]. Đăng xuất hệ thống                 " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ╚══════════════════════════════════════════╝" + RESET);
            System.out.print(YELLOW + "    > Nhập lựa chọn của bạn: " + RESET);
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    productService.displayAll();
                    break;

                case 2:
                    System.out.print("Nhập ID sản phẩm: ");
                    int pid = sc.nextInt();

                    System.out.print("Số lượng: ");
                    int qty = sc.nextInt();
                    sc.nextLine();

                    cartService.addToCart(user.getUserId(), pid, qty);
                    break;

                case 3:
                    cartService.viewCart(user.getUserId());
                    break;

                case 4:
                    System.out.print("Bạn có chắc muốn đặt hàng? (y/n): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("y")) {
                        orderService.checkout(user.getUserId());
                    } else {
                        System.out.println(RED + "Đã hủy đặt hàng" + RESET);
                    }
                    break;
                case 5:
                    updateUserInfo(user);
                    break;
                case 6:
                    orderMenu(user);
                    break;
                case 7:
                    sortProduct();
                    break;
                case 0:
                    return;

                default:
                    System.out.println(RED + "Sai lựa chọn" + RESET);
            }
        }
    }

    // Menu con của người dùng
    private static void orderMenu(User user) {
        while (true) {
            System.out.println("\n===== QUẢN LÝ ĐƠN HÀNG =====");
            System.out.println("1. Xem đơn hàng");
            System.out.println("2. Cập nhật thông tin");
            System.out.println("3. Hủy đơn hàng");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    orderService.viewOrders(user.getUserId());
                    break;

                case 2:
                    updateUserInfo(user);
                    break;

                case 3:
                    cancelOrder(user);
                    break;
                case 0:
                    return;

                default:
                    System.out.println(RED + "Sai lựa chọn!" + RESET);
            }
        }
    }
    // Cập nhật thông tin ( sđt và địa chỉ )
    private static void updateUserInfo(User user) {
        String phone;
        String address;

        while (true) {
            System.out.print("Nhập số điện thoại mới: ");
            phone = sc.nextLine().trim();

            if (phone.isEmpty()) {
                System.out.println(RED + "Không được để trống!" + RESET);
                continue;
            }

            if (!phone.matches("^0\\d{9}$")) {
                System.out.println(RED + "Số điện thoại phải có 10 số và bắt đầu bằng số 0" + RESET);
                continue;
            }

            break;
        }

        while (true) {
            System.out.print("Nhập địa chỉ mới: ");
            address = sc.nextLine().trim();

            if (address.isEmpty()) {
                System.out.println(RED + "Địa chỉ không được để trống" + RESET);
                continue;
            }

            break;
        }

        authService.updateUserInfo(user.getUserId(), phone, address);
    }

    // hủy đơn hàng
    private static void cancelOrder(User user) {
        System.out.print("Nhập ID đơn hàng cần hủy: ");
        int orderId = sc.nextInt();
        sc.nextLine();

        System.out.print("Bạn có chắc muốn hủy? (y/n): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            orderService.cancelOrder(user.getUserId(), orderId);
        } else {
            System.out.println(RED + "Đã hủy thao tác!" + RESET);
        }
    }

    // sắp xếp sản phẩm
    private static void sortProduct() {
        System.out.println("1. Giá tăng dần");
        System.out.println("2. Giá giảm dần");
        System.out.print("Chọn: ");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            productService.sortProductByPrice(true);
        } else if (choice == 2) {
            productService.sortProductByPrice(false);
        } else {
            System.out.println(RED + "Lựa chọn không hợp lệ" + RESET);
        }
    }
}
