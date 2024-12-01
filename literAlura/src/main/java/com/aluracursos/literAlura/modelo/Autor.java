package com.aluracursos.literAlura.modelo;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)

    private String nombre;


    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;


    private String fechaDeNacimiento;
    private String fechaDeMuerte;

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDeMuerte = datosAutor.fechaDeMuerte();
    }

    @Override
    public String toString() {
        return "Autor: " +
                "nombre='" + nombre + '\'' +
                ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                ", fechaDeMuerte='" + fechaDeMuerte + '\'';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(String fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }
}
