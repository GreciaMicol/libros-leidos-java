import java.time.LocalDate;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private LocalDate fechaLectura;
    private double puntuacion;
    private String notas;

    // Constructor

    public Libro(int id, String titulo, String autor, LocalDate fechaLectura, double puntuacion, String notas) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.fechaLectura = fechaLectura;
        this.puntuacion = puntuacion;
        this.notas = notas;
    }

    // Getters y Setters
    public int getId() {return id;}
    public void setId(int id){this.id = id;}
    public String getTitulo() {return titulo;}
    public String getAutor() {return autor;}
    public LocalDate getFechaLectura() {return fechaLectura;}
    public double getPuntuacion() {return puntuacion;}
    public String getNotas() {return notas;}

    @Override
    public String toString() {
        return String.format("📚 %s - %s | ⭐ %.1f/5", 
                       titulo, autor, puntuacion);
    }

}