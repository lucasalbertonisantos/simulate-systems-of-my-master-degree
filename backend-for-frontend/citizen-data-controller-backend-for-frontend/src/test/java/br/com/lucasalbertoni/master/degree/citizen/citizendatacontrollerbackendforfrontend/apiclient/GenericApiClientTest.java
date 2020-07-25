package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder.HeaderBuilder;

@RunWith(SpringRunner.class)
public class GenericApiClientTest {
	
	@InjectMocks
	private GenericApiClient genericApiClient;
	
	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private HeaderBuilder headerBuilder;
	
	private String url;
	private String token;
	private TestDTO body;
	
	@Before
	public void init() {
		url = "bla-666";
		token = "bla-token";
		body = new TestDTO();
	}
	
	@Test
	public void testPostRequest() {
		ReturnTestDTO returnTestDTO = mockRequest(HttpMethod.POST);
		
		ReturnTestDTO returnTestDTORetornado = genericApiClient.postRequest(url, body, ReturnTestDTO.class);
		Assert.assertEquals(returnTestDTO, returnTestDTORetornado);
	}
	
	@Test
	public void testPostRequestToken() {
		ReturnTestDTO returnTestDTO = mockRequestToken(HttpMethod.POST);
		
		ReturnTestDTO returnTestDTORetornado = genericApiClient.postRequest(url, token, body, ReturnTestDTO.class);
		Assert.assertEquals(returnTestDTO, returnTestDTORetornado);
	}
	
	@Test
	public void testPutRequestToken() {
		ReturnTestDTO returnTestDTO = mockRequestToken(HttpMethod.PUT);
		
		ReturnTestDTO returnTestDTORetornado = genericApiClient.putRequest(url, token, body, ReturnTestDTO.class);
		Assert.assertEquals(returnTestDTO, returnTestDTORetornado);
	}
	
	@Test
	public void testPutPutRequestWithInternalCredentials() {
		ReturnTestDTO returnTestDTO = mockRequestWithInternalCredentials(HttpMethod.PUT);
		
		ReturnTestDTO returnTestDTORetornado = genericApiClient.putRequestWithInternalCredentials(url, body, ReturnTestDTO.class);
		Assert.assertEquals(returnTestDTO, returnTestDTORetornado);
	}
	
	@Test
	public void testPutPutRequestWithInternalCredentialsWithoutBody() {
		ReturnTestDTO returnTestDTO = mockRequestWithInternalCredentialsWithoudBody(HttpMethod.PUT);
		
		ReturnTestDTO returnTestDTORetornado = genericApiClient.putRequestWithInternalCredentials(url, ReturnTestDTO.class);
		Assert.assertEquals(returnTestDTO, returnTestDTORetornado);
	}
	
	@Test
	public void testGetRequestWithInternalCredentialsWithoutBody() {
		ReturnTestDTO returnTestDTO = mockRequestWithInternalCredentialsWithoudBody(HttpMethod.GET);
		
		ReturnTestDTO returnTestDTORetornado = genericApiClient.getRequestWithInternalCredentials(url, ReturnTestDTO.class);
		Assert.assertEquals(returnTestDTO, returnTestDTORetornado);
	}
	
	
	//MOCKs
	private ReturnTestDTO mockRequestWithInternalCredentialsWithoudBody(HttpMethod httpMethod) {
		HttpHeaders headers = mockBuldWithJsonContentTypeAndInternalCredentials();
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);

		return mockRestTemplate(url, httpMethod, entity);
	}
	
	private ReturnTestDTO mockRequestWithInternalCredentials(HttpMethod httpMethod) {
		HttpHeaders headers = mockBuldWithJsonContentTypeAndInternalCredentials();
		HttpEntity<TestDTO> entity = new HttpEntity<TestDTO>(body, headers);

		return mockRestTemplate(url, httpMethod, entity);
	}
	
	private ReturnTestDTO mockRequest(HttpMethod httpMethod) {
		HttpHeaders headers = mockBuildWithJsonContentType();
		HttpEntity<TestDTO> entity = new HttpEntity<TestDTO>(body, headers);

		return mockRestTemplate(url, httpMethod, entity);
	}
	
	private ReturnTestDTO mockRequestToken(HttpMethod httpMethod) {
		HttpHeaders headers = mockBuildWithJsonContentTypeAndOAuthToken(token);
		Mockito.when(headerBuilder.buildWithJsonContentTypeAndOAuthToken(token)).thenReturn(headers);
		HttpEntity<TestDTO> entity = new HttpEntity<TestDTO>(body, headers);
		return mockRestTemplate(url, httpMethod, entity);
	}
	
	private HttpHeaders mockBuldWithJsonContentTypeAndInternalCredentials() {
		HttpHeaders headers = new HttpHeaders();
		Mockito.when(headerBuilder.buldWithJsonContentTypeAndInternalCredentials()).thenReturn(headers);
		return headers;
	}
	
	private HttpHeaders mockBuildWithJsonContentType() {
		HttpHeaders headers = new HttpHeaders();
		Mockito.when(headerBuilder.buildWithJsonContentType()).thenReturn(headers);
		return headers;
	}
	
	private HttpHeaders mockBuildWithJsonContentTypeAndOAuthToken(String token) {
		HttpHeaders headers = new HttpHeaders();
		Mockito.when(headerBuilder.buildWithJsonContentTypeAndOAuthToken(token)).thenReturn(headers);
		return headers;
	}
	
	private <T> ReturnTestDTO mockRestTemplate(String url, HttpMethod httpMethod, HttpEntity<T> entity) {
		ReturnTestDTO returnTestDTO = new ReturnTestDTO();
		ResponseEntity<ReturnTestDTO> response = ResponseEntity.ok(returnTestDTO);
		Mockito.when(restTemplate.exchange(url, httpMethod, entity, ReturnTestDTO.class)).thenReturn(response);
		return returnTestDTO;
	}

}
