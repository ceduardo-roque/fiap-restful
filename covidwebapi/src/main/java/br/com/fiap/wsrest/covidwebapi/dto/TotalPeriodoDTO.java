package br.com.fiap.wsrest.covidwebapi.dto;

public class TotalPeriodoDTO {

	private int casos;
	private int mortes;
	private int recuperados;
	
	public int getCasos() {
		return casos;
	}
	public void setCasos(int casos) {
		this.casos = casos;
	}
	public int getMortes() {
		return mortes;
	}
	public void setMortes(int mortes) {
		this.mortes = mortes;
	}
	public int getRecuperados() {
		return recuperados;
	}
	public void setRecuperados(int recuperados) {
		this.recuperados = recuperados;
	}
	
	
	
}
