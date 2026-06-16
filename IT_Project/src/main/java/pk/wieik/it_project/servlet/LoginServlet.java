package pk.wieik.it_project.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt; // Necesario para la seguridad
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

        // 1. Obtener el hash desde la BD (necesitas este método en UserDAO)
        String storedHash = userDAO.getHashedPassword(username);

        // 2. Validar con BCrypt
        if (storedHash != null && BCrypt.checkpw(password, storedHash)) {
            UserDTO user = userDAO.findByUsername(username);

            // 3. Crear sesión y configurar seguridad (Paso 11)
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Expiración por inactividad de 15 minutos
            session.setMaxInactiveInterval(15 * 60);

            response.sendRedirect(request.getContextPath() + "/index.jsp?page=main");
        } else {
            // Login fallido
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}