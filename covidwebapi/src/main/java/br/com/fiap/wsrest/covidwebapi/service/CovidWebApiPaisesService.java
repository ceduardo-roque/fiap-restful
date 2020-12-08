package br.com.fiap.wsrest.covidwebapi.service;

import java.util.List;

import br.com.fiap.wsrest.covidwebapi.dto.RetornoGlobalDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoPaisDTO;

/**
 * Interface Service utilizada para manipulação na Controller. Ela expõe métodos para consulta de dados da Covid-19 de países 
 * @author Carlos Eduardo Roque da Silva
 *
 */
public interface CovidWebApiPaisesService {

	List<RetornoPaisDTO> buscaSituacaoPaises(String paises, String de, String ate);
	RetornoPaisDTO buscaSituacaoEmUmPais(String pais, String de, String ate);
	RetornoGlobalDTO buscaSituacaoGlobais();
}
