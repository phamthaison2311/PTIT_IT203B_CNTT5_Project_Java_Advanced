package view;

import dao.CartDAO;
import model.Product;
import model.User;
import service.*;
import view.MenuAdmin;
import view.MenuUser;

import java.util.Scanner;

import static view.MenuAdmin.*;

public class MainMenu {
    private static Scanner sc = new Scanner(System.in);
    private static AuthService authService = new AuthService();
    private static CategoryService categoryService = new CategoryService();
    private static ProductService productService = new ProductService();
    private static CartService cartService = new CartService();
    private static OrderService orderService = new OrderService();


    /**
     * PHẦN MENU TỔNG THỂ CHO CẢ HAI ĐỐI TƯỢNG
     */


    public static void start() {
        while (true) {
            System.out.println(CYAN + "    ╔══════════════════════════════════════════╗" + RESET);
            System.out.println(CYAN + "    ║" + YELLOW + "            ZENIZZZ PHONE STORE           " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ╠══════════════════════════════════════════╣" + RESET);
            System.out.println(CYAN + "    ║" + GREEN + "  [1]. ĐĂNG KÝ TÀI KHOẢN                  " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ║" + GREEN + "  [2]. ĐĂNG NHẬP HỆ THỐNG                 " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ║" + RED + "  [0]. THOÁT CHƯƠNG TRÌNH                 " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ╚══════════════════════════════════════════╝" + RESET);
            System.out.print(YELLOW + "    > Vui lòng chọn: " + RESET);

            int choice;

            try {
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println(RED + "CẤM PHÁ HOẠI HỆ THỐNG!!! Nhập số đi *I*" + RESET);
                sc.nextLine(); // clear input lỗi
                continue; // quay lại menu
            }

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    /**
     * ĐĂNG KÝ
     */

    private static void register() {
        String name;
        String email;
        String password;

        while (true) {
            System.out.print("Tên: ");
            name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println(RED + "Tên không được để trống!" + RESET);
            } else {
                break;
            }
        }

        while (true) {
            System.out.print("Email: ");
            email = sc.nextLine().trim();

            if (email.isEmpty()) {
                System.out.println(RED + "Email không được để trống!" + RESET);
                continue;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                System.out.println(RED + "Email không hợp lệ!" + RESET);
                continue;
            }

            // check trùng email
            try {
                if (authService.checkEmailExists(email)) {
                    System.out.println(RED + "Email đã tồn tại!" + RESET);
                    continue;
                }
            } catch (Exception e) {
                System.out.println(RED + "Lỗi kiểm tra email!" + RESET);
            }

            break;
        }

        while (true) {
            System.out.print("Mật khẩu: ");
            password = sc.nextLine().trim();

            if (password.isEmpty()) {
                System.out.println(RED + "Mật khẩu không được để trống!" + RESET);
                continue;
            }

            if (password.length() < 6) {
                System.out.println(RED + "Mật khẩu phải >=6 ký tự" + RESET);
                continue;
            }

            break;
        }

        authService.register(name, email, password);
    }

    /**
     * ĐĂNG NHẬP
     */

    private static void login() {
        while (true) {
            System.out.print("Email: ");
            String email = sc.nextLine().trim();

            System.out.print("Mật khẩu: ");
            String password = sc.nextLine().trim();

            if (email.isEmpty()) {
                System.out.println(RED + "Vui lòng nhập email để đăng nhập!" + RESET);
                continue;
            }

            if (password.isEmpty()) {
                System.out.println(RED + "Vui lòng nhập mật khẩu!" + RESET);
                continue;
            }

            User user = authService.login(email, password);

            if (user == null) {
                System.out.println(RED + "sau tài khoản hoặc mật khẩu -_* Vui lòng nhập lại....." + RESET);
            } else {
                if ("admin".equalsIgnoreCase(user.getRole())) {
                    MenuAdmin.adminMenu();
                } else {
                    MenuUser.userMenu(user);
                }
                break;
            }
        }
    }

}