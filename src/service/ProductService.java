package service;

import dao.ProductDAO;
import model.Product;

import java.util.List;

import static view.MenuAdmin.*;

public class ProductService {

    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String RED = "\u001B[31m";
    public static final String PURPLE = "\u001B[35m";
    public static final String WHITE = "\u001B[37m";

    private ProductDAO productDAO = new ProductDAO();

    public void addProduct(Product p) {
        try {
            productDAO.insert(p);
            System.out.println(GREEN + "Thêm sản phẩm thành công *-*" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Thêm sản phẩm thất bại!!!! *_-" + RESET);
            e.printStackTrace();
        }
    }

    public void displayAll() {
        try {
            List<Product> list = productDAO.getAll();

            if (list.isEmpty()) {
                System.out.println(RED + "Không có sản phẩm *_-" + RESET);
                return;
            }

            System.out.println(CYAN + "\n    ╔══════════════════════════════════════════════════════════════════════════════════════════╗" + RESET);
            System.out.println(CYAN + "    ║" + YELLOW + "                                   DANH SÁCH SẢN PHẨM                                 " + CYAN + "║" + RESET);
            System.out.println(CYAN + "    ╠══════╦══════════════════════╦══════════════╦════════════╦════════════╦══════════════╦════════╣" + RESET);
            System.out.printf(CYAN + "    ║ %-4s ║ %-20s ║ %-12s ║ %-10s ║ %-10s ║ %-12s ║ %-6s ║\n",
                    "ID", "Tên Sản Phẩm", "Hãng", "Dung Lượng", "Màu Sắc", "Giá Bán", "Kho");
            System.out.println(CYAN + "    ╠══════╬══════════════════════╬══════════════╬════════════╬════════════╬══════════════╬════════╣" + RESET);

            for (Product p : list) {
                if (p.getStock() > 0) {
                    System.out.printf(CYAN + "    ║" + WHITE + " %-4d " + CYAN + "║" + WHITE + " %-20s " + CYAN + "║" + WHITE + " %-12s " + CYAN + "║" + WHITE + " %-10s " + CYAN + "║" + WHITE + " %-10s " + CYAN + "║" + YELLOW + " %,12.0f " + CYAN + "║" + WHITE + " %-6d " + CYAN + "║\n",
                            p.getIdProduct(),
                            (p.getProductName().length() > 20 ? p.getProductName().substring(0, 17) + "..." : p.getProductName()),
                            p.getBrand(),
                            p.getCapacity(),
                            p.getColor(),
                            p.getPrice(),
                            p.getStock());
                }
            }

            System.out.println(CYAN + "    ╚══════╩══════════════════════╩══════════════╩════════════╩════════════╩══════════════╩════════╝" + RESET);
        } catch (Exception e) {
            System.out.println("Lỗi hiển thị!");
            e.printStackTrace();
        }
    }


    public void updateProduct(Product p) {
        try {
            productDAO.update(p);
            System.out.println(GREEN + "Cập nhật thành công *-*" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Cập nhật thất bại *_-" + RESET);
            e.printStackTrace();
        }
    }


    public void deleteProduct(int id) {
        try {
            productDAO.delete(id);
            System.out.println(GREEN + "Xóa sản phẩm thành công *-*" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Không thể xóa! Có thể sản phẩm đang được sử dụng *_-" + RESET);
            e.printStackTrace();
        }
    }


    public void searchProduct(String keyword) {
        try {
            List<Product> list = productDAO.searchByName(keyword);

            if (list.isEmpty()) {
                System.out.println(RED + "Không tìm thấy sản phẩm *_-" + RESET);
                return;
            }

            for (Product p : list) {
                System.out.println(p.getProductName() + " - " + p.getPrice());
            }

        } catch (Exception e) {
            System.out.println(RED + "Lỗi tìm kiếm ....." + RESET);
            e.printStackTrace();
        }
    }

    public Product getProductById(int id) {
        try {
            return productDAO.getById(id);
        } catch (Exception e) {
            System.out.println(RED + "Lỗi lấy sản phẩm" + RESET);
            return null;
        }
    }

    // sắp xếp sản phẩm theo giá
    public void sortProductByPrice(boolean asc) {
        try {
            List<Product> list = productDAO.sortByPrice(asc);

            if (list.isEmpty()) {
                System.out.println(RED + "Không có sản phẩm *_-" + RESET);
                return;
            }

            System.out.println(PURPLE + "\n    ┌────────────────────────────────────────────────────────────┐" + RESET);
            System.out.println(PURPLE + "    │" + YELLOW + "                KẾT QUẢ SẮP XẾP SẢN PHẨM               " + PURPLE + "│" + RESET);
            System.out.println(PURPLE + "    ├────────┬────────────────────────┬──────────────┬──────────┤" + RESET);
            System.out.printf(PURPLE + "    │ %-6s │ %-22s │ %-12s │ %-8s │\n",
                    "ID", "Tên Sản Phẩm", "Giá Bán", "Kho");
            System.out.println(PURPLE + "    ├────────┼────────────────────────┼──────────────┼──────────┤" + RESET);

            for (Product p : list) {
                String displayName = p.getProductName();
                if (displayName.length() > 22) {
                    displayName = displayName.substring(0, 19) + "...";
                }

                System.out.printf(PURPLE + "    │" + WHITE + " %-6d " + PURPLE + "│" + WHITE + " %-22s " + PURPLE + "│" + YELLOW + " %,12.0f " + PURPLE + "│" + WHITE + " %-8d " + PURPLE + "│\n",
                        p.getIdProduct(),
                        displayName,
                        p.getPrice(),
                        p.getStock());
            }

            System.out.println(PURPLE + "    └────────┴────────────────────────┴──────────────┴──────────┘" + RESET);

        } catch (Exception e) {
            System.out.println(RED + "Lỗi lỗi rồi ựa *_-" + RESET);
        }
    }
}