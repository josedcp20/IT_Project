package pk.wieik.it_project.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pk.wieik.it_project.dao.ComicDAO;
import pk.wieik.it_project.dao.SettingsDAO;
import pk.wieik.it_project.dto.ComicDTO;
import pk.wieik.it_project.dto.SettingsDTO;
import pk.wieik.it_project.dto.UserDTO;

import java.io.IOException;
import java.util.List;

@WebServlet("/comics")
public class ComicServlet extends HttpServlet {

    private final ComicDAO comicDAO = new ComicDAO();
    private final SettingsDAO settingsDAO = new SettingsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list" -> handleList(request, response);
            case "search" -> handleSearch(request, response);
            case "detail" -> handleDetail(request, response);
            case "editForm" -> handleEditForm(request, response);
            default -> handleList(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        if ("addFav".equals(action) || "removeFav".equals(action)) {
            UserDTO loggedUser = getLoggedUser(request);
            if (loggedUser == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
            if ("addFav".equals(action)) handleAddFavorite(request, response, loggedUser);
            else handleRemoveFavorite(request, response, loggedUser);
            return;
        }

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/comics");
            return;
        }

        switch (action) {
            case "add" -> handleAdd(request, response);
            case "update" -> handleUpdate(request, response);
            case "delete" -> handleDelete(request, response);
            default -> response.sendRedirect(request.getContextPath() + "/comics");
        }
    }

    private void handleAddFavorite(HttpServletRequest request, HttpServletResponse response, UserDTO user)
            throws IOException {
        int comicId = parseInt(request.getParameter("comicId"), -1);
        if (comicId > 0 && !settingsDAO.isFavorite(user.getId(), comicId)) {
            ComicDTO c = comicDAO.findById(comicId);
            if (c != null) {
                SettingsDTO s = new SettingsDTO(user.getId(), c.getTitle(), c.getSeries(), c.getId());
                settingsDAO.addSettings(s);
            }
        }
        String from = request.getParameter("from");
        if ("detail".equals(from)) {
            response.sendRedirect(request.getContextPath() + "/comics?action=detail&id=" + comicId);
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp?page=settings");
        }
    }

    private void handleRemoveFavorite(HttpServletRequest request, HttpServletResponse response, UserDTO user)
            throws IOException {
        int comicId = parseInt(request.getParameter("comicId"), -1);
        if (comicId > 0) {
            settingsDAO.removeFavorite(user.getId(), comicId);
        }
        String from = request.getParameter("from");
        if ("detail".equals(from)) {
            response.sendRedirect(request.getContextPath() + "/comics?action=detail&id=" + comicId);
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp?page=settings");
        }
    }

    private UserDTO getLoggedUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        UserDTO u = (UserDTO) session.getAttribute("user");
        return (u != null && u.getPrivileges() > 0) ? u : null;
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");
        List<ComicDTO> comics = comicDAO.getAll(sortBy);
        request.setAttribute("comics", comics);
        request.getRequestDispatcher("/comics.jsp").forward(request, response);
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String series = request.getParameter("series");
        String cartoonist = request.getParameter("cartoonist");
        String publisher = request.getParameter("publisher");
        List<ComicDTO> comics = comicDAO.search(title, series, cartoonist, publisher);
        request.setAttribute("comics", comics);
        request.setAttribute("searchMode", true);
        request.getRequestDispatcher("/comics.jsp").forward(request, response);
    }

    private void handleDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = parseInt(request.getParameter("id"), -1);
        ComicDTO comic = comicDAO.findById(id);
        if (comic == null) {
            response.sendRedirect(request.getContextPath() + "/comics");
            return;
        }
        request.setAttribute("comic", comic);
        request.getRequestDispatcher("/comic_detail.jsp").forward(request, response);
    }

    private void handleEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/comics");
            return;
        }
        int id = parseInt(request.getParameter("id"), -1);
        ComicDTO comic = (id > 0) ? comicDAO.findById(id) : new ComicDTO();
        request.setAttribute("comic", comic);
        request.getRequestDispatcher("/comic_form.jsp").forward(request, response);
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        comicDAO.addComic(buildFromRequest(request, 0));
        response.sendRedirect(request.getContextPath() + "/comics");
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = parseInt(request.getParameter("id"), -1);
        if (id <= 0) {
            response.sendRedirect(request.getContextPath() + "/comics");
            return;
        }
        comicDAO.updateComic(buildFromRequest(request, id));
        response.sendRedirect(request.getContextPath() + "/comics");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = parseInt(request.getParameter("id"), -1);
        if (id > 0) comicDAO.deleteComic(id);
        response.sendRedirect(request.getContextPath() + "/comics");
    }

    private ComicDTO buildFromRequest(HttpServletRequest request, int id) {
        ComicDTO c = new ComicDTO();
        c.setId(id);
        c.setTitle(request.getParameter("title"));
        c.setSeries(request.getParameter("series"));
        c.setCartoonist(request.getParameter("cartoonist"));
        c.setWriter(request.getParameter("writer"));
        c.setPublisher(request.getParameter("publisher"));
        c.setReleaseDate(request.getParameter("releaseDate"));
        c.setDescription(request.getParameter("description"));
        return c;
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;
        UserDTO user = (UserDTO) session.getAttribute("user");
        return user != null && user.getPrivileges() == 2;
    }

    private int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}
