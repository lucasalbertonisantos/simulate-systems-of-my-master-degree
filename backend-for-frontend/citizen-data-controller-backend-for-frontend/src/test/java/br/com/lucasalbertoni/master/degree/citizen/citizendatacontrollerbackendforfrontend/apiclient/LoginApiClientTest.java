package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder.LoginDTOBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginCheckStatus;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TrocarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.LoginDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.TrocaSenhaLoginDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.LoginDTOToLoginCheckStatus;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.TrocarSenhaLoginToTrocaSenhaLoginDTO;

@RunWith(SpringRunner.class)
public class LoginApiClientTest {
	
	@InjectMocks
	private LoginApiClient loginApiClient;
	
	@Mock
	private GenericApiClient genericApiClient;
	
	@Mock
	private LoginDTOToLoginCheckStatus loginDTOToLoginCheckStatus;
	
	@Mock
	private LoginDTOBuilder loginDTOBuilder;
	
	@Mock
	private TrocarSenhaLoginToTrocaSenhaLoginDTO trocarSenhaLoginToTrocaSenhaLoginDTO;
	
	@Test
	public void testFindByCpf() {
		String cpf = "234.539.090-54";
		String url = "bla-666";
		ReflectionTestUtils.setField(loginApiClient, "url", url);
		
		LoginDTO loginDTO = new LoginDTO();
		Mockito.when(genericApiClient.getRequestWithInternalCredentials(url+"?cpf="+cpf, LoginDTO.class)).thenReturn(loginDTO);
		
		LoginCheckStatus loginCheckStatus = new LoginCheckStatus();
		Mockito.when(loginDTOToLoginCheckStatus.parse(loginDTO)).thenReturn(loginCheckStatus);
		
		LoginCheckStatus loginCheckStatuRetornado = loginApiClient.findByCpf(cpf);
		Assert.assertEquals(loginCheckStatus, loginCheckStatuRetornado);
	}
	
	@Test
	public void testRecuperarSenha() {
		String cpf = "234.539.090-54";
		String email = "bla@bla.com";
		String url = "bla-666";
		ReflectionTestUtils.setField(loginApiClient, "urlRecuperarSenha", url);
		
		LoginDTO loginDTO = new LoginDTO();
		Mockito.when(loginDTOBuilder.build(cpf, email)).thenReturn(loginDTO);

		LoginDTO loginDTORetornadoClientApi = new LoginDTO();
		Mockito.when(genericApiClient.putRequestWithInternalCredentials(url, loginDTO, LoginDTO.class)).thenReturn(loginDTORetornadoClientApi);
		
		LoginCheckStatus loginCheckStatus = new LoginCheckStatus();
		Mockito.when(loginDTOToLoginCheckStatus.parse(loginDTORetornadoClientApi)).thenReturn(loginCheckStatus);
		
		LoginCheckStatus loginCheckStatuRetornado = loginApiClient.recuperarSenha(cpf, email);
		Assert.assertEquals(loginCheckStatus, loginCheckStatuRetornado);
	}
	
	@Test
	public void testTrocarSenha() {
		TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
		String url = "bla-666";
		ReflectionTestUtils.setField(loginApiClient, "urlTrocarSenha", url);
		
		TrocaSenhaLoginDTO trocaSenhaLoginDTO = new TrocaSenhaLoginDTO();
		Mockito.when(trocarSenhaLoginToTrocaSenhaLoginDTO.parse(trocarSenhaLogin)).thenReturn(trocaSenhaLoginDTO);

		LoginDTO loginDTORetornadoClientApi = new LoginDTO();
		Mockito.when(genericApiClient.putRequestWithInternalCredentials(url, trocaSenhaLoginDTO, LoginDTO.class)).thenReturn(loginDTORetornadoClientApi);
		
		LoginCheckStatus loginCheckStatus = new LoginCheckStatus();
		Mockito.when(loginDTOToLoginCheckStatus.parse(loginDTORetornadoClientApi)).thenReturn(loginCheckStatus);
		
		LoginCheckStatus loginCheckStatuRetornado = loginApiClient.trocarSenha(trocarSenhaLogin);
		Assert.assertEquals(loginCheckStatus, loginCheckStatuRetornado);
	}

}
