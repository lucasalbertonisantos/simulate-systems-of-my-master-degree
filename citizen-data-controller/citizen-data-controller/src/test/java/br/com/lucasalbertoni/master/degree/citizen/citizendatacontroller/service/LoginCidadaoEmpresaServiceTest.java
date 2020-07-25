package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PerfilEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.LoginRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CnpjValidator;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CpfValidator;

@RunWith(SpringRunner.class)
public class LoginCidadaoEmpresaServiceTest {
	
	@InjectMocks
	private LoginCidadaoEmpresaService loginCidadaoEmpresaService;
	
	@Mock
	private LoginRepository loginRepository;
	
	@Mock
	private LoginCidadaoService loginCidadaoService;
	
	@Mock
	private LoginEmpresaService loginEmpresaService;
	
	@Mock
	private CpfValidator cpfValidator;
	
	@Mock
	private CnpjValidator cnpjValidator;
	
	@Test(expected = RuntimeException.class)
	public void testCreateNull() {
		loginCidadaoEmpresaService.create(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateCpfCnpjNull() {
		Login login = new Login();
		loginCidadaoEmpresaService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateCpfCnpjVazio() {
		Login login = new Login();
		login.setCpfCnpj("   ");
		loginCidadaoEmpresaService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateCpfCnpjJaExiste() {
		Login login = new Login();
		login.setCpfCnpj("897.119.550-91");

		Mockito.when(loginRepository.existsByCpfCnpj(login.getCpfCnpj())).thenReturn(true);
		
		loginCidadaoEmpresaService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreatePerfilInvalido() {
		Login login = new Login();
		login.setCpfCnpj("897.119.550-91");

		Mockito.when(loginRepository.existsByCpfCnpj(login.getCpfCnpj())).thenReturn(false);
		
		loginCidadaoEmpresaService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreatePerfilCidadaoJaExiste() {
		Login login = new Login();
		login.setCpfCnpj("897.119.550-91");
		login.setPerfil(PerfilEnum.CIDADAO);

		Mockito.when(loginRepository.existsByCpfCnpj(login.getCpfCnpj())).thenReturn(false);
		
		String cpf = login.getCpfCnpj().replace("-", "").replace(".", "");
		Mockito.when(cpfValidator.validate(login.getCpfCnpj())).thenReturn(cpf);
		
		Login loginSalvo = new Login();
		Mockito.when(loginCidadaoService.create(login)).thenReturn(loginSalvo);
		
		Mockito.when(loginRepository.existsByCpfCnpj(cpf)).thenReturn(true);
		
		Login loginRetornado = loginCidadaoEmpresaService.create(login);
		Assert.assertNotNull(loginRetornado);
		Assert.assertEquals(loginSalvo, loginRetornado);
		Mockito.verify(loginEmpresaService, Mockito.times(0)).create(Mockito.any());
	}
	
	@Test
	public void testCreatePerfilCidadao() {
		Login login = new Login();
		login.setCpfCnpj("897.119.550-91");
		login.setPerfil(PerfilEnum.CIDADAO);

		Mockito.when(loginRepository.existsByCpfCnpj(login.getCpfCnpj())).thenReturn(false);
		
		String cpf = login.getCpfCnpj().replace("-", "").replace(".", "");
		Mockito.when(cpfValidator.validate(login.getCpfCnpj())).thenReturn(cpf);
		
		Login loginSalvo = new Login();
		Mockito.when(loginCidadaoService.create(login)).thenReturn(loginSalvo);
		
		Mockito.when(loginRepository.existsByCpfCnpj(cpf)).thenReturn(false);
		
		Login loginRetornado = loginCidadaoEmpresaService.create(login);
		Assert.assertNotNull(loginRetornado);
		Assert.assertEquals(loginSalvo, loginRetornado);
		Mockito.verify(loginEmpresaService, Mockito.times(0)).create(Mockito.any());
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreatePerfilEmpresaJaExiste() {
		Login login = new Login();
		login.setCpfCnpj("90.256.982/0001-70");
		login.setPerfil(PerfilEnum.EMPRESA);

		Mockito.when(loginRepository.existsByCpfCnpj(login.getCpfCnpj())).thenReturn(false);
		
		String cnpj = login.getCpfCnpj().replace("-", "").replace(".", "").replace("/", "");
		Mockito.when(cnpjValidator.validate(login.getCpfCnpj())).thenReturn(cnpj);
		
		Login loginSalvo = new Login();
		Mockito.when(loginEmpresaService.create(login)).thenReturn(loginSalvo);
		
		Mockito.when(loginRepository.existsByCpfCnpj(cnpj)).thenReturn(true);
		
		Login loginRetornado = loginCidadaoEmpresaService.create(login);
		Assert.assertNotNull(loginRetornado);
		Assert.assertEquals(loginSalvo, loginRetornado);
		Mockito.verify(loginCidadaoService, Mockito.times(0)).create(Mockito.any());
	}
	
	@Test
	public void testCreatePerfilEmpresa() {
		Login login = new Login();
		login.setCpfCnpj("90.256.982/0001-70");
		login.setPerfil(PerfilEnum.EMPRESA);

		Mockito.when(loginRepository.existsByCpfCnpj(login.getCpfCnpj())).thenReturn(false);
		
		String cnpj = login.getCpfCnpj().replace("-", "").replace(".", "").replace("/", "");
		Mockito.when(cnpjValidator.validate(login.getCpfCnpj())).thenReturn(cnpj);
		
		Login loginSalvo = new Login();
		Mockito.when(loginEmpresaService.create(login)).thenReturn(loginSalvo);
		
		Mockito.when(loginRepository.existsByCpfCnpj(cnpj)).thenReturn(false);
		
		Login loginRetornado = loginCidadaoEmpresaService.create(login);
		Assert.assertNotNull(loginRetornado);
		Assert.assertEquals(loginSalvo, loginRetornado);
		Mockito.verify(loginCidadaoService, Mockito.times(0)).create(Mockito.any());
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateNull() {
		loginCidadaoEmpresaService.update(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateCpfCnpjNull() {
		Login login = new Login();
		loginCidadaoEmpresaService.update(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateCpfCnpjVazio() {
		Login login = new Login();
		login.setCpfCnpj("   ");
		loginCidadaoEmpresaService.update(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdatePerfilInvalido() {
		Login login = new Login();
		login.setCpfCnpj("897.119.550-91");
		
		loginCidadaoEmpresaService.update(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdatePerfilCidadaoNaoExiste() {
		Login login = new Login();
		login.setCpfCnpj("897.119.550-91");
		login.setPerfil(PerfilEnum.CIDADAO);
		
		String cpf = login.getCpfCnpj().replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(login.getCpfCnpj())).thenReturn(cpf);
		
		Mockito.when(loginRepository.findByCpfCnpj(cpf)).thenReturn(null);
		
		loginCidadaoEmpresaService.update(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdatePerfilCidadaoIdDiferente() {
		Login login = new Login();
		login.setCpfCnpj("897.119.550-91");
		login.setPerfil(PerfilEnum.CIDADAO);
		
		String cpf = login.getCpfCnpj().replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(login.getCpfCnpj())).thenReturn(cpf);
		
		Login loginEncontrado = new Login();
		loginEncontrado.setId(666l);
		Mockito.when(loginRepository.findByCpfCnpj(cpf)).thenReturn(loginEncontrado);
		
		loginCidadaoEmpresaService.update(login);
	}
	
	@Test
	public void testUpdatePerfilCidadao() {
		Login login = new Login();
		login.setCpfCnpj("897.119.550-91");
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setId(666l);
		
		String cpf = login.getCpfCnpj().replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(login.getCpfCnpj())).thenReturn(cpf);
		
		Login loginEncontrado = new Login();
		loginEncontrado.setId(666l);
		Mockito.when(loginRepository.findByCpfCnpj(cpf)).thenReturn(loginEncontrado);
		
		Login loginSalvo = new Login();
		Mockito.when(loginCidadaoService.create(login)).thenReturn(loginSalvo);
		
		Login loginRetornado = loginCidadaoEmpresaService.update(login);
		Assert.assertNotNull(loginRetornado);
		Assert.assertEquals(loginSalvo, loginRetornado);
		Mockito.verify(loginEmpresaService, Mockito.times(0)).create(Mockito.any());
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdatePerfilEmpresaLoginNaoExiste() {
		Login login = new Login();
		login.setCpfCnpj("90.256.982/0001-70");
		login.setPerfil(PerfilEnum.EMPRESA);
		login.setId(666l);
		
		String cnpj = login.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "");
		Mockito.when(cnpjValidator.validate(login.getCpfCnpj())).thenReturn(cnpj);
		
		Mockito.when(loginRepository.findByCpfCnpj(cnpj)).thenReturn(null);
		
		loginCidadaoEmpresaService.update(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdatePerfilEmpresaIdDiferente() {
		Login login = new Login();
		login.setCpfCnpj("90.256.982/0001-70");
		login.setPerfil(PerfilEnum.EMPRESA);
		
		String cnpj = login.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "");
		Mockito.when(cnpjValidator.validate(login.getCpfCnpj())).thenReturn(cnpj);
		
		Login loginEncontrado = new Login();
		loginEncontrado.setId(666l);
		Mockito.when(loginRepository.findByCpfCnpj(cnpj)).thenReturn(loginEncontrado);
		
		loginCidadaoEmpresaService.update(login);
	}
	
	@Test
	public void testUpdatePerfilEmpresa() {
		Login login = new Login();
		login.setCpfCnpj("90.256.982/0001-70");
		login.setPerfil(PerfilEnum.EMPRESA);
		login.setId(666l);
		
		String cnpj = login.getCpfCnpj().replace(".", "").replace("-", "").replace("/", "");
		Mockito.when(cnpjValidator.validate(login.getCpfCnpj())).thenReturn(cnpj);
		
		Login loginEncontrado = new Login();
		loginEncontrado.setId(666l);
		Mockito.when(loginRepository.findByCpfCnpj(cnpj)).thenReturn(loginEncontrado);
		
		Login loginSalvo = new Login();
		Mockito.when(loginEmpresaService.create(login)).thenReturn(loginSalvo);
		
		Login loginRetornado = loginCidadaoEmpresaService.update(login);
		Assert.assertNotNull(loginRetornado);
		Assert.assertEquals(loginSalvo, loginRetornado);
		Mockito.verify(loginCidadaoService, Mockito.times(0)).create(Mockito.any());
	}

}
