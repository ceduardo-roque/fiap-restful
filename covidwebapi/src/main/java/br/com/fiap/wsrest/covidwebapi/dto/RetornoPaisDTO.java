package br.com.fiap.wsrest.covidwebapi.dto;

import java.util.List;

public class RetornoPaisDTO {

	private String pais;
	private String nome;
	private List<OcorrenciaDiariaCovidDTO> periodos;
	private TotalPeriodoDTO totaisPeriodo;
	
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
