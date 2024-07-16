package com.aluradesafio.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idioma lenguaje;
    private Integer numero_descargas;

   public Book(DatosBooks datosBook){
        this.titulo = datosBook.titulo();
        this.lenguaje = Idioma.fromString(datosBook.idiomas().toString().split(",")[0].trim());
        this.numero_descargas = datosBook.numero_descargas();
    }

    @Override
    public String toString() {
        String nombreAutor = (autor != null) ? autor.getNombre() : "El Autor es desconocido";
        return String.format("********** Libro **********%nTitulo:" +
                " %s%nAutor: %s%nIdioma: %s%nNumero de Descargar:" +
                " %d%n***************************%n",
                titulo,
                nombreAutor,
                lenguaje,
                numero_descargas);
    }

    public Long getId() {

       return id;
    }

    public void setId(Long id) {

       this.id = id;
    }

    public String getTitulo() {

       return titulo;
    }

    public void setTitulo(String titulo) {

       this.titulo = titulo;
    }

    public Autor getAutor() {

       return autor;
    }

    public void setAutor(Autor autor) {

       this.autor = autor;
    }

    public Idioma getLenguaje() {

       return lenguaje;
    }

    public void setLenguaje(Idioma lenguaje) {

       this.lenguaje = lenguaje;
    }

    public Integer getNumero_descargas() {

       return numero_descargas;
    }

    public void setNumero_descargas(Integer numero_descargas) {

       this.numero_descargas = numero_descargas;
    }
}
