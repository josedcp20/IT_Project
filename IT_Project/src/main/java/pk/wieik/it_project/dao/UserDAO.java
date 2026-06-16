package pk.wieik.it_project.dao;

import pk.wieik.it_project.database.DatabaseManager;
import pk.wieik.it_project.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class UserDAO {

    /**
     * Inserta un usuario nuevo en la BD.
     * Devuelve true si se insertó correctamente.
     * Falla (devuelve false) si el nombre de usuario ya existe (columna UNIQUE).
     */
    public boolean addUser(UserDTO user) {
        String sql = "INSERT INTO users(user, password, privileges) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getUser());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getPrivileges());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Valida credenciales. Devuelve true si el usuario existe Y la contraseña coincide
     * Y NO está bloqueado (privileges > 0).
     * El método mínimo que pide el PDF.
     */
    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE user = ? AND password = ? AND privileges > 0";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca un usuario por su nombre de login. Devuelve null si no existe.
     * Lo necesitamos en el login para guardar el UserDTO completo en sesión.
     */
    public UserDTO findByUsername(String username) {
        String sql = "SELECT id, user, password, privileges FROM users WHERE user = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lista todos los usuarios. Necesario para el panel de administración.
     */
    public List<UserDTO> getAllUsers() {
        String sql = "SELECT id, user, password, privileges FROM users ORDER BY id";
        List<UserDTO> users = new ArrayList<>();

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Cambia los privilegios de un usuario.
     * 0 = bloqueado, 1 = usuario normal, 2 = admin.
     */
    public boolean updatePrivileges(int userId, int privileges) {
        String sql = "UPDATE users SET privileges = ? WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, privileges);
            ps.setInt(2, userId);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Borra un usuario. Por el ON DELETE CASCADE en `settings`,
     * sus favoritos se borran automáticamente.
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cuenta cuántos usuarios hay. Lo usaremos en UserInitializer
     * para insertar el admin/user iniciales SOLO si la BD está vacía.
     */
    public int countUsers() {
        String sql = "SELECT COUNT(*) FROM users";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean register(String username, String password) {
        // La consulta SQL debe insertar el usuario, la contraseña (ya hasheada) y los privilegios (1 por defecto)
        String sql = "INSERT INTO users (user, password, privileges) VALUES (?, ?, 1)";

        try (Connection connection = DatabaseManager.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            return ps.executeUpdate() == 1; // Devuelve true si se insertó una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getHashedPassword(String username) {
        String sql = "SELECT password FROM users WHERE user = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Devuelve null si el usuario no existe o hay error
    }

    /**
     * Helper privado: convierte una fila del ResultSet en un UserDTO.
     * Evita duplicar código en findByUsername / getAllUsers.
     */
    private UserDTO mapRow(ResultSet rs) throws SQLException {
        return new UserDTO(
                rs.getInt("id"),
                rs.getString("user"),
                rs.getString("password"),
                rs.getInt("privileges")
        );
    }
}
