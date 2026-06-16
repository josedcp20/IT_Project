package pk.wieik.it_project.servlet;

import pk.wieik.it_project.dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // Cifrado BCrypt (Paso 11)
        String hashed = BCrypt.hashpw(pass, BCrypt.gensalt(12));

        // Llamada al método register (asegúrate de tenerlo en UserDAO)
        if (userDAO.register(user, hashed)) {
            response.sendRedirect("login.jsp");
        } else {
            response.getWriter().println("Error al registrar el usuario.");
        }
    }
}