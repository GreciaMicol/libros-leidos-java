import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static LibroDAO libroDAO = new LibroDAO();
    
    public static void main(String[] args) {
        System.out.println("📖 === SISTEMA DE LIBROS LEÍDOS === 📖");
        
        while (true) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");
            
            try {
                switch (opcion) {
                    case 1:
                        agregarLibro();
                        break;
                    case 2:
                        listarLibros();
                        break;
                    case 3:
                        eliminarLibro();
                        break;
                    case 4:
                        System.out.println("👋 ¡Hasta luego!");
                        ConexionBD.cerrar();
                        return;
                    default:
                        System.out.println("❌ Opción no válida");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
            
            System.out.println("\nPresiona Enter para continuar...");
            scanner.nextLine();
            scanner.nextLine();
        }
    }
    
    private static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("1️⃣  Agregar libro");
        System.out.println("2️⃣  Listar libros");
        System.out.println("3️⃣  Eliminar libro");
        System.out.println("4️⃣  Salir");
        System.out.println("=".repeat(40));
    }
    
    private static void agregarLibro() throws Exception {
        System.out.println("\n📝 NUEVO LIBRO:");
        
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        
        System.out.print("Fecha de lectura (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine());
        
        double puntuacion = leerDouble("Puntuación (0-5): ");
        
        System.out.print("Notas (opcional): ");
        String notas = scanner.nextLine();
        
        Libro libro = new Libro(0, titulo, autor, fecha, puntuacion, notas);
        libroDAO.agregar(libro);
    }
    
    private static void listarLibros() throws Exception {
        List<Libro> libros = libroDAO.listarTodos();
        
        if (libros.isEmpty()) {
            System.out.println("\n📭 No hay libros registrados aún");
            return;
        }
        
        System.out.println("\n📚 MIS LIBROS LEÍDOS:");
        System.out.println("-".repeat(50));
        
        for (Libro libro : libros) {
            System.out.println("ID: " + libro.getId());
            System.out.println(libro);
            System.out.println("   📅 Leído: " + libro.getFechaLectura());
            if (libro.getNotas() != null && !libro.getNotas().isEmpty()) {
                System.out.println("   📝 Notas: " + libro.getNotas());
            }
            System.out.println("-".repeat(50));
        }
    }
    
    private static void eliminarLibro() throws Exception {
        listarLibros();
        int id = leerEntero("\nID del libro a eliminar: ");
        libroDAO.eliminar(id);
    }
    
    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("❌ Por favor ingresa un número: ");
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return numero;
    }
    
    private static double leerDouble(String mensaje) {
    System.out.print(mensaje);
    while (!scanner.hasNextDouble()) {
        System.out.print("❌ Por favor ingresa un número: ");
        scanner.next();
    }
    double numero = scanner.nextDouble();
    scanner.nextLine();
    // Validar que la puntuación esté entre 0 y 5
    if (numero < 0) {
        System.out.println("⚠️ La puntuación mínima es 0. Se asignará 0.");
        numero = 0;
    }
    if (numero > 5) {
        System.out.println("⚠️ La puntuación máxima es 5. Se asignará 5.");
        numero = 5;
    }
    return numero;
}
}
