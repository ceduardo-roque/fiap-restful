package br.com.fiap.wsrest.covidwebapi.fake;

import java.util.LinkedList;
import java.util.List;

import br.com.fiap.wsrest.covidwebapi.dto.OcorrenciaDiariaCovidDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoEstadoDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoGlobalDTO;
import br.com.fiap.wsrest.covidwebapi.dto.RetornoPaisDTO;
import br.com.fiap.wsrest.covidwebapi.dto.TotalPeriodoDTO;

public class GeradorRetornoFake {

	public RetornoGlobalDTO montaRetornoGlobalFake() {
		RetornoGlobalDTO retornoGlobal = new RetornoGlobalDTO();
		retornoGlobal.setPeriodos(criaPeriodosFake());
		retornoGlobal.setTotaisPeriodo(criaTotaisPeriodoFake());
		return retornoGlobal;
	}
	
	public RetornoEstadoDTO montaRetornoEstadoFake() {
		RetornoEstadoDTO retornoEstado = new RetornoEstadoDTO();
		retornoEstado.setPeriodos(criaPeriodosFake());
		retornoEstado.setTotaisPeriodo(criaTotaisPeriodoFake());
		return retornoEstado;
	}

	private TotalPeriodoDTO criaTotaisPeriodoFake() {
		TotalPeriodoDTO total = new TotalPeriodoDTO();
		total.setCasos(3702);
		total.setMortes(963);
		total.setRecuperados(3369);
		return total;
	}

	private List<OcorrenciaDiariaCovidDTO> criaPeriodosFake() {
		OcorrenciaDiariaCovidDTO ocorrencia1 = criaOcorrencia1();
		OcorrenciaDiariaCovidDTO ocorrencia2 = criaOcorrencia1();
		OcorrenciaDiariaCovidDTO ocorrencia3 = criaOcorrencia1();
		ocorrencia1.setData("2020-11-26");
		ocorrencia2.setData("2020-11-25");
		ocorrencia3.setData("2020-11-24");
		List<OcorrenciaDiariaCovidDTO> listaRetorno = new LinkedList<OcorrenciaDiariaCovidDTO>();
	    listaRetorno.add(ocorrencia1);
		listaRetorno.add(ocorrencia2);
		listaRetorno.add(ocorrencia3);
		return listaRetorno;
	}

	private OcorrenciaDiariaCovidDTO criaOcorrencia1() {
		OcorrenciaDiariaCovidDTO ocorrencia = new OcorrenciaDiariaCovidDTO();
		ocorrencia.setCasos(1234);
		ocorrencia.setMortes(321);
		ocorrencia.setRecuperados(1123);
		return ocorrencia;
	}

	public String recuperaNomeEstado(String estado) {
		switch (estado) {
		case "SP":
			return "São Paulo";
		case "RJ":
			return "Rio de Janeiro";
		case "MG":
			return "Minas Gerais";			
		case "ES":
			return "Espirito Santo";			
		default:
			return "Estado Brasileiro";
		}
	}

	public RetornoPaisDTO montaRetornoPaisFake() {
		RetornoPaisDTO retornoEstado = new RetornoPaisDTO();
		retornoEstado.setPeriodos(criaPeriodosFake());
		retornoEstado.setTotaisPeriodo(criaTotaisPeriodoFake());
		return retornoEstado;
	}

	public String recuperaNomePais(String pais) {
		switch (pais) {
		case "italy":
			return "Itália";
		case "brazil":
			return "Brasil";
		case "germany":
			return "Alemanha";			
		case "canada":
			return "Canada";			
		default:
			return "País Mundial";
		}
	}

}
