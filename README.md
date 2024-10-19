# Desafío con framework Spring
# Desarrolladora: Mariana A. Lois

Este proyecto es una aplicación de consola que interactúa con la API de libros "Gutendex"
para consultar y mostrar información sobre libros de dominio público.
Permite realizar búsquedas y generar estadísticas sobre los libros disponibles.

## Contenido:

- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Descripción del Proyecto](#descripción-del-proyecto)
- [Clases Principales](#clases-principales)
- [Funcionalidades](#funcionalidades)
- [Contribuciones](#contribuciones)

## Tecnologías Utilizadas:

- **Java 21**
- **Spring Boot**
- **Jackson** para la manipulación de JSON.
- **Streams** de Java para el procesamiento de datos.

## Descripción del Proyecto:

Este proyecto permite a los usuarios buscar libros de la API de Gutendex basándose en distintos criterios,
como el título del libro o el período de vida de los autores. También muestra estadísticas sobre los libros,
incluyendo los más descargados.

## Clases Principales:

### `DesafioApplication`

Esta es la clase principal del proyecto que inicia la aplicación Spring Boot. Implementa la interfaz
CommandLineRunner, lo que permite ejecutar código al iniciar la aplicación. En el método run(), se crea una
instancia de la clase Principal y se llama al método muestraElMenu(),
que despliega el menú interactivo para el usuario.

### `Principal`

Es la clase que contiene el método `muestraElMenu()`, el cual despliega un menú interactivo que permite a
los usuarios realizar diferentes acciones:

1. **Mostrar los 10 libros más descargados.**
2. **Buscar libros por título.**
3. **Mostrar estadísticas de la biblioteca virtual.**
4. **Buscar libros por un rango de tiempo en el que sus autores están vivos.**

### `ConsumoAPI`

Maneja las solicitudes HTTP a la API de Gutendex y devuelve los datos en formato JSON.

### `ConvierteDatos`

Se encarga de convertir el JSON recibido de la API a los objetos de tipo `Datos` y `DatosLibros`.

IConvierteDatos
Esta interfaz define un método genérico obtenerDatos, que toma un String en formato JSON y una clase de tipo
Class<T>. Este método permite convertir el JSON en un objeto de la clase especificada.
Facilita la implementación de distintos métodos de conversión a diferentes tipos de datos en el proyecto.

### `Datos`, `DatosLibros`, `DatosAutor`

Estos son records que representan la estructura de datos obtenida desde la API, mapeando los campos relevantes
como títulos de libros, autores, idiomas y fechas de nacimiento/muerte.


## Funcionalidades:

- **Mostrar los libros más descargados**: Presenta un listado de los 10 libros más descargados desde la API.
- **Búsqueda de libros**: Permite buscar un libro mediante su título o parte de él.
- **Estadísticas de descargas**: Calcula y muestra estadísticas como el promedio de descargas,
- el libro más descargado y el menos descargado.
- **Filtrar libros por autores vivos**: Permite ingresar un rango de fechas y muestra solo los libros cuyos
- autores están vivos en ese periodo, mostrando información relevante como el nombre del autor,
- fechas de nacimiento y muerte, y los idiomas disponibles.
