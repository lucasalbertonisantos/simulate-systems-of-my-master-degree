package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Empresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PerfilEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PermissaoDadosCidadaoEmpresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.CidadaoRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.PermissaoDadosCidadaoEmpresaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenLiberarDadosService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenService;

@RunWith(SpringRunner.class)
public class CidadaoServiceTest {
	
	@InjectMocks
	private CidadaoService cidadaoService;
	
	@Mock
	private CidadaoRepository cidadaoRepository;
	
	@Mock
	private TokenService tokenService;
	
	@Mock
	private LoginService loginService;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private PermissaoDadosCidadaoEmpresaRepository permissaoDadosCidadaoEmpresaRepository;
	
	@Mock
	private EmpresaService empresaService;
	
	@Mock
	private TokenLiberarDadosService tokenLiberarDadosService;
	
	@Test(expected = RuntimeException.class)
	public void testSaveNull() {
		cidadaoService.save(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveIdNotNull() {
		Cidadao cidadao = new Cidadao();
		cidadao.setId(1l);
		cidadaoService.save(cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveCpfNull() {
		Cidadao cidadao = new Cidadao();
		cidadaoService.save(cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveCpfVazio() {
		Cidadao cidadao = new Cidadao();
		cidadao.setCpf("  ");
		cidadaoService.save(cidadao);
	}
	
	@Test
	public void testSave() {
		Cidadao cidadao = new Cidadao();
		cidadao.setCpf("25309154078");
		
		Cidadao cidadaoSalvo = new Cidadao();
		Mockito.when(cidadaoRepository.save(cidadao)).thenReturn(cidadaoSalvo);
		
		Cidadao cidadaoRetornado = cidadaoService.save(cidadao);
		Assert.assertEquals(cidadaoSalvo, cidadaoRetornado);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindByCpfReturnNull() {
		String cpf = "25309154078";
		Mockito.when(cidadaoRepository.findByCpf(cpf)).thenReturn(null);
		cidadaoService.findByCpf(cpf);
	}
	
	@Test
	public void testFindByCpf() {
		String cpf = "25309154078";
		
		Cidadao cidadaoSalvo = new Cidadao();
		Mockito.when(cidadaoRepository.findByCpf(cpf)).thenReturn(cidadaoSalvo);
		
		Cidadao cidadaoRetornado = cidadaoService.findByCpf(cpf);
		Assert.assertEquals(cidadaoSalvo, cidadaoRetornado);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosNull() {
		cidadaoService.liberarDados(null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosCnpjEmpresaNull() {
		Empresa empresa = new Empresa();
		cidadaoService.liberarDados(null, empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosCnpjEmpresaVazio() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("   ");
		cidadaoService.liberarDados(null, empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosRetornoEmpresaNull() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("77949328000183");
		empresa.setNome("xxxx");
		
		Mockito.when(empresaService.findByCnpj(empresa.getCnpj())).thenReturn(null);
		
		cidadaoService.liberarDados(null, empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosTokenNull() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("77949328000183");
		empresa.setNome("xxxx");
		
		Empresa empresaSalva = new Empresa();
		Mockito.when(empresaService.findByCnpj(empresa.getCnpj())).thenReturn(empresaSalva);
		
		cidadaoService.liberarDados(null, empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosLoginNull() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("77949328000183");
		empresa.setNome("xxxx");
		String token = "Bearer bla";
		
		Empresa empresaSalva = new Empresa();
		Mockito.when(empresaService.findByCnpj(empresa.getCnpj())).thenReturn(empresaSalva);
		
		String email = "aaa@aaa.com";
		Mockito.when(tokenService.getEmailUsuarioToken(token.replace("Bearer ", ""))).thenReturn(email);
		
		Mockito.when(loginService.findByUsername(email)).thenReturn(null);
		
		cidadaoService.liberarDados(token, empresa);
	}
	
	@Test
	public void testLiberarDados() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("77949328000183");
		empresa.setNome("xxxx");
		String token = "Bearer bla";
		
		Empresa empresaSalva = new Empresa();
		empresaSalva.setCnpj(empresa.getCnpj());
		empresaSalva.setNome(empresa.getNome());
		Mockito.when(empresaService.findByCnpj(empresa.getCnpj())).thenReturn(empresaSalva);
		
		String email = "aaa@aaa.com";
		Mockito.when(tokenService.getEmailUsuarioToken(token.replace("Bearer ", ""))).thenReturn(email);
		
		Login login = new Login();
		login.setCpfCnpj("12755967030");
		Mockito.when(loginService.findByUsername(email)).thenReturn(login);
		
		Cidadao cidadaoSalvo = new Cidadao();
		Mockito.when(cidadaoRepository.findByCpf(login.getCpfCnpj())).thenReturn(cidadaoSalvo);
		
		PermissaoDadosCidadaoEmpresa permissaoSalva = new PermissaoDadosCidadaoEmpresa();
		permissaoSalva.setTokenAcesso(UUID.randomUUID().toString());
		Mockito.when(permissaoDadosCidadaoEmpresaRepository.save(Mockito.any())).thenReturn(permissaoSalva);
		
		String tokenGerado = "blablabla";
		Mockito.when(tokenLiberarDadosService.gerarTokenEnvioEmail(permissaoSalva.getTokenAcesso())).thenReturn(tokenGerado);
		
		String url = "http://blablabla.bla";
		ReflectionTestUtils.setField(cidadaoService, "url", url);
		
		PermissaoDadosCidadaoEmpresa permissaoRetornada = cidadaoService.liberarDados(token, empresa);
		Assert.assertEquals(permissaoSalva, permissaoRetornada);
		
		ArgumentCaptor<PermissaoDadosCidadaoEmpresa> permissaoCaptor = ArgumentCaptor.forClass(PermissaoDadosCidadaoEmpresa.class);
		Mockito.verify(permissaoDadosCidadaoEmpresaRepository).save(permissaoCaptor.capture());
		Assert.assertNotNull(permissaoCaptor.getValue().getDataExpiracaoToken());
		Assert.assertNotNull(permissaoCaptor.getValue().getTokenAcesso());
		Assert.assertEquals(cidadaoSalvo, permissaoCaptor.getValue().getCidadao());
		Assert.assertEquals(empresaSalva, permissaoCaptor.getValue().getEmpresa());
		Assert.assertFalse(permissaoCaptor.getValue().isConfirmado());
		
		ArgumentCaptor<String> nomeEmpresaCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> cnpjCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> tokenEnvioCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> tokenAcessoCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(emailService).sendTokenLiberarAcessoDados(
				nomeEmpresaCaptor.capture(), 
				cnpjCaptor.capture(), 
				urlCaptor.capture(), 
				tokenEnvioCaptor.capture(), 
				tokenAcessoCaptor.capture(), 
				emailCaptor.capture());
		Assert.assertEquals(empresaSalva.getNome(), nomeEmpresaCaptor.getValue());
		Assert.assertEquals(empresaSalva.getCnpj(), cnpjCaptor.getValue());
		Assert.assertEquals(url, urlCaptor.getValue());
		Assert.assertEquals(tokenGerado, tokenEnvioCaptor.getValue());
		Assert.assertEquals(permissaoRetornada.getTokenAcesso(), tokenAcessoCaptor.getValue());
		Assert.assertEquals(email, emailCaptor.getValue());
	}
	
	@Test(expected = RuntimeException.class)
	public void testConfirmarTokenInvalido() {
		String urlToken = "xxx";
		
		Mockito.when(tokenLiberarDadosService.isTokenValidoLiberarLogin(urlToken)).thenReturn(false);
		
		cidadaoService.confirmar(urlToken);
	}
	
	@Test(expected = RuntimeException.class)
	public void testConfirmarSemPermissao() {
		String urlToken = "xxx";
		Mockito.when(tokenLiberarDadosService.isTokenValidoLiberarLogin(urlToken)).thenReturn(true);
		
		String tokenPermissao = "xxx-xxx-xxx";
		Mockito.when(tokenLiberarDadosService.getIdPermissaoToken(urlToken)).thenReturn(tokenPermissao);
		
		Mockito.when(permissaoDadosCidadaoEmpresaRepository.findByTokenAcesso(tokenPermissao)).thenReturn(Optional.empty());
		
		cidadaoService.confirmar(urlToken);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindIfAllowedTokenCpfCodigoNull() {
		cidadaoService.findIfAllowed(null, null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindIfAllowedTokenVazioCpfCodigoNull() {
		String token = "   ";
		cidadaoService.findIfAllowed(token, null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindIfAllowedCpfCodigoNull() {
		String token = "xxxx";
		cidadaoService.findIfAllowed(token, null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindIfAllowedCpfVazioCodigoNull() {
		String token = "xxxx";
		String cpf = "   ";
		cidadaoService.findIfAllowed(token, cpf, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindIfAllowedCodigoNull() {
		String token = "xxxx";
		String cpf = "24073530054";
		cidadaoService.findIfAllowed(token, cpf, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindIfAllowedCodigoVazio() {
		String token = "xxxx";
		String cpf = "24073530054";
		String codigo = "   ";
		cidadaoService.findIfAllowed(token, cpf, codigo);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindIfAllowedEmailNull() {
		String token = "Bearer xxxx";
		String cpf = "24073530054";
		String codigo = "bla-xxx-blablbal";
		
		Mockito.when(tokenService.getEmailUsuarioToken(token.replace("Bearer ", ""))).thenReturn(null);

		cidadaoService.findIfAllowed(token, cpf, codigo);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindIfAllowedEmailVazio() {
		String token = "Bearer xxxx";
		String cpf = "24073530054";
		String codigo = "bla-xxx-blablbal";
		
		String email = "     ";
		Mockito.when(tokenService.getEmailUsuarioToken(token.replace("Bearer ", ""))).thenReturn(email);

		cidadaoService.findIfAllowed(token, cpf, codigo);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindIfAllowedLoginNull() {
		String token = "Bearer xxxx";
		String cpf = "24073530054";
		String codigo = "bla-xxx-blablbal";
		
		String email = "xxxx@blabla.com";
		Mockito.when(tokenService.getEmailUsuarioToken(token.replace("Bearer ", ""))).thenReturn(email);
		
		Mockito.when(loginService.findByUsername(email)).thenReturn(null);

		cidadaoService.findIfAllowed(token, cpf, codigo);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindIfAllowedPerfilNull() {
		String token = "Bearer xxxx";
		String cpf = "24073530054";
		String codigo = "bla-xxx-blablbal";
		
		String email = "xxxx@blabla.com";
		Mockito.when(tokenService.getEmailUsuarioToken(token.replace("Bearer ", ""))).thenReturn(email);
		
		Login login = new Login();
		Mockito.when(loginService.findByUsername(email)).thenReturn(login);

		cidadaoService.findIfAllowed(token, cpf, codigo);
	}
	
	@Test
	public void testFindIfAllowedPerfilCidadao() {
		String token = "Bearer xxxx";
		String cpf = "24073530054";
		String codigo = "bla-xxx-blablbal";
		
		String email = "xxxx@blabla.com";
		Mockito.when(tokenService.getEmailUsuarioToken(token.replace("Bearer ", ""))).thenReturn(email);
		
		Login login = new Login();
		login.setPerfil(PerfilEnum.CIDADAO);
		Mockito.when(loginService.findByUsername(email)).thenReturn(login);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoRepository.findByCpf(cpf)).thenReturn(cidadao);

		Cidadao cidadaoRetornado = cidadaoService.findIfAllowed(token, cpf, codigo);
		Assert.assertEquals(cidadao, cidadaoRetornado);
	}

}
