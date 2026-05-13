import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    
    public void agregar(Libro libro) throws SQLException {
        String sql = "INSERT INTO libros (titulo, autor, fecha_lectura, puntuacion, notas) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = ConexionBD.conectar().prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getFechaLectura().toString());
            pstmt.setDouble(4, libro.getPuntuacion());
            pstmt.setString(5, libro.getNotas());
            pstmt.executeUpdate();
            System.out.println("✅ Libro agregado correctamente");
        }
    }
    
    public List<Libro> listarTodos() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros ORDER BY fecha_lectura DESC";
        
        try (Statement stmt = ConexionBD.conectar().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Libro libro = new Libro(
                    0, rs.getString("titulo"),
                    rs.getString("autor"),
                    LocalDate.parse(rs.getString("fecha_lectura")),
                    rs.getDouble("puntuacion"),
                    rs.getString("notas")
                );
                libro.setId(rs.getInt("id"));
                libros.add(libro);
            }
        }
        return libros;
    }
    
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (PreparedStatement pstmt = ConexionBD.conectar().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int afectados = pstmt.executeUpdate();
            if (afectados > 0) {
                System.out.println("✅ Libro eliminado");
            } else {
                System.out.println("❌ No se encontró el libro con ID " + id);
            }
        }
    }
    
}