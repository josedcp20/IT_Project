package pk.wieik.it_project.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import pk.wieik.it_project.dao.UserDAO;
import pk.wieik.it_project.dto.UserDTO;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String storedHash = userDAO.getHashedPassword(username);

        if (storedHash != null && BCrypt.checkpw(password, storedHash)) {
            UserDTO user = userDAO.findByUsername(username);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(15 * 60);

            response.sendRedirect(request.getContextPath() + "/index.jsp?page=main");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
