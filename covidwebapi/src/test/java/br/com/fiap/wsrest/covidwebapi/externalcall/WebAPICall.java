package br.com.fiap.wsrest.covidwebapi.externalcall;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class WebAPICall {

	public static void main(String[] args) throws IOException, ParseException {
//		HttpPost post = new HttpPost("https://covid19-brazil-api.now.sh/api/report/v1/countries");
//		post.setEntity(new StringEntity(smsPayload));
//		CloseableHttpResponse httpResponse = httpclient.execute(post);
//		String response = EntityUtils.toString(httpResponse.getEntity());
//		System.out.println(response);
//	
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
		    HttpGet httpGet = new HttpGet("https://covid19-brazil-api.now.sh/api/report/v1/countries");
		    
		    try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
		        System.out.println(response1.getCode() + " " + response1.getReasonPhrase());
		        HttpEntity entity1 = response1.getEntity();
		        if(entity1!=null) {
			        System.out.println(EntityUtils.toString(entity1));	
		        	EntityUtils.consume(entity1);
			        System.out.println("Finalizado.");
		        }
		    }

		
		} 
	}
}
