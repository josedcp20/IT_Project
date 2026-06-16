package pk.wieik.it_project.servlet;

import pk.wieik.it_project.dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/register.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirm  = request.getParameter("confirm");

        if (username == null || username.isBlank()
                || password == null || password.isBlank()
                || !password.equals(confirm)) {
            response.sendRedirect(request.getContextPath() + "/register.jsp?error=invalid");
            return;
        }

        if (userDAO.findByUsername(username) != null) {
            response.sendRedirect(request.getContextPath() + "/register.jsp?error=exists");
            return;
        }

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));

        if (userDAO.register(username, hashed)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?registered=1");
        } else {
            response.sendRedirect(request.getContextPath() + "/register.jsp?error=db");
        }
    }
}