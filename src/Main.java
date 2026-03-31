import dao.UserDAO;
import view.MainMenu;

public class Main {
    public static void main(String[] args) throws Exception {
        UserDAO userDAO = new UserDAO();
        // tạo admin mặc định
        userDAO.createDefaultAdmin();
        // chạy menu
        MainMenu.start();
    }
}