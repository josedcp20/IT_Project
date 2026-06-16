package pk.wieik.it_project.dao;

import pk.wieik.it_project.database.DatabaseManager;
import pk.wieik.it_project.dto.SettingsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class SettingsDAO {
    /**
     * Añade una entrada de favoritos. Método mínimo del PDF.
     * En nuestro dominio: marca un cómic como favorito para un usuario.
     */
    public boolean addSettings(SettingsDTO settings) {
        String sql = "INSERT INTO settings(user_id, name, surname, age) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, settings.getUserId());
            ps.setString(2, settings.getName());
            ps.setString(3, settings.getSurname());
            ps.setInt(4, settings.getAge());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lista los favoritos de un usuario concreto.
     */
    public List<SettingsDTO> getByUserId(int userId) {
        String sql = "SELECT id, user_id, name, surname, age FROM settings WHERE user_id = ?";
        List<SettingsDTO> list = new ArrayList<>();

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Comprueba si un usuario ya tiene un cómic concreto como favorito.
     * Útil para evitar duplicados en la UI.
     */
    public boolean isFavorite(int userId, int comicId) {
        String sql = "SELECT 1 FROM settings WHERE user_id = ? AND age = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, comicId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un favorito concreto (por usuario + id del cómic).
     */
    public boolean removeFavorite(int userId, int comicId) {
        String sql = "DELETE FROM settings WHERE user_id = ? AND age = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, comicId);
            return ps.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private SettingsDTO mapRow(ResultSet rs) throws SQLException {
        return new SettingsDTO(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getInt("age")
        );
    }
}
