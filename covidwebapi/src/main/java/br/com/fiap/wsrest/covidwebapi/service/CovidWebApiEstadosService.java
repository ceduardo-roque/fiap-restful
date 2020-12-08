package br.com.fiap.wsrest.covidwebapi.service;

import java.util.List;

import br.com.fiap.wsrest.covidwebapi.dto.RetornoEstadoDTO;

/**
 * Interface Service utilizada para manipulação na Controller. Ela expõe métodos para consulta de dados da Covid-19 de estados 
 * @author Carlos Eduardo Roque da Silva
 *
 */
public interface CovidWebApiEstadosService {
	
	List<RetornoEstadoDTO> buscaSituacaoEmEstados(String estados, String de, String ate);
	RetornoEstadoDTO buscaSituacaoEmUmEstado(String estado, String de, String ate);
	
}
