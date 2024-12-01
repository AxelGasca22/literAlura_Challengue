package com.aluracursos.literAlura.principal;

import com.aluracursos.literAlura.modelo.Autor;
import com.aluracursos.literAlura.modelo.DatosAutor;
import com.aluracursos.literAlura.modelo.DatosLibro;
import com.aluracursos.literAlura.modelo.Libro;
import com.aluracursos.literAlura.repository.AutorRepository;
import com.aluracursos.literAlura.repository.LibroRepository;
import com.aluracursos.literAlura.service.ArregloLibros;
import com.aluracursos.literAlura.service.ConsumoAPI;
import com.aluracursos.literAlura.service.ConvierteDatos;

import javax.swing.text.html.Option;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi= new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;
    private List<Libro> totalLibros;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepositorio = libroRepository;
        this.autorRepositorio = autorRepository;
    }


    public void muestraElMenu(){
        var opcion = -1;
        while(opcion != 0){
            var menu = """
                    1- Buscar libros por titulo
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Listar libros por idioma
                    0- Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    buscarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutorAño();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibrosPorIdioma() {
    }

    private void buscarAutorAño() {
        System.out.println("Ingresa el año para determinar los autores vivos en ese año: ");
        var anioBuscar = teclado.nextLine();
        List <Autor> autorEncontrado = autorRepositorio.autoresVivosEn(anioBuscar);
        autorEncontrado.forEach(e ->
                System.out.printf("Nombre: %s Fecha de nacimiento: %s Fecha de fallecimiento: %s \n",
                        e.getNombre(), e.getFechaDeNacimiento(), e.getFechaDeMuerte()));
    }

    private void listarAutoresRegistrados() {
        try{
            System.out.println("Ingresa el nombre del Autor: ");
            var nombreAutorBuscar = teclado.nextLine();
            Autor autorEncontrado = autorRepositorio.autorPorNombre(nombreAutorBuscar);
            System.out.println("-----------Autor-------------");
            System.out.println("Nombre del Autor: " + autorEncontrado.getNombre());
            System.out.println("Fecha de nacimiento: " + autorEncontrado.getFechaDeNacimiento());
            System.out.println("Fecha de muerte: " + autorEncontrado.getFechaDeMuerte());
            System.out.println("-------------------------------\n");
        } catch (Exception e) {
            System.out.println("No se encontro el autor en la base de datos");
        }
    }

    private void buscarLibrosRegistrados() {
        //totalLibros = libroRepositorio.findAll();
        try{
            System.out.println("Escribe el nombre del libro: ");
            var nombreLibroBuscar = teclado.nextLine();
            Libro libroEncontrado = libroRepositorio.libroPorNombre(nombreLibroBuscar);
            System.out.println("-----------LIBRO-------------");
            System.out.println("Titulo: " + libroEncontrado.getTitulo());
            System.out.println("Nombre del Autor: " + libroEncontrado.getAutor().getNombre());
            System.out.println("Idioma: " + libroEncontrado.getIdioma());
            System.out.println("Descargas: " + libroEncontrado.getNumeroDescargas());
            System.out.println("-------------------------------\n");
        } catch (Exception e) {
            System.out.println("No se encontró el libro en la base de datos");
        }



    }

    private void buscarLibroPorTitulo(){
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro =teclado.nextLine();
        var busqueda = URLEncoder.encode(nombreLibro);
        var json =consumoApi.obtenerDatos(URL_BASE+busqueda);
        ConvierteDatos conversor = new ConvierteDatos();
        ArregloLibros arregloLibros = conversor.obtenerDatos(json,ArregloLibros.class);
        List<DatosLibro> datosLibros = arregloLibros.resultados();
        Optional<DatosLibro> primerResultado = datosLibros.stream().findFirst();

        if(primerResultado.isPresent()){
            DatosLibro datosLibro = primerResultado.get();
            //Convetir Autor
            List<DatosAutor> datosAutor = datosLibro.autores();
            Optional<DatosAutor> primerResultadoAutor = datosAutor.stream().findFirst();
            DatosAutor datosAutor1 = primerResultadoAutor.get();

            //Convirtiendo a clases de libro y Autor
            Autor autor = new Autor(datosAutor1);
            Libro libro = new Libro(datosLibro,autor);

            //Imprimir los datos
            System.out.println(libro.toString());

            autorRepositorio.save(autor);
            libroRepositorio.save(libro);


        }
        else{
            System.out.println("Libro no encontrado");
        }
    }
}
