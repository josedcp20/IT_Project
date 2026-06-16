package pk.wieik.it_project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pk.wieik.it_project.dao.UserDAO;
import pk.wieik.it_project.dto.UserDTO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DG", value = "/DG")
public class DG extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/index.jsp?page=main");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");

        String redirect = request.getContextPath() + "/index.jsp?page=main";

        if ("logout".equals(action)) {
            session.invalidate();
        } else if ("administration".equals(action)) {
            if (user != null && user.getPrivileges() == 2) {
                List<UserDTO> all = userDAO.getAllUsers();
                for (UserDTO u : all) {
                    String privParam = request.getParameter("priv_" + u.getId());
                    if (privParam != null) {
                        try {
                            int newPriv = Integer.parseInt(privParam);
                            if (newPriv != u.getPrivileges()) {
                                userDAO.updatePrivileges(u.getId(), newPriv);
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                }
                redirect = request.getContextPath() + "/index.jsp?page=administration";
            }
        } else if ("deleteUser".equals(action)) {
            if (user != null && user.getPrivileges() == 2) {
                try {
                    int targetId = Integer.parseInt(request.getParameter("userId"));
                    if (targetId != user.getId()) {
                        userDAO.deleteUser(targetId);
                    }
                } catch (NumberFormatException ignored) {}
                redirect = request.getContextPath() + "/index.jsp?page=administration";
            }
        }

        response.sendRedirect(redirect);
    }
}
