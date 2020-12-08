package br.com.fiap.wsrest.covidwebapi.service;

import java.util.List;

import br.com.fiap.wsrest.covidwebapi.dto.RetornoGlobalDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoPaisDTO;

public interface CovidWebApiPaisesService {

	List<RetornoPaisDTO> buscaSituacaoPaises(String paises, String de, String ate);
	RetornoPaisDTO buscaSituacaoEmUmPais(String pais, String de, String ate);
	RetornoGlobalDTO buscaSituacaoGlobais();
}
