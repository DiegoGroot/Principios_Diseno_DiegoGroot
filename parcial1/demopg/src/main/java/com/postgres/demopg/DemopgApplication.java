package com.postgres.demopg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal de DemoPG
 * API REST para gestión de tweets
 * 
 * @author Diego Groot
 * @version 1.0.0
 */
@SpringBootApplication
public class DemopgApplication {

	/**
	 * Punto de entrada de la aplicación
	 * @param args Argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(DemopgApplication.class, args);
	}

}
