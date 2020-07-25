package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.AutenticadorApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginTokenInterno;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.LoginTokenInternoRepository;

@RunWith(SpringRunner.class)
public class AutenticadorInternoServiceTest {
	
	@InjectMocks
	private AutenticadorInternoService autenticadorInternoService;

	@Mock
	private LoginTokenInternoRepository loginTokenInternoRepository;
	
	@Mock
	private AutenticadorApiClient autenticadorApiClient;
	
	private String usuario;
	private String senha;
	
	@Test
	public void testGetTokenListWithNullElement() {
		LoginTokenInterno loginInternoNull = null;
		Iterable<LoginTokenInterno> loginIterable = Arrays.asList(loginInternoNull);
		Mockito.when(loginTokenInternoRepository.findAll()).thenReturn(loginIterable);
		
		LoginTokenInterno loginTokenInterno = mockUsuarioSenha();
		
		LoginTokenInterno loginInterno = new LoginTokenInterno();
		Mockito.when(loginTokenInternoRepository.save(loginTokenInterno)).thenReturn(loginInterno);
		
		LoginTokenInterno loginInternoRetornado = autenticadorInternoService.getToken();
		Assert.assertEquals(loginInterno, loginInternoRetornado);
		Mockito.verify(loginTokenInternoRepository, Mockito.times(1)).deleteAll();
	}
	
	@Test
	public void testGetTokenEmptyList() {
		Iterable<LoginTokenInterno> loginIterable = Collections.emptyList();
		Mockito.when(loginTokenInternoRepository.findAll()).thenReturn(loginIterable);
		
		LoginTokenInterno loginTokenInterno = mockUsuarioSenha();
		
		LoginTokenInterno loginInterno = new LoginTokenInterno();
		Mockito.when(loginTokenInternoRepository.save(loginTokenInterno)).thenReturn(loginInterno);
		
		LoginTokenInterno loginInternoRetornado = autenticadorInternoService.getToken();
		Assert.assertEquals(loginInterno, loginInternoRetornado);
	}
	
	@Test
	public void testGetToken() {
		LoginTokenInterno loginInterno = new LoginTokenInterno();
		Iterable<LoginTokenInterno> loginIterable = Arrays.asList(loginInterno);
		Mockito.when(loginTokenInternoRepository.findAll()).thenReturn(loginIterable);
		
		LoginTokenInterno loginInternoRetornado = autenticadorInternoService.getToken();
		Assert.assertEquals(loginInterno, loginInternoRetornado);
	}

	private LoginTokenInterno mockUsuarioSenha() {
		usuario = "blablabla";
		senha = "blablabla666";
		ReflectionTestUtils.setField(autenticadorInternoService, "usuario", usuario);
		ReflectionTestUtils.setField(autenticadorInternoService, "senha", senha);
		LoginTokenInterno loginTokenInterno = new LoginTokenInterno();
		Mockito.when(autenticadorApiClient.findByUsuarioSenhaInterno(usuario , senha)).thenReturn(loginTokenInterno);
		return loginTokenInterno;
	}

}
