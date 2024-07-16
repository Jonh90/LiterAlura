package com.aluradesafio.LiterAlura.repository;

import com.aluradesafio.LiterAlura.model.Book;
import com.aluradesafio.LiterAlura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByLenguaje(Idioma idioma);

    Optional<Book> findByTitulo(String titulo);

    @Query("SELECT l FROM Book l ORDER BY l.numero_descargas DESC LIMIT 10")
    List<Book> top10LibrosMasDescargados();
}
