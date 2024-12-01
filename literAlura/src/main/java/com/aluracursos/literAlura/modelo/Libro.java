package com.aluracursos.literAlura.modelo;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)

    private String titulo;


    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false) // Columna que almacenará el ID del autor
    private Autor autor;



    private String idioma;

    private Integer numeroDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro, Autor autor){
        this.autor = autor;
        this.titulo = datosLibro.titulo();
         this.idioma = convertirIdiomas(datosLibro.idioma()).toString();
         this.numeroDescargas = datosLibro.numeroDescargas();
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Libro: " + '\n' +
                "Nombre del libro: " + titulo + '\n' +
                "Idioma: " + idioma + '\n' +
                "Numero de descargas: " + numeroDescargas + "\n" +
                "Autor:  " + autor.getNombre();
    }


    private List<String> convertirIdiomas(List<String> idiomas) {
        try {
            // Verifica si la lista es nula; si no, la retorna. Si es nula, devuelve una lista vacía.
            return idiomas != null ? new ArrayList<>(idiomas) : new ArrayList<>();
        } catch (ClassCastException e) {
            System.err.println("Error al convertir idiomas: " + e.getMessage());
            return new ArrayList<>(); // En caso de error, devuelve una lista vacía para evitar fallos.
        }
    }
}
