package br.com.fiap.wsrest.covidwebapi.service;

import java.util.List;

import br.com.fiap.wsrest.covidwebapi.dto.RetornoEstadoDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoGlobalDTO;

public interface CovidWebApiEstadosService {

	RetornoGlobalDTO buscaSituacaoGlobais();
	List<RetornoEstadoDTO> buscaSituacaoEmEstados(String estados, String de, String ate);
	RetornoEstadoDTO buscaSituacaoEmUmEstado(String estado, String de, String ate);
	
}
