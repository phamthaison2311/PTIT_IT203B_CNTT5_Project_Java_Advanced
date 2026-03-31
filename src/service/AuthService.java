package service;

import dao.UserDAO;
import model.User;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    public boolean checkEmailExists(String email) throws Exception {
        return userDAO.findByEmail(email) != null;
    }

    public void register(String userName, String email, String password) {
        try {
            User user = new User();
            user.setUserName(userName);
            user.setEmail(email);
            user.setPassword(password);

            userDAO.register(user);

            System.out.println("Đăng ký thành công!");
        } catch (Exception e) {
            System.out.println("Đăng ký thất bại!");
            e.printStackTrace();
        }
    }

    public User login(String email, String password) {
        try {
            return userDAO.login(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateUserInfo(int userId, String phone, String address) {
        try {
            if (phone == null || phone.trim().isEmpty()) {
                System.out.println("Số điện thoại không được để trống!");
                return;
            }

            if (!phone.matches("^0\\d{9}$")) {
                System.out.println("Số điện thoại không hợp lệ!");
                return;
            }

            if (address == null || address.trim().isEmpty()) {
                System.out.println("Địa chỉ không được để trống!");
                return;
            }

            userDAO.updateInfo(userId, phone, address);
            System.out.println("Cập nhật thông tin thành công!");

        } catch (Exception e) {
            System.out.println("Lỗi cập nhật thông tin!");
            e.printStackTrace();
        }
    }
}