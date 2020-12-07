package br.com.fiap.wsrest.covidwebapi.service;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultWebApiService {

	private final Logger logger = LoggerFactory.getLogger(DefaultWebApiService.class);
	
	public String invokeWebApi(String url) {
		
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
		    HttpGet httpGet = new HttpGet(url);
		    
		    try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
		        if(response1.getCode() == HttpStatus.SC_OK) {
			        HttpEntity entity1 = response1.getEntity();
			        if(entity1!=null) {
				        String result = EntityUtils.toString(entity1);	
			        	EntityUtils.consume(entity1);
				        return result;
			        }
		        } else {
		        	logger.info("Retorno da API diferente de 200 - OK. Http Status Code retornado: " + response1.getCode() + " - " + response1.getReasonPhrase() );
		        }
		    } catch (ParseException e) {
				logger.error("Erro ao invocar a API em: " + url + ". Não foi possível converter o resultado para String/JSON", e);
			}
		} catch (IOException e) {
			logger.error("Erro ao invocar a API em: " + url + ". Não foi possível inovcar a URL no caminho invocado.", e);
		} 
		return null;
	}
	
	
	
}
