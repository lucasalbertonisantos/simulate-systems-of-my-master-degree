package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.bdd.steps;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.Application;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.JavaMailMock;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.apiclient.CidadaoDadosIntegradoApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.bdd.mock.CidadaoM1APIMock;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.ColunaEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Email;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Empresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.LoginStatusEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PerfilEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PermissaoDadosCidadaoEmpresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.RG;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Telefone;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.dto.TrocaSenhaLoginDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.CidadaoRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.EmpresaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.LoginRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.PermissaoDadosCidadaoEmpresaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.dto.LoginDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.dto.TokenDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.CidadaoService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.LoginService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.RespostaService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.utils.Encryption;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CnpjValidator;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CpfValidator;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@DirtiesContext
public class CucumberTestSteps {
	
	private static final String NAO_EXISTE_PASSO = "Status não será implementado nesse micro serviço, pois não existe esse passo.";
	private static final String EMAIL_PADRAO = "teste@exemplo.com";
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private Encryption encryption;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private CpfValidator cpfValidator;
	
	@Autowired
	private CnpjValidator cnpjValidator;
	
	@Autowired
	private CidadaoRepository cidadaoRepository;
	
	@Autowired
	private CidadaoDadosIntegradoApiClient cidadaoDadosIntegradoApiClient;
	
	@Autowired
	private RespostaService respostaService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CidadaoM1APIMock cidadaoM1APIMock;
	
	@Autowired
    private JavaMailMock javaMailSender;
	
	@Autowired
	private CidadaoService cidadaoService;
	
	@Autowired
	private PermissaoDadosCidadaoEmpresaRepository permissaoDadosCidadaoEmpresaRepository;
	
	@LocalServerPort
	private int port;
	
	private static final String URL_BASE = "http://localhost:";
	private static final String URL_AUTENTICACAO = "/auth";
	private static final String URL_AUTENTICACAO_CIDADAO = "/auth?perfil=CIDADAO";
	private static final String URL_CADASTRO_EMPRESA = "/empresas";
	private static final String URL_CIDADAO = "/cidadaos";
	private static final String URL_LIBERAR_DADOS = "/liberar-dados";
	private static final String URL_CADASTRO_LOGIN = "/login";
	private static final String URL_CADASTRO_LOGIN_EMPRESA = URL_CADASTRO_LOGIN + "/empresa";
	private static final String URL_LOGIN_SITUACAO = URL_CADASTRO_LOGIN + "/situacao";
	private static final String URL_LOGIN_TROCAR_SENHA = URL_CADASTRO_LOGIN + "/trocar-senha";
	private static final String URL_PERGUNTAS = "/perguntas";
	private static final String URL_RESPOSTAS = "/respostas";
	private static final String URL_LIBERAR_LOGIN_CIDADAO = URL_CADASTRO_LOGIN + "/liberar-cidadao";
	private static final String URL_RECUPERAR_SENHA_LOGIN = URL_CADASTRO_LOGIN + "/recuperar-senha";
	private static final String URL_CONFIRMAR_PERMISSAO = URL_CIDADAO + "/confirmar-liberacao-dados";
	

	private static final String MOCK_URL_CIDADAO = "/cidadaom1apimock";
	
	private String cpf;
	private String email;
	private String senha;
	private String cpfAutenticacao;
	private String senhaAutenticacao;
	private TokenDTO token;
	private int codigoHttp;
	private Empresa empresaCadastrada;
	private Empresa empresaRecebida;
	private Login loginRecebido;
	private Login loginCadastrado;
	private Login loginSistemaInterno;

	private String cpfInformado;
	private boolean pularRequisicao;

	private Login loginSalvo;

	private Login loginRetornado;

	private Cidadao cidadaoSalvo;

	private String nomeInformado;

	private String emailInformado;

	private String nomeMaeInformada;

	private String nomePaiInformado;

	private List<String> rgsInformados;

	private List<String> celularesInformados;

	private String dataNascimentoInformado;

	private boolean cidadaoEstaCadastrado;

	private Pergunta[] perguntasRetornadas;

	private String senhaCriadaoCidadao;

	private String tokenGeradoCidadao;

	private List<Resposta> respostasCriadas;

	private String senhaInformada;

	private String senhaReal;

	private PerfilEnum perfilInformado;

	private String emailReal;

	private String cnpjInformado;

	private Empresa empresaSalva;

	private Login loginEmpresaSalvo;

	private String tokenGeradoEmpresa;

	private String codigoGeradoPermissao;

	private Cidadao[] cidadaosRetornados;

	private String novaSenhaInformada;

	private PermissaoDadosCidadaoEmpresa permissaoSalva;
	private String primeiroCpfInformado;
	private Map<String, PermissaoDadosCidadaoEmpresa> permissoes;
	
	@Before
	public void init() throws IOException {
		permissaoDadosCidadaoEmpresaRepository.deleteAll();
		empresaRepository.deleteAll();
		loginRepository.deleteAll();
		cidadaoRepository.deleteAll();
		token = getTokenSistemaInterno();
		pularRequisicao = false;
		ReflectionTestUtils.setField(cidadaoDadosIntegradoApiClient, "urlCidadaoPrincipal", URL_BASE + port + MOCK_URL_CIDADAO);
		String urlToken = URL_BASE + port + URL_LIBERAR_LOGIN_CIDADAO;
		ReflectionTestUtils.setField(respostaService, "url", urlToken);
		ReflectionTestUtils.setField(loginService, "url", urlToken);
		ReflectionTestUtils.setField(cidadaoService, "url", URL_BASE + port + URL_CONFIRMAR_PERMISSAO);
	}
	
	//Servico de autenticação
	@Dado("que exista um login cadastrado cujo cpf é (.*?)$")
	public void que_exista_um_login_cadastrado_cujo_cpf_e(String cpf) {
		this.cpf = cpf;
	}
	
	@E("o e-mail é (.*?)$")
	public void o_e_mail_e(String email) {
		this.email = email;
	}
	
	@E("a senha é (.*?)$")
	public void a_senha_e(String senha) {
		this.senha = senha;
	}
	
	@Quando("for feita a requisição da autenticação passando o cpf (.*?)$")
	public void for_feita_a_requisicao_da_autenticacao_passando_o_cpf(String cpf) {
		Login login = new Login();
		login.setCpfCnpj(this.cpf);
		login.setEmail(email);
		login.setSenha(encryption.encrypt(senha));
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setDataCriacao(LocalDateTime.now());
		login.setStatus(LoginStatusEnum.CREATED_LOGIN);
		login.setLoginAtivo(true);
		login.setSenhaGeradaAutomaticamente(false);
		Login loginSalvo = loginRepository.save(login);
		Assert.assertNotNull(loginSalvo);
		Assert.assertNotNull(loginSalvo.getId());
		Assert.assertTrue(encryption.check("123", loginSalvo.getSenha()));
		this.cpfAutenticacao = cpf;
	}
	
	@E("utilizando a senha (.*?)$")
	public void utilizando_a_senha(String senha) {
		this.senhaAutenticacao = senha;
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(cpfAutenticacao);
		loginDTO.setSenha(senhaAutenticacao);
		try {
			ResponseEntity<TokenDTO> response = restTemplate.postForEntity(URL_BASE+port+URL_AUTENTICACAO, loginDTO, TokenDTO.class);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getBody());
			codigoHttp = response.getStatusCode().value();
			this.token = response.getBody();
		}catch (HttpClientErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@Entao("o código HTTP de resposta deve ser (\\d+)$")
	public void o_codigo_http_de_resposta_deve_ser(int codigo) {
		Assert.assertEquals(codigo, codigoHttp);
	}
	
	@E("o tipo do token deve ser (.*?)$")
	public void o_tipo_do_token_deve_ser(String tipoToken) {
		Assert.assertEquals(tipoToken, token.getTipo());
	}
	
	@E("o token deve estar preenchido no formato JWT$")
	public void o_token_deve_estar_preenchido_no_formato_JWT() {
		Assert.assertTrue(tokenService.isTokenValido(token.getToken()));
	}
	
	//Validação de token
	@Dado("que o usuário de e-mail (.*?) tenha um token válido$")
	public void que_o_usuario_de_email_tenha_um_token_valido(String email) {
		String senha = "123";
		Login login = new Login();
		login.setEmail(email);
		login.setSenha(encryption.encrypt(senha));
		login.setPerfil(PerfilEnum.SISTEMA_INTERNO);
		login.setDataCriacao(LocalDateTime.now());
		login.setStatus(LoginStatusEnum.CREATED_LOGIN);
		login.setLoginAtivo(true);
		login.setSenhaGeradaAutomaticamente(false);
		Login loginSalvo = loginRepository.save(login);
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(email);
		loginDTO.setSenha(senha);
		ResponseEntity<TokenDTO> response = restTemplate.postForEntity(URL_BASE+port+URL_AUTENTICACAO, loginDTO, TokenDTO.class);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getBody());
		token = response.getBody();
		Assert.assertNotNull(loginSalvo);
		Assert.assertNotNull(loginSalvo.getId());
	}
	
	@Quando("for feita a requisição para uma funcionalidade que ele possua acesso")
	public void for_feita_a_requisiçao_para_uma_funcionalidade_que_ele_possua_acesso() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("25.549.665/0001-80");
		empresa.setNome("bla");
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", token.getTipo() + " " + token.getToken());
			HttpEntity<Empresa> entity = new HttpEntity<Empresa>(empresa, headers);
			ResponseEntity<Empresa> response = restTemplate.postForEntity(URL_BASE+port+URL_CADASTRO_EMPRESA, entity, Empresa.class);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getBody());
			empresaCadastrada = response.getBody();
			codigoHttp = response.getStatusCode().value();
		}catch (HttpClientErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@Quando("for feita a requisição para uma funcionalidade que ele não possua acesso")
	public void for_feita_a_requisição_para_uma_funcionalidade_que_ele_não_possua_acesso() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("25.549.665/0001-80");
		empresa.setNome("bla");
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", token.getTipo() + " " + token.getToken());
			HttpEntity<Empresa> entity = new HttpEntity<Empresa>(empresa, headers);
			ResponseEntity<PermissaoDadosCidadaoEmpresa> response = restTemplate.postForEntity(URL_BASE+port+URL_CIDADAO+URL_LIBERAR_DADOS, entity, PermissaoDadosCidadaoEmpresa.class);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getBody());
			codigoHttp = response.getStatusCode().value();
		}catch (HttpClientErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@E("o corpo da resposta deve conter o que é esperado da funcionalidade")
	public void o_corpo_da_resposta_deve_conter_o_que_e_esperado_da_funcionalidade() {
		Assert.assertNotNull(empresaCadastrada);
		Assert.assertNotNull(empresaCadastrada.getId());
	}
	
	//-------------------------------------------//
	//-------------Cadastro de Empresa-----------//
	//-------------------------------------------//
	
	@Dado("uma empresa de CNPJ (.*?)")
	public void uma_empresa_de_CNPJ(String cnpj) {
		empresaRecebida = new Empresa();
		empresaRecebida.setCnpj(cnpj);
	}

	@E("nome da empresa (.*?)")
	public void nome_da_empresa_bla(String nome) {
	    empresaRecebida.setNome(nome);
	}

	@Quando("for feita a requisição para o cadastro dessa empresa")
	public void for_feita_a_requisição_para_o_cadastro_dessa_empresa() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token.getToken());
		HttpEntity<Empresa> entity = new HttpEntity<Empresa>(empresaRecebida, headers);
	    ResponseEntity<Empresa> resposta = restTemplate.postForEntity(URL_BASE + port + URL_CADASTRO_EMPRESA, entity, Empresa.class);
	    codigoHttp = resposta.getStatusCodeValue();
	    empresaCadastrada = resposta.getBody();
	}

	@Entao("a empresa deve ter as mesmas informações passadas")
	public void a_empresa_deve_ter_as_mesmas_informações_passadas() {
	    Assert.assertEquals(cnpjValidator.validate(empresaRecebida.getCnpj()), empresaCadastrada.getCnpj());
	    Assert.assertEquals(empresaRecebida.getNome(), empresaCadastrada.getNome());
	}

	@Entao("o ID de cadastro dessa empresa deve ter sido gerado")
	public void o_ID_de_cadastro_dessa_empresa_deve_ter_sido_gerado() {
		Assert.assertNotNull(empresaCadastrada.getId());
	}

	@E("que essa empresa possua cadastro no sistema")
	public void que_essa_empresa_possua_cadastro_no_sistema() {
		empresaRecebida.setCnpj(cnpjValidator.validate(empresaRecebida.getCnpj()));
	    empresaCadastrada = empresaRepository.save(empresaRecebida);
	}

	@Quando("for necessário alterar o nome da empresa para (.*?)")
	public void for_necessario_alterar_o_nome_da_empresa_para(String nome) {
	    empresaRecebida.setNome(nome);
	}

	@Quando("CNPJ dessa empresa para (.*?)")
	public void cnpj_dessa_empresa_para(String nome) {
		empresaRecebida.setCnpj(nome);
	}

	@Quando("seja utilizado o ID de cadastro dessa empresa")
	public void seja_utilizado_o_ID_de_cadastro_dessa_empresa() {
	    Assert.assertNotNull(empresaCadastrada.getId());
	}

	@Quando("for feita a requisição para alteração dessa empresa")
	public void for_feita_a_requisição_para_alteracao_dessa_empresa() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", token.getTipo() + " " + token.getToken());
		HttpEntity<Empresa> entity = new HttpEntity<Empresa>(empresaRecebida, headers);
		ResponseEntity<Empresa> response = restTemplate.exchange(URL_BASE + port + URL_CADASTRO_EMPRESA, HttpMethod.PUT, entity, Empresa.class);
	    codigoHttp = response.getStatusCodeValue();
	    empresaCadastrada = response.getBody();
	}

	@Entao("a empresa deve ter nome de (.*?)")
	public void a_empresa_deve_ter_nome_de_bla(String nome) {
		Assert.assertEquals(nome, empresaCadastrada.getNome());
	}

	@E("o CNPJ dessa empresa deve ser (.*?)")
	public void o_cnpj_dessa_empresa_deve_ser(String cnpj) {
		Assert.assertEquals(cnpjValidator.validate(cnpj), empresaCadastrada.getCnpj());
	}

	
	//-------------------------------------------//
	//-----Cadastro de Login da Empresa----------//
	//-------------------------------------------//
	

	private TokenDTO getTokenSistemaInterno() {
		if(loginSistemaInterno == null) {
			loginSistemaInterno = loginRepository.findByEmail("login_teste_interno@email.com");
			if(loginSistemaInterno == null) {
				loginSistemaInterno = new Login();
				loginSistemaInterno.setDataCriacao(LocalDateTime.now());
				loginSistemaInterno.setLoginAtivo(true);
				loginSistemaInterno.setSenhaGeradaAutomaticamente(false);
				loginSistemaInterno.setSenha(encryption.encrypt("123"));
				loginSistemaInterno.setEmail("login_teste_interno@email.com");
				loginSistemaInterno.setPerfil(PerfilEnum.SISTEMA_INTERNO);
				loginSistemaInterno.setStatus(LoginStatusEnum.CREATED_LOGIN);
				loginSistemaInterno = loginRepository.save(loginSistemaInterno);
			}
		}
		this.senhaAutenticacao = senha;
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(loginSistemaInterno.getEmail());
		loginDTO.setSenha("123");
		ResponseEntity<TokenDTO> response = restTemplate.postForEntity(URL_BASE+port+URL_AUTENTICACAO, loginDTO, TokenDTO.class);
		return response.getBody();
	}
	
	@Quando("for necessário o cadastro de login dessa empresa")
	public void for_necessário_o_cadastro_de_login_dessa_empresa() {
		loginRecebido = new Login();
		loginRecebido.setCpfCnpj(empresaRecebida.getCnpj());
		loginRecebido.setPerfil(PerfilEnum.EMPRESA);
	}
	
	@E("esse login deva ter e-mail (.*?)")
	public void esse_login_deva_ter_e_mail(String email){
		loginRecebido.setEmail(email);
	}
	
	@E("esse login deva ter a senha (.*?)")
	public void esse_login_deva_ter_a_senha(String senha) {
		loginRecebido.setSenha(senha);
	}
	
	@E("for feita a requisição para criação de login")
	public void for_feita_a_requisição_para_criação_de_login() throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token.getToken());
		HttpEntity<Login> entity = new HttpEntity<Login>(loginRecebido, headers);
		try {
			ResponseEntity<Login> response = restTemplate.postForEntity(URL_BASE+port+URL_CADASTRO_LOGIN_EMPRESA, entity, Login.class);
			codigoHttp = response.getStatusCodeValue();
			loginCadastrado = response.getBody();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@E("o login deve ter sido cadastrado com a mesma senha")
	public void o_login_deve_ter_sido_cadastrado_com_a_mesma_senha() {
		Assert.assertTrue(encryption.check(loginRecebido.getSenha(), loginRepository.findById(loginCadastrado.getId()).get().getPassword()));
	}
	
	@E("o login deve possuir o mesmo e-mail")
	public void o_login_deve_possuir_o_mesmo_e_mail () {
		Assert.assertEquals(loginRecebido.getEmail(), loginCadastrado.getEmail());
	}
	
	@E("o ID desse login deve ter sido gerado")
	public void o_ID_desse_login_deve_ter_sido_gerado () {
		Assert.assertNotNull(loginCadastrado.getId());
	}
	
	@Quando("for necessária a alteração desse login da empresa")
	public void for_necessaria_a_alteracao_desse_login_da_empresa () {
	}
	
	@E("seja utilizado o ID desse login")
	public void seja_utilizado_o_ID_desse_login () {
		loginRecebido.setId(loginCadastrado.getId());
	}
	
	@E("esse login deva ter o e-mail (.*?)")
	public void esse_login_deva_ter_o_e_mail (String email) {
		loginRecebido.setEmail(email);
	}
	
	@Quando("for enviada uma alteração para esse login")
	public void esse_login_deva_ter_o_e_mail () {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token.getToken());
		HttpEntity<Login> entity = new HttpEntity<Login>(loginRecebido, headers);
		try {
			ResponseEntity<Login> response = restTemplate.exchange(URL_BASE+port+URL_CADASTRO_LOGIN_EMPRESA, HttpMethod.PUT, entity, Login.class);
			codigoHttp = response.getStatusCodeValue();
			loginCadastrado = response.getBody();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@E("a senha desse login deve ser a alterada")
	public void a_senha_desse_login_deve_ser_a_alterada () {
		Assert.assertTrue(encryption.check(loginRecebido.getSenha(), loginRepository.findById(loginCadastrado.getId()).get().getPassword()));
	}
	
	@E("o e-mail desse login deve ser o alterado")
	public void o_e_mail_desse_login_deve_ser_o_alterado () {
		Assert.assertEquals(loginRecebido.getEmail(), loginCadastrado.getEmail());
	}
	
	@E("que essa empresa possua cadastro de login com e-mail (.*?)")
	public void que_essa_empresa_possua_cadastro_de_login_com_e_mail (String email){
		Login login = new Login();
		login.setCpfCnpj(empresaCadastrada.getCnpj());
		login.setEmail(email);
		login.setSenha(encryption.encrypt("123"));
		login.setPerfil(PerfilEnum.EMPRESA);
		login.setDataCriacao(LocalDateTime.now());
		login.setStatus(LoginStatusEnum.CREATED_LOGIN);
		login.setLoginAtivo(true);
		login.setSenhaGeradaAutomaticamente(false);
		loginCadastrado = loginRepository.save(login);
		loginRecebido = loginCadastrado;
	}
	
	@E("essa empresa deseja alterar o CNPJ para (.*?)")
	public void essa_empresa_deseja_alterar_o_cnpj_para (String cnpj) {
		loginRecebido.setCpfCnpj(cnpj);
	}
	
	
	//----------------------------------------------------------------//
	//--Cadastro de Login já existe no cache do M1--------------------//
	//----------------------------------------------------------------//
	
	@Dado("que exista um status de login dentro do serviço de cache do M1 com o valor de status (.*?) para o CPF (.*?)")
	public void que_exista_um_status_de_login_dentro_do_serviço_de_cache_do_M1_com_o_valor_de_status_para_o_CPF (String status, String cpf){
		pularRequisicao = true;
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Quando("for feita a requisição de busca de status passando o CPF informado")
	public void for_feita_a_requisição_de_busca_de_status_passando_o_CPF_informado () {
		if(pularRequisicao) {
			codigoHttp = 200;
			System.out.println(NAO_EXISTE_PASSO);
		}else {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + token.getToken());
			HttpEntity<Login> entity = new HttpEntity<Login>(loginRecebido, headers);
			try {
				ResponseEntity<Login> response = restTemplate.exchange(URL_BASE+port+URL_LOGIN_SITUACAO+"?cpf="+cpfInformado, HttpMethod.GET, entity, Login.class);
				if(LoginStatusEnum.SEARCHING_DATA.equals(response.getBody().getStatus())){
					codigoHttp = 500;
				}else {
					codigoHttp = response.getStatusCodeValue();
				}
				loginRetornado = response.getBody();
			}catch (HttpServerErrorException e) {
				codigoHttp = e.getStatusCode().value();
			}
		}
	}
	
	@E("o status retornado deve ser o mesmo passado")
	public void o_status_retornado_deve_ser_o_mesmo_passado () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, porém existente--------------------------//
	//--na base de dados do M2----------------------------------------------------//
	//----------------------------------------------------------------------------//
	
	@Dado("que não exista um status de login dentro do serviço de cache do M1 para o CPF (.*?)")
	public void que_nao_exista_um_status_de_login_dentro_do_servico_de_cache_do_M1_para_o_CPF (String cpf) {
		cpfInformado = cpfValidator.validate(cpf);
	}

	@E("exista um login cadastrado no M2 com esse CPF")
	public void exista_um_login_cadastrado_no_M2_com_esse_CPF () {
		Login login = new Login();
		login.setCpfCnpj(cpfInformado);
		login.setDataCriacao(LocalDateTime.now());
		login.setEmail("email@email.com");
		login.setLoginAtivo(true);
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setSenha(encryption.encrypt("123"));
		login.setSenhaGeradaAutomaticamente(false);
		login.setStatus(LoginStatusEnum.CREATED_LOGIN);
		loginSalvo = loginRepository.save(login);
		pularRequisicao = false;
	}

	@E("o status retornado deve ser (.*?)")
	public void o_status_retornado_deve_ser (String status) {
		if(!pularRequisicao) {
			Assert.assertEquals(loginRetornado.getStatus().name(), status);
		}else {
			System.out.println(NAO_EXISTE_PASSO);
		}
	}
	
	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, não existe na----------------------------//
	//--base de dados do M2, porém existe um cidadão com--------------------------//
	//--o CPF informado na base de dados do M2------------------------------------//
	//----------------------------------------------------------------------------//
	
	@E("não exista um login cadastrado no M2 com esse CPF")
	public void nao_exista_um_login_cadastrado_no_M2_com_esse_cpf () {
		Login login = loginRepository.findByCpfCnpj(cpfInformado);
		if(login != null) {
			loginRepository.deleteById(login.getId());
		}
	}

	@E("exista um cidadão cadastrado no M2 com esse CPF")
	public void exista_um_cidadao_cadastrado_no_M2_com_esse_cpf () {
		Cidadao cidadao = new Cidadao();
		cidadao.setCpf(cpfInformado);
		cidadao.setNome("TESTE");
		cidadao.setNomeMae("MAE TESTE");
		cidadao.setNomePai("PAI TESTE");
		cidadao.setDataNascimento(LocalDate.now());
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now());
		cidadao.setDigitoVerificadorReceitaFederal(cpfInformado.substring(cpfInformado.length()-1));
		cidadao.setFormacao("FORMACAO");
		cidadao.setSituacaoReceitaFederal("ATIVO");
		cidadaoSalvo = cidadaoRepository.save(cidadao);
	}

	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, não existe na base de dados do M2,-------//
	//--não existe um cidadão com o CPF informado na base de dados do M2,---------// 
	//--porém existe um cidadão principal na base de dados do M3------------------//
	//----------------------------------------------------------------------------//
	
	@E("não exista um cidadão cadastrado no M2 com esse CPF")
	public void nao_exista_um_cidadão_cadastrado_no_M2_com_esse_cpf () {
		Cidadao cidadao = cidadaoRepository.findByCpf(cpfInformado);
		if(cidadao != null) {
			cidadaoRepository.deleteById(cidadao.getId());
		}
	}

	@E("exista um cidadão cadastrado no M3 como principal com esse CPF")
	public void exista_um_cidadao_cadastrado_no_M3_como_principal_com_esse_cpf () {
		cidadaoM1APIMock.estaCadastrado();
	}
	
	@E("o M2 deve persistir esse cidadão na base de dados")
	public void o_M2_deve_persistir_esse_cidadao_na_base_de_dados () {
		Cidadao cidadao = cidadaoRepository.findByCpf(cpfInformado);
		Assert.assertNotNull(cidadao);
	}
	
	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, não existe na base de dados do M2,-------//
	//--não existe um cidadão com o CPF informado na base de dados do M2,---------//
	//--não existe um cidadão principal na base de dados do M3,-------------------//
	//--porém existe um ou mais cidadão não principal na base de dados------------//
	//----------------------------------------------------------------------------//
	
	@E("não exista um cidadão cadastrado no M3 como principal esse CPF")
	public void nao_exista_um_cidadao_cadastrado_no_M3_como_principal_esse_CPF () {
		cidadaoM1APIMock.estaCadastrado();
	}
	
	@E("existam {int} cidadãos cadastrados no M3 para esse CPF vindos de diferentes órgãos")
	public void existam_cidadaos_cadastrados_no_M3_para_esse_CPF_vindos_de_diferentes_orgaos (int quantidadeCidadãos) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o cidadão do órgão de tipo (.*?) tenha nome (.*?)")
	public void o_cidadão_do_orgao_de_tipo_tenha_nome (String tipoOrgao, String nome) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("não tenha nome da mãe cadastrada para o órgão (.*?)")
	public void nao_tenha_nome_da_mae_cadastrada_para_o_orgao (String tipoOrgao) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("a mãe se chame (.*?) no órgão (.*?)")
	public void a_mae_se_chame_no_orgao (String nomeMae, String tipoOrgao) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("não exista o nome do pai cadastrado para o órgão (.*?)")
	public void nao_exista_o_nome_do_pai_cadastrado_para_o_orgao (String tipoOrgao) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o pai se chame (.*?) no órgão (.*?)")
	public void o_cidadão_do_orgao_de_tipo_nao_tenha_nome_do_pai_cadastrado (String nomePai, String tipoOrgao) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o M3 deve ter executado o algoritmo de unificação")
	public void o_M3_deve_ter_executado_o_algoritmo_de_unificacao () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o cidadão principal com o CPF deve ter o nome igual o passado pelo órgão (.*?)")
	public void o_cidadao_principal_com_o_cpf_deve_ter_o_nome_igual_o_passado_pelo_orgao (String tipoOrgao) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o cidadão principal com o CPF deve ter o nome da mãe igual o passado pelo órgão (.*?)")
	public void o_cidadao_principal_com_o_cpf_deve_ter_o_nome_da_mae_igual_o_passado_pelo_orgao (String tipoOrgao) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o cidadão principal com o CPF deve ter o nome do pai igual o passado pelo órgão (.*?)")
	public void o_cidadao_principal_com_o_cpf_deve_ter_o_nome_do_pai_igual_o_passado_pelo_orgao (String tipoOrgao) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, não existe na base de dados do M2,-------//
	//--não existe um cidadão com o CPF informado na base de dados do M2,---------//
	//--não existe nenhum cidadão com esse CPF na base de dados do M3-------------//
	//----------------------------------------------------------------------------//
	
	
	@E("não exista nenhum cidadão cadastrado no M3")
	public void nao_exista_nenhum_cidadao_cadastrado_no_M3 () {
		cidadaoM1APIMock.naoEstaCadastrado();
	}
	
	@E("exista três sistemas órgãos dentro da solução integrada")
	public void nao_exista_nenhum_cidadao_cadastrado_no_M3 (Map<String, String> tipoOrgaos) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o nome desse cidadão dentro do órgão (.*?) é (.*?)")
	public void o_nome_desse_cidadao_dentro_do_orgao_e (String orgao, String nomeCidadao) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o RG desse cidadão dentro do órgão (.*?) é (.*?)")
	public void o_rg_desse_cidadao_dentro_do_orgao_e (String orgao, String rgCidadao) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o órgão (.*?) não possua esse cidadão cadastrado")
	public void o_orgao_nao_possua_esse_cidadao_cadastrado (String orgao) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("deve conter o código solicitação de busca")
	public void deve_conter_o_codigo_solicitacao_de_busca () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Quando("os órgãos existentes na solução integrada retornarem os dados de seus cidadãos")
	public void os_orgaos_existentes_na_solucao_integrada_retornarem_os_dados_de_seus_cidadaos () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Entao("o M3 deve persistir cada cidadão retornado")
	public void o_M3_deve_persistir_cada_cidadao_retornado () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("os dados devem ser iguais aos existentes em cada órgão")
	public void os_dados_devem_ser_iguais_aos_existentes_em_cada_orgao () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("a quantidade de cidadãos cadastradas devem ser iguais aos de cidadãos retornados")
	public void a_quantidade_de_cidadãos_cadastradas_devem_ser_iguais_aos_de_cidadãos_retornados () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("não deve existir nenhum cidadão como principal")
	public void nao_deve_existir_nenhum_cidadao_como_principal () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	//----------------------------------------------------------------------------//
	//--Criação de login através de perguntas e respostas para o cidadão----------//
	//----------------------------------------------------------------------------//
	
	//----------------------------------------------------------------------------//
	//--Fluxo de perguntas com respostas corretas---------------------------------//
	//----------------------------------------------------------------------------//

	@Dado("que existe o cidadão com o CPF (.*?) cadastrado como cidadão, mas sem login")
	public void que_existe_o_cidadão_com_o_CPF_cadastrado_com_nome (String cpf) {
		cpfInformado = cpfValidator.validate(cpf);
		cidadaoEstaCadastrado = true;
	}
	
	@E("que esse cidadão interessado em criar o login tenha o nome de (.*?)")
	public void que_esse_cidadao_interessado_em_criar_o_login_tenha_o_nome_de (String nome) {
		nomeInformado = nome;
	}
	
	@E("que esse cidadão interessado em criar o login tenha o e-mail (.*?)")
	public void que_esse_cidadao_interessado_em_criar_o_login_tenha_o_e_mail (String email) {
		emailInformado = email;
	}
	
	@E("que a mãe desse cidadão interessado em criar o login tenha o nome de (.*?)")
	public void que_a_mãe_desse_cidadão_interessado_em_criar_o_login_tenha_o_nome_de (String nomeMae) {
		nomeMaeInformada = nomeMae;
	}
	
	@E("que o pai desse cidadão interessado em criar o login tenha o nome de (.*?)")
	public void que_o_pai_desse_cidadão_interessado_em_criar_o_login_tenha_o_nome_de (String nomePai) {
		nomePaiInformado = nomePai;
	}
	
	@E("que esse cidadão interessado em criar o login tenha os rgs")
	public void que_esse_cidadao_interessado_em_criar_o_login_tenha_os_rgs (List<String> rgs) {
		rgsInformados = rgs;
	}
	
	@E("que esse cidadão interessado em criar o login tenha os celulares")
	public void que_esse_cidadao_interessado_em_criar_o_login_tenha_os_celulares (List<String> celulares) {
		celularesInformados = celulares;
	}
	
	@E("que a data de nascimento desse cidadão interessado em criar o login seja (.*?)")
	public void que_a_data_de_nascimento_desse_cidadao_interessado_em_criar_o_login_seja (String dataNascimento) {
		dataNascimentoInformado = dataNascimento;
	}
	
	@Quando("for feita a solicitação do início das perguntas")
	public void for_feita_a_solicitação_do_início_das_perguntas () {
		if(cidadaoEstaCadastrado) {
			gerarCidadao();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token.getToken());
		HttpEntity<Object> entity = new HttpEntity<>(headers);
		try {
			ResponseEntity<Pergunta[]> response = restTemplate.exchange(URL_BASE+port+URL_PERGUNTAS+"?cpf="+cpfInformado, HttpMethod.PUT, entity, Pergunta[].class);
			codigoHttp = response.getStatusCodeValue();
			perguntasRetornadas = response.getBody();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}

	private void gerarCidadao() {
		Cidadao cidadao = new Cidadao();
		cidadao.setCpf(cpfInformado);
		if(nomeInformado != null) {
			cidadao.setNome(nomeInformado);
		}else {
			cidadao.setNome("TESTE");
		}
		if(nomeMaeInformada != null) {
			cidadao.setNomeMae(nomeMaeInformada);
		}else {
			cidadao.setNomeMae("MAE TESTE");
		}
		if(nomePaiInformado != null) {
			cidadao.setNomePai(nomePaiInformado);
		}else {
			cidadao.setNomePai("PAI TESTE");
		}
		if(dataNascimentoInformado != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			cidadao.setDataNascimento(LocalDate.parse(dataNascimentoInformado, formatter));
		}else {
			cidadao.setDataNascimento(LocalDate.now());
		}
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now());
		Email email = new Email();
		email.setAtivo(true);
		email.setCidadao(cidadao);
		if(emailInformado != null) {
			email.setEmail(emailInformado);
		}else {
			email.setEmail(EMAIL_PADRAO);
		}
		cidadao.setEmails(new HashSet<>());
		cidadao.getEmails().add(email);
		if(rgsInformados != null && !rgsInformados.isEmpty()) {
			int cont = 0;
			cidadao.setRgs(new HashSet<>());
			for(String rg : rgsInformados) {
				RG o = new RG();
				o.setCidadao(cidadao);
				o.setDataEmissao(LocalDate.now());
				o.setNumero(rg);
				if(cont == 0) {
					o.setUf("SP");
					o.setSecretariaEmissao("SSP");
				}else {
					o.setUf("AC");
					o.setSecretariaEmissao("SAC");
				}
				o.setCidadao(cidadao);
				cidadao.getRgs().add(o);
				cont++;
			}
		}
		if(celularesInformados != null && !celularesInformados.isEmpty()) {
			cidadao.setTelefones(new HashSet<>());
			for(String celular : celularesInformados) {
				Telefone t = new Telefone();
				t.setCelular(true);
				t.setCidadao(cidadao);
				String[] celularSeparado = celular.split("_");
				t.setDdi(Integer.parseInt(celularSeparado[0]));
				t.setDdd(Integer.parseInt(celularSeparado[1]));
				t.setNumero(Long.parseLong(celularSeparado[2]));
				cidadao.getTelefones().add(t);
			}
		}
		cidadaoSalvo = cidadaoRepository.save(cidadao);
	}
	
	@E("deve conter todas as {int} perguntas no corpo da resposta")
	public void deve_conter_todas_as_perguntas_no_corpo_da_resposta (int quantidadePerguntas) {
		Assert.assertEquals(perguntasRetornadas.length, quantidadePerguntas);
	}
	
	@E("deve ser executado o processo de resposta das {int} perguntas informando os dados corretamente")
	public void deve_ser_executado_o_processo_de_resposta_das_perguntas_informando_os_dados_corretamente (int quantidadePerguntas) {
		gerarRespostas(null);
	}

	private void gerarRespostas(String dataNascimento) {
		List<Resposta> respostas = new ArrayList<>();
		for(Pergunta pergunta : perguntasRetornadas) {
			Resposta resposta = new Resposta();
			resposta.setCpf(cpfInformado);
			resposta.setPergunta(pergunta);
			if(ColunaEnum.CELULAR.equals(pergunta.getColuna())) {
				StringBuilder telefoneString = new StringBuilder();
				String delimiter = "___";
				for(Telefone telefone : cidadaoSalvo.getTelefones()) {
					telefoneString.append(telefone.getDdi());
					telefoneString.append(delimiter);
					telefoneString.append(telefone.getDdd());
					telefoneString.append(delimiter);
					telefoneString.append(telefone.getNumero());
					break;
				}
				resposta.setResposta(telefoneString.toString());
			}else if(ColunaEnum.DATA_NASCIMENTO.equals(pergunta.getColuna())) {
				if(dataNascimento == null) {
					resposta.setResposta(cidadaoSalvo.getDataNascimento().toString());
				}else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					resposta.setResposta(LocalDate.parse(dataNascimentoInformado, formatter).toString());
				}
			}else if(ColunaEnum.EMAIL.equals(pergunta.getColuna())) {
				String emailString = "";
				for(Email email : cidadaoSalvo.getEmails()) {
					emailString = email.getEmail();
					break;
				}
				resposta.setResposta(emailString);
			}else if(ColunaEnum.NOME.equals(pergunta.getColuna())) {
				resposta.setResposta(cidadaoSalvo.getNome());
			}else if(ColunaEnum.NOME_MAE.equals(pergunta.getColuna())) {
				resposta.setResposta(cidadaoSalvo.getNomeMae());
			}else if(ColunaEnum.NOME_PAI.equals(pergunta.getColuna())) {
				resposta.setResposta(cidadaoSalvo.getNomePai());
			}else if(ColunaEnum.QUAIS_RGS_UFS.equals(pergunta.getColuna())) {
				StringBuilder rgsString = new StringBuilder();
				String delimiterRG = "___";
				String delimiterNumeroUF = "__";
				int cont = 1;
				for(RG rg : cidadaoSalvo.getRgs()) {
					rgsString.append(rg.getNumero());
					rgsString.append(delimiterNumeroUF);
					rgsString.append(rg.getUf());
					if(cont < cidadaoSalvo.getRgs().size()) {
						rgsString.append(delimiterRG);
					}
				}
				resposta.setResposta(rgsString.toString());
			}else if(ColunaEnum.QUANTOS_RGS_POSSUI.equals(pergunta.getColuna())) {
				resposta.setResposta(String.valueOf(cidadaoSalvo.getRgs().size()));
			}
			respostas.add(resposta);
		}
		this.respostasCriadas = respostas;
	}
	
	@Quando("for feita a resposta de todas as perguntas")
	public void for_feita_a_resposta_de_todas_as_perguntas () {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token.getToken());
		HttpEntity<List<Resposta>> entity = new HttpEntity<List<Resposta>>(respostasCriadas, headers);
		try {
			ResponseEntity<Resposta[]> response = restTemplate.exchange(URL_BASE+port+URL_RESPOSTAS+"?cpf="+cpfInformado, HttpMethod.PUT, entity, Resposta[].class);
			codigoHttp = response.getStatusCodeValue();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@E("o cidadão deve receber um e-mail")
	public void o_cidadão_deve_receber_um_e_mail () {
		validaRecebimentoSenha();
	}

	private void validaRecebimentoSenha() {
		SimpleMailMessage simple = javaMailSender.getMailSent();
		Assert.assertNotNull(simple);
		String[] recuperarSenha = simple.getText().split("! A senha gerada foi: ");
		senhaCriadaoCidadao = recuperarSenha[1].substring(0, recuperarSenha[1].indexOf("."));
		String[] recuperarToken = simple.getText().split("Acesse o link :")[1].split(" para liberar o seu login!");
		tokenGeradoCidadao = recuperarToken[0];
	}
	
	@E("o login deve ter sido criado")
	public void o_login_deve_ter_sido_criado () {
		loginSalvo = loginRepository.findByCpfCnpj(cpfInformado);
		Assert.assertNotNull(loginSalvo);
	}
	
	@E("o login deve estar como inativo")
	public void o_login_deve_estar_como_inativo () {
		Assert.assertFalse(loginSalvo.isEnabled());
	}
	
	@E("a senha deve estar como gerada automaticamente")
	public void a_senha_deve_estar_como_gerada_automaticamente () {
		Assert.assertTrue(loginSalvo.isSenhaGeradaAutomaticamente());
	}
	
	@E("ele não deve conseguir logar sem antes confirmar o e-mail")
	public void ele_nao_deve_conseguir_logar_sem_antes_confirmar_o_e_mail () {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(loginSalvo.getEmail());
		loginDTO.setSenha(senhaCriadaoCidadao);
		try {
			codigoHttp = 200;
			restTemplate.postForEntity(URL_BASE+port+URL_AUTENTICACAO, loginDTO, TokenDTO.class);
		}catch (HttpClientErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
		Assert.assertEquals(400, codigoHttp);
	}
	
	@Quando("o cidadão clicar no token do e-mail recebido")
	public void o_cidadao_clicar_no_token_do_e_mail_recebido () {
		liberarTokenEmail();
	}

	private void liberarTokenEmail() {
		ResponseEntity<String> response = restTemplate.getForEntity(tokenGeradoCidadao, String.class);
		Assert.assertEquals("Login liberado!", response.getBody());
	}
	
	@Entao("o login deve ser liberado")
	public void o_login_deve_ser_liberado () {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(loginSalvo.getEmail());
		loginDTO.setSenha(senhaCriadaoCidadao);
		ResponseEntity<TokenDTO> response = restTemplate.postForEntity(URL_BASE+port+URL_AUTENTICACAO, loginDTO, TokenDTO.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertNotNull(response.getBody().getToken());
		Assert.assertNotNull(response.getBody().getTipo());
	}
	
	//----------------------------------------------------------------------------//
	//--Fluxo de perguntas com uma resposta incorreta-----------------------------//
	//----------------------------------------------------------------------------//
	
	@Entao("deve ser respondido a data de nascimento como (.*?)")
	public void deve_ser_respondido_a_data_de_nascimento_como (String dataNascimento) {
		dataNascimentoInformado = dataNascimento;
	}
	
	@Entao("deve ser executado o processo de resposta das outras 7 perguntas informando os dados corretamente")
	public void deve_ser_executado_o_processo_de_resposta_das_outras_7_perguntas_informando_os_dados_corretamente () {
		gerarRespostas(dataNascimentoInformado);
	}
	
	@E("o cidadão não deve ser receber um e-mail")
	public void o_cidadão_não_deve_ser_receber_um_e_mail (){
		Assert.assertNull(javaMailSender.getMailSent());
	}
	
	@E("o login não deve ter sido criado")
	public void o_login_nao_deve_ter_sido_criado (){
		Login login = loginRepository.findByCpfCnpj(cpfInformado);
		Assert.assertNull(login);
	}
	
	//----------------------------------------------------------------------------//
	//--Login do cidadão----------------------------------------------------------//
	//----------------------------------------------------------------------------//
	
	
	//----------------------------------------------------------------------------//
	//--Efetuar login do cidadão--------------------------------------------------//
	//----------------------------------------------------------------------------//
	
	@Dado("que existe um login para o cidadão com o CPF (.*?)")
	public void que_existe_um_login_para_o_cidadao_com_o_CPF (String cpf) {
		cpfInformado = cpfValidator.validate(cpf);
		perfilInformado = PerfilEnum.CIDADAO;
	}

	@E("a senha do login desse cidadão é (.*?)")
	public void a_senha_do_login_desse_cidadão_e (String senhaReal) {
		this.senhaReal = senhaReal;
	}
	
	@E("esse login esteja cadastrado na base de dados")
	public void esse_login_esteja_cadastrado_na_base_de_dados () {
		if(cpfInformado != null) {
			Login login = new Login();
			login.setCpfCnpj(cpfInformado);
			if(emailReal != null) {
				login.setEmail(emailReal);
			}else {
				login.setEmail("teste@teste.com");
			}
			login.setLoginAtivo(true);
			login.setPerfil(perfilInformado);
			if(senhaReal != null) {
				login.setSenha(encryption.encrypt(senhaReal));
			}else {
				login.setSenha(encryption.encrypt("123"));
			}
			login.setSenhaGeradaAutomaticamente(false);
			login.setStatus(LoginStatusEnum.CREATED_LOGIN);
			login.setDataCriacao(LocalDateTime.now());
			loginSalvo = loginRepository.save(login);
		}else if(loginSalvo != null && senhaReal != null) {
			loginSalvo.setSenha(encryption.encrypt(senhaReal));
			loginSalvo = loginRepository.save(loginSalvo);
		}
	}

	@E("ele informe a senha (.*?)")
	public void ele_informe_a_senha (String senhaInformada) {
		this.senhaInformada = senhaInformada;
	}
	
	@Quando("for feita a requisicao para efetuar o login")
	public void for_feita_a_requisicao_para_efetuar_o_login () {
		try {
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setUsername(cpfInformado);
			loginDTO.setSenha(senhaInformada);
			ResponseEntity<TokenDTO> response = restTemplate.postForEntity(URL_BASE+port+URL_AUTENTICACAO_CIDADAO, loginDTO, TokenDTO.class);
			codigoHttp = response.getStatusCode().value();
			tokenGeradoCidadao = response.getBody().getToken();
		}catch (HttpServerErrorException | HttpClientErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}

	@E("o login deve ser feito corretamente")
	public void o_login_deve_ser_feito_corretamente () {
		Assert.assertNotNull(tokenGeradoCidadao);
	}
	
	//----------------------------------------------------------------------------//
	//--Tentativa de login de uma empresa pelo aplicativo-------------------------//
	//----------------------------------------------------------------------------//
	
	@Dado("que existe um login para a empresa de CNPJ (.*?)")
	public void que_existe_um_login_para_a_empresa_de_cnpj (String cnpj) {
		cpfInformado = cnpjValidator.validate(cnpj);
		perfilInformado = PerfilEnum.EMPRESA;
	}
	
	@E("a senha do login dessa empresa é (.*?)")
	public void a_senha_do_login_dessa_empresa_e (String senhaReal) {
		this.senhaReal = senhaReal;
	}
	
	@E("essa empresa informe a senha (.*?)")
	public void essa_empresa_informe_a_senha (String senhaInformada) {
		this.senhaInformada = senhaInformada; 
	}
	
	@E("o login não deve ser permitido")
	public void o_login_não_deve_ser_permitido () {
		Assert.assertNull(tokenGeradoCidadao);
	}
	
	//----------------------------------------------------------------------------//
	//--Recuperação de senha do cidadão-------------------------------------------//
	//----------------------------------------------------------------------------//
	
	//----------------------------------------------------------------------------//
	//--Recuperação de senha realizado com sucesso--------------------------------//
	//----------------------------------------------------------------------------//
	
	@E("esse login tenha e-mail (.*?)")
	public void esse_login_tenha_e_mail (String email){
		emailReal = email;
	}

	@E("ele informe o e-mail (.*?)")
	public void ele_informe_o_e_mail (String email){
		emailInformado = email;
	}

	@E("ele informe o CPF (.*?)")
	public void ele_informe_o_CPF (String cpf){
		cpfInformado = cpfValidator.validate(cpf);
	}

	@Quando("for feita a requisição para o processo de recuperação de senha")
	public void for_feita_a_requisicao_para_o_processo_de_recuperacao_de_senha (){
		try {
			Login login = new Login();
			login.setCpfCnpj(cpfInformado);
			login.setEmail(emailInformado);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", token.getTipo() + " " + token.getToken());
			HttpEntity<Login> entity = new HttpEntity<Login>(login, headers);
			ResponseEntity<Login> response = restTemplate.exchange(URL_BASE+port+URL_RECUPERAR_SENHA_LOGIN, HttpMethod.PUT, entity, Login.class);
			codigoHttp = response.getStatusCode().value();
			loginRetornado = response.getBody();
		}catch (HttpServerErrorException | HttpClientErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}

	@E("o login deve ter a senha alterada")
	public void o_login_deve_ter_a_senha_alterada (){
		loginSalvo = loginRepository.findByCpfCnpj(cpfInformado);
		String senhaCriptografada = loginSalvo.getSenha();
		Assert.assertFalse(encryption.check("123", senhaCriptografada));
	}

	@E("o login deve ficar inativo")
	public void o_login_deve_ficar_inativo (){
		Assert.assertFalse(loginSalvo.isEnabled());
	}

	@E("o login deve ficar como geração de senha automático")
	public void o_login_deve_ficar_como_geracao_de_senha_automatico (){
		Assert.assertTrue(loginSalvo.isSenhaGeradaAutomaticamente());
	}

	@E("o cidadão deve receber um e-mail de confirmação")
	public void o_cidadao_deve_receber_um_e_mail_de_confirmacao (){
		validaRecebimentoSenha();
	}

	@Quando("o cidadão clicar no token que recebeu por e-mail")
	public void o_cidadao_clicar_no_token_que_recebeu_por_e_mail (){
		liberarTokenEmail();
	}
	
	@E("o cidadão poderá logar utilizando a senha recebida")
	public void o_cidadao_podera_logar_utilizando_a_senha_recebida () {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(loginSalvo.getEmail());
		loginDTO.setSenha(senhaCriadaoCidadao);
		ResponseEntity<TokenDTO> response = restTemplate.postForEntity(URL_BASE+port+URL_AUTENTICACAO, loginDTO, TokenDTO.class);
		Assert.assertEquals(200, response.getStatusCode().value());
		Assert.assertNotNull(response.getBody().getToken());
		Assert.assertNotNull(response.getBody().getTipo());
	}

	//----------------------------------------------------------------------------//
	//--Recuperação de senha com e-mail divergente--------------------------------//
	//----------------------------------------------------------------------------//
	
	@E("o login não deve ter a senha alterada")
	public void o_login_nao_deve_ter_a_senha_alterada () {
		loginSalvo = loginRepository.findByCpfCnpj(cpfInformado);
		if(loginSalvo == null) {
			loginSalvo = loginRepository.findByEmail(emailReal);
		}
		String senhaCriptografada = loginSalvo.getSenha();
		Assert.assertTrue(encryption.check("123", senhaCriptografada));
	}
	
	@E("o login não deve ficar inativo")
	public void o_login_nao_deve_ficar_inativo () {
		Assert.assertTrue(loginSalvo.isEnabled());
	}
	
	@E("o login não deve ficar como geração de senha automático")
	public void o_login_nao_deve_ficar_como_geracao_de_senha_automatico () {
		Assert.assertFalse(loginSalvo.isSenhaGeradaAutomaticamente());
	}
	
	@E("o cidadão não deve receber um e-mail de confirmação")
	public void o_cidadao_nao_deve_receber_um_e_mail_de_confirmacao () {
		Assert.assertNull(javaMailSender.getMailSent());
	}

	//----------------------------------------------------------------------------//
	//--Recuperação de senha com CPF divergente-----------------------------------//
	//----------------------------------------------------------------------------//

	
	//----------------------------------------------------------------------------//
	//--Liberação do acesso aos dados do cidadão, pelo cidadão para empresa-------//
	//----------------------------------------------------------------------------//
	
	//----------------------------------------------------------------------------//
	//--Fluxo de liberação concluído com sucesso----------------------------------//
	//----------------------------------------------------------------------------//
	
	@Dado("que exista um cidadão logado")
	public void que_exista_um_cidadao_logado () {
		Cidadao cidadao = new Cidadao();
		cidadao.setCpf(cpfValidator.validate("452.860.900-28"));
		cidadao.setDataInscricaoReceitaFederal(LocalDate.now());
		cidadao.setDataNascimento(LocalDate.now());
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now());
		cidadao.setDigitoVerificadorReceitaFederal("8");
		cidadao.setFormacao("GRADUADO");
		cidadao.setNome("TESTE");
		cidadao.setNomeMae("MAE TESTE");
		cidadao.setNomePai("PAI TESTE");
		Email email = new Email();
		email.setAtivo(true);
		email.setCidadao(cidadao);
		email.setEmail(EMAIL_PADRAO);
		cidadao.setEmails(new HashSet<>());
		cidadao.getEmails().add(email);
		cidadao.setProfissao("ANALISTA DE SISTEMAS");
		cidadao.setSituacaoReceitaFederal("ATIVO");
		cidadaoSalvo = cidadaoRepository.save(cidadao);
		Login login = new Login();
		login.setCpfCnpj(cidadaoSalvo.getCpf());
		login.setDataCriacao(LocalDateTime.now());
		login.setEmail(email.getEmail());
		login.setLoginAtivo(true);
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setSenha(encryption.encrypt("123"));
		login.setSenhaGeradaAutomaticamente(false);
		login.setStatus(LoginStatusEnum.CREATED_LOGIN);
		loginSalvo = loginRepository.save(login);
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(loginSalvo.getEmail());
		loginDTO.setSenha("123");
		ResponseEntity<TokenDTO> response = restTemplate.postForEntity(URL_BASE+port+URL_AUTENTICACAO, loginDTO, TokenDTO.class);
		tokenGeradoCidadao = response.getBody().getToken();
	}
	
	@E("ele informe o CNPJ (.*?)")
	public void ele_informe_o_cnpj (String cnpj) {
		cnpjInformado = cnpjValidator.validate(cnpj);
	}
	
	@E("essa empresa esteja cadastrada")
	public void essa_empresa_esteja_cadastrada () {
		gerarEmpresa();
		gerarLoginEmpresaSalva();
		logarEmpresaSalva();
	}

	private void logarEmpresaSalva() {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUsername(loginEmpresaSalvo.getCpfCnpj());
		loginDTO.setSenha("123");
		ResponseEntity<TokenDTO> response = restTemplate.postForEntity(URL_BASE+port+URL_AUTENTICACAO, loginDTO, TokenDTO.class);
		tokenGeradoEmpresa = response.getBody().getToken();
	}

	private void gerarLoginEmpresaSalva() {
		Login login = new Login();
		login.setCpfCnpj(empresaSalva.getCnpj());
		login.setDataCriacao(LocalDateTime.now());
		login.setLoginAtivo(true);
		login.setPerfil(PerfilEnum.EMPRESA);
		login.setSenha(encryption.encrypt("123"));
		login.setSenhaGeradaAutomaticamente(false);
		login.setStatus(LoginStatusEnum.CREATED_LOGIN);
		login.setEmail("emailteste@empresa.com");
		loginEmpresaSalvo = loginRepository.save(login);
	}

	private void gerarEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cnpjInformado);
		empresa.setNome("EMPRESA TESTE");
		empresaSalva = empresaRepository.save(empresa);
	}
	
	@Quando("for feita a requisição para o processo de liberação de dados para empresa")
	public void for_feita_a_requisição_para_o_processo_de_liberação_de_dados_para_empresa () {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + tokenGeradoCidadao);
			Empresa empresa = new Empresa();
			empresa.setCnpj(cnpjInformado);
			HttpEntity<Empresa> entity = new HttpEntity<Empresa>(empresa, headers);
			ResponseEntity<PermissaoDadosCidadaoEmpresa> response = restTemplate.exchange(URL_BASE+port+URL_CIDADAO+URL_LIBERAR_DADOS, HttpMethod.POST, entity, PermissaoDadosCidadaoEmpresa.class);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getBody());
			codigoHttp = response.getStatusCode().value();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@E("o cidadão deve receber um e-mail com o token de confirmação")
	public void o_cidadao_deve_receber_um_e_mail_com_o_token_de_confirmacao () {
		Assert.assertNotNull(javaMailSender.getMailSent());
		tokenGeradoCidadao = javaMailSender.getMailSent().getText().split(" acesse o link :")[1].split(". Esse link só ficará valido pelos próximos 9 minutos")[0];
	}
	
	@E("a empresa ainda não deve conseguir acessar os dados")
	public void a_empresa_ainda_não_deve_conseguir_acessar_os_dados () {
		codigoHttp = 200;
		try {
			buscarCidadao();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
		Assert.assertEquals(500, codigoHttp);
	}
	
	@Quando("o cidadão clicar no token")
	public void o_cidadao_clicar_no_token () {
		ResponseEntity<String> response = restTemplate.getForEntity(tokenGeradoCidadao, String.class);
		Assert.assertTrue(response.getBody().contains("Pode utilizar o código:"));
		codigoGeradoPermissao = response.getBody().replace("Pode utilizar o código: ", "").split(" para liberar acesso")[0];
	}
	
	@Entao("será feito o procedimento de confirmação liberando o acesso para empresa")
	public void sera_feito_o_procedimento_de_confirmacao_liberando_o_acesso_para_empresa () {
		Optional<PermissaoDadosCidadaoEmpresa> optionalPermissao = permissaoDadosCidadaoEmpresaRepository.findByTokenAcesso(codigoGeradoPermissao);
		Assert.assertNotNull(optionalPermissao);
		Assert.assertTrue(optionalPermissao.isPresent());
		PermissaoDadosCidadaoEmpresa permissao = optionalPermissao.get();
		Assert.assertTrue(permissao.getCidadao().getCpf().equals(loginSalvo.getCpfCnpj()));
		Assert.assertNotNull(permissao.getDataConfirmacao());
		Assert.assertEquals(codigoGeradoPermissao, permissao.getTokenAcesso());
	}
	
	@Quando("a empresa tentar buscar os dados do cidadão através de sua credencial e CPF do cidadão")
	public void a_empresa_tentar_buscar_os_dados_do_cidadao_atraves_de_sua_credencial_e_cpf_do_cidadao () {
		ResponseEntity<Cidadao[]> response = buscarCidadao();
		codigoHttp = response.getStatusCode().value();
		cidadaosRetornados = response.getBody();
	}

	private ResponseEntity<Cidadao[]> buscarCidadao() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + tokenGeradoEmpresa);
		HttpEntity<Object> entity = new HttpEntity<>(headers);
		ResponseEntity<Cidadao[]> response = restTemplate.exchange(URL_BASE+port+URL_CIDADAO+"?cpf="+loginSalvo.getCpfCnpj()+"&codigo="+codigoGeradoPermissao, HttpMethod.GET, entity, Cidadao[].class);
		return response;
	}
	
	@Entao("ela deve conseguir acessar esses dados")
	public void ela_deve_conseguir_acessar_esses_dados () {
		Assert.assertEquals(200, codigoHttp);
		Assert.assertNotNull(cidadaosRetornados);
		Assert.assertEquals(1, cidadaosRetornados.length);
		Assert.assertEquals(loginSalvo.getCpfCnpj(), cidadaosRetornados[0].getCpf());
	}

	//----------------------------------------------------------------------------//
	//--Fluxo de liberação CNPJ não existe----------------------------------------//
	//----------------------------------------------------------------------------//
	
	@E("essa empresa não esteja cadastrada")
	public void essa_empresa_nao_esteja_cadastrada () {
		Empresa empresa = empresaRepository.findByCnpj(cnpjInformado);
		if(empresa != null) {
			empresaRepository.deleteById(empresa.getId());
		}
	}
	
	@E("o cidadão não deve receber um e-mail com o token de confirmação")
	public void o_cidadao_nao_deve_receber_um_e_mail_com_o_token_de_confirmacao () {
		Assert.assertNull(javaMailSender.getMailSent());
	}
	
	//----------------------------------------------------------------------------//
	//--Alteração de senha do cidadão---------------------------------------------//
	//----------------------------------------------------------------------------//
	
	//----------------------------------------------------------------------------//
	//--Fluxo de alteração de senha concluído com sucesso-------------------------//
	//----------------------------------------------------------------------------//
	
	@E("sua senha seja (.*?)")
	public void sua_senha_seja (String senha) {
		senhaReal = senha;
	}
	
	@E("ele informe como senha atual (.*?)")
	public void ele_informe_como_senha_atual (String senhaAtual) {
		senhaInformada = senhaAtual;
	}
	
	@E("ele informe como nova senha (.*?)")
	public void ele_informe_como_nova_senha (String novaSenha) {
		novaSenhaInformada = novaSenha;
	}
	
	@Quando("for feita a requisição para o processo de alteração de senha")
	public void for_feita_a_requisicao_para_o_processo_de_alteracao_de_senha () {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + tokenGeradoCidadao);
			TrocaSenhaLoginDTO trocarSenhaDTO = new TrocaSenhaLoginDTO();
			trocarSenhaDTO.setSenha(senhaInformada);
			trocarSenhaDTO.setNovaSenha(novaSenhaInformada);
			HttpEntity<TrocaSenhaLoginDTO> entity = new HttpEntity<TrocaSenhaLoginDTO>(trocarSenhaDTO, headers);
			ResponseEntity<Login> response = restTemplate.exchange(URL_BASE+port+URL_LOGIN_TROCAR_SENHA, HttpMethod.PUT, entity, Login.class);
			loginRetornado = response.getBody();
			codigoHttp = response.getStatusCode().value();
		}catch (HttpServerErrorException | HttpClientErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@E("o cidadão deve ter a senha alterada")
	public void o_cidadao_deve_ter_a_senha_alterada () {
		loginSalvo = loginRepository.findByCpfCnpj(loginRetornado.getCpfCnpj());
		Assert.assertTrue(encryption.check(novaSenhaInformada, loginSalvo.getSenha()));
	}
	
	@E("o cidadão deve voltar para tela de login quando confirmar")
	public void o_cidadao_deve_voltar_para_tela_de_login_quando_confirmar () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	//----------------------------------------------------------------------------//
	//--Fluxo de alteração com senha atual divergente-----------------------------//
	//----------------------------------------------------------------------------//
	
	@E("o cidadão não deve ter a senha alterada")
	public void o_cidadao_nao_deve_ter_a_senha_alterada () {
		Assert.assertTrue(encryption.check(senhaReal, loginSalvo.getSenha()));
	}
	
	//----------------------------------------------------------------------------//
	//--Consulta dos dados do cidadão pela empresa liberada-----------------------//
	//----------------------------------------------------------------------------//
	
	//----------------------------------------------------------------------------//
	//--Empresa consulta um cidadão com acesso------------------------------------//
	//----------------------------------------------------------------------------//
	
	@Dado("que exista a permissão de acesso confirmada do CPF (.*?) para a empresa de CNPJ (.*?)")
	public void que_exista_a_permissão_de_acesso_confirmada_do_cpf_para_a_empresa_de_cnpj (String cpf, String cnpj){
		gerarCidadaoLoginCidadao(cpf);
		gerarEmpresaLoginEmpresa(cnpj);
		gerarPermissaoCidadaoEmpresa(true);
	}

	private void gerarPermissaoCidadaoEmpresa(boolean confirmacao) {
		PermissaoDadosCidadaoEmpresa permissao = new PermissaoDadosCidadaoEmpresa();
		permissao.setCidadao(cidadaoSalvo);
		permissao.setEmpresa(empresaSalva);
		permissao.setConfirmado(confirmacao);
		if(confirmacao) {
			permissao.setDataConfirmacao(LocalDateTime.now());
		}
		permissao.setDataExpiracaoToken(LocalDateTime.now().plusMinutes(300l));
		permissao.setTokenAcesso(UUID.randomUUID().toString());
		permissaoSalva = permissaoDadosCidadaoEmpresaRepository.save(permissao);
	}

	private void gerarEmpresaLoginEmpresa(String cnpj) {
		cnpjInformado = cnpjValidator.validate(cnpj);
		gerarEmpresa();
		gerarLoginEmpresaSalva();
		logarEmpresaSalva();
	}

	private void gerarCidadaoLoginCidadao(String cpf) {
		cpfInformado = cpfValidator.validate(cpf);
		gerarCidadao();
		Login login = new Login();
		login.setCpfCnpj(cpfInformado);
		Login loginEncontrado = loginRepository.findByEmail(EMAIL_PADRAO);
		if(loginEncontrado != null) {
			for(int i=0 ; i<10; i++) {
				loginEncontrado = loginRepository.findByEmail(EMAIL_PADRAO+i);
				if(loginEncontrado == null) {
					login.setEmail(EMAIL_PADRAO + i);
					break;
				}
			}
		}else {
			login.setEmail(EMAIL_PADRAO);
		}
		login.setLoginAtivo(true);
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setSenha(encryption.encrypt("123"));
		login.setSenhaGeradaAutomaticamente(false);
		login.setStatus(LoginStatusEnum.CREATED_LOGIN);
		login.setDataCriacao(LocalDateTime.now());
		loginSalvo = loginRepository.save(login);
	}
	
	@E("a empresa esteja autenticada")
	public void a_empresa_esteja_autenticada () {
		logarEmpresaSalva();
	}
	
	@E("ela possua o código de acesso")
	public void ela_possua_o_codigo_de_acesso () {
		codigoGeradoPermissao = permissaoSalva.getTokenAcesso();
	}
	
	@Quando("for feita a requisição para busca dos dados do cidadão passando o código de acesso e o CPF do cidadão informado")
	public void for_feita_a_requisicao_para_busca_dos_dados_do_cidadao_passando_o_codigo_de_acesso_e_o_cpf_do_cidadao_informado () {
		try {
			ResponseEntity<Cidadao[]> cidadaos = buscarCidadao();
			codigoHttp = cidadaos.getStatusCode().value();
			cidadaosRetornados = cidadaos.getBody();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@E("a empresa deve receber os dados do cidadão")
	public void a_empresa_deve_receber_os_dados_do_cidadao () {
		Assert.assertNotNull(cidadaosRetornados);
		Assert.assertEquals(1, cidadaosRetornados.length);
	}

	//----------------------------------------------------------------------------//
	//--Empresa tenta consultar um cidadão sem permissão--------------------------//
	//----------------------------------------------------------------------------//
	
	@Dado("que não exista permissão do CPF (.*?) para a empresa de CNPJ (.*?)")
	public void que_nao_exista_permissao_do_cpf_para_a_empresa_de_cnpj (String cpf, String cnpj){
		primeiroCpfInformado = cpfValidator.validate(cpf);
		gerarCidadaoLoginCidadao(cpf);
		gerarEmpresaLoginEmpresa(cnpj);
	}
	
	@E("existe permissão de acesso com confirmação de acesso para o CPF (.*?) para essa empresa")
	public void existe_permissao_de_acesso_com_confirmacao_de_acesso_para_o_cpf_para_essa_empresa (String cpf) {
		gerarCidadaoLoginCidadao(cpf);
		gerarPermissaoCidadaoEmpresa(true);
	}
	
	@E("ela possua o código de acesso de um segundo cidadão")
	public void ela_possua_o_codigo_de_acesso_de_um_segundo_cidadao () {
		codigoGeradoPermissao = permissaoSalva.getTokenAcesso();
	}
	
	@Quando("for feita a requisição para busca dos dados do cidadão passando o código de acesso do segundo cidadão e o CPF do primeiro cidadão")
	public void for_feita_a_requisicao_para_busca_dos_dados_do_cidadao_passando_o_codigo_de_acesso_do_segundo_cidadao_e_o_cpf_do_primeiro_cidadao () {
		codigoHttp = 200;
		try {
			loginSalvo = loginRepository.findByCpfCnpj(primeiroCpfInformado);
			buscarCidadao();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@E("a empresa não deve receber os dados do cidadão")
	public void a_empresa_nao_deve_receber_os_dados_do_cidadao () {
		Assert.assertNull(cidadaosRetornados);
	}

	//----------------------------------------------------------------------------//
	//--Empresa tenta consultar um cidadão sem confirmação de permissão-----------//
	//----------------------------------------------------------------------------//
	
	@Dado("que exista permissão sem confirmação do CPF (.*?) para a empresa de CNPJ (.*?)")
	public void que_exista_permissao_sem_confirmacao_do_cpf_para_a_empresa_de_cnpj (String cpf, String cnpj){
		primeiroCpfInformado = cpfValidator.validate(cpf);
		gerarCidadaoLoginCidadao(cpf);
		gerarEmpresaLoginEmpresa(cnpj);
		gerarPermissaoCidadaoEmpresa(false);
	}
	
	@E("ela possua o código de acesso do primeiro cidadão")
	public void ela_possua_o_codigo_de_acesso_do_primeiro_cidadao () {
		Cidadao primeiroCidadao = cidadaoRepository.findByCpf(primeiroCpfInformado);
		if(permissoes == null) {
			permissoes = new HashMap<>();
		}
		permissoes.put(primeiroCpfInformado, permissaoDadosCidadaoEmpresaRepository.findByEmpresaIdAndCidadaoId(empresaSalva.getId(), primeiroCidadao.getId()).get(0));
	}
	
	@E("ela possua o código de acesso do segundo cidadão")
	public void ela_possua_o_código_de_acesso_do_segundo_cidadao () {
		if(permissoes == null) {
			permissoes = new HashMap<>();
		}
		permissoes.put(cidadaoSalvo.getCpf(), permissaoDadosCidadaoEmpresaRepository.findByEmpresaIdAndCidadaoId(empresaSalva.getId(), cidadaoSalvo.getId()).get(0));
	}
	
	@Quando("for feita a requisição para busca dos dados do cidadão passando o código de acesso do primeiro cidadão e o CPF do primeiro cidadão")
	public void for_feita_a_requisicao_para_busca_dos_dados_do_cidadao_passando_o_codigo_de_acesso_do_primeiro_cidadao_e_o_cpf_do_primeiro_cidadao () {
		codigoHttp = 200;
		try {
			loginSalvo = loginRepository.findByCpfCnpj(primeiroCpfInformado);
			codigoGeradoPermissao = permissoes.get(primeiroCpfInformado).getTokenAcesso();
			buscarCidadao();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
}
