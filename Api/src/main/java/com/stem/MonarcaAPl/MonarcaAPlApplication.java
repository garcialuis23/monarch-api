package com.stem.MonarcaAPl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MonarcaAPlApplication {

	public static void main(String[] args) {
		// El método estático SpringApplication.run() inicia la aplicación Spring Boot.
		// Toma dos argumentos: la clase principal de la aplicación y los argumentos de
		// la línea de comandos (args).

		SpringApplication.run(MonarcaAPlApplication.class, args);
	}

}
