package br.com.fiap.wsrest.covidwebapi.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import br.com.fiap.wsrest.covidwebapi.service.CovidWebApiEstadosService;
import br.com.fiap.wsrest.covidwebapi.service.CovidWebApiPaisesService;

@RestController
@RequestMapping("covid")
public class CovidController {
	
	//private final Logger logger = LoggerFactory.getLogger(CovidController.class);
	private CovidWebApiEstadosService coviApiEstadosService;
	private CovidWebApiPaisesService coviApiPaisesService;
	
	public CovidController(CovidWebApiEstadosService estadosService, CovidWebApiPaisesService paisesService) {
		this.coviApiEstadosService = estadosService;
		this.coviApiPaisesService = paisesService;
	}
	
	@GetMapping("estado/{estado}")
	public ResponseEntity<RetornoEstadoDTO> buscaCasosEmUmEstado(@PathVariable String estado, @RequestParam String periodoDe, @RequestParam String periodoAte){
		RetornoEstadoDTO result = null;
		
		if(!dataValida(periodoDe) || !(dataValida(periodoAte)))
			return new ResponseEntity<RetornoEstadoDTO>(result, HttpStatus.BAD_REQUEST); 
		
		result = coviApiEstadosService.buscaSituacaoEmUmEstado(estado, periodoDe, periodoAte);
		
		if(result!=null)
			return new ResponseEntity<RetornoEstadoDTO>(result, HttpStatus.OK);
		else
			return new ResponseEntity<RetornoEstadoDTO>(result, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("estado")
	public ResponseEntity<List<RetornoEstadoDTO>> buscaCasosEmDiversosEstados(@RequestParam String estados, @RequestParam String periodoDe, @RequestParam String periodoAte){
		List<RetornoEstadoDTO> result = null;
		
		if(!dataValida(periodoDe) || !(dataValida(periodoAte)))
			return new ResponseEntity<List<RetornoEstadoDTO>>(result, HttpStatus.BAD_REQUEST); 
		
		result = coviApiEstadosService.buscaSituacaoEmEstados(estados, periodoDe, periodoAte);
		
		if(result!=null)
			return new ResponseEntity<List<RetornoEstadoDTO>>(result, HttpStatus.OK);
		else
			return new ResponseEntity<List<RetornoEstadoDTO>>(result, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("pais/{pais}")
	public ResponseEntity<RetornoPaisDTO> buscaCasosEmUmPais(@PathVariable String pais, @RequestParam String periodoDe, @RequestParam String periodoAte){
		RetornoPaisDTO result = null;
		
		if(!dataValida(periodoDe) || !(dataValida(periodoAte)))
			return new ResponseEntity<RetornoPaisDTO>(result, HttpStatus.BAD_REQUEST); 
		
		result = coviApiPaisesService.buscaSituacaoEmUmPais(pais, periodoDe, periodoAte);
		
		if(result!=null)
			return new ResponseEntity<RetornoPaisDTO>(result, HttpStatus.OK);
		else
			return new ResponseEntity<RetornoPaisDTO>(result, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("pais")
	public ResponseEntity<List<RetornoPaisDTO>> buscaCasosEmDiversosPaises(@RequestParam String paises, @RequestParam String periodoDe, @RequestParam String periodoAte){
		List<RetornoPaisDTO> result = null;
		if(!dataValida(periodoDe) || !(dataValida(periodoAte)))
			return new ResponseEntity<List<RetornoPaisDTO>>(result, HttpStatus.BAD_REQUEST); 

		result = coviApiPaisesService.buscaSituacaoPaises(paises, periodoDe, periodoAte);
		
		if(result!=null)
			return new ResponseEntity<List<RetornoPaisDTO>>(result, HttpStatus.OK);
		else
			return new ResponseEntity<List<RetornoPaisDTO>>(result, HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("global")
	public RetornoGlobalDTO buscaCasosGlobais(){
		return coviApiEstadosService.buscaSituacaoGlobais();

	}
	
    private boolean dataValida(String dateStr) {
        try {
        	LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
	
}

