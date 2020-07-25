package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd.steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.Application;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.AutenticadorApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.CidadaoApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.LoginApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.PerguntaApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.RespostaApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd.mock.AutenticadorM2APIMock;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd.mock.CidadaoM2APIMock;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd.mock.LoginM2APIMock;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd.mock.PerguntaM2APIMock;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd.mock.RespostaM2APIMock;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.CidadaoPergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.ColunaEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginCheckStatus;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginStatusEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Permissao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.RecuperarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.SolicitarLiberacao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.StatusPerguntaFlow;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TrocarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.CidadaoPerguntaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.CidadaoRespostaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.LoginCheckStatusRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.LoginTokenInternoRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.LoginTokenRepository;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import redis.embedded.RedisServer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@DirtiesContext
public class CucumberTestSteps {
	
	@Value("${spring.redis.port}")
	private int redisPort;
	
	@LocalServerPort
	private int port;
	
	private RedisServer redisServer;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private LoginCheckStatusRepository loginCheckStatusRepository;
	
	@Autowired
	private LoginApiClient loginApiClient;
	
	@Autowired
	private LoginM2APIMock loginM2APIMock;
	
	@Autowired
	private AutenticadorApiClient autenticadorApiClient;
	
	@Autowired
	private PerguntaM2APIMock perguntaM2APIMock;
	
	@Autowired
	private PerguntaApiClient perguntaApiClient;
	
	@Autowired
	private CidadaoPerguntaRepository cidadaoPerguntaRepository;
	
	@Autowired
	private CidadaoRespostaRepository cidadaoRespostaRepository;
	
	@Autowired
	private LoginTokenInternoRepository loginTokenInternoRepository;
	
	@Autowired
	private LoginTokenRepository loginTokenRepository;
	
	@Autowired
	private RespostaApiClient respostaApiClient;
	
	@Autowired
	private RespostaM2APIMock respostaM2APIMock;
	
	@Autowired
	private AutenticadorM2APIMock autenticadorM2APIMock;
	
	@Autowired
	private CidadaoApiClient cidadaoApiClient;
	
	@Autowired
	private CidadaoM2APIMock cidadaoM2APIMock;
	
	private static final String NAO_EXISTE_PASSO = "Status não será implementado nesse micro serviço, pois não existe esse passo.";
	
	private static final String URL_BASE = "http://localhost:";
	private static final String URL_LOGIN = "/login";
	private static final String URL_CIDADAO = "/cidadaos";
	private static final String URL_CIDADAO_LIBERAR_EMPRESA = URL_CIDADAO+"/liberar-dados-empresa";
	private static final String URL_PERGUNTAS = "/perguntas";
	private static final String URL_LOGIN_RECUPERAR_SENHA = URL_LOGIN + "/recuperar-senha";
	private static final String URL_LOGIN_TROCAR_SENHA = URL_LOGIN + "/trocar-senha";
	
	//MOCKs
	private static final String URL_LOGIN_M2 = "/loginm2mock";
	private static final String URL_LOGIN_RECUPERAR_SENHA_M2 = URL_LOGIN_M2+"/recuperar";
	private static final String URL_LOGIN_TROCAR_SENHA_M2 = URL_LOGIN_M2+"/trocar";
	private static final String URL_AUTH_M2 = "/authm2mock";
	private static final String URL_PERGUNTA_M2 = "/perguntam2mock";
	private static final String URL_RESPOSTA_M2 = "/respostam2mock";
	private static final String URL_CIDADAO_M2 = "/cidadaom2mock";
	
	private int codigoHttp;
	private LoginCheckStatus loginCheckStatusCadastrado;
	private LoginCheckStatus loginCheckStatusRetornado;
	private String cpfInformado;
	private String emailInformado;
	private StatusPerguntaFlow statusPerguntaFlowRetornada;

	private int quantidadePerguntasRecebidas;

	private String senhaInformada;

	private LoginToken loginTokenRetornado;

	private String cnpjInformado;

	private Permissao permissaoRetornada;
	
	private String senhaNovaInformada;
	
	@Before
	public void test() throws IOException {
		try {
			redisServer = new RedisServer(redisPort);
			redisServer.start();
		}catch (Exception e) {
			// Silent
		}
		ReflectionTestUtils.setField(loginApiClient, "url", URL_BASE+port+URL_LOGIN_M2);
		ReflectionTestUtils.setField(loginApiClient, "urlRecuperarSenha", URL_BASE+port+URL_LOGIN_RECUPERAR_SENHA_M2);
		ReflectionTestUtils.setField(loginApiClient, "urlTrocarSenha", URL_BASE+port+URL_LOGIN_TROCAR_SENHA_M2);
		ReflectionTestUtils.setField(autenticadorApiClient, "url", URL_BASE+port+URL_AUTH_M2);
		ReflectionTestUtils.setField(perguntaApiClient, "url", URL_BASE+port+URL_PERGUNTA_M2);
		ReflectionTestUtils.setField(respostaApiClient, "url", URL_BASE+port+URL_RESPOSTA_M2);
		ReflectionTestUtils.setField(cidadaoApiClient, "url", URL_BASE+port+URL_CIDADAO_M2);
		cidadaoPerguntaRepository.deleteAll();
		cidadaoRespostaRepository.deleteAll();
		loginCheckStatusRepository.deleteAll();
		loginTokenInternoRepository.deleteAll();
		loginTokenRepository.deleteAll();
	}
	
//	@After
//	public void finalizarRedis() {
//		redisServer.stop();
//	}
	
	
	//----------------------------------------------------------------------------//
	//--Busca de status do login pelo CPF-----------------------------------------//
	//----------------------------------------------------------------------------//
	
	
	//----------------------------------------------------------------------------//
	//--Cadastro de Login já existe no cache do M1--------------------------------//
	//----------------------------------------------------------------------------//
	
	@Dado("que exista um status de login dentro do serviço de cache do M1 com o valor de status (.*?) para o CPF (.*?)")
	public void que_exista_um_status_de_login_dentro_do_serviço_de_cache_do_M1_com_o_valor_de_status_para_o_CPF (String status, String cpf){
		LoginCheckStatus loginCheckStatus = new LoginCheckStatus();
		loginCheckStatus.setCpf(cpf);
		if(LoginStatusEnum.CREATED_LOGIN.name().equals(status)) {
			loginCheckStatus.setStatus(LoginStatusEnum.CREATED_LOGIN);
		}else if(LoginStatusEnum.PENDING_LOGIN.name().equals(status)) {
			loginCheckStatus.setStatus(LoginStatusEnum.PENDING_LOGIN);
		}else if(LoginStatusEnum.SEARCHING_DATA.name().equals(status)) {
			loginCheckStatus.setStatus(LoginStatusEnum.SEARCHING_DATA);
		}
		loginCheckStatusCadastrado = loginCheckStatusRepository.save(loginCheckStatus);
		this.cpfInformado = cpf;
	}
	
	@Quando("for feita a requisição de busca de status passando o CPF informado")
	public void for_feita_a_requisição_de_busca_de_status_passando_o_CPF_informado () {
		try {
			ResponseEntity<LoginCheckStatus> response = restTemplate.getForEntity(URL_BASE+port+URL_LOGIN+"?cpf="+cpfInformado, LoginCheckStatus.class);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getBody());
			codigoHttp = response.getStatusCode().value();
			this.loginCheckStatusRetornado = response.getBody();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@Entao("o código HTTP de resposta deve ser (\\d+)$")
	public void o_codigo_http_de_resposta_deve_ser (int codigo) {
		Assert.assertEquals(codigo, codigoHttp);
	}
	
	@E("o status retornado deve ser o mesmo passado")
	public void o_status_retornado_deve_ser_o_mesmo_passado () {
		Assert.assertEquals(loginCheckStatusCadastrado.getStatus(), loginCheckStatusRetornado.getStatus());
		Assert.assertEquals(loginCheckStatusCadastrado.getCpf(), loginCheckStatusRetornado.getCpf());
	}
	
	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, porém existente--------------------------//
	//--na base de dados do M2----------------------------------------------------//
	//----------------------------------------------------------------------------//
	
	@Dado("que não exista um status de login dentro do serviço de cache do M1 para o CPF (.*?)")
	public void que_nao_exista_um_status_de_login_dentro_do_servico_de_cache_do_M1_para_o_CPF (String cpf) {
		loginCheckStatusRepository.deleteById(cpf);
		cpfInformado = cpf;
	}

	@E("exista um login cadastrado no M2 com esse CPF")
	public void exista_um_login_cadastrado_no_M2_com_esse_CPF () {
		loginM2APIMock.existeLoginCadastrado();
	}

	@E("o status retornado deve ser (.*?)")
	public void o_status_retornado_deve_ser (String status) {
		Assert.assertEquals(status, loginCheckStatusRetornado.getStatus().name());
	}
	
	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, não existe na----------------------------//
	//--base de dados do M2, porém existe um cidadão com--------------------------//
	//--o CPF informado na base de dados do M2------------------------------------//
	//----------------------------------------------------------------------------//
	
	
	@E("não exista um login cadastrado no M2 com esse CPF")
	public void nao_exista_um_login_cadastrado_no_M2_com_esse_cpf () {
		loginM2APIMock.naoExisteLoginCadastrado();
	}

	@E("exista um cidadão cadastrado no M2 com esse CPF")
	public void exista_um_cidadao_cadastrado_no_M2_com_esse_cpf () {
		loginM2APIMock.existeCidadaoCadastrado();
	}

	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, não existe na base de dados do M2,-------//
	//--não existe um cidadão com o CPF informado na base de dados do M2,---------// 
	//--porém existe um cidadão principal na base de dados do M3------------------//
	//----------------------------------------------------------------------------//
	
	@E("não exista um cidadão cadastrado no M2 com esse CPF")
	public void nao_exista_um_cidadão_cadastrado_no_M2_com_esse_cpf () {
		loginM2APIMock.naoExisteCidadaoCadastrado();
	}

	@E("exista um cidadão cadastrado no M3 como principal com esse CPF")
	public void exista_um_cidadao_cadastrado_no_M3_como_principal_com_esse_cpf () {
		loginM2APIMock.existeCidadaoCadastrado();
	}
	
	@E("o M2 deve persistir esse cidadão na base de dados")
	public void o_M2_deve_persistir_esse_cidadao_na_base_de_dados () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, não existe na base de dados do M2,-------//
	//--não existe um cidadão com o CPF informado na base de dados do M2,---------//
	//--não existe um cidadão principal na base de dados do M3,-------------------//
	//--porém existe um ou mais cidadão não principal na base de dados------------//
	//----------------------------------------------------------------------------//
	
	@E("não exista um cidadão cadastrado no M3 como principal esse CPF")
	public void nao_exista_um_cidadao_cadastrado_no_M3_como_principal_esse_CPF () {
		loginM2APIMock.naoExisteCidadaoCadastrado();
	}
	
	@E("existam {int} cidadãos cadastrados no M3 para esse CPF vindos de diferentes órgãos")
	public void existam_cidadaos_cadastrados_no_M3_para_esse_CPF_vindos_de_diferentes_orgaos (int quantidadeCidadãos) {
		loginM2APIMock.existeCidadaoCadastrado();
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
		loginM2APIMock.existeCidadaoCadastrado();
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
		loginM2APIMock.naoExisteCidadaoCadastrado();
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
		cpfInformado = cpf;
	}
	
	@E("que esse cidadão interessado em criar o login tenha o nome de (.*?)")
	public void que_esse_cidadao_interessado_em_criar_o_login_tenha_o_nome_de (String nome) {
		perguntaM2APIMock.adicionaPergunta(nome, ColunaEnum.NOME);
	}
	
	@E("que esse cidadão interessado em criar o login tenha o e-mail (.*?)")
	public void que_esse_cidadao_interessado_em_criar_o_login_tenha_o_e_mail (String email) {
		emailInformado = email;
		perguntaM2APIMock.adicionaPergunta(email, ColunaEnum.EMAIL);
	}
	
	@E("que a mãe desse cidadão interessado em criar o login tenha o nome de (.*?)")
	public void que_a_mãe_desse_cidadão_interessado_em_criar_o_login_tenha_o_nome_de (String nomeMae) {
		perguntaM2APIMock.adicionaPergunta(nomeMae, ColunaEnum.NOME_MAE);
	}
	
	@E("que o pai desse cidadão interessado em criar o login tenha o nome de (.*?)")
	public void que_o_pai_desse_cidadão_interessado_em_criar_o_login_tenha_o_nome_de (String nomePai) {
		perguntaM2APIMock.adicionaPergunta(nomePai, ColunaEnum.NOME_PAI);
	}
	
	@E("que esse cidadão interessado em criar o login tenha os rgs")
	public void que_esse_cidadao_interessado_em_criar_o_login_tenha_os_rgs (List<String> rgs) {
		perguntaM2APIMock.adicionaPergunta(rgs.get(0), ColunaEnum.QUANTOS_RGS_POSSUI);
		perguntaM2APIMock.adicionaPergunta(rgs.get(0), ColunaEnum.QUAIS_RGS_UFS);
	}
	
	@E("que esse cidadão interessado em criar o login tenha os celulares")
	public void que_esse_cidadao_interessado_em_criar_o_login_tenha_os_celulares (List<String> celulares) {
		perguntaM2APIMock.adicionaPergunta(celulares.get(0), ColunaEnum.CELULAR);
	}
	
	@E("que a data de nascimento desse cidadão interessado em criar o login seja (.*?)")
	public void que_a_data_de_nascimento_desse_cidadao_interessado_em_criar_o_login_seja (String dataNascimento) {
		perguntaM2APIMock.adicionaPergunta(dataNascimento, ColunaEnum.DATA_NASCIMENTO);
	}
	
	@Quando("for feita a solicitação do início das perguntas")
	public void for_feita_a_solicitação_do_início_das_perguntas () {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Resposta> entity = new HttpEntity<>(headers);
			ResponseEntity<StatusPerguntaFlow> response = restTemplate.postForEntity(URL_BASE+port+URL_PERGUNTAS+"?cpf="+cpfInformado, entity, StatusPerguntaFlow.class);
			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getBody());
			codigoHttp = response.getStatusCode().value();
			this.statusPerguntaFlowRetornada = response.getBody();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@E("deve conter todas as {int} perguntas no corpo da resposta")
	public void deve_conter_todas_as_perguntas_no_corpo_da_resposta (int quantidadePerguntas) {
		this.quantidadePerguntasRecebidas = quantidadePerguntas;
		Optional<CidadaoPergunta> cidadaoPergunta = cidadaoPerguntaRepository.findById(cpfInformado);
		Assert.assertTrue(cidadaoPergunta.isPresent());
		Assert.assertEquals(quantidadePerguntas, cidadaoPergunta.get().getPerguntas().size());
	}
	
	@E("deve ser executado o processo de resposta das {int} perguntas informando os dados corretamente")
	public void deve_ser_executado_o_processo_de_resposta_das_perguntas_informando_os_dados_corretamente (int quantidadePerguntas) {
		for(int i=0; i<quantidadePerguntas; i++) {
			Resposta respostaPergunta = new Resposta();
			respostaPergunta.setPergunta(perguntaM2APIMock.getPergunta(i));
			respostaPergunta.setResposta(perguntaM2APIMock.getPergunta(i).getPergunta());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Resposta> entity = new HttpEntity<>(respostaPergunta, headers);
			ResponseEntity<StatusPerguntaFlow> resposta = restTemplate.exchange(URL_BASE+port+URL_PERGUNTAS+"?cpf="+cpfInformado, HttpMethod.PUT, entity, StatusPerguntaFlow.class);
			statusPerguntaFlowRetornada = resposta.getBody();
		}
	}
	
	@Quando("for feita a resposta de todas as perguntas")
	public void for_feita_a_resposta_de_todas_as_perguntas () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o cidadão deve receber um e-mail")
	public void o_cidadão_deve_receber_um_e_mail () {
		Assert.assertEquals("SUCESSO", statusPerguntaFlowRetornada.getStatus());
		Assert.assertEquals(quantidadePerguntasRecebidas, respostaM2APIMock.getRespostasRecebidas().size());
	}
	
	@E("o login deve ter sido criado")
	public void o_login_deve_ter_sido_criado () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o login deve estar como inativo")
	public void o_login_deve_estar_como_inativo () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("a senha deve estar como gerada automaticamente")
	public void a_senha_deve_estar_como_gerada_automaticamente () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("ele não deve conseguir logar sem antes confirmar o e-mail")
	public void ele_nao_deve_conseguir_logar_sem_antes_confirmar_o_e_mail () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Quando("o cidadão clicar no token do e-mail recebido")
	public void o_cidadao_clicar_no_token_do_e_mail_recebido () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Entao("o login deve ser liberado")
	public void o_login_deve_ser_liberado () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	
	//----------------------------------------------------------------------------//
	//--Fluxo de perguntas com uma resposta incorreta-----------------------------//
	//----------------------------------------------------------------------------//
	
	@Entao("deve ser respondido a data de nascimento como (.*?)")
	public void deve_ser_respondido_a_data_de_nascimento_como (String dataNascimento) {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Entao("deve ser executado o processo de resposta das outras 7 perguntas informando os dados corretamente")
	public void deve_ser_executado_o_processo_de_resposta_das_outras_7_perguntas_informando_os_dados_corretamente () {
		respostaM2APIMock.respostaIncorreta();
		for(int i=0; i<8; i++) {
			Resposta respostaPergunta = new Resposta();
			respostaPergunta.setPergunta(perguntaM2APIMock.getPergunta(i));
			respostaPergunta.setResposta(perguntaM2APIMock.getPergunta(i).getPergunta());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Resposta> entity = new HttpEntity<>(respostaPergunta, headers);
			try {
				ResponseEntity<StatusPerguntaFlow> resposta = restTemplate.exchange(URL_BASE+port+URL_PERGUNTAS+"?cpf="+cpfInformado, HttpMethod.PUT, entity, StatusPerguntaFlow.class);
				statusPerguntaFlowRetornada = resposta.getBody();
				codigoHttp = resposta.getStatusCode().value();
			}catch (HttpServerErrorException e) {
				codigoHttp = e.getStatusCode().value();
			}
		}
	}
	
	@E("o cidadão não deve ser receber um e-mail")
	public void o_cidadão_não_deve_ser_receber_um_e_mail (){
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o login não deve ter sido criado")
	public void o_login_nao_deve_ter_sido_criado (){
		System.out.println(NAO_EXISTE_PASSO);
	}

	
	//----------------------------------------------------------------------------//
	//--Login do cidadão----------------------------------------------------------//
	//----------------------------------------------------------------------------//
	
	
	//----------------------------------------------------------------------------//
	//--Efetuar login do cidadão--------------------------------------------------//
	//----------------------------------------------------------------------------//
	
	
	@Dado("que existe um login para o cidadão com o CPF (.*?)")
	public void que_existe_um_login_para_o_cidadao_com_o_CPF (String cpf) {
		cpfInformado = cpf;
	}

	@E("a senha do login desse cidadão é (.*?)")
	public void a_senha_do_login_desse_cidadão_e (String senhaReal) {
		senhaInformada = senhaReal;
	}
	
	@E("esse login esteja cadastrado na base de dados")
	public void esse_login_esteja_cadastrado_na_base_de_dados () {
		autenticadorM2APIMock.setUsuario(cpfInformado);
		autenticadorM2APIMock.setSenha(senhaInformada);
		loginM2APIMock.setEmail(emailInformado);
		loginM2APIMock.setCpf(cpfInformado);
		loginM2APIMock.setSenha(senhaInformada);
	}

	@E("ele informe a senha (.*?)")
	public void ele_informe_a_senha (String senhaInformada) {
		this.senhaInformada = senhaInformada;
	}
	
	@Quando("for feita a requisicao para efetuar o login")
	public void for_feita_a_requisicao_para_efetuar_o_login () {
		loginTokenRetornado = null;
		try {
			Login login = new Login();
			login.setUsuario(cpfInformado);
			login.setSenha(senhaInformada);
			ResponseEntity<LoginToken> resposta = restTemplate.postForEntity(URL_BASE+port+URL_LOGIN, login, LoginToken.class);
			loginTokenRetornado = resposta.getBody();
			codigoHttp = resposta.getStatusCode().value();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}

	@E("o login deve ser feito corretamente")
	public void o_login_deve_ser_feito_corretamente () {
		Assert.assertNotNull(loginTokenRetornado);
		Assert.assertNotNull(loginTokenRetornado.getToken());
		Assert.assertNotNull(loginTokenRetornado.getTipo());
	}

	
	//----------------------------------------------------------------------------//
	//--Tentativa de login de uma empresa pelo aplicativo-------------------------//
	//----------------------------------------------------------------------------//
	
	@Dado("que existe um login para a empresa de CNPJ (.*?)")
	public void que_existe_um_login_para_a_empresa_de_cnpj (String cnpj) {
		cpfInformado = cnpj;
		autenticadorM2APIMock.setPerfil("EMPRESA");
	}
	
	@E("a senha do login dessa empresa é (.*?)")
	public void a_senha_do_login_dessa_empresa_e (String senhaReal) {
		senhaInformada = senhaReal;
	}
	
	@E("essa empresa informe a senha (.*?)")
	public void essa_empresa_informe_a_senha (String senhaInformada) {
		this.senhaInformada = senhaInformada;
	}
	
	@E("o login não deve ser permitido")
	public void o_login_não_deve_ser_permitido () {
		Assert.assertNull(loginTokenRetornado);
	}
	

	
	//----------------------------------------------------------------------------//
	//--Recuperação de senha do cidadão-------------------------------------------//
	//----------------------------------------------------------------------------//
	
	//----------------------------------------------------------------------------//
	//--Recuperação de senha realizado com sucesso--------------------------------//
	//----------------------------------------------------------------------------//
	
	@E("esse login tenha e-mail (.*?)")
	public void esse_login_tenha_e_mail (String email){
		emailInformado = email;
	}

	@E("ele informe o e-mail (.*?)")
	public void ele_informe_o_e_mail (String email){
		this.emailInformado = email;
	}

	@E("ele informe o CPF (.*?)")
	public void ele_informe_o_CPF (String cpf){
		this.cpfInformado = cpf;
	}

	@Quando("for feita a requisição para o processo de recuperação de senha")
	public void for_feita_a_requisicao_para_o_processo_de_recuperacao_de_senha (){
		loginCheckStatusRetornado = null;
		try {
			RecuperarSenhaLogin recuperarSenhaLogin = new RecuperarSenhaLogin();
			recuperarSenhaLogin.setEmail(emailInformado);
			recuperarSenhaLogin.setCpf(cpfInformado);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<RecuperarSenhaLogin> entity = new HttpEntity<>(recuperarSenhaLogin, headers);
			ResponseEntity<LoginCheckStatus> resposta = restTemplate.exchange(URL_BASE+port+URL_LOGIN_RECUPERAR_SENHA, HttpMethod.PUT, entity, LoginCheckStatus.class);
			loginCheckStatusRetornado = resposta.getBody();
			codigoHttp = resposta.getStatusCode().value();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}

	@E("o login deve ter a senha alterada")
	public void o_login_deve_ter_a_senha_alterada (){
		Assert.assertNotNull(loginCheckStatusRetornado);
	}

	@E("o login deve ficar inativo")
	public void o_login_deve_ficar_inativo (){
		System.out.println(NAO_EXISTE_PASSO);
	}

	@E("o login deve ficar como geração de senha automático")
	public void o_login_deve_ficar_como_geracao_de_senha_automatico (){
		System.out.println(NAO_EXISTE_PASSO);
	}

	@E("o cidadão deve receber um e-mail de confirmação")
	public void o_cidadao_deve_receber_um_e_mail_de_confirmacao (){
		System.out.println(NAO_EXISTE_PASSO);
	}

	@Quando("o cidadão clicar no token que recebeu por e-mail")
	public void o_cidadao_clicar_no_token_que_recebeu_por_e_mail (){
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o cidadão poderá logar utilizando a senha recebida")
	public void o_cidadao_podera_logar_utilizando_a_senha_recebida () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	

	//----------------------------------------------------------------------------//
	//--Recuperação de senha com e-mail divergente--------------------------------//
	//----------------------------------------------------------------------------//
	
	@E("o login não deve ter a senha alterada")
	public void o_login_nao_deve_ter_a_senha_alterada () {
		Assert.assertNull(loginCheckStatusRetornado);
	}
	
	@E("o login não deve ficar inativo")
	public void o_login_nao_deve_ficar_inativo () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o login não deve ficar como geração de senha automático")
	public void o_login_nao_deve_ficar_como_geracao_de_senha_automatico () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o cidadão não deve receber um e-mail de confirmação")
	public void o_cidadao_nao_deve_receber_um_e_mail_de_confirmacao () {
		System.out.println(NAO_EXISTE_PASSO);
	}

	//----------------------------------------------------------------------------//
	//--Recuperação de senha com e-mail divergente--------------------------------//
	//----------------------------------------------------------------------------//
	
	//----------------------------------------------------------------------------//
	//-----------------fim recuperação de senha-----------------------------------//
	//----------------------------------------------------------------------------//
	
	
	//----------------------------------------------------------------------------//
	//--Liberação do acesso aos dados do cidadão, pelo cidadão para empresa-------//
	//----------------------------------------------------------------------------//
	
	//----------------------------------------------------------------------------//
	//--Fluxo de liberação concluído com sucesso----------------------------------//
	//----------------------------------------------------------------------------//
	
	@Dado("que exista um cidadão logado")
	public void que_exista_um_cidadao_logado () {
		this.loginTokenRetornado = new LoginToken();
		loginTokenRetornado.setTipo("Bearer");
		loginTokenRetornado.setToken("blablabla");
	}
	
	@E("ele informe o CNPJ (.*?)")
	public void ele_informe_o_cnpj (String cnpj) {
		cnpjInformado = cnpj;
	}
	
	@E("essa empresa esteja cadastrada")
	public void essa_empresa_esteja_cadastrada () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Quando("for feita a requisição para o processo de liberação de dados para empresa")
	public void for_feita_a_requisição_para_o_processo_de_liberação_de_dados_para_empresa () {
		permissaoRetornada = null;
		try {
			SolicitarLiberacao liberacao = new SolicitarLiberacao();
			liberacao.setCnpj(cnpjInformado);
			liberacao.setToken(loginTokenRetornado);
			ResponseEntity<Permissao> resposta = restTemplate.postForEntity(URL_BASE+port+URL_CIDADAO_LIBERAR_EMPRESA, liberacao, Permissao.class);
			permissaoRetornada = resposta.getBody();
			codigoHttp = resposta.getStatusCode().value();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@E("o cidadão deve receber um e-mail com o token de confirmação")
	public void o_cidadao_deve_receber_um_e_mail_com_o_token_de_confirmacao () {
		Assert.assertNotNull(permissaoRetornada);
	}
	
	@E("a empresa ainda não deve conseguir acessar os dados")
	public void a_empresa_ainda_não_deve_conseguir_acessar_os_dados () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Quando("o cidadão clicar no token")
	public void o_cidadao_clicar_no_token () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Entao("será feito o procedimento de confirmação liberando o acesso para empresa")
	public void sera_feito_o_procedimento_de_confirmacao_liberando_o_acesso_para_empresa () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Quando("a empresa tentar buscar os dados do cidadão através de sua credencial e CPF do cidadão")
	public void a_empresa_tentar_buscar_os_dados_do_cidadao_atraves_de_sua_credencial_e_cpf_do_cidadao () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Entao("ela deve conseguir acessar esses dados")
	public void ela_deve_conseguir_acessar_esses_dados () {
		System.out.println(NAO_EXISTE_PASSO);
	}

	//----------------------------------------------------------------------------//
	//--Fluxo de liberação CNPJ não existe----------------------------------------//
	//----------------------------------------------------------------------------//
	
	@E("essa empresa não esteja cadastrada")
	public void essa_empresa_nao_esteja_cadastrada () {
		cidadaoM2APIMock.setEmpresaNaoCadastrada();
	}
	
	@E("o cidadão não deve receber um e-mail com o token de confirmação")
	public void o_cidadao_nao_deve_receber_um_e_mail_com_o_token_de_confirmacao () {
		Assert.assertNull(permissaoRetornada);
	}
	
	
	//----------------------------------------------------------------------------//
	//--Alteração de senha do cidadão---------------------------------------------//
	//----------------------------------------------------------------------------//
	
	//----------------------------------------------------------------------------//
	//--Fluxo de alteração de senha concluído com sucesso-------------------------//
	//----------------------------------------------------------------------------//
	
	@E("sua senha seja (.*?)")
	public void sua_senha_seja (String senha) {
		this.senhaInformada = senha;
	}
	
	@E("ele informe como senha atual (.*?)")
	public void ele_informe_como_senha_atual (String senhaAtual) {
		this.senhaInformada = senhaAtual;
	}
	
	@E("ele informe como nova senha (.*?)")
	public void ele_informe_como_nova_senha (String novaSenha) {
		this.senhaNovaInformada = novaSenha;
	}
	
	@Quando("for feita a requisição para o processo de alteração de senha")
	public void for_feita_a_requisicao_para_o_processo_de_alteracao_de_senha () {
		loginCheckStatusRetornado = null;
		try {
			TrocarSenhaLogin trocarSenhaLogin = new TrocarSenhaLogin();
			trocarSenhaLogin.setToken(loginTokenRetornado);
			trocarSenhaLogin.setSenhaAtual(senhaInformada);
			trocarSenhaLogin.setNovaSenha(senhaNovaInformada);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<TrocarSenhaLogin> entity = new HttpEntity<>(trocarSenhaLogin, headers);
			ResponseEntity<LoginCheckStatus> resposta = restTemplate.exchange(URL_BASE+port+URL_LOGIN_TROCAR_SENHA, HttpMethod.PUT, entity, LoginCheckStatus.class);
			codigoHttp = resposta.getStatusCode().value();
			loginCheckStatusRetornado = resposta.getBody();
		}catch (HttpServerErrorException e) {
			codigoHttp = e.getStatusCode().value();
		}
	}
	
	@E("o cidadão deve ter a senha alterada")
	public void o_cidadao_deve_ter_a_senha_alterada () {
		Assert.assertNotNull(loginCheckStatusRetornado);
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
		Assert.assertNull(loginCheckStatusRetornado);
	}
}