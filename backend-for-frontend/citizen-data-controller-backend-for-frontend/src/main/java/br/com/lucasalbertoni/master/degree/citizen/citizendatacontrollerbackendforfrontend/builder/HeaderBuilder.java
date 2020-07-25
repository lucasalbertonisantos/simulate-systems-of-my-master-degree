package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service.AutenticadorInternoService;

@Component
public class HeaderBuilder {
	
	@Autowired
	private AutenticadorInternoService autenticadorInternoService;
	
	public HttpHeaders buildWithJsonContentType() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
	
	public HttpHeaders buildWithJsonContentTypeAndOAuthToken(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+token);
		return headers;
	}
	
	public HttpHeaders buldWithJsonContentTypeAndInternalCredentials() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+autenticadorInternoService.getToken().getToken());
		return headers;
	}

}
