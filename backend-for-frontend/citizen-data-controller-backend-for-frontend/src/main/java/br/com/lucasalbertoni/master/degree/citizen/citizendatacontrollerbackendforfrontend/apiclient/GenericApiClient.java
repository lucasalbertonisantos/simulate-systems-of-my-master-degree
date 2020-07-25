package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder.HeaderBuilder;

@Component
public class GenericApiClient {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HeaderBuilder headerBuilder;
	
	//POST
	public <T, Y> T postRequest(String url, Y body, Class<T> clazz) {
		HttpHeaders headers = headerBuilder.buildWithJsonContentType();
		HttpEntity<Y> entity = new HttpEntity<Y>(body, headers);
		return request(url, clazz, entity, HttpMethod.POST);
	}
	
	public <T, Y> T postRequest(String url, String token, Y body, Class<T> clazz) {
		HttpHeaders headers = headerBuilder.buildWithJsonContentTypeAndOAuthToken(token);
		HttpEntity<Y> entity = new HttpEntity<Y>(body, headers);
		return request(url, clazz, entity, HttpMethod.POST);
	}
	
	//PUT
	public <T, Y> T putRequest(String url, String token, Y body, Class<T> clazz) {
		HttpHeaders headers = headerBuilder.buildWithJsonContentTypeAndOAuthToken(token);
		HttpEntity<Y> entity = new HttpEntity<Y>(body, headers);
		return request(url, clazz, entity, HttpMethod.PUT);
	}
	
	public <T, Y> T putRequestWithInternalCredentials(String url, Y body, Class<T> clazz) {
		HttpHeaders headers = headerBuilder.buldWithJsonContentTypeAndInternalCredentials();
		HttpEntity<Y> entity = new HttpEntity<Y>(body, headers);
		return request(url, clazz, entity, HttpMethod.PUT);
	}
	
	public <T> T putRequestWithInternalCredentials(String url, Class<T> clazz) {
		HttpHeaders headers = headerBuilder.buldWithJsonContentTypeAndInternalCredentials();
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		return request(url, clazz, entity, HttpMethod.PUT);
	}
	
	//GET
	public <T> T getRequestWithInternalCredentials(String url, Class<T> clazz) {
		HttpHeaders headers = headerBuilder.buldWithJsonContentTypeAndInternalCredentials();
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		return request(url, clazz, entity, HttpMethod.GET);
	}
	
	//private
	private <T, Y> T request(String url, Class<T> clazz, HttpEntity<Y> entity, HttpMethod httpMethod) {
		ResponseEntity<T> resposta = restTemplate.exchange(url, httpMethod, entity, clazz);
		return resposta.getBody();
	}

}
