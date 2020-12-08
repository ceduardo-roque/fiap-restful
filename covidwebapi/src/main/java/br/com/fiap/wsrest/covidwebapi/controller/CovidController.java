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

/**
 * Controller responsável por expor todos os métodos da API expostos nesta aplicação.
 * @author Carlos Eduardo Roque da Silva
 *
 */
@RestController
@RequestMapping("covid")
public class CovidController {
	
	//private final Logger logger = LoggerFactory.getLogger(CovidController.class);
	private CovidWebApiEstadosService coviApiEstadosService;
	private CovidWebApiPaisesService coviApiPaisesService;
	
	/**
	 * Contrutor que recebe as classes Service de consultas de dados da COVID-19 em Estados e Paises
	 * @param estadosService A instancia de classe de serviço para consulta de Estados
	 * @param paisesService A instancia de classe de serviço para consulta de Paises
	 */
	public CovidController(CovidWebApiEstadosService estadosService, CovidWebApiPaisesService paisesService) {
		this.coviApiEstadosService = estadosService;
		this.coviApiPaisesService = paisesService;
	}
	
	/**
	 * Método responsável por consultar os dados da Covid-19 em um Estado específico e em um range de datas
	 * @param estado O estado a ser consultado
	 * @param periodoDe A data inicial da consulta no formato yyyy-MM-dd
	 * @param periodoAte A data final da consulta no formato yyyy-MM-dd
	 * @return Um JSON reprentando o objeto RetornoEstadoDTO
	 */
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
	
	/**
	 * Método responsável por consultar os dados da Covid-19 em diversos Estados e em um range de datas
	 * @param estados O estados a serem consultados
	 * @param periodoDe A data inicial da consulta no formato yyyy-MM-dd
	 * @param periodoAte A data final da consulta no formato yyyy-MM-dd
	 * @return Um JSON reprentando a collection de objetos List<RetornoEstadoDTO>
	 */
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
	
	/**
	 * Método responsável por consultar os dados da Covid-19 em um País específico e em um range de datas
	 * @param pais O país a ser consultado
	 * @param periodoDe A data inicial da consulta no formato yyyy-MM-dd
	 * @param periodoAte A data final da consulta no formato yyyy-MM-dd
	 * @return Um JSON representando o objeto RetornoPaisDTO
	 */
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
	
	/**
	 * Método responsável por consultar os dados da Covid-19 em Países específico e em um range de datas
	 * @param pais Os países a serem consultado
	 * @param periodoDe A data inicial da consulta no formato yyyy-MM-dd
	 * @param periodoAte A data final da consulta no formato yyyy-MM-dd
	 * @return Um JSON representando uma collection do objeto retornado como List<RetornoPaisDTO>
	 */
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
	
	/**
	 * Método responsável por retornar a consolidação dos dados mundiais da COVID-19
	 * @return Um JSON representando o objeto RetornoGlobalDTO
	 */
	@GetMapping("global")
	public RetornoGlobalDTO buscaCasosGlobais(){
		return coviApiPaisesService.buscaSituacaoGlobais();
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

