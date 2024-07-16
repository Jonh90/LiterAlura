package com.aluradesafio.LiterAlura;

import com.aluradesafio.LiterAlura.principal.Principal;
import com.aluradesafio.LiterAlura.repository.AutorRepository;
import com.aluradesafio.LiterAlura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.aluradesafio.LiterAlura.repository")
@ComponentScan(basePackages = {"com.aluradesafio.LiterAlura"})

public class LiterAluraApplication implements CommandLineRunner {
	@Autowired
	private BookRepository repositoryLibro;
	@Autowired
	private AutorRepository repositoryAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositoryLibro, repositoryAutor);
		principal.muestraMenu();

	}
}
