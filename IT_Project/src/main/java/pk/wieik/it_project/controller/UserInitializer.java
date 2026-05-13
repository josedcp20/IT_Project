package pk.wieik.it_project.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pk.wieik.it_project.model.DGuser;

import java.util.HashMap;

@WebListener
public class UserInitializer  implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce){
        ServletContext context = sce.getServletContext();

        HashMap<String, DGuser> users = new HashMap<>();
        users.put("user1", new DGuser("user1", "user1", 1));
        users.put("user2", new DGuser ("user2", "user2", 1));
        users.put("user3", new DGuser ("user3", "user3", 2));
        users.put("admin", new DGuser ("admin", "admin", 2));
        context.setAttribute("users", users);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){

    }
}
