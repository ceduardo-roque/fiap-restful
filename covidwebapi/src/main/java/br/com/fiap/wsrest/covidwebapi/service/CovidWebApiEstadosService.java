package br.com.fiap.wsrest.covidwebapi.service;

import java.util.List;

import br.com.fiap.wsrest.covidwebapi.dto.RetornoEstadoDTO;

public interface CovidWebApiEstadosService {
	
	List<RetornoEstadoDTO> buscaSituacaoEmEstados(String estados, String de, String ate);
	RetornoEstadoDTO buscaSituacaoEmUmEstado(String estado, String de, String ate);
	
}
