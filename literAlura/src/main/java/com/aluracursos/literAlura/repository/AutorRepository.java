package com.aluracursos.literAlura.repository;

import com.aluracursos.literAlura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombreAutorBuscar, '%'))")
    Autor autorPorNombre(@Param("nombreAutorBuscar") String nombreAutorBuscar);

    @Query(value = "SELECT * FROM autores WHERE TO_DATE(fecha_de_nacimiento, 'YYYY-MM-DD') <= TO_DATE(:anio || '-01-01', 'YYYY-MM-DD') AND (fecha_de_muerte IS NULL OR TO_DATE(fecha_de_muerte, 'YYYY-MM-DD') > TO_DATE(:anio || '-01-01', 'YYYY-MM-DD'))", nativeQuery = true)
    List<Autor> autoresVivosEn(@Param("anio") String anio);



}
