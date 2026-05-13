import java.sql.*;

public class ConexionBD {
    private static Connection connection = null;
    
    public static Connection conectar() {
        if (connection == null) {
            try {
                // 👇 NO necesitas Class.forName con Maven
                connection = DriverManager.getConnection("jdbc:sqlite:libros.db");
                crearTabla();
                System.out.println("✅ Base de datos conectada");
            } catch (SQLException e) {
                System.out.println("❌ Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
    
    private static void crearTabla() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS libros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                autor TEXT NOT NULL,
                fecha_lectura TEXT,
                puntuacion REAL,
                notas TEXT
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    public static void cerrar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar: " + e.getMessage());
        }
    }
}