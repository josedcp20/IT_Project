package pk.wieik.it_project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    // Ruta a la BD: dentro del directorio de trabajo (raíz del proyecto al lanzar desde IntelliJ)
    private static final String DB_PATH = System.getProperty("user.dir") + "/comics-db.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    // Bloque static: se ejecuta UNA sola vez, la primera vez que se carga la clase.
    // Esto cumple con "Create relational database tables automatically" del PDF.
    static {
        createTables();
    }

    /**
     * Devuelve una conexión nueva a la BD.
     * Cada llamada abre una conexión que el caller debe cerrar (try-with-resources).
     * SQLite es un fichero, no un servidor, así que abrir/cerrar es barato.
     */
    public static Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        // SQLite tiene las FK desactivadas por defecto. Las activamos por conexión.
        try (Statement st = connection.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }

    /**
     * Crea las tres tablas si no existen. Idempotente.
     */
    private static void createTables() {
        String usersTable =
                "CREATE TABLE IF NOT EXISTS users (" +
                        "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "  user TEXT NOT NULL UNIQUE," +
                        "  password TEXT NOT NULL," +
                        "  privileges INTEGER NOT NULL DEFAULT 1" +
                        ")";

        // settings: reutilizada como "favoritos" del usuario.
        // name = título, surname = serie/autor, age = id del cómic.
        String settingsTable =
                "CREATE TABLE IF NOT EXISTS settings (" +
                        "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "  user_id INTEGER NOT NULL," +
                        "  name TEXT," +
                        "  surname TEXT," +
                        "  age INTEGER," +
                        "  FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE" +
                        ")";

        String comicsTable =
                "CREATE TABLE IF NOT EXISTS comics (" +
                        "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "  title TEXT NOT NULL," +
                        "  series TEXT," +
                        "  cartoonist TEXT," +
                        "  writer TEXT," +
                        "  publisher TEXT," +
                        "  release_date TEXT," +
                        "  date_added TEXT DEFAULT CURRENT_DATE," +
                        "  description TEXT" +
                        ")";

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.execute(usersTable);
            statement.execute(settingsTable);
            statement.execute(comicsTable);

            System.out.println("[DatabaseManager] DB lista en: " + DB_PATH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
