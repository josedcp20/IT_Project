package pk.wieik.it_project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pk.wieik.it_project.model.DGuser;
import pk.wieik.it_project.model.Tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

@WebServlet(name = "DG", value = "/DG")
public class DG extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                response.sendRedirect(request.getContextPath() + "/index.jsp?page=main");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        DGuser user = (DGuser) session.getAttribute("user");
        if (user == null) {
            user = new DGuser();
            session.setAttribute("user", user);
        }

        String redirect = request.getContextPath() + "/index.jsp?page=main";

        if ("login".equals(action)) {
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            HashMap<String, DGuser> users = (HashMap<String, DGuser>) getServletContext().getAttribute("users");
            DGuser found = users.get(login);

            if (found != null && found.getPassword().equals(password)) {
                user.setLogin(found.getLogin());
                user.setPrivileges(found.getPrivileges());
                user.setAge(new Random().nextInt(66) + 15);
            }
        } else if ("logout".equals(action)) {
            session.invalidate();
        } else if ("savesettings".equals(action)) {
            if (user.getPrivileges() > 0) {
                user.setName(request.getParameter("name"));
                user.setSurname(request.getParameter("surname"));
                user.setAge(Tools.parseInteger(request.getParameter("age"), 0));
                redirect = request.getContextPath() + "/index.jsp?page=settings&saved=1";
            } else {
                redirect = request.getContextPath() + "/index.jsp?page=main";
            }
        }else if ("administration".equals(action)){
            if (user.getPrivileges() == 2){
                HashMap<String, DGuser> users = (HashMap<String, DGuser>) getServletContext().getAttribute("users");
                String backgroundColor = request.getParameter("backgroundColor");
                if(backgroundColor == null) backgroundColor = "";
                getServletContext().setAttribute("backgroundColor", backgroundColor);

                for(String key : users.keySet()){
                    String privParam = request.getParameter("priv_"+key);
                    if(privParam != null){
                        users.get(key).setPrivileges(Integer.parseInt(privParam));
                    }
                }
                redirect = request.getContextPath() + "/index.jsp?page=administration";
            }
        }


        response.sendRedirect(redirect);
    }
}
