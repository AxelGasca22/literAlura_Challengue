package com.aluracursos.literAlura.service;

import com.aluracursos.literAlura.modelo.DatosLibro;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record ArregloLibros(
        @JsonAlias("results") List<DatosLibro> resultados
) {


}
