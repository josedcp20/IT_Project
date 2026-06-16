package pk.wieik.it_project.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.mindrot.jbcrypt.BCrypt;
import pk.wieik.it_project.dao.*;
import pk.wieik.it_project.dto.*;
import pk.wieik.it_project.database.DatabaseManager;

@WebListener
public class UserInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName(DatabaseManager.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        UserDAO userDAO = new UserDAO();

        if (userDAO.countUsers() == 0) {
            userDAO.addUser(new UserDTO("user1", BCrypt.hashpw("user1", BCrypt.gensalt(12)), 1));
            userDAO.addUser(new UserDTO("user2", BCrypt.hashpw("user2", BCrypt.gensalt(12)), 1));
            userDAO.addUser(new UserDTO("admin", BCrypt.hashpw("admin", BCrypt.gensalt(12)), 2));
            System.out.println("[UserInitializer] Initial users inserted correctly.");
        }

        ComicDAO comicDAO = new ComicDAO();
        if (comicDAO.getAll("id").isEmpty()) {
            comicDAO.addComic(new ComicDTO(
                    "Watchmen", "Watchmen", "Dave Gibbons", "Alan Moore",
                    "DC Comics", "1986-09-01",
                    "A deconstruction of the superhero genre."));
            comicDAO.addComic(new ComicDTO(
                    "Maus", "Maus", "Art Spiegelman", "Art Spiegelman",
                    "Pantheon Books", "1986-08-12",
                    "A graphic novel about the Holocaust."));
            comicDAO.addComic(new ComicDTO(
                    "The Dark Knight Returns", "Batman", "Frank Miller", "Frank Miller",
                    "DC Comics", "1986-02-01",
                    "An aged Batman returns to fight crime in a dystopian Gotham."));
            System.out.println("[UserInitializer] Example comics inserted.");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
