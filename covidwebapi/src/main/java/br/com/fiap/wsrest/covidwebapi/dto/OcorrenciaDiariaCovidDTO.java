package br.com.fiap.wsrest.covidwebapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO de retorno de uma ocorrencia diária da COVID-19
 * @author Carlos Eduardo Roque da Silva
 *
 */
@JsonIgnoreProperties(value = { "possuiDados", "estado", "uf", "slug", "pais" })
public class OcorrenciaDiariaCovidDTO {

	private long casos;
	private long mortes;
	private long recuperados;
	private String data;
	private boolean possuiDados;
	private String estado;
	private String uf;
	private String slug;
	private String pais;
	
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public boolean isPossuiDados() {
		return possuiDados;
	}
	public void setPossuiDados(boolean possuiDados) {
		this.possuiDados = possuiDados;
	}
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	
}
