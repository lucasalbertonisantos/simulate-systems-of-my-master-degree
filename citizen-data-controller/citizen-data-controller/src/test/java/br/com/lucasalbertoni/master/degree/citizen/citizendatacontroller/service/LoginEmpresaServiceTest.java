package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.LoginRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.utils.Encryption;

@RunWith(SpringRunner.class)
public class LoginEmpresaServiceTest {
	
	@InjectMocks
	private LoginEmpresaService loginEmpresaService;
	
	@Mock
	private EmpresaService empresaService;
	
	@Mock
	private LoginRepository loginRepository;
	
	@Mock
	private Encryption encryption;
	
	@Test(expected=RuntimeException.class)
	public void testCreateNull() {
		loginEmpresaService.create(null);
	}
	
	@Test(expected=RuntimeException.class)
	public void testCreateEmpresaNull() {
		Login login = new Login();
		login.setCpfCnpj("478.847.910-99");
		Mockito.when(empresaService.findByCnpj(login.getCpfCnpj())).thenReturn(null);
		loginEmpresaService.create(login);
	}

}
