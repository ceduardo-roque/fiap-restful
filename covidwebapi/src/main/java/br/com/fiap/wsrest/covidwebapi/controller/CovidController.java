package br.com.fiap.wsrest.covidwebapi.controller;


import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("estado/{estado}")
	public String buscaCasosEmUmEstado(@PathVariable String estado, @RequestParam String periodoDe, @RequestParam String periodoAte){
		return "Estado: " + estado + "; PeriodoDe: " + periodoDe + "; PeriodoAte: " + periodoAte;
	}
	
	@GetMapping("estado")
	public String buscaCasosEmDiversosEstados(@RequestParam String estados, @RequestParam String periodoDe, @RequestParam String periodoAte){
		return "Estados: " + estados + "; PeriodoDe: " + periodoDe + "; PeriodoAte: " + periodoAte;
	}
	
	@GetMapping("pais/{pais}")
	public String buscaCasosEmUmPais(@PathVariable String pais, @RequestParam String periodoDe, @RequestParam String periodoAte){
		return "País: " + pais + "; PeriodoDe: " + periodoDe + "; PeriodoAte: " + periodoAte;
	}
	
	@GetMapping("pais")
	public String buscaCasosEmDiversosPaises(@RequestParam String paises, @RequestParam String periodoDe, @RequestParam String periodoAte){
		return "Paises: " + paises + "; PeriodoDe: " + periodoDe + "; PeriodoAte: " + periodoAte;
	}
	
	@GetMapping("global")
	public String buscaCasosGlobais(){
		return "Casos Globais";
	}
}

