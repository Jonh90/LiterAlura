package com.aluradesafio.LiterAlura.principal;

import com.aluradesafio.LiterAlura.model.*;
import com.aluradesafio.LiterAlura.repository.AutorRepository;
import com.aluradesafio.LiterAlura.repository.BookRepository;
import com.aluradesafio.LiterAlura.service.ConsumoAPI;
import com.aluradesafio.LiterAlura.service.ConvierteDatos;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private BookRepository repositoryBook;
    private AutorRepository repositoryAutor;
    private List<Autor> autores;
    private List<Book> books;

    public Principal(BookRepository repositoryBook, AutorRepository repositoryAutor) {
        this.repositoryBook = repositoryBook;
        this.repositoryAutor = repositoryAutor;
    }

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en determinados año
                    5 - Listar libros por idioma
                    6 - Top 10 libros mas descargados
                    7 - Libro mas descargado y libro menos descargado
                                                     
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    top10LibrosMasDescargados();
                    break;
                case 7:
                    rankingBooks();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosBusqueda getBusqueda() {
        System.out.println("Escribe el nombre del libro: ");
        var nombreBook = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreBook.replace(" ", "%20"));
        DatosBusqueda datos = conversor.obtenerDatos(json, DatosBusqueda.class);
        return datos;

    }

    private void buscarLibroPorTitulo() {
        DatosBusqueda datosBusqueda = getBusqueda();
        if (datosBusqueda != null && !datosBusqueda.resultado().isEmpty()) {
            DatosBooks primerBook = datosBusqueda.resultado().get(0);


            Book book = new Book(primerBook);
            System.out.println("***** Libro *****");
            System.out.println(book);
            System.out.println("*****************");

            Optional<Book> libroExiste = repositoryBook.findByTitulo(book.getTitulo());
            if (libroExiste.isPresent()){
                System.out.println("\nEl libro ya se encuentra registrado\n");
            }else {

                if (!primerBook.autor().isEmpty()) {
                    DatosAutor autor = primerBook.autor().get(0);
                    Autor autor1 = new Autor(autor);
                    Optional<Autor> autorOptional = repositoryAutor.findByNombre(autor1.getNombre());

                    if (autorOptional.isPresent()) {
                        Autor autorExiste = autorOptional.get();
                        book.setAutor(autorExiste);
                        repositoryBook.save(book);
                    } else {
                        Autor autorNuevo = repositoryAutor.save(autor1);
                        book.setAutor(autorNuevo);
                        repositoryBook.save(book);
                    }

                    Integer numeroDescargas = book.getNumero_descargas() != null ? book.getNumero_descargas() : 0;
                    System.out.println("********** Libro **********");
                    System.out.printf("Titulo: %s%nAutor: %s%nIdioma: %s%nNumero de Descargas: %s%n",
                            book.getTitulo(), autor1.getNombre(), book.getLenguaje(), book.getNumero_descargas());
                    System.out.println("***************************\n");
                } else {
                    System.out.println("Sin autor");
                }
            }
        } else {
            System.out.println("El libro no fue encontrado");
        }
    }

    private void listarLibrosRegistrados() {
        books = repositoryBook.findAll();
        books.stream()
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autores = repositoryAutor.findAll();
        autores.stream()
                .forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año donde vivio el autor(es) que desea buscar: ");
        var anio = teclado.nextInt();
        autores = repositoryAutor.listaAutoresVivosPorAnio(anio);
        autores.stream()
                .forEach(System.out::println);

    }

    private void listarLibrosPorIdioma() {
        System.out.println("Selecciona el lenguaje/idioma que deseas buscar: ");

        var opcion = -1;
        while (opcion != 0) {
            var opciones = """
                    1. en = Ingles
                    2. es = Español
                    3. fr = Francés
                    4. pt = Portugués
                    
                    0. Volver a Las opciones anteriores
                    """;
            System.out.println(opciones);
            while (!teclado.hasNextInt()) {
                System.out.println("Formato inválido, ingrese un número que esté disponible en el menú");
                teclado.nextLine();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    List<Book> librosEnIngles = datosBusquedaLenguaje("[en]");
                    librosEnIngles.forEach(System.out::println);
                    break;
                case 2:
                    List<Book> librosEnEspanol = datosBusquedaLenguaje("[es]");
                    librosEnEspanol.forEach(System.out::println);
                    break;
                case 3:
                    List<Book> librosEnFrances = datosBusquedaLenguaje("[fr]");
                    librosEnFrances.forEach(System.out::println);
                    break;
                case 4:
                    List<Book> librosEnPortugues = datosBusquedaLenguaje("[pt]");
                    librosEnPortugues.forEach(System.out::println);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Ningún idioma a sido seleccionado");
            }
        }

    }

    private List<Book> datosBusquedaLenguaje(String idioma){
        var dato = Idioma.fromString(idioma);
        System.out.println("Lenguaje buscado: " + dato);

        List<Book> bookPorIdioma = repositoryBook.findByLenguaje(dato);
        return bookPorIdioma;
    }

    private void top10LibrosMasDescargados() {
        List<Book> topLibros = repositoryBook.top10LibrosMasDescargados();
        topLibros.forEach(System.out::println);
    }

    private void rankingBooks() {
        books = repositoryBook.findAll();
        IntSummaryStatistics est = books.stream()
                .filter(l -> l.getNumero_descargas() > 0)
                .collect(Collectors.summarizingInt(Book::getNumero_descargas));

        Book bookMasDescargado = books.stream()
                .filter(l -> l.getNumero_descargas() == est.getMax())
                .findFirst()
                .orElse(null);

        Book bookMenosDescargado = books.stream()
                .filter(l -> l.getNumero_descargas() == est.getMin())
                .findFirst()
                .orElse(null);

        System.out.println("******************************************************");
        System.out.printf("%nLibro más descargado: %s%nNúmero de descargas: " +
                        "%d%n%nLibro menos descargado: %s%nNúmero de descargas: " +
                        "%d%n%n",bookMasDescargado.getTitulo(),est.getMax(),
                bookMenosDescargado.getTitulo(),est.getMin());
        System.out.println("******************************************************");
    }



}
