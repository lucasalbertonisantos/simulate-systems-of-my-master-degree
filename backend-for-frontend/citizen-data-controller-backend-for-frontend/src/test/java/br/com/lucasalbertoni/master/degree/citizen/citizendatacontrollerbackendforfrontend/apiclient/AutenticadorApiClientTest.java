package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder.AutenticacaoDTOBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginTokenInterno;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.AutenticacaoDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.TokenDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.TokenDTOToLoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.TokenDTOToLoginTokenInterno;

@RunWith(SpringRunner.class)
public class AutenticadorApiClientTest {
	
	@InjectMocks
	private AutenticadorApiClient autenticadorApiClient;
	
	@Mock
	private GenericApiClient genericApiClient;
	
	@Mock
	private TokenDTOToLoginToken tokenDTOToLoginToken;
	
	@Mock
	private TokenDTOToLoginTokenInterno tokenDTOToLoginTokenInterno;
	
	@Mock
	private AutenticacaoDTOBuilder autenticacaoDTOBuilder;
	
	private String usuario = "bla";
	private String senha = "bla666";
	private String url = "http://auth";
	
	@Before
	public void init() {
		ReflectionTestUtils.setField(autenticadorApiClient, "url", url);
	}
	
	@Test
	public void testFindByUsuarioSenha() {
		String urlCidadao = url+"?perfil=CIDADAO";
		
		TokenDTO tokenDTO = authMock(usuario, senha, urlCidadao);
		LoginToken loginToken = new LoginToken();
		Mockito.when(tokenDTOToLoginToken.parse(tokenDTO)).thenReturn(loginToken);
		
		LoginToken loginTokenRetornado = autenticadorApiClient.findByUsuarioSenha(usuario, senha);
		Assert.assertEquals(loginToken, loginTokenRetornado);
	}
	
	@Test
	public void testFindByUsuarioSenhaInterno() {
		TokenDTO tokenDTO = authMock(usuario, senha, url);
		LoginTokenInterno loginTokenInterno = new LoginTokenInterno();
		Mockito.when(tokenDTOToLoginTokenInterno.parse(tokenDTO)).thenReturn(loginTokenInterno);
		
		LoginTokenInterno loginTokenInternoRetornado = autenticadorApiClient.findByUsuarioSenhaInterno(usuario, senha);
		Assert.assertEquals(loginTokenInterno, loginTokenInternoRetornado);
	}
	
	private TokenDTO authMock(String usuario, String senha, String url) {
		AutenticacaoDTO autenticacaoDTO = new AutenticacaoDTO();
		Mockito.when(autenticacaoDTOBuilder.build(usuario, senha)).thenReturn(autenticacaoDTO);
		
		TokenDTO tokenDTO = new TokenDTO();
		Mockito.when(genericApiClient.postRequest(url, autenticacaoDTO, TokenDTO.class)).thenReturn(tokenDTO);
		
		return tokenDTO;
	}

}
