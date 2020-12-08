package br.com.fiap.wsrest.covidwebapi.dto;

/**
 * DTO de retorno representnado quantos casos, óbitos e recuperados há mundialmente
 * @author Carlos Eduardo Roque da Silva
 *
 */
public class TotalPeriodoDTO {

	private long casos;
	private long mortes;
	private long recuperados;
	
	public long getCasos() {
		return casos;
	}
	public void setCasos(long casos) {
		this.casos = casos;
	}
	public long getMortes() {
		return mortes;
	}
	public void setMortes(long mortes) {
		this.mortes = mortes;
	}
	public long getRecuperados() {
		return recuperados;
	}
	public void setRecuperados(long recuperados) {
		this.recuperados = recuperados;
	}
	
	
	
}
