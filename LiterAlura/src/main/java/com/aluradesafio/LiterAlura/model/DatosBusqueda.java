package com.aluradesafio.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBusqueda(

        @JsonAlias("count") Integer cuenta,
        @JsonAlias("results") List<DatosBooks> resultado
                            ) {
}
