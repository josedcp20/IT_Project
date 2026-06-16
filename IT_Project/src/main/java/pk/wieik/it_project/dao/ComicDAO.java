package pk.wieik.it_project.dao;

import pk.wieik.it_project.database.DatabaseManager;
import pk.wieik.it_project.dto.ComicDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComicDAO {

    public boolean addComic(ComicDTO comic) {
        String sql = "INSERT INTO comics(title, series, cartoonist, writer, publisher, release_date, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, comic.getTitle());
            ps.setString(2, comic.getSeries());
            ps.setString(3, comic.getCartoonist());
            ps.setString(4, comic.getWriter());
            ps.setString(5, comic.getPublisher());
            ps.setString(6, comic.getReleaseDate());
            ps.setString(7, comic.getDescription());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateComic(ComicDTO comic) {
        String sql = "UPDATE comics SET title=?, series=?, cartoonist=?, writer=?, publisher=?, " +
                "release_date=?, description=? WHERE id=?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, comic.getTitle());
            ps.setString(2, comic.getSeries());
            ps.setString(3, comic.getCartoonist());
            ps.setString(4, comic.getWriter());
            ps.setString(5, comic.getPublisher());
            ps.setString(6, comic.getReleaseDate());
            ps.setString(7, comic.getDescription());
            ps.setInt(8, comic.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteComic(int comicId) {
        String sql = "DELETE FROM comics WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, comicId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ComicDTO findById(int comicId) {
        String sql = "SELECT * FROM comics WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, comicId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ComicDTO> getAll(String sortBy) {
        String column = switch (sortBy == null ? "" : sortBy) {
            case "title" -> "title";
            case "release_date" -> "release_date";
            case "date_added" -> "date_added";
            default -> "id";
        };
        String sql = "SELECT * FROM comics ORDER BY " + column;
        List<ComicDTO> list = new ArrayList<>();

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ComicDTO> search(String title, String series, String cartoonist, String publisher) {
        StringBuilder sql = new StringBuilder("SELECT * FROM comics WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (title != null && !title.isBlank()) {
            sql.append(" AND title LIKE ?");
            params.add("%" + title + "%");
        }
        if (series != null && !series.isBlank()) {
            sql.append(" AND series LIKE ?");
            params.add("%" + series + "%");
        }
        if (cartoonist != null && !cartoonist.isBlank()) {
            sql.append(" AND cartoonist LIKE ?");
            params.add("%" + cartoonist + "%");
        }
        if (publisher != null && !publisher.isBlank()) {
            sql.append(" AND publisher LIKE ?");
            params.add("%" + publisher + "%");
        }
        sql.append(" ORDER BY title");

        List<ComicDTO> list = new ArrayList<>();
        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setString(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private ComicDTO mapRow(ResultSet rs) throws SQLException {
        ComicDTO c = new ComicDTO();
        c.setId(rs.getInt("id"));
        c.setTitle(rs.getString("title"));
        c.setSeries(rs.getString("series"));
        c.setCartoonist(rs.getString("cartoonist"));
        c.setWriter(rs.getString("writer"));
        c.setPublisher(rs.getString("publisher"));
        c.setReleaseDate(rs.getString("release_date"));
        c.setDateAdded(rs.getString("date_added"));
        c.setDescription(rs.getString("description"));
        return c;
    }
}
