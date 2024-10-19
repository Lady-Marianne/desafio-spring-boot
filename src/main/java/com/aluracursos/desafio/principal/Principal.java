package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.model.Datos;
import com.aluracursos.desafio.model.DatosLibros;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    public void muestraElMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        // Top 10 de los libros más descargados:
        System.out.println("\nTop 10 de los libros más descargados:");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        // Búsqueda de libros por título o parte del mismo:
        System.out.println("\nIngrese el título del libro que desea buscar o parte del mismo:");
        var tituloLibro = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado: " + libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado.");
        }

        // Trabajar con estadísticas:
        System.out.println("\nEstadísticas de la biblioteca virtual:");
        DoubleSummaryStatistics est = datosBusqueda.resultados().stream()
                .filter(d -> d.numeroDeDescargas()>0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println(est);
        System.out.println("Promedio de descargas: " + est.getAverage());
        System.out.println("Libro más descargado: " + est.getMax());
        System.out.println("Libro menos descargado. " + est.getMin());
        System.out.println("Cantidad de registros: " + est.getCount());

        // Búsqueda de libros por un rango de tiempo en el que se encuentran sus autores vivos:
        System.out.println("\nBuscar libros por un rango de tiempo en el que se encuentran sus autores vivos:");
        System.out.println("Ingrese la fecha inicial:");
        var fechaInicial = teclado.nextLine();
        System.out.println("Ingrese la fecha final:");
        var fechaFinal = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE
                + "?author_year_start="+fechaInicial+"&author_year_end="+fechaFinal);
        datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        // Filtrar y mostrar los libros con autores válidos y en el rango de fechas ingresadas por el usuario:
        List<DatosLibros> librosFiltrados = datosBusqueda.resultados().stream()
                // Asegurarse de que tiene autores:
                .filter(l -> !l.autor().isEmpty())
                //Validar que tenga fecha de nacimiento:
                .filter(l -> l.autor().get(0).fechaDeNacimiento() != null)
                // Ordenar por fecha de nacimiento del autor:
                .sorted(Comparator.comparing(libro -> libro.autor().get(0).fechaDeNacimiento()))
                .collect(Collectors.toList());

        // Mostrar los libros filtrados:
        librosFiltrados.forEach(libro -> {
            System.out.println("\nTítulo: " + libro.titulo());
            // Iterar sobre todos los autores de cada libro, porque puede haber más de un autor:
            libro.autor().forEach(autor -> {
                System.out.println("Autor: " + autor.nombre());
                System.out.println("Fecha de nacimiento: " + autor.fechaDeNacimiento());
                // Verificar si la fecha de muerte está disponible:
                if (autor.fechaDeMuerte() != null) {
                    System.out.println("Fecha de muerte: " + autor.fechaDeMuerte());
                } else {
                    System.out.println("Fecha de muerte desonocida o el autor aún vive.");
                }
            });
            // Mostrar en qué idiomas se halla disponible el libro:
            System.out.println("Idiomas disponibles: " + String.join(", ", libro.idiomas()));
        });

    }
}
