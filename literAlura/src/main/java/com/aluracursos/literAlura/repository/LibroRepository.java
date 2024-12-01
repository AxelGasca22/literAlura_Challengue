package com.aluracursos.literAlura.repository;

import com.aluracursos.literAlura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    @Query("SELECT e FROM Libro e WHERE LOWER(e.titulo) LIKE LOWER(CONCAT('%', :nombreLibro, '%'))")
    Libro libroPorNombre(@Param("nombreLibro") String nombreLibro);

}
