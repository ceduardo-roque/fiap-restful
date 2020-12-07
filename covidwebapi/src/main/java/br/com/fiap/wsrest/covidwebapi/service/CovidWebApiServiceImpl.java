package br.com.fiap.wsrest.covidwebapi.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.fiap.wsrest.covidwebapi.dto.OcorrenciaDiariaCovidDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoEstadoDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoGlobalDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoPaisDTO;
import br.com.fiap.wsrest.covidwebapi.dto.TotalPeriodoDTO;
import br.com.fiap.wsrest.covidwebapi.service.utils.ConsultaBrasilNoDia;
import br.com.fiap.wsrest.covidwebapi.service.utils.DadosBrasileirosCovid;

@Service
public class CovidWebApiServiceImpl extends DefaultWebApiService implements CovidWebApiService {

	private final Logger logger = LoggerFactory.getLogger(CovidWebApiServiceImpl.class);
	
	@Value("${covid.api.url.status-api}")
	private String covidUrlApiStatus; // OK
	
	@Value("${covid.api.url.summary}")	
	private String covidUrlApiStatusWorldSummary; // OK

	@Value("${covid.api.url.data-especifica-brasil}")	
	private String covidUrlApiBrasilDataEspecifica; // OK
	
	
	@Override
	public List<RetornoEstadoDTO> buscaSituacaoEmEstados(String estados, String de, String ate) {
		try {
			// Converte data
			LocalDate dataInicial = criaLocalDateAPartirDaDataPassada(de);
			LocalDate dataFinal = criaLocalDateAPartirDaDataPassada(ate);
			LocalDate diaParaConsulta = ChronoUnit.DAYS.addTo(dataInicial, 0);
			
			List<RetornoEstadoDTO> listaRetorno = new LinkedList<RetornoEstadoDTO>();
			
			OcorrenciaDiariaCovidDTO primeiraOcorrenciaComValores = null;
			OcorrenciaDiariaCovidDTO ultimaOcorrenciaComValores = null;
			String[] estadoParaPesquisar = estados.split(",");
			for (String estado : estadoParaPesquisar) {
				RetornoEstadoDTO retorno = new RetornoEstadoDTO();
				while(!diaParaConsulta.isAfter(dataFinal)) {
					OcorrenciaDiariaCovidDTO ocorrencia = consultaOcorrenciaDiariaCovid(estado, diaParaConsulta);
					if(ocorrencia.isPossuiDados() && primeiraOcorrenciaComValores==null) {
						primeiraOcorrenciaComValores = ocorrencia;
					}
					if(ocorrencia.isPossuiDados()) 
						ultimaOcorrenciaComValores = ocorrencia;
					diaParaConsulta = ChronoUnit.DAYS.addTo(diaParaConsulta, 1); // Adiciona um dia a mais para nova consulta	
					retorno.getPeriodos().add(ocorrencia);
				}
				if(primeiraOcorrenciaComValores != null && ultimaOcorrenciaComValores!=null) {
					TotalPeriodoDTO totalPeriodoDTO = consolidaTotalPeriodo(primeiraOcorrenciaComValores, ultimaOcorrenciaComValores);
					retorno.setTotaisPeriodo(totalPeriodoDTO);
					retorno.setEstado(primeiraOcorrenciaComValores.getUf());
					retorno.setNome(primeiraOcorrenciaComValores.getEstado());
					listaRetorno.add(retorno);
				}
				primeiraOcorrenciaComValores = null;
				ultimaOcorrenciaComValores = null;
				diaParaConsulta = ChronoUnit.DAYS.addTo(dataInicial, 0);
			}
			if(listaRetorno.isEmpty())
				return null;
			return listaRetorno;
		} catch (DateTimeParseException e) {
			logger.error("Data informada em formato invalido. ", e);
		}
		return null;
	}

	@Override
	public RetornoEstadoDTO buscaSituacaoEmUmEstado(String estado, String de, String ate) {

		try {
			// Converte data
			LocalDate dataInicial = criaLocalDateAPartirDaDataPassada(de);
			LocalDate dataFinal = criaLocalDateAPartirDaDataPassada(ate);
			LocalDate diaParaConsulta = ChronoUnit.DAYS.addTo(dataInicial, 0);
			
			RetornoEstadoDTO retorno = new RetornoEstadoDTO();
			
			OcorrenciaDiariaCovidDTO primeiraOcorrenciaComValores = null;
			OcorrenciaDiariaCovidDTO ultimaOcorrenciaComValores = null;
			while(!diaParaConsulta.isAfter(dataFinal)) {
				OcorrenciaDiariaCovidDTO ocorrencia = consultaOcorrenciaDiariaCovid(estado, diaParaConsulta);
				if(ocorrencia.isPossuiDados() && primeiraOcorrenciaComValores==null) {
					primeiraOcorrenciaComValores = ocorrencia;
				}
				if(ocorrencia.isPossuiDados()) 
					ultimaOcorrenciaComValores = ocorrencia;
				diaParaConsulta = ChronoUnit.DAYS.addTo(diaParaConsulta, 1); // Adiciona um dia a mais para nova consulta	
				
				retorno.getPeriodos().add(ocorrencia);
			}
			if(primeiraOcorrenciaComValores != null && ultimaOcorrenciaComValores!=null) {
				TotalPeriodoDTO totalPeriodoDTO = consolidaTotalPeriodo(primeiraOcorrenciaComValores, ultimaOcorrenciaComValores);
				retorno.setTotaisPeriodo(totalPeriodoDTO);
				retorno.setEstado(primeiraOcorrenciaComValores.getUf());
				retorno.setNome(primeiraOcorrenciaComValores.getEstado());
				return retorno;				
			}
		} catch (DateTimeParseException e) {
			logger.error("Data informada em formato invalido. ", e);
		}

		return null;
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

	private OcorrenciaDiariaCovidDTO consultaOcorrenciaDiariaCovid(String estado, LocalDate diaParaConsulta) {
		String url = covidUrlApiBrasilDataEspecifica + DateTimeFormatter.ofPattern("yyyyMMdd").format(diaParaConsulta);
		// Invoca a API de consulta neste dia
		ConsultaBrasilNoDia brasilNumDia = new Gson().fromJson(super.invokeWebApi(url), ConsultaBrasilNoDia.class);
		OcorrenciaDiariaCovidDTO ocorrencia = new OcorrenciaDiariaCovidDTO();
		ocorrencia.setData(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(diaParaConsulta));
		for (DadosBrasileirosCovid dados : brasilNumDia.getData()) {
			if(dados.getUf().toUpperCase().equals(estado.toUpperCase())) {
				ocorrencia.setCasos(dados.getCases());
				ocorrencia.setMortes(dados.getDeaths());
				ocorrencia.setRecuperados(dados.getRefuses());	
				ocorrencia.setPossuiDados(true);
				ocorrencia.setUf(dados.getUf().toUpperCase());
				ocorrencia.setEstado(dados.getState().toUpperCase());
				return ocorrencia;
			}
		}
		return ocorrencia;
	}

	private LocalDate criaLocalDateAPartirDaDataPassada(String dataPassada) {
		return LocalDate.parse(dataPassada, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	@Override
	public RetornoPaisDTO buscaSituacaoEmUmPais(String pais, String de, String ate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetornoGlobalDTO buscaSituacaoGlobais()  {
				// Get Summary from World Data API 
//				Global global = new Gson().fromJson(super.invokeWebApi(covidUrlApiStatusWorldSummary), Global.class);
				
				// Generate DTO to return
//				RetornoGlobalDTO result = new RetornoGlobalDTO();
//				
//				// Create internal DTO 
//				TotalPeriodoDTO totaisPeriodo = new TotalPeriodoDTO();
//				totaisPeriodo.setCasos(global.getGlobal().getTotalConfirmed());
//				totaisPeriodo.setMortes(global.getGlobal().getTotalDeaths());
//				totaisPeriodo.setRecuperados(global.getGlobal().getNewRecovered());
//				
//				result.setTotaisPeriodo(totaisPeriodo);
//				return result;

		return null;
	}

	@Override
	public List<RetornoPaisDTO> buscaSituacaoPaises(String paises, String de, String ate) {
//		if(validaStatusDaApi()) {		
//			// Gather data from a specific country in a range of dates 
//			
//			String url = covidUrlApiPaisEspecifico + 
//					"brazil?from="+de+"T00:00:00Z&to="+ate+"T00:00:00Z";
//			
//			OcorrenciaPais paisPorPeriodo = new Gson().fromJson(super.invokeWebApi(url), OcorrenciaPais.class);
//			System.out.println(paisPorPeriodo);
//			
//		} else {
//			logger.error("Não foi possível recuperar os dados da API. Covid Web API indisponível!");
//		}
		return null;
	}

	
}
