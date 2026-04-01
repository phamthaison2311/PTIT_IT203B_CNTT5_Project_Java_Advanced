package view;

import model.Category;
import model.Product;
import service.*;


import java.util.Scanner;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;

public class MenuAdmin {
    private static Scanner sc = new Scanner(System.in);
    private static CategoryService categoryService = new CategoryService();
    private static ProductService productService = new ProductService();
    private static OrderService orderService = new OrderService();

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";
    public static final String GREEN = "\u001B[32m";

    /**
     * PHẦN MENU DÀNH CHO ADMIN
     */
    public static void adminMenu() {
        while (true) {
            System.out.println(RED + "    ╔══════════════════════════════════════════╗" + RESET);
            System.out.println(RED + "    ║" + YELLOW + "          HỆ THỐNG QUẢN TRỊ VIÊN          " + RED + "║" + RESET);
            System.out.println(RED + "    ╠══════════════════════════════════════════╣" + RESET);
            System.out.println(RED + "    ║" + CYAN + "   [1]. Quản lý danh mục sản phẩm         " + RED + "║" + RESET);
            System.out.println(RED + "    ║" + CYAN + "   [2]. Quản lý kho sản phẩm              " + RED + "║" + RESET);
            System.out.println(RED + "    ║" + CYAN + "   [3]. Quản lý đơn hàng khách hàng       " + RED + "║" + RESET);
            System.out.println(RED + "    ╠══════════════════════════════════════════╣" + RESET);
            System.out.println(RED + "    ║" + PURPLE + "   [0]. Đăng xuất hệ thống                " + RED + "║" + RESET);
            System.out.println(RED + "    ╚══════════════════════════════════════════╝" + RESET);
            System.out.print(YELLOW + "    > Admin chọn chức năng: " + RESET);
            int choice;

            try {
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println(RED + "CẤM PHÁ HOẠI HỆ THỐNG!!! Nhập số đi" + RESET);
                sc.nextLine(); // clear input lỗi
                continue; // quay lại menu
            }

            switch (choice) {
                case 1:
                    categoryMenu();
                    break;
                case 2:
                    productMenu();
                    break;
                case 3:
                    orderAdminMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Sai lựa chọn!" + RESET);
            }
        }
    }

    // Menu quản lý danh mục
    private static void categoryMenu() {
        while (true) {
            System.out.println(GREEN + "    ┌──────────────────────────────────────────┐" + RESET);
            System.out.println(GREEN + "    │" + YELLOW + "            QUẢN LÝ DANH MỤC              " + GREEN + "│" + RESET);
            System.out.println(GREEN + "    ├──────────────────────────────────────────┤" + RESET);
            System.out.println(GREEN + "    │" + CYAN + "  [1]. Thêm danh mục mới                  " + GREEN + "│" + RESET);
            System.out.println(GREEN + "    │" + CYAN + "  [2]. Hiển thị danh sách danh mục        " + GREEN + "│" + RESET);
            System.out.println(GREEN + "    │" + CYAN + "  [3]. Xóa danh mục sản phẩm              " + GREEN + "│" + RESET);
            System.out.println(GREEN + "    │" + CYAN + "  [4]. Cập nhật thông tin danh mục        " + GREEN + "│" + RESET);
            System.out.println(GREEN + "    ├──────────────────────────────────────────┤" + RESET);
            System.out.println(GREEN + "    │" + PURPLE + "  [0]. Quay lại Menu Admin                " + GREEN + "│" + RESET);
            System.out.println(GREEN + "    └──────────────────────────────────────────┘" + RESET);
            System.out.print(YELLOW + "    > Chọn chức năng: " + RESET);

            int choice;

            try {
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println(RED + "CẤM PHÁ HOẠI HỆ THỐNG!!! Nhập số đi" + RESET);
                sc.nextLine(); // clear input lỗi
                continue; // quay lại menu
            }

            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    categoryService.displayAll();
                    break;
                case 3:
                    deleteCategory();
                    break;
                case 4:
                    updateCategory();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "LỖI: Lựa chọn sai nhận lại!" + RESET);
            }
        }
    }
    // Thêm danh mục
    private static void addCategory() {
        String name;

        while (true) {
            System.out.print("Nhập tên danh mục: ");
            name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println(RED + "LỖI: Không được để trống thông tin này!" + RESET);
                continue;
            }

            if (categoryService.checkCategoryNameExists(name)) {
                System.out.println(RED + "LỖI: Danh mục đã tồn tại!" + RESET);
                continue;
            }

            break;
        }

        Category c = new Category();
        c.setName(name);

        categoryService.addCategory(String.valueOf(c));
    }

    // Xóa danh mục
    private static void deleteCategory() {
        System.out.print("Nhập ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        categoryService.delete(id);
    }
    // Cập nhật danh mục
    private static void updateCategory() {
        System.out.print("ID danh mục cần sửa: ");
        int id = sc.nextInt();
        sc.nextLine();

        String name;
        while (true) {
            System.out.print("Nhập tên danh mục: ");
            name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println(RED + "LỖI: Không được để trống thông tin này!" + RESET);
                continue;
            }

            if (categoryService.checkCategoryNameExists(name)) {
                System.out.println(RED + "LỖI: Danh mục đã tồn tại!" + RESET);
                continue;
            }

            break;
        }

        categoryService.update(id, name);
    }

    // Menu quản lý sản phẩm
    private static void productMenu() {
        while (true) {
            System.out.println(BLUE + "    ╭──────────────────────────────────────────╮" + RESET);
            System.out.println(BLUE + "    │" + YELLOW + "         QUẢN LÝ KHO SẢN PHẨM          " + BLUE + "│" + RESET);
            System.out.println(BLUE + "    ├──────────────────────────────────────────┤" + RESET);
            System.out.println(BLUE + "    │" + GREEN + "  [1]. Thêm mới sản phẩm               " + BLUE + "│" + RESET);
            System.out.println(BLUE + "    │" + GREEN + "  [2]. Chỉnh sửa thông tin             " + BLUE + "│" + RESET);
            System.out.println(BLUE + "    │" + RED + "  [3]. 🗑Xóa sản phẩm khỏi kho          " + BLUE + "│" + RESET);
            System.out.println(BLUE + "    ├──────────────────────────────────────────┤" + RESET);
            System.out.println(BLUE + "    │" + CYAN + "  [4]. Hiển thị tất cả sản phẩm        " + BLUE + "│" + RESET);
            System.out.println(BLUE + "    │" + CYAN + "  [5]. Tìm kiếm sản phẩm               " + BLUE + "│" + RESET);
            System.out.println(BLUE + "    │" + CYAN + "  [6]. Sắp xếp sản phẩm theo giá       " + BLUE + "│" + RESET);
            System.out.println(BLUE + "    ├──────────────────────────────────────────┤" + RESET);
            System.out.println(BLUE + "    │" + PURPLE + "  [0]. Quay lại Admin Menu             " + BLUE + "│" + RESET);
            System.out.println(BLUE + "    ╰──────────────────────────────────────────╯" + RESET);
            System.out.print(YELLOW + "    Nhập lệnh điều khiển (0-6): " + RESET);

            int choice;

            try {
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println(RED + "CẤM PHÁ HOẠI HỆ THỐNG!!! Nhập số đi" + RESET);
                sc.nextLine(); // clear input lỗi
                continue; // quay lại menu
            }

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    productService.displayAll();
                    break;
                case 5:
                    searchProduct();
                    break;
                case 6 :
                    sortProduct();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ" + RESET);
            }
        }
    }

    // Thêm sản phẩm
    private static void addProduct() {
        Product p = new Product();

        // ===== TÊN =====
        while (true) {
            System.out.print("Tên: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println(RED + "Tên không được để trống!" + RESET);
            } else {
                p.setProductName(name);
                break;
            }
        }

        // ===== HÃNG =====
        while (true) {
            System.out.print("Hãng: ");
            String brand = sc.nextLine().trim();

            if (brand.isEmpty()) {
                System.out.println(RED + "Hãng không được để trống!" + RESET);
            } else {
                p.setBrand(brand);
                break;
            }
        }

        // ===== DUNG LƯỢNG =====
        while (true) {
            System.out.print("Dung lượng: ");
            String capacity = sc.nextLine().trim();

            if (capacity.isEmpty()) {
                System.out.println(RED + "Dung lượng không được để trống!" + RESET);
            } else {
                p.setCapacity(capacity);
                break;
            }
        }

        // ===== MÀU =====
        while (true) {
            System.out.print("Màu: ");
            String color = sc.nextLine().trim();

            if (color.isEmpty()) {
                System.out.println(RED + "Màu không được để trống!" + RESET);
            } else {
                p.setColor(color);
                break;
            }
        }

        // ===== GIÁ =====
        while (true) {
            try {
                System.out.print("Giá: ");
                double price = Double.parseDouble(sc.nextLine());

                if (price <= 0) {
                    System.out.println(RED + "Giá phải > 0!" + RESET);
                } else {
                    p.setPrice(price);
                    break;
                }
            } catch (Exception e) {
                System.out.println(RED + "Giá phải là số!" + RESET);
            }
        }

        // ===== STOCK =====
        while (true) {
            try {
                System.out.print("Stock: ");
                int stock = Integer.parseInt(sc.nextLine());

                if (stock < 0) {
                    System.out.println(RED + "Stock không được âm!" + RESET);
                } else {
                    p.setStock(stock);
                    break;
                }
            } catch (Exception e) {
                System.out.println(RED + "Stock phải là số nguyên!" + RESET);
            }
        }

        // ===== MÔ TẢ =====
        while (true) {
            System.out.print("Mô tả: ");
            String desc = sc.nextLine().trim();

            if (desc.isEmpty()) {
                System.out.println(RED + "Mô tả không được để trống!" + RESET);
            } else {
                p.setDescription(desc);
                break;
            }
        }

        // ===== CATEGORY =====
        int categoryId;
        while (true) {
            try {
                System.out.print("Nhập ID danh mục: ");
                categoryId = Integer.parseInt(sc.nextLine());

                if (!categoryService.checkCategoryExists(categoryId)) {
                    System.out.println(RED + "Danh mục không tồn tại!" + RESET);
                } else {
                    p.setIdCategory(categoryId);
                    break;
                }
            } catch (Exception e) {
                System.out.println(RED + "ID phải là số!" + RESET);
            }
        }

        productService.addProduct(p);
    }

    // Cập nhật sản phẩm
    private static void updateProduct() {
        System.out.print("Nhập ID sản phẩm: ");
        int id = sc.nextInt();
        sc.nextLine();

        Product old = productService.getProductById(id);

        if (old == null) {
            System.out.println(RED + "Sản phẩm không tồn tại *_*" + RESET);
            return;
        }

        System.out.println(BLUE + "    ┌──────────────────────────────────────────┐" + RESET);
        System.out.println(BLUE + "    │" + YELLOW + "            THÔNG TIN HIỆN TẠI            " + BLUE + "│" + RESET);
        System.out.println(BLUE + "    ├──────────────────────────────────────────┤" + RESET);
        System.out.printf(BLUE + "    │ " + WHITE + "Tên: " + RESET + " %-31s " + BLUE + "│\n", old.getProductName());
        System.out.printf(BLUE + "    │ " + WHITE + "Hãng: " + RESET + " %-30s " + BLUE + "│\n", old.getBrand());
        System.out.printf(BLUE + "    │ " + WHITE + "Dung lượng: " + RESET + " %-24s " + BLUE + "│\n", old.getCapacity());
        System.out.printf(BLUE + "    │ " + WHITE + "Màu sắc: " + RESET + " %-27s " + BLUE + "│\n", old.getColor());
        System.out.printf(BLUE + "    │ " + WHITE + "Giá: " + YELLOW + " %,15.0f VNĐ" + RESET + "          " + BLUE + "│\n", old.getPrice());
        System.out.printf(BLUE + "    │ " + WHITE + "Tồn kho: " + RESET + " %-27s " + BLUE + "│\n", old.getStock() + " sản phẩm");
        System.out.println(BLUE + "    ├──────────────────────────────────────────┤" + RESET);
        System.out.printf(BLUE + "    │ " + WHITE + "Mô tả: " + RESET + " %-30s " + BLUE + "│\n",
                (old.getDescription().length() > 30 ? old.getDescription().substring(0, 27) + "..." : old.getDescription()));
        System.out.println(BLUE + "    └──────────────────────────────────────────┘" + RESET);

        Product p = new Product();
        p.setIdProduct(id);

        System.out.print("Tên mới (Enter = giữ nguyên): ");
        String name = sc.nextLine().trim();
        p.setProductName(name.isEmpty() ? old.getProductName() : name);

        System.out.print("Hãng mới: ");
        String brand = sc.nextLine().trim();
        p.setBrand(brand.isEmpty() ? old.getBrand() : brand);

        System.out.print("Dung lượng mới: ");
        String capacity = sc.nextLine().trim();
        p.setCapacity(capacity.isEmpty() ? old.getCapacity() : capacity);

        System.out.print("Màu mới: ");
        String color = sc.nextLine().trim();
        p.setColor(color.isEmpty() ? old.getColor() : color);

        while (true) {
            System.out.print("Giá mới: ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                p.setPrice(old.getPrice());
                break;
            }

            try {
                double price = Double.parseDouble(input);
                if (price <= 0) {
                    System.out.println(RED + "Giá phải > 0!" + RESET);
                } else {
                    p.setPrice(price);
                    break;
                }
            } catch (Exception e) {
                System.out.println(RED + "Giá phải là số!" + RESET);
            }
        }

        while (true) {
            System.out.print("Stock mới: ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                p.setStock(old.getStock());
                break;
            }

            try {
                int stock = Integer.parseInt(input);
                if (stock < 0) {
                    System.out.println(RED + "Stock không được âm!" + RESET);
                } else {
                    p.setStock(stock);
                    break;
                }
            } catch (Exception e) {
                System.out.println(RED + "Stock phải là số!" + RESET);
            }
        }

        System.out.print("Mô tả mới: ");
        String desc = sc.nextLine().trim();
        p.setDescription(desc.isEmpty() ? old.getDescription() : desc);

        while (true) {
            System.out.print("ID danh mục mới: ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                p.setIdCategory(old.getIdCategory());
                break;
            }

            try {
                int categoryId = Integer.parseInt(input);

                if (!categoryService.checkCategoryExists(categoryId)) {
                    System.out.println(RED + "Danh mục không tồn tại!" + RESET);
                } else {
                    p.setIdCategory(categoryId);
                    break;
                }
            } catch (Exception e) {
                System.out.println(RED + "ID phải là số!" + RESET);
            }
        }

        productService.updateProduct(p);
        System.out.println(GREEN + "Cập nhật thành công!" + RESET);
    }

    // Xóa sản phẩm
    private static void deleteProduct() {
        System.out.print("Nhập ID cần xóa: ");
        int id = sc.nextInt();
        sc.nextLine();

        Product p = productService.getProductById(id);

        if (p == null) {
            System.out.println(RED + "Sản phẩm này không tổn tại *_-" + RESET);
            return;
        }

        System.out.println(BLUE + "    ╭──────────────────────────────────────────╮" + RESET);
        System.out.println(BLUE + "    │" + YELLOW + "           CHI TIẾT SẢN PHẨM            " + BLUE + "│" + RESET);
        System.out.println(BLUE + "    ├──────────────────────────────────────────┤" + RESET);
        System.out.printf(BLUE + "    │ " + CYAN + "Tên: " + RESET + " %-31s " + BLUE + "│\n", p.getProductName());
        System.out.printf(BLUE + "    │ " + CYAN + "Hãng: " + RESET + " %-30s " + BLUE + "│\n", p.getBrand());
        System.out.printf(BLUE + "    │ " + CYAN + "RAM/ROM: " + RESET + " %-27s " + BLUE + "│\n", p.getCapacity());
        System.out.printf(BLUE + "    │ " + CYAN + "Màu sắc: " + RESET + " %-28s " + BLUE + "│\n", p.getColor());
        System.out.println(BLUE + "    ├──────────────────────────────────────────┤" + RESET);
        System.out.printf(BLUE + "    │ " + GREEN + "Giá bán: " + YELLOW + " %,15.0f VNĐ" + RESET + "          " + BLUE + "│\n", p.getPrice());
        System.out.printf(BLUE + "    │ " + GREEN + "Kho còn: " + RESET + " %-3d cái" + RESET + "                  " + BLUE + "│\n", p.getStock());
        System.out.println(BLUE + "    ├──────────────────────────────────────────┤" + RESET);
        System.out.printf(BLUE + "    │ " + CYAN + "Mô tả: " + RESET + " %-30s " + BLUE + "│\n",
                (p.getDescription().length() > 30 ? p.getDescription().substring(0, 27) + "..." : p.getDescription()));
        System.out.println(BLUE + "    ╰──────────────────────────────────────────╯" + RESET);

        System.out.print("Bạn có chắc muốn xóa? (Y/N): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            productService.deleteProduct(id);
        } else {
            System.out.println(RED + ":/ Đã hủy thao tác" + RESET);
        }
    }

    // Tìm kiếm sản phẩm
    private static void searchProduct() {
        System.out.print("Nhập tên cần tìm: ");
        String keyword = sc.nextLine();

        productService.searchProduct(keyword);
    }

    // sắp xếp
    private static void sortProduct() {
        System.out.println("1. Giá tăng dần");
        System.out.println("2. Giá giảm dần");
        System.out.print("Chọn: ");

        int choice;

        try {
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer
        } catch (Exception e) {
            System.out.println(RED + "CẤM PHÁ HOẠI HỆ THỐNG!!! Nhập số đi" + RESET);
            sc.nextLine(); // clear input lỗi
            return;
        }

        if (choice == 1) {
            productService.sortProductByPrice(true);
        } else if (choice == 2) {
            productService.sortProductByPrice(false);
        } else {
            System.out.println(RED + "Lựa chọn không hợp lệ !!!!" + RESET);
        }
    }

    // quản lý các đơn hàng của hệ thống
    private static void orderAdminMenu() {
        while (true) {
            System.out.println(PURPLE + "    ╔══════════════════════════════════════════╗" + RESET);
            System.out.println(PURPLE + "    ║" + YELLOW + "          QUẢN LÝ ĐƠN HÀNG HỆ THỐNG        " + PURPLE + "║" + RESET);
            System.out.println(PURPLE + "    ╠══════════════════════════════════════════╣" + RESET);
            System.out.println(PURPLE + "    ║" + GREEN + "  [1]. Xem danh sách tất cả đơn hàng      " + PURPLE + "║" + RESET);
            System.out.println(PURPLE + "    ║" + GREEN + "  [2]. Tra cứu chi tiết mã đơn hàng       " + PURPLE + "║" + RESET);
            System.out.println(PURPLE + "    ║" + GREEN + "  [3]. Cập nhật trang thái đơn hàng       " + PURPLE + "║" + RESET);
            System.out.println(PURPLE + "    ╠══════════════════════════════════════════╣" + RESET);
            System.out.println(PURPLE + "    ║" + CYAN + "  [0]. Quay lại trang Quản trị            " + PURPLE + "║" + RESET);
            System.out.println(PURPLE + "    ╚══════════════════════════════════════════╝" + RESET);
            System.out.print(YELLOW + "    Nhập lựa chọn của bạn: " + RESET);

            int choice;

            try {
                choice = sc.nextInt();
                sc.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println(RED + "CẤM PHÁ HOẠI HỆ THỐNG!!! Nhập số đi" + RESET);
                sc.nextLine(); // clear input lỗi
                continue; // quay lại menu
            }

            switch (choice) {
                case 1:
                    orderService.viewAllOrders();
                    break;
                case 2:
                    viewOrderDetail();
                    break;
                case 3:
                    updateOrderStatus();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void viewOrderDetail() {
        System.out.print("Nhập ID đơn hàng: ");
        int orderId = sc.nextInt();
        sc.nextLine();

        orderService.viewOrderDetail(orderId);
    }

    private static void updateOrderStatus() {
        try {
            System.out.print("Nhập ID đơn hàng: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.println("1. pending");
            System.out.println("2. paid");
            System.out.println("3. shipped");
            System.out.println("4. cancelled");

            System.out.print("Chọn trạng thái: ");
            int choice = Integer.parseInt(sc.nextLine());

            String status = "";

            switch (choice) {
                case 1: status = "pending"; break;
                case 2: status = "paid"; break;
                case 3: status = "shipped"; break;
                case 4: status = "cancelled"; break;
                default:
                    System.out.println("Sai lựa chọn!");
                    return;
            }

            System.out.print("Xác nhận cập nhật? (y/n): ");
            String confirm = sc.nextLine();

            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("Đã hủy!");
                return;
            }

            orderService.updateOrderStatus(id, status);

        } catch (Exception e) {
            System.out.println("Dữ liệu không hợp lệ!");
        }
    }

}
