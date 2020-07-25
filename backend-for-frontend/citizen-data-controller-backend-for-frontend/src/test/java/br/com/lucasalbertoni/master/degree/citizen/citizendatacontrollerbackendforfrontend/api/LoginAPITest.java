package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginCheckStatus;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.RecuperarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TrocarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service.LoginService;

@RunWith(SpringRunner.class)
public class LoginAPITest {
	
	@InjectMocks
	private LoginAPI loginAPI;
	
	@Mock
	private LoginService loginService;
	
	@Test
	public void testLogar() {
		Login login = new Login();
		LoginToken loginToken = new LoginToken();
		Mockito.when(loginService.logar(login)).thenReturn(loginToken);
		
		LoginToken loginTokenRetornado = loginAPI.logar(login);
		Assert.assertEquals(loginToken, loginTokenRetornado);
	}
	
	@Test
	public void testFindCheckStatus() {
		String cpf = "775.822.110-65";
		LoginCheckStatus loginCheckStatus = new LoginCheckStatus();
		Mockito.when(loginService.findCheckStatus(cpf)).thenReturn(loginCheckStatus);
		
		LoginCheckStatus loginCheckStatusRetornado = loginAPI.findCheckStatus(cpf);
		Assert.assertEquals(loginCheckStatus, loginCheckStatusRetornado);
	}
	
	@Test
	public void testRecuperarSenha() {
		RecuperarSenhaLogin recuperarSenha = new RecuperarSenhaLogin();
		LoginCheckStatus loginCheckStatus = new LoginCheckStatus();
		Mockito.when(loginService.recuperarSenha(recuperarSenha)).thenReturn(loginCheckStatus);
		
		LoginCheckStatus loginCheckStatusRetornado = loginAPI.recuperarSenha(recuperarSenha);
		Assert.assertEquals(loginCheckStatus, loginCheckStatusRetornado);
	}
	
	@Test
	public void testTrocarSenha() {
		TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
		LoginCheckStatus loginCheckStatus = new LoginCheckStatus();
		Mockito.when(loginService.trocarSenha(trocarSenhaLogin)).thenReturn(loginCheckStatus);
		
		LoginCheckStatus loginCheckStatusRetornado = loginAPI.trocarSenha(trocarSenhaLogin);
		Assert.assertEquals(loginCheckStatus, loginCheckStatusRetornado);
	}

}
