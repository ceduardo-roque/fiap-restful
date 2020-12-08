package br.com.fiap.wsrest.covidwebapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe inicial - Spring Boot Application que inicia a aplicação Spring Boot através do método Main
 * 
 * @author Carlos Eduardo Roque da Silva
 *
 */
@SpringBootApplication
public class CovidWebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidWebApiApplication.class, args);
	}
}
