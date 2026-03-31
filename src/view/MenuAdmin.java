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
            int choice = sc.nextInt();
            sc.nextLine();

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

            int choice = sc.nextInt();
            sc.nextLine();

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
                    System.out.println("Sai lựa chọn!");
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
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Tên mới: ");
        String name = sc.nextLine();

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

            int choice = sc.nextInt();
            sc.nextLine();

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

        System.out.print("Tên: ");
        p.setProductName(sc.nextLine());

        System.out.print("Hãng: ");
        p.setBrand(sc.nextLine());

        System.out.print("Dung lượng: ");
        p.setCapacity(sc.nextLine());

        System.out.print("Màu: ");
        p.setColor(sc.nextLine());

        System.out.print("Giá: ");
        p.setPrice(sc.nextDouble());

        System.out.print("Stock: ");
        p.setStock(sc.nextInt());
        sc.nextLine();

        System.out.print("Mô tả: ");
        p.setDescription(sc.nextLine());

        int categoryId;
        while (true) {
            System.out.print("Nhập ID danh mục: ");
            categoryId = sc.nextInt();
            sc.nextLine();

            if (!categoryService.checkCategoryExists(categoryId)) {
                System.out.println(RED + "LỖI: Danh mục không tồn tại!" + RESET);
            } else {
                break;
            }
        }

        p.setIdCategory(categoryId);

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

        System.out.print("Tên mới: ");
        p.setProductName(sc.nextLine());

        System.out.print("Hãng mới: ");
        p.setBrand(sc.nextLine());

        System.out.print("Dung lượng mới: ");
        p.setCapacity(sc.nextLine());

        System.out.print("Màu mới: ");
        p.setColor(sc.nextLine());

        System.out.print("Giá mới: ");
        p.setPrice(sc.nextDouble());

        System.out.print("Stock mới: ");
        p.setStock(sc.nextInt());
        sc.nextLine();

        System.out.print("Mô tả mới: ");
        p.setDescription(sc.nextLine());

        int categoryId;
        while (true) {
            System.out.print("Nhập ID danh mục: ");
            categoryId = sc.nextInt();
            sc.nextLine();

            if (!categoryService.checkCategoryExists(categoryId)) {
                System.out.println(RED + "Danh mục không tồn tại *_-" + RESET);
            } else {
                break;
            }
        }

        p.setIdCategory(categoryId);

        productService.updateProduct(p);
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

        int choice = sc.nextInt();
        sc.nextLine();

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
            System.out.println(PURPLE + "    ╠══════════════════════════════════════════╣" + RESET);
            System.out.println(PURPLE + "    ║" + CYAN + "  [0]. Quay lại trang Quản trị            " + PURPLE + "║" + RESET);
            System.out.println(PURPLE + "    ╚══════════════════════════════════════════╝" + RESET);
            System.out.print(YELLOW + "    Nhập lựa chọn của bạn: " + RESET);

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    orderService.viewAllOrders();
                    break;
                case 2:
                    viewOrderDetail();
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

}
