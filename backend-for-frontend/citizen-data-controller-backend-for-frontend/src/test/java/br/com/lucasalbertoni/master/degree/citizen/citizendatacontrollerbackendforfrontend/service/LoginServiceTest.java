package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.AutenticadorApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.LoginApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.RecuperarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TrocarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.LoginCheckStatusRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.LoginTokenRepository;

@RunWith(SpringRunner.class)
public class LoginServiceTest {
	
	@InjectMocks
	private LoginService loginService;
	
	@Mock
	private LoginTokenRepository loginTokenRepository;
	
	@Mock
	private LoginCheckStatusRepository loginCheckStatusRepository;
	
	@Mock
	private LoginApiClient loginApiClient;
	
	@Mock
	private AutenticadorApiClient autenticadorApiClient;
	
	@Test(expected = RuntimeException.class)
	public void testLogarNull() {
		loginService.logar(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLogarUsuarioNull() {
		Login login = new Login();
		loginService.logar(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLogarUsuarioVazio() {
		Login login = new Login();
		login.setUsuario("   ");
		loginService.logar(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLogarSenhaNull() {
		Login login = new Login();
		login.setUsuario("blablabla");
		loginService.logar(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLogarSenhaVazia() {
		Login login = new Login();
		login.setUsuario("blablabla");
		login.setSenha("   ");
		loginService.logar(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaNull() {
		loginService.recuperarSenha(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaEmailNull() {
		RecuperarSenhaLogin recuperarSenha = new RecuperarSenhaLogin();
		loginService.recuperarSenha(recuperarSenha);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaEmailVazio() {
		RecuperarSenhaLogin recuperarSenha = new RecuperarSenhaLogin();
		recuperarSenha.setEmail("  ");
		loginService.recuperarSenha(recuperarSenha);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaCpfNull() {
		RecuperarSenhaLogin recuperarSenha = new RecuperarSenhaLogin();
		recuperarSenha.setEmail("aaaa@bla.com");
		loginService.recuperarSenha(recuperarSenha);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaCpfVazio() {
		RecuperarSenhaLogin recuperarSenha = new RecuperarSenhaLogin();
		recuperarSenha.setEmail("aaaa@bla.com");
		recuperarSenha.setCpf("   ");
		loginService.recuperarSenha(recuperarSenha);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaNull() {
		loginService.trocarSenha(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaTokenNull() {
		TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
		loginService.trocarSenha(trocarSenhaLogin);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaStringTokenNull() {
		TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
		trocarSenhaLogin.setToken(new LoginToken());
		loginService.trocarSenha(trocarSenhaLogin);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaStringTokenVazio() {
		TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
		trocarSenhaLogin.setToken(new LoginToken());
		trocarSenhaLogin.getToken().setToken("   ");
		loginService.trocarSenha(trocarSenhaLogin);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaTipoTokenNull() {
		TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
		trocarSenhaLogin.setToken(new LoginToken());
		trocarSenhaLogin.getToken().setToken("123456");
		loginService.trocarSenha(trocarSenhaLogin);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaTipoTokenVazio() {
		TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
		trocarSenhaLogin.setToken(new LoginToken());
		trocarSenhaLogin.getToken().setToken("123456");
		trocarSenhaLogin.getToken().setTipo("   ");
		loginService.trocarSenha(trocarSenhaLogin);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaSenhaAtualNull() {
		TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
		trocarSenhaLogin.setToken(new LoginToken());
		trocarSenhaLogin.getToken().setToken("123456");
		trocarSenhaLogin.getToken().setTipo("Bearer ");
		loginService.trocarSenha(trocarSenhaLogin);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaSenhaAtualVazia() {
		TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
		trocarSenhaLogin.setToken(new LoginToken());
		trocarSenhaLogin.getToken().setToken("123456");
		trocarSenhaLogin.getToken().setTipo("Bearer ");
		trocarSenhaLogin.setSenhaAtual("   ");
		loginService.trocarSenha(trocarSenhaLogin);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaNovaSenhaNull() {
		TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
		trocarSenhaLogin.setToken(new LoginToken());
		trocarSenhaLogin.getToken().setToken("123456");
		trocarSenhaLogin.getToken().setTipo("Bearer ");
		trocarSenhaLogin.setSenhaAtual("123");
		loginService.trocarSenha(trocarSenhaLogin);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaNovaSenhaVazia() {
		TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
		trocarSenhaLogin.setToken(new LoginToken());
		trocarSenhaLogin.getToken().setToken("123456");
		trocarSenhaLogin.getToken().setTipo("Bearer ");
		trocarSenhaLogin.setSenhaAtual("123");
		trocarSenhaLogin.setNovaSenha("   ");
		loginService.trocarSenha(trocarSenhaLogin);
	}

}
