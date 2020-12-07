package br.com.fiap.wsrest.covidwebapi.service;

import java.util.List;

import br.com.fiap.wsrest.covidwebapi.dto.RetornoEstadoDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoGlobalDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoPaisDTO;

public interface CovidWebApiService {

	RetornoGlobalDTO buscaSituacaoGlobais();
	List<RetornoPaisDTO> buscaSituacaoPaises(String paises, String de, String ate);
	RetornoPaisDTO buscaSituacaoEmUmPais(String pais, String de, String ate);
	List<RetornoEstadoDTO> buscaSituacaoEmEstados(String estados, String de, String ate);
	RetornoEstadoDTO buscaSituacaoEmUmEstado(String estado, String de, String ate);
	
}
