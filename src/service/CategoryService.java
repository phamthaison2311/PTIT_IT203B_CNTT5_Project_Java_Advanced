package service;

import dao.CategoryDAO;
import model.Category;

import java.util.List;

public class CategoryService {

    private CategoryDAO categoryDAO = new CategoryDAO();

    // THÊM DANH MỤC
    public void addCategory(String name) {
        try {
            categoryDAO.insert(name);
            System.out.println("Thêm danh mục thành công!");
        } catch (Exception e) {
            System.out.println("Thêm danh mục thất bại!");
            e.printStackTrace();
        }
    }

    // kiểm tra trùng tên
    public boolean checkCategoryNameExists(String name) {
        try {
            return categoryDAO.isCategoryNameExists(name);
        } catch (Exception e) {
            System.out.println("Lỗi kiểm tra danh mục!");
            return false;
        }
    }

    // HIỂN THỊ DANH MỤC
    public void displayAll() {
        try {
            List<Category> list = categoryDAO.getAll();

            if (list.isEmpty()) {
                System.out.println("Không có danh mục!");
                return;
            }

            System.out.println("===== DANH SÁCH DANH MỤC =====");
            for (Category c : list) {
                System.out.println(c.getId() + " - " + c.getName());
            }

        } catch (Exception e) {
            System.out.println("Lỗi hiển thị danh mục!");
            e.printStackTrace();
        }
    }


    // XÓA DANH MỤC
    public void delete(int id) {
        try {
            categoryDAO.delete(id);
            System.out.println("Xóa danh mục thành công!");
        } catch (Exception e) {
            System.out.println("Không thể xóa! Có thể danh mục đang được sử dụng.");
        }
    }

    // CẬP NHẬT DANH MỤC
    public void update(int id, String name) {
        try {
            categoryDAO.update(id, name);
            System.out.println("Cập nhật danh mục thành công!");
        } catch (Exception e) {
            System.out.println("Cập nhật thất bại!");
            e.printStackTrace();
        }
    }

    public boolean checkCategoryExists(int id) {
        try {
            return categoryDAO.isCategoryExists(id);
        } catch (Exception e) {
            System.out.println("Lỗi kiểm tra danh mục!");
            return false;
        }
    }
}
