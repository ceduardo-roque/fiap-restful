package br.com.fiap.wsrest.covidwebapi.service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.fiap.wsrest.covidwebapi.dto.OcorrenciaDiariaCovidDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoPaisDTO;
import br.com.fiap.wsrest.covidwebapi.dto.TotalPeriodoDTO;
import br.com.fiap.wsrest.covidwebapi.service.utils.DadosPaisCovid;

@Service
public class CovidWebApiPaisesServiceImpl extends DefaultWebApiService implements CovidWebApiPaisesService {

	private final Logger logger = LoggerFactory.getLogger(CovidWebApiPaisesServiceImpl.class);
	
	@Value("${covid.api.url.data-especifica-pais}")
	private String covidUrlApiPaisDataEspecifica; // OK
	
	@Override
	public RetornoPaisDTO buscaSituacaoEmUmPais(String pais, String de, String ate) {

		try {
			// Converte data
			LocalDate dataInicial = criaLocalDateAPartirDaDataPassada(de);
			LocalDate dataFinal = criaLocalDateAPartirDaDataPassada(ate);
			
			List<OcorrenciaDiariaCovidDTO> listaOcorrenciasNoPais = consultaOcorrenciaDiariaPaisCovid(pais, dataInicial, dataFinal);
			if(listaOcorrenciasNoPais!=null && !listaOcorrenciasNoPais.isEmpty()) {
				RetornoPaisDTO retorno = new RetornoPaisDTO();
				retorno.setPeriodos(listaOcorrenciasNoPais);
				String nomePais = recuperaNomePais(listaOcorrenciasNoPais);
				retorno.setPais(pais);
				retorno.setNome(nomePais);
				retorno.setTotaisPeriodo(consolidaTotaisPais(listaOcorrenciasNoPais));
				return retorno;
			}
		} catch (DateTimeParseException e) {
			logger.error("Data informada em formato invalido. ", e);
		}

		return null;
	}
	
	@Override
	public List<RetornoPaisDTO> buscaSituacaoPaises(String paises, String de, String ate) {
		String[] paisesQuebrados = paises.split(",");
		List<RetornoPaisDTO> listaRetorno = new LinkedList<RetornoPaisDTO>();
		for (String pais : paisesQuebrados) {
			RetornoPaisDTO paisConsolidado = buscaSituacaoEmUmPais(pais, de, ate);
			if(paisConsolidado!=null)
				listaRetorno.add(paisConsolidado);
		}
		if(listaRetorno.isEmpty())
			return null;
		return listaRetorno;
	}
	
	private List<OcorrenciaDiariaCovidDTO> consultaOcorrenciaDiariaPaisCovid(String pais, LocalDate diaInicial, LocalDate diaFinal) {
		String url = covidUrlApiPaisDataEspecifica + pais + 
				"?from=" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(diaInicial) + "T00:00:00Z" +
				"&to="   + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(diaFinal) + "T00:00:00Z";
		
		Type collectionType = new TypeToken<Collection<DadosPaisCovid>>(){}.getType();
		Collection<DadosPaisCovid> dadosPais = new Gson().fromJson(super.invokeWebApi(url), collectionType);

		Map<String, OcorrenciaDiariaCovidDTO> mapaAgrupadoPorData = new HashMap<String, OcorrenciaDiariaCovidDTO>();

		
		List<OcorrenciaDiariaCovidDTO> listaRetorno = new LinkedList<OcorrenciaDiariaCovidDTO>();
		if(dadosPais!=null) {
			for (DadosPaisCovid dadosPaisCovid : dadosPais) {
				OcorrenciaDiariaCovidDTO ocorrencia = null;

				if(mapaAgrupadoPorData.containsKey(dadosPaisCovid.getDate()))
					ocorrencia = mapaAgrupadoPorData.get(dadosPaisCovid.getDate());
				else {
					ocorrencia = new OcorrenciaDiariaCovidDTO();
					mapaAgrupadoPorData.put(dadosPaisCovid.getDate(), ocorrencia);
					listaRetorno.add(ocorrencia);
				}
				ocorrencia.setCasos(Long.valueOf(dadosPaisCovid.getConfirmed()) + ocorrencia.getCasos());
				ocorrencia.setMortes(dadosPaisCovid.getDeaths() + ocorrencia.getMortes());
				ocorrencia.setRecuperados(dadosPaisCovid.getRecovered() + ocorrencia.getRecuperados());	
				ocorrencia.setPossuiDados(true);
				ocorrencia.setPais(dadosPaisCovid.getCountry());
				ocorrencia.setData(dadosPaisCovid.getDate().substring(0, 10));
			}
		}
		return listaRetorno;
	}
	

	private String recuperaNomePais(List<OcorrenciaDiariaCovidDTO> listaOcorrenciasNoPais) {
		for (OcorrenciaDiariaCovidDTO ocorrenciaDiariaCovidDTO : listaOcorrenciasNoPais) {
			if(ocorrenciaDiariaCovidDTO.isPossuiDados())
				return ocorrenciaDiariaCovidDTO.getPais();
		}

		return "Não informado";
	}

	private TotalPeriodoDTO consolidaTotaisPais(List<OcorrenciaDiariaCovidDTO> listaOcorrenciasNoPais) {
		long totalCasos = 0;
		long totalMortes = 0;
		long totalRecuperados = 0;

		OcorrenciaDiariaCovidDTO primeiraOcorrenciaNoPeriodo = listaOcorrenciasNoPais.get(0);
		OcorrenciaDiariaCovidDTO ultimaOcorrenciaNoPeriodo = listaOcorrenciasNoPais.get(listaOcorrenciasNoPais.size()-1);
		totalCasos = ultimaOcorrenciaNoPeriodo.getCasos() - primeiraOcorrenciaNoPeriodo.getCasos();
		totalMortes = ultimaOcorrenciaNoPeriodo.getMortes() - primeiraOcorrenciaNoPeriodo.getMortes();
		totalRecuperados = ultimaOcorrenciaNoPeriodo.getRecuperados() - primeiraOcorrenciaNoPeriodo.getRecuperados();
		
		TotalPeriodoDTO totalPeriodoDTO = new TotalPeriodoDTO();
		totalPeriodoDTO.setCasos(totalCasos);
		totalPeriodoDTO.setMortes(totalMortes);
		totalPeriodoDTO.setRecuperados(totalRecuperados);
		return totalPeriodoDTO;		

	}

	private TotalPeriodoDTO consolidaTotalPeriodo(OcorrenciaDiariaCovidDTO primeiraOcorrenciaComValores, OcorrenciaDiariaCovidDTO ultimaOcorrenciaComValores) {
		long totalCasos = ultimaOcorrenciaComValores.getCasos() - primeiraOcorrenciaComValores.getCasos();
		long totalMortes = ultimaOcorrenciaComValores.getMortes() - primeiraOcorrenciaComValores.getMortes();
		long totalRecuperados = ultimaOcorrenciaComValores.getRecuperados() - primeiraOcorrenciaComValores.getRecuperados();
		TotalPeriodoDTO totalPeriodoDTO = new TotalPeriodoDTO();
		totalPeriodoDTO.setCasos(totalCasos);
		totalPeriodoDTO.setMortes(totalMortes);
		totalPeriodoDTO.setRecuperados(totalRecuperados);
		return totalPeriodoDTO;
	}



	
}
