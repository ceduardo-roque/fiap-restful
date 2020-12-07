package br.com.fiap.wsrest.covidwebapi.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.wsrest.covidwebapi.dto.RetornoEstadoDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoGlobalDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoPaisDTO;
import br.com.fiap.wsrest.covidwebapi.fake.GeradorRetornoFake;
import br.com.fiap.wsrest.covidwebapi.service.CovidWebApiService;

@RestController
@RequestMapping("covid")
public class CovidController {
	
	//private final Logger logger = LoggerFactory.getLogger(CovidController.class);
	private CovidWebApiService coviApiService;
	
	public CovidController(CovidWebApiService service) {
		this.coviApiService = service;
	}
	
	@GetMapping("estado/{estado}")
	public ResponseEntity<RetornoEstadoDTO> buscaCasosEmUmEstado(@PathVariable String estado, @RequestParam String periodoDe, @RequestParam String periodoAte){
		RetornoEstadoDTO result = coviApiService.buscaSituacaoEmUmEstado(estado, periodoDe, periodoAte);
		if(result!=null)
			return new ResponseEntity<RetornoEstadoDTO>(result, HttpStatus.OK);
		else
			return new ResponseEntity<RetornoEstadoDTO>(result, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("estado")
	public ResponseEntity<List<RetornoEstadoDTO>> buscaCasosEmDiversosEstados(@RequestParam String estados, @RequestParam String periodoDe, @RequestParam String periodoAte){
		List<RetornoEstadoDTO> result = coviApiService.buscaSituacaoEmEstados(estados, periodoDe, periodoAte);
		
		if(result!=null)
			return new ResponseEntity<List<RetornoEstadoDTO>>(result, HttpStatus.OK);
		else
			return new ResponseEntity<List<RetornoEstadoDTO>>(result, HttpStatus.NO_CONTENT);
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
		return coviApiService.buscaSituacaoPaises(paises, periodoDe, periodoAte);
	}
	
	
	@GetMapping("global")
	public RetornoGlobalDTO buscaCasosGlobais(){
		return coviApiService.buscaSituacaoGlobais();

	}
}

