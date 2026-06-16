package pk.wieik.it_project.servlet;

import pk.wieik.it_project.dao.ComicDAO;
import pk.wieik.it_project.dto.ComicDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/comics")
public class ComicServlet extends HttpServlet {
    private ComicDAO comicDAO = new ComicDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtenemos la lista de cómics del DAO
        List<ComicDTO> listaComics = comicDAO.getAll("title");

        // Guardamos la lista en el request para que el JSP la pueda leer
        request.setAttribute("listaComics", listaComics);

        // Redirigimos a la vista
        request.getRequestDispatcher("/comics.jsp").forward(request, response);
    }
}