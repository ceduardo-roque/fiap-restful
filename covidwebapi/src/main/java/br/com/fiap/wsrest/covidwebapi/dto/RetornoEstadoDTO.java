package br.com.fiap.wsrest.covidwebapi.dto;

import java.util.LinkedList;
import java.util.List;

public class RetornoEstadoDTO {

	private String estado;
	private String nome;
	private List<OcorrenciaDiariaCovidDTO> periodos = new LinkedList<OcorrenciaDiariaCovidDTO>();
	private TotalPeriodoDTO totaisPeriodo = new TotalPeriodoDTO();
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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
