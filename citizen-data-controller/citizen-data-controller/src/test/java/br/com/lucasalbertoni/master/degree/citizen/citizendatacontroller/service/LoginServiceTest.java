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
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.LoginStatusEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PerfilEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.dto.TrocaSenhaLoginDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.LoginRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenConfirmarCadastroService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.utils.Encryption;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CnpjValidator;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CpfValidator;

@RunWith(SpringRunner.class)
public class LoginServiceTest {
	
	@InjectMocks
	private LoginService loginService;
	
	@Mock
	private LoginRepository loginRepository;
	
	@Mock
	private LoginCidadaoEmpresaService loginCidadaoEmpresaService;
	
	@Mock
	private LoginSistemaInternoService loginSistemaInternoService;
	
	@Mock
	private Encryption encryption;
	
	@Mock
	private TokenConfirmarCadastroService tokenConfirmarCadastroService;
	
	@Mock
	private TokenService tokenService;
	
	@Mock
	private GeradorSenhaService geradorSenhaService;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private CpfValidator cpfValidator;
	
	@Mock
	private CnpjValidator cnpjValidator;
	
	@Test(expected = RuntimeException.class)
	public void testCreateNull() {
		loginService.create(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateIdNotNull() {
		Login login = new Login();
		login.setId(1l);
		loginService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreatePerfilNull() {
		Login login = new Login();
		loginService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmailNull() {
		Login login = new Login();
		login.setPerfil(PerfilEnum.CIDADAO);
		loginService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmailVazio() {
		Login login = new Login();
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setEmail("   ");
		loginService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmailJaExiste() {
		Login login = new Login();
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setEmail("aaa@bla.com");
		
		Mockito.when(loginRepository.existsByEmail(login.getEmail())).thenReturn(true);
		
		loginService.create(login);
	}
	
	@Test
	public void testCreateLoginInterno() {
		Login login = new Login();
		login.setPerfil(PerfilEnum.SISTEMA_INTERNO);
		login.setEmail("aaa@bla.com");
		
		Mockito.when(loginRepository.existsByEmail(login.getEmail())).thenReturn(false);
		
		Login loginSalvo = new Login();
		Mockito.when(loginSistemaInternoService.create(login)).thenReturn(loginSalvo);
		
		Login loginRetornado = loginService.create(login);
		Assert.assertEquals(loginSalvo, loginRetornado);
	}
	
	@Test
	public void testCreateLoginCidadao() {
		Login login = new Login();
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setEmail("aaa@bla.com");
		
		Mockito.when(loginRepository.existsByEmail(login.getEmail())).thenReturn(false);
		
		Login loginSalvo = new Login();
		Mockito.when(loginCidadaoEmpresaService.create(login)).thenReturn(loginSalvo);
		
		Login loginRetornado = loginService.create(login);
		Assert.assertEquals(loginSalvo, loginRetornado);
	}
	
	@Test
	public void testCreateLoginEmpresa() {
		Login login = new Login();
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setEmail("aaa@bla.com");
		
		Mockito.when(loginRepository.existsByEmail(login.getEmail())).thenReturn(false);
		
		Login loginSalvo = new Login();
		Mockito.when(loginCidadaoEmpresaService.create(login)).thenReturn(loginSalvo);
		
		Login loginRetornado = loginService.create(login);
		Assert.assertEquals(loginSalvo, loginRetornado);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindByUsernameNull() {
		loginService.findByUsername(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGerarLoginCidadaoRespostasCpfEmailSenhaNull() {
		loginService.gerarLoginCidadaoRespostas(null, null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGerarLoginCidadaoRespostasCpfVazioEmailSenhaNull() {
		String cpf = "  ";
		loginService.gerarLoginCidadaoRespostas(cpf, null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGerarLoginCidadaoRespostasEmailSenhaNull() {
		String cpf = "077.008.580-63";
		loginService.gerarLoginCidadaoRespostas(cpf, null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGerarLoginCidadaoRespostasEmailVazioSenhaNull() {
		String cpf = "077.008.580-63";
		String email = "   ";
		loginService.gerarLoginCidadaoRespostas(cpf, email, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGerarLoginCidadaoRespostasSenhaNull() {
		String cpf = "077.008.580-63";
		String email = "aaaaaa@aaaa.com";
		loginService.gerarLoginCidadaoRespostas(cpf, email, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGerarLoginCidadaoRespostasSenhaVazia() {
		String cpf = "077.008.580-63";
		String email = "aaaaaa@aaaa.com";
		String senha = "   ";
		loginService.gerarLoginCidadaoRespostas(cpf, email, senha);
	}
	
	@Test
	public void testGerarLoginCidadaoRespostasLoginJaExiste() {
		String cpf = "077.008.580-63";
		String email = "aaaaaa@aaaa.com";
		String senha = "123";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		Mockito.when(loginRepository.existsByCpfCnpj(cpfModificado)).thenReturn(true);
		
		Login login = new Login();
		Mockito.when(loginRepository.findByCpfCnpj(cpfModificado)).thenReturn(login);
		
		String senhaCriptografada = "bla";
		Mockito.when(encryption.encrypt(senha)).thenReturn(senhaCriptografada);
		
		Login loginSalvo = new Login();
		Mockito.when(loginRepository.save(login)).thenReturn(loginSalvo);
		
		Login loginRetornado = loginService.gerarLoginCidadaoRespostas(cpf, email, senha);
		Assert.assertNotNull(loginRetornado);
		Assert.assertEquals(loginSalvo, loginRetornado);
		
		ArgumentCaptor<Login> captorLogin = ArgumentCaptor.forClass(Login.class);
		Mockito.verify(loginRepository).save(captorLogin.capture());
		Assert.assertNotNull(captorLogin.getValue());
		Assert.assertNotNull(captorLogin.getValue().getDataCriacao());
		Assert.assertFalse(captorLogin.getValue().isLoginAtivo());
		Assert.assertTrue(captorLogin.getValue().isSenhaGeradaAutomaticamente());
		Assert.assertEquals(cpfModificado, captorLogin.getValue().getCpfCnpj());
		Assert.assertEquals(email, captorLogin.getValue().getEmail());
		Assert.assertEquals(PerfilEnum.CIDADAO, captorLogin.getValue().getPerfil());
		Assert.assertEquals(senhaCriptografada, captorLogin.getValue().getSenha());
		Assert.assertEquals(LoginStatusEnum.CREATED_LOGIN, captorLogin.getValue().getStatus());
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarLoginCidadaoTokenInvalido() {
		String token = "blablabla";
		Mockito.when(!tokenConfirmarCadastroService.isTokenValidoLiberarLogin(token)).thenReturn(false);
		loginService.liberarLoginCidadao(token);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarLoginCidadaoLoginNull() {
		String token = "blablabla";
		Mockito.when(tokenConfirmarCadastroService.isTokenValidoLiberarLogin(token)).thenReturn(true);
		String cpf = "81274071003";
		Mockito.when(tokenConfirmarCadastroService.getCpfUsuarioToken(token)).thenReturn(cpf);
		Mockito.when(loginRepository.findByCpfCnpj(cpf)).thenReturn(null);
		loginService.liberarLoginCidadao(token);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarLoginCidadaoLoginNaoCidadao() {
		String token = "blablabla";
		Mockito.when(tokenConfirmarCadastroService.isTokenValidoLiberarLogin(token)).thenReturn(true);
		String cpf = "81274071003";
		Mockito.when(tokenConfirmarCadastroService.getCpfUsuarioToken(token)).thenReturn(cpf);
		Login login = new Login();
		login.setPerfil(PerfilEnum.EMPRESA);
		Mockito.when(loginRepository.findByCpfCnpj(cpf)).thenReturn(login);
		loginService.liberarLoginCidadao(token);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaNull() {
		loginService.trocarSenha(null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaNovaSenhaETokenNull() {
		TrocaSenhaLoginDTO trocaSenhaLoginDTO = new TrocaSenhaLoginDTO();
		loginService.trocarSenha(trocaSenhaLoginDTO, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaNovaSenhaVaziaETokenNull() {
		TrocaSenhaLoginDTO trocaSenhaLoginDTO = new TrocaSenhaLoginDTO();
		trocaSenhaLoginDTO.setNovaSenha("    ");
		loginService.trocarSenha(trocaSenhaLoginDTO, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaSenhaETokenNull() {
		TrocaSenhaLoginDTO trocaSenhaLoginDTO = new TrocaSenhaLoginDTO();
		trocaSenhaLoginDTO.setNovaSenha("123");
		loginService.trocarSenha(trocaSenhaLoginDTO, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaSenhaVaziaETokenNull() {
		TrocaSenhaLoginDTO trocaSenhaLoginDTO = new TrocaSenhaLoginDTO();
		trocaSenhaLoginDTO.setNovaSenha("123");
		trocaSenhaLoginDTO.setSenha("   ");
		loginService.trocarSenha(trocaSenhaLoginDTO, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaTokenNull() {
		TrocaSenhaLoginDTO trocaSenhaLoginDTO = new TrocaSenhaLoginDTO();
		trocaSenhaLoginDTO.setNovaSenha("123");
		trocaSenhaLoginDTO.setSenha("1234");
		loginService.trocarSenha(trocaSenhaLoginDTO, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testTrocarSenhaTokenVazio() {
		TrocaSenhaLoginDTO trocaSenhaLoginDTO = new TrocaSenhaLoginDTO();
		trocaSenhaLoginDTO.setNovaSenha("123");
		trocaSenhaLoginDTO.setSenha("1234");
		String token = "   ";
		loginService.trocarSenha(trocaSenhaLoginDTO, token);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaNull() {
		loginService.recuperarSenha(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaCpfCnpjNull() {
		Login login = new Login();
		loginService.recuperarSenha(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaCpfCnpjVazio() {
		Login login = new Login();
		login.setCpfCnpj("   ");
		loginService.recuperarSenha(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaEmailNull() {
		Login login = new Login();
		login.setCpfCnpj("266.414.210-07");
		loginService.recuperarSenha(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaEmailVazio() {
		Login login = new Login();
		login.setCpfCnpj("266.414.210-07");
		login.setEmail("   ");
		loginService.recuperarSenha(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaLoginRetornadoNull() {
		Login loginRecebido = new Login();
		loginRecebido.setCpfCnpj("266.414.210-07");
		loginRecebido.setEmail("aaaa@bla.com");
		
		String cpf = "26641421007";
		Mockito.when(cpfValidator.validate(loginRecebido.getCpfCnpj())).thenReturn(cpf);

		Mockito.when(loginRepository.findByCpfCnpj(loginRecebido.getCpfCnpj())).thenReturn(null);
		
		loginService.recuperarSenha(loginRecebido);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaLoginSalvoEmailNull() {
		Login loginRecebido = new Login();
		loginRecebido.setCpfCnpj("266.414.210-07");
		loginRecebido.setEmail("aaaa@bla.com");
		
		String cpf = "26641421007";
		Mockito.when(cpfValidator.validate(loginRecebido.getCpfCnpj())).thenReturn(cpf);

		Login loginSalvo = new Login();
		Mockito.when(loginRepository.findByCpfCnpj(loginRecebido.getCpfCnpj())).thenReturn(loginSalvo);
		
		loginService.recuperarSenha(loginRecebido);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaLoginSalvoEmailVazio() {
		Login loginRecebido = new Login();
		loginRecebido.setCpfCnpj("266.414.210-07");
		loginRecebido.setEmail("aaaa@bla.com");
		
		String cpf = "26641421007";
		Mockito.when(cpfValidator.validate(loginRecebido.getCpfCnpj())).thenReturn(cpf);

		Login loginSalvo = new Login();
		loginSalvo.setEmail("   ");
		Mockito.when(loginRepository.findByCpfCnpj(loginRecebido.getCpfCnpj())).thenReturn(loginSalvo);
		
		loginService.recuperarSenha(loginRecebido);
	}
	
	@Test(expected = RuntimeException.class)
	public void testRecuperarSenhaEmailsDiferentes() {
		Login loginRecebido = new Login();
		loginRecebido.setCpfCnpj("266.414.210-07");
		loginRecebido.setEmail("aaaa@bla.com");
		
		String cpf = "26641421007";
		Mockito.when(cpfValidator.validate(loginRecebido.getCpfCnpj())).thenReturn(cpf);

		Login loginSalvo = new Login();
		loginSalvo.setEmail("aaaa2@bla.com");
		Mockito.when(loginRepository.findByCpfCnpj(loginRecebido.getCpfCnpj())).thenReturn(loginSalvo);
		
		loginService.recuperarSenha(loginRecebido);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmpresaNull() {
		loginService.createEmpresa(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmpresaPerfilNull() {
		Login login = new Login();
		loginService.createEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmpresaEmailNull() {
		Login login = new Login();
		login.setPerfil(PerfilEnum.EMPRESA);
		loginService.createEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmpresaEmailVazio() {
		Login login = new Login();
		login.setPerfil(PerfilEnum.EMPRESA);
		login.setEmail("   ");
		loginService.createEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmpresaLoginJaExisteEmail() {
		Login login = new Login();
		login.setPerfil(PerfilEnum.EMPRESA);
		login.setEmail("aaa@bla.com");
		
		Mockito.when(loginRepository.existsByEmail(login.getEmail())).thenReturn(true);
		
		loginService.createEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmpresaPerfilNaoEmpresa() {
		Login login = new Login();
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setEmail("aaa@bla.com");
		
		Mockito.when(loginRepository.existsByEmail(login.getEmail())).thenReturn(false);
		
		loginService.createEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmpresaLoginJaExisteCnpj() {
		Login login = new Login();
		login.setPerfil(PerfilEnum.EMPRESA);
		login.setEmail("aaa@bla.com");
		login.setCpfCnpj("40.693.115/0001-23");
		
		Mockito.when(loginRepository.existsByEmail(login.getEmail())).thenReturn(false);
		
		String cnpj = login.getCpfCnpj().replace("-", "").replace(".", "").replace("/", "");
		Mockito.when(cnpjValidator.validate(login.getCpfCnpj())).thenReturn(cnpj);
		
		Mockito.when(loginRepository.existsByCpfCnpj(cnpj)).thenReturn(true);
		
		loginService.createEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateEmpresaNull() {
		loginService.updateEmpresa(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateEmpresaIdNull() {
		Login login = new Login();
		loginService.updateEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateEmpresaLoginNaoExiste() {
		Login login = new Login();
		login.setId(666l);
		
		Mockito.when(loginRepository.existsById(login.getId())).thenReturn(false);
		
		loginService.updateEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateEmpresaPerfilNull() {
		Login login = new Login();
		login.setId(666l);
		
		Mockito.when(loginRepository.existsById(login.getId())).thenReturn(true);
		
		loginService.updateEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateEmpresaEmailNull() {
		Login login = new Login();
		login.setId(666l);
		login.setPerfil(PerfilEnum.EMPRESA);
		
		Mockito.when(loginRepository.existsById(login.getId())).thenReturn(true);
		
		loginService.updateEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateEmpresaEmailVazio() {
		Login login = new Login();
		login.setId(666l);
		login.setPerfil(PerfilEnum.EMPRESA);
		login.setEmail("    ");
		
		Mockito.when(loginRepository.existsById(login.getId())).thenReturn(true);
		
		loginService.updateEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateEmpresaEmailDeOutraEmpresa() {
		Login login = new Login();
		Long id = 666l;
		login.setId(id);
		login.setPerfil(PerfilEnum.EMPRESA);
		login.setEmail("aaaaa@bla.com");
		
		Mockito.when(loginRepository.existsById(login.getId())).thenReturn(true);
		
		Login empresaEncontrada = new Login();
		empresaEncontrada.setId(777l);
		Mockito.when(loginRepository.findByEmail(login.getEmail())).thenReturn(empresaEncontrada);
		
		loginService.updateEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateEmpresaPerfilCidadao() {
		Login login = new Login();
		Long id = 666l;
		login.setId(id);
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setEmail("aaaaa@bla.com");
		
		Mockito.when(loginRepository.existsById(login.getId())).thenReturn(true);
		
		Login empresaEncontrada = new Login();
		empresaEncontrada.setId(id);
		Mockito.when(loginRepository.findByEmail(login.getEmail())).thenReturn(empresaEncontrada);
		
		loginService.updateEmpresa(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateEmpresaCpfCnpjDeOutraEmpresa() {
		Login login = new Login();
		Long id = 666l;
		login.setId(id);
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setEmail("aaaaa@bla.com");
		
		Mockito.when(loginRepository.existsById(login.getId())).thenReturn(true);
		
		Login empresaEncontrada = new Login();
		empresaEncontrada.setId(id);
		Mockito.when(loginRepository.findByEmail(login.getEmail())).thenReturn(empresaEncontrada);
		
		String cnpj = "40.693.115/0001-23";
		Mockito.when(cnpjValidator.validate(login.getCpfCnpj())).thenReturn(cnpj);

		Login empresaEncontrada2 = new Login();
		empresaEncontrada2.setId(777l);
		Mockito.when(loginRepository.findByCpfCnpj(login.getCpfCnpj())).thenReturn(empresaEncontrada2);
		
		loginService.updateEmpresa(login);
	}

}
