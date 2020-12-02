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

import br.com.fiap.wsrest.covidwebapi.dto.RetornoEstadoDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoGlobalDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoPaisDTO;
import br.com.fiap.wsrest.covidwebapi.fake.GeradorRetornoFake;

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
	public RetornoEstadoDTO buscaCasosEmUmEstado(@PathVariable String estado, @RequestParam String periodoDe, @RequestParam String periodoAte){
		GeradorRetornoFake gerador = new GeradorRetornoFake();
		RetornoEstadoDTO retorno = gerador.montaRetornoEstadoFake();
		retorno.setEstado(estado);
		retorno.setNome(gerador.recuperaNomeEstado(estado));
		return retorno;
	}
	
	@GetMapping("estado")
	public List<RetornoEstadoDTO> buscaCasosEmDiversosEstados(@RequestParam String estados, @RequestParam String periodoDe, @RequestParam String periodoAte){
		GeradorRetornoFake gerador = new GeradorRetornoFake();
		String[] estadosSeparados = estados.split(",");
		List<RetornoEstadoDTO> retorno = new LinkedList<RetornoEstadoDTO>();
		for (String estadoSeparado : estadosSeparados) {
			RetornoEstadoDTO estadoDTO = gerador.montaRetornoEstadoFake();
			estadoDTO.setEstado(estadoSeparado);
			estadoDTO.setNome(gerador.recuperaNomeEstado(estadoSeparado));
			retorno.add(estadoDTO);
		}
		return retorno;
	}
	
	@GetMapping("pais/{pais}")
	public RetornoPaisDTO buscaCasosEmUmPais(@PathVariable String pais, @RequestParam String periodoDe, @RequestParam String periodoAte){
		GeradorRetornoFake gerador = new GeradorRetornoFake();
		RetornoPaisDTO retorno = gerador.montaRetornoPaisFake();
		retorno.setPais(pais);
		retorno.setNome(gerador.recuperaNomePais(pais));
		return retorno;
	}
	
	@GetMapping("pais")
	public List<RetornoPaisDTO> buscaCasosEmDiversosPaises(@RequestParam String paises, @RequestParam String periodoDe, @RequestParam String periodoAte){
		GeradorRetornoFake gerador = new GeradorRetornoFake();
		String[] paisesSeparados = paises.split(",");
		List<RetornoPaisDTO> retorno = new LinkedList<RetornoPaisDTO>();
		for (String paisSeparado : paisesSeparados) {
			RetornoPaisDTO paisDTO = gerador.montaRetornoPaisFake();
			paisDTO.setPais(paisSeparado);
			paisDTO.setNome(gerador.recuperaNomePais(paisSeparado));
			retorno.add(paisDTO);
		}
		return retorno;
	}
	
	@GetMapping("global")
	public RetornoGlobalDTO buscaCasosGlobais(){
		GeradorRetornoFake gerador = new GeradorRetornoFake();
		RetornoGlobalDTO retorno = gerador.montaRetornoGlobalFake();
		return retorno;
	}
}

