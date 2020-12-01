package br.com.fiap.wsrest.covidwebapi.controller;


import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("covid")
public class CovidController {
	
	private final Logger logger = LoggerFactory.getLogger(CovidController.class);
	
	public CovidController() {
	}
	
	@GetMapping()
	public List<String> findAll(){
		List<String> lista = new LinkedList<String>();
		lista.add("Teste de GET na Controller");
		return lista;
	}
	
	@GetMapping("{id}")
	public String buscaClienteAlunoPorID(@PathVariable Long id){
		return "ID: " + id;
	}
}

