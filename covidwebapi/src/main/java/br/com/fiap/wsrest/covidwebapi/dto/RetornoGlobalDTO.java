package br.com.fiap.wsrest.covidwebapi.dto;

import java.util.List;

public class RetornoGlobalDTO {

	private List<OcorrenciaDiariaCovidDTO> periodos;
	private TotalPeriodoDTO totaisPeriodo;
	
	public List<OcorrenciaDiariaCovidDTO> getPeriodos() {
		return periodos;
	}
	public void setPeriodos(List<OcorrenciaDiariaCovidDTO> periodos) {
		this.periodos = periodos;
	}
	public TotalPeriodoDTO getTotaisPeriodo() {
		return totaisPeriodo;
	}
	public void setTotaisPeriodo(TotalPeriodoDTO totaisPeriodo) {
		this.totaisPeriodo = totaisPeriodo;
	}
	
	
}
