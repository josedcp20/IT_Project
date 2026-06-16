package pk.wieik.it_project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_PATH = "C:/Users/Llanos Pujante/Desktop/Uni/Erasmus/2 Cuatri/Internet Technologies/IT_Project/IT_Project/comics-db.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        createTables();
    }

    public static Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        try (Statement st = connection.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }

    private static void createTables() {
        String usersTable =
                "CREATE TABLE IF NOT EXISTS users (" +
                        "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "  user TEXT NOT NULL UNIQUE," +
                        "  password TEXT NOT NULL," +
                        "  privileges INTEGER NOT NULL DEFAULT 1" +
                        ")";

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

            System.out.println("[DatabaseManager] DB ready at: " + DB_PATH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
