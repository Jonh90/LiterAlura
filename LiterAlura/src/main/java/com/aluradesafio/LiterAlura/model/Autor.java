package com.aluradesafio.LiterAlura.model;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer fecha_nacimiento;
    private Integer fecha_deceso;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> book;

    public Autor(DatosAutor datosAutor){
        this.nombre=datosAutor.nombre();
        this.fecha_nacimiento = datosAutor.fechaNacimiento();
        this.fecha_deceso = datosAutor.fechaFallecimiento();
    }

    @Override
    public String toString() {
        StringBuilder booksStr = new StringBuilder();
        booksStr.append("Libros: ");

        for(int i = 0; i < book.size() ; i++) {
            booksStr.append(book.get(i).getTitulo());
            if (i < book.size() - 1 ){
                booksStr.append(", ");
            }
        }
        return String.format("********** Autor **********%nNombre:" +
                " %s%n%s%nFecha de Nacimiento: %s%nFecha de Deceso:" +
                " %s%n***************************%n",nombre,booksStr.toString(),fecha_nacimiento,fecha_deceso);
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public Integer getFecha_nacimiento() {

        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Integer fecha_nacimiento) {

        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Integer getFecha_deceso() {
        return fecha_deceso;
    }

    public void setFecha_deceso(Integer fecha_deceso) {

        this.fecha_deceso = fecha_deceso;
    }

    public List<Book> getLibro() {

        return book;
    }

    public void setLibro(List<Book> book) {

        this.book = book;
    }
}
