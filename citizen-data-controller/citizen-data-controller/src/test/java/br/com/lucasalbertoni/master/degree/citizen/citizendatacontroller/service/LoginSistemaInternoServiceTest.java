package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.LoginRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.utils.Encryption;

@RunWith(SpringRunner.class)
public class LoginSistemaInternoServiceTest {
	
	@InjectMocks
	private LoginSistemaInternoService loginSistemaInternoService;
	
	@Mock
	private Encryption encryption;
	
	@Mock
	private LoginRepository loginRepository;
	
	@Test(expected = RuntimeException.class)
	public void testCreateNull() {
		loginSistemaInternoService.create(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateSenhaNula() {
		Login login = new Login();
		loginSistemaInternoService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateSenhaVazia() {
		Login login = new Login();
		login.setSenha(" ");
		loginSistemaInternoService.create(login);
	}
	
	@Test
	public void testCreate() {
		Login login = new Login();
		login.setSenha("xxxxx");
		
		String senhaCriptografada = "bla";
		Mockito.when(encryption.encrypt(login.getSenha())).thenReturn(senhaCriptografada);
		
		Login loginSalvo = new Login();
		Mockito.when(loginRepository.save(login)).thenReturn(loginSalvo);
		
		Login loginRetornado = loginSistemaInternoService.create(login);
		Assert.assertNotNull(loginRetornado);
		Assert.assertEquals(loginSalvo, loginRetornado);
		
		ArgumentCaptor<Login> loginCaptor = ArgumentCaptor.forClass(Login.class);
		Mockito.verify(loginRepository).save(loginCaptor.capture());
		Assert.assertEquals(login, loginCaptor.getValue());
		Assert.assertEquals(senhaCriptografada, loginCaptor.getValue().getSenha());
	}

}
