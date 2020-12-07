package br.com.fiap.wsrest.covidwebapi.service.utils;

public class DadosBrasileirosCovid {
	
	private String uf;
	private String state;
	private long cases;
	private long deaths;
	private long suspects;
	private long refuses;
	
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getCases() {
		return cases;
	}
	public void setCases(long cases) {
		this.cases = cases;
	}
	public long getDeaths() {
		return deaths;
	}
	public void setDeaths(long deaths) {
		this.deaths = deaths;
	}
	public long getSuspects() {
		return suspects;
	}
	public void setSuspects(long suspects) {
		this.suspects = suspects;
	}
	public long getRefuses() {
		return refuses;
	}
	public void setRefuses(long refuses) {
		this.refuses = refuses;
	}
	
	
		
}
