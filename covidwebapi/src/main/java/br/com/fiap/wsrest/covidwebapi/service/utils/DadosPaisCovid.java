package br.com.fiap.wsrest.covidwebapi.service.utils;

public class DadosPaisCovid {
	
    private String Country;
    private String CountryCode;
    private String Province;
    private String City;
    private String CityCode;
    private String Lat;
    private String Lon;
    private String Confirmed;
    private long Deaths;
    private long Recovered;
    private long Active;
    private String Date;
    
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getCountryCode() {
		return CountryCode;
	}
	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}
	public String getProvince() {
		return Province;
	}
	public void setProvince(String province) {
		Province = province;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getCityCode() {
		return CityCode;
	}
	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}
	public String getLat() {
		return Lat;
	}
	public void setLat(String lat) {
		Lat = lat;
	}
	public String getLon() {
		return Lon;
	}
	public void setLon(String lon) {
		Lon = lon;
	}
	public String getConfirmed() {
		return Confirmed;
	}
	public void setConfirmed(String confirmed) {
		Confirmed = confirmed;
	}
	public long getDeaths() {
		return Deaths;
	}
	public void setDeaths(long deaths) {
		Deaths = deaths;
	}
	public long getRecovered() {
		return Recovered;
	}
	public void setRecovered(long recovered) {
		Recovered = recovered;
	}
	public long getActive() {
		return Active;
	}
	public void setActive(long active) {
		Active = active;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
    
		
}
