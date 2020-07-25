package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.bdd.steps;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.awaitility.Awaitility;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.Application;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.BuscaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoDadosProfissionais;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoReceitaFederal;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoSistemas;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Email;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Endereco;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.RG;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Sistema;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.SistemaBuscaDados;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Telefone;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.CidadaoRespostaFila;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.Header;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.RequisicaoCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.RespostaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TipoOrgao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.BuscaCidadaoRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.CidadaoRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.CidadaoSistemasRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.SistemaBuscaDadosRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.SistemaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.validate.CpfValidator;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

@EmbeddedKafka(
	    partitions = 1, 
	    controlledShutdown = false,
	    topics = {"${topico.requisicao.cidadao}", "${topico.resposta.cidadao}"},
	    brokerProperties = {
	        "listeners=PLAINTEXT://localhost:3333", 
	        "port=3333"
	})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@DirtiesContext
public class CucumberTestSteps {
	
	private static final String NAO_EXISTE_PASSO = "Status não será implementado nesse micro serviço, pois não existe esse passo.";
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private CidadaoRepository cidadaoRepository;
	
	@Autowired
	private BuscaCidadaoRepository buscaCidadaoRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CpfValidator cpfValidator;
	
	@Autowired
	private SistemaBuscaDadosRepository sistemaBuscaDadosRepository;
	
	@Autowired
	private SistemaRepository sistemaRepository;
	
	@Autowired
	private CidadaoSistemasRepository cidadaoSistemasRepository;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${topico.resposta.cidadao}")
	private String topic;
	
	private static final String URL_BASE = "http://localhost:";
	private static final String URL_CIDADAO = "/cidadaos";
	private static final String URL_CIDADAO_PRINCIPAL = URL_CIDADAO+"/principal";
	
	private Cidadao cidadaoSalvo;
	private int codigoHttp;
	private String cpfInformado;
	private boolean executarRequisicao = false;
	private Cidadao cidadaoRetornado;
	private Cidadao cidadaoReceitaFederalSalvo;
	private Cidadao cidadaoEstadoAcreSalvo;
	private Cidadao cidadaoMunicipioSaoPauloSalvo;
	private List<Sistema> sistemasEstaduaisSalvos = new ArrayList<>();
	private List<Sistema> sistemasFederaisSalvos = new ArrayList<>();
	private Map<Long, Sistema> sistemasSalvos = new HashMap<>();
	private Map<Long, CidadaoRespostaFila> sistemasCidadaoInformados = new HashMap<>();
	private Map<Long, TipoOrgao> idTipoOrgaoSalvo = new HashMap<>();

	private String erroRetornado;

	private Map<Long, Long> sistemasSalvosCodigo = new HashMap<>();
	
	@Before
	public void test() {
		cidadaoSistemasRepository.deleteAll();
		cidadaoRepository.deleteAll();
		sistemaBuscaDadosRepository.deleteAll();
		sistemaRepository.deleteAll();
		buscaCidadaoRepository.deleteAll();
	}
	
	
	//----------------------------------------------------------------------------//
	//--Busca de status do login pelo CPF-----------------------------------------//
	//----------------------------------------------------------------------------//
	
	
	//----------------------------------------------------------------//
	//--Cadastro de Login já existe no cache do M1--------------------//
	//----------------------------------------------------------------//
	
	@Dado("que exista um status de login dentro do serviço de cache do M1 com o valor de status (.*?) para o CPF (.*?)")
	public void que_exista_um_status_de_login_dentro_do_serviço_de_cache_do_M1_com_o_valor_de_status_para_o_CPF (String status, String cpf){
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@Quando("for feita a requisição de busca de status passando o CPF informado")
	public void for_feita_a_requisição_de_busca_de_status_passando_o_CPF_informado () {
		if(!executarRequisicao) {
			System.out.println(NAO_EXISTE_PASSO);
			codigoHttp = 200;
		}else {
			try {
				ResponseEntity<Cidadao> response = restTemplate.getForEntity(URL_BASE+port+URL_CIDADAO_PRINCIPAL+"?cpf="+cpfInformado, Cidadao.class);
				codigoHttp = response.getStatusCode().value();
				cidadaoRetornado = response.getBody();
			}catch (HttpServerErrorException | HttpClientErrorException e) {
				codigoHttp = e.getStatusCode().value();
				erroRetornado = e.getResponseBodyAsString();
			}
		}
	}
	
	@Entao("o código HTTP de resposta deve ser (\\d+)$")
	public void o_codigo_http_de_resposta_deve_ser (int codigo) {
		Assert.assertEquals(codigoHttp, codigo);
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
		System.out.println(NAO_EXISTE_PASSO);
	}

	@E("o status retornado deve ser (.*?)")
	public void o_status_retornado_deve_ser (String status) {
		System.out.println(NAO_EXISTE_PASSO);
	}


	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, não existe na----------------------------//
	//--base de dados do M2, porém existe um cidadão com--------------------------//
	//--o CPF informado na base de dados do M2------------------------------------//
	//----------------------------------------------------------------------------//
	
	@E("não exista um login cadastrado no M2 com esse CPF")
	public void nao_exista_um_login_cadastrado_no_M2_com_esse_cpf () {
		System.out.println(NAO_EXISTE_PASSO);
	}

	@E("exista um cidadão cadastrado no M2 com esse CPF")
	public void exista_um_cidadao_cadastrado_no_M2_com_esse_cpf () {
		System.out.println(NAO_EXISTE_PASSO);
	}

	
	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, não existe na base de dados do M2,-------//
	//--não existe um cidadão com o CPF informado na base de dados do M2,---------// 
	//--porém existe um cidadão principal na base de dados do M3------------------//
	//----------------------------------------------------------------------------//
	
	@E("não exista um cidadão cadastrado no M2 com esse CPF")
	public void nao_exista_um_cidadão_cadastrado_no_M2_com_esse_cpf () {
		executarRequisicao  = true;
	}

	@E("exista um cidadão cadastrado no M3 como principal com esse CPF")
	public void exista_um_cidadao_cadastrado_no_M3_como_principal_com_esse_cpf () {
		Cidadao cidadao = criarCidadao();
		cidadao.setPrincipal(true);
		cidadaoSalvo = cidadaoRepository.save(cidadao);
	}

	private Cidadao criarCidadao() {
		Cidadao cidadao = new Cidadao();
		cidadao.setCpf(cpfInformado);
		cidadao.setPrincipal(true);
		cidadao.setNome("Teste");
		cidadao.setNomeMae("Mae Teste");
		cidadao.setNomePai("Pai Teste");
		cidadao.setCidadaoReceitaFederal(new CidadaoReceitaFederal());
		cidadao.getCidadaoReceitaFederal().setCidadao(cidadao);
		cidadao.getCidadaoReceitaFederal().setCidadao(cidadao);
		cidadao.getCidadaoReceitaFederal().setDataInscricao(LocalDate.now());
		cidadao.getCidadaoReceitaFederal().setDigitoVerificador(Character.toString(cpfInformado.charAt(cpfInformado.length()-1)));
		cidadao.getCidadaoReceitaFederal().setSituacaoCadastral("OK");
		cidadao.setDadosProfissionais(new HashSet<>());
		CidadaoDadosProfissionais cdp = new CidadaoDadosProfissionais();
		cdp.setCargo("Analista de Sistemas");
		cdp.setCidadao(cidadao);
		cdp.setCnpjEmpresaPagadora("91.154.230/0001-60");
		cdp.setDataFim(LocalDate.now());
		cdp.setDataInicio(LocalDate.now().minusYears(2l));
		cdp.setNomeEmpresaPagadora("Empresa Teste");
		cdp.setSalarioAnual(65000.00);
		cdp.setSalarioMensal(5000.00);
		cidadao.getDadosProfissionais().add(cdp);
		cidadao.setDataNascimento(LocalDate.now().minusYears(40l));
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now());
		Email email = new Email();
		email.setAtivo(true);
		email.setCidadao(cidadao);
		email.setEmail("aaa.aaa@gmail.com");
		cidadao.setEmails(new HashSet<>());
		cidadao.getEmails().add(email);
		Endereco endereco = new Endereco();
		endereco.setCep("44.555-888");
		endereco.setComplemento("APTO 5");
		endereco.setCidadao(cidadao);
		endereco.setLogradouro("Av bla");
		endereco.setMunicipio("São Paulo");
		endereco.setNumero("666");
		endereco.setPais("BR");
		endereco.setUf("SP");
		cidadao.setEnderecos(new HashSet<>());
		cidadao.getEnderecos().add(endereco);
		cidadao.setFormacao("Superior");
		cidadao.setProfissao("Analista de Sistemas");
		RG rg = new RG();
		rg.setCidadao(cidadao);
		rg.setDataEmissao(LocalDate.now());
		rg.setNumero("856547");
		rg.setSecretariaEmissao("SSP");
		rg.setUf("SP");
		cidadao.setRgs(new HashSet<>());
		cidadao.getRgs().add(rg);
		Telefone t = new Telefone();
		t.setCelular(true);
		t.setCidadao(cidadao);
		t.setDdd(11);
		t.setDdi(55);
		t.setNumero(999999999l);
		cidadao.setTelefones(new HashSet<>());
		cidadao.getTelefones().add(t);
		return cidadao;
	}
	
	@E("o M2 deve persistir esse cidadão na base de dados")
	public void o_M2_deve_persistir_esse_cidadao_na_base_de_dados () {
		if(cidadaoSalvo == null) {
			cidadaoSalvo = cidadaoRepository.findByCpfAndPrincipal(cpfInformado, true);
		}
		Assert.assertEquals(cidadaoSalvo.getCpf(), cidadaoRetornado.getCpf());
	}

	
	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, não existe na base de dados do M2,-------//
	//--não existe um cidadão com o CPF informado na base de dados do M2,---------//
	//--não existe um cidadão principal na base de dados do M3,-------------------//
	//--porém existe um ou mais cidadão não principal na base de dados------------//
	//----------------------------------------------------------------------------//
	
	@E("não exista um cidadão cadastrado no M3 como principal esse CPF")
	public void nao_exista_um_cidadao_cadastrado_no_M3_como_principal_esse_CPF () {
		cidadaoRepository.deleteAll();
	}
	
	@E("existam 3 cidadãos cadastrados no M3 para esse CPF vindos de diferentes órgãos")
	public void existam_cidadaos_cadastrados_no_M3_para_esse_CPF_vindos_de_diferentes_orgaos () {
		BuscaCidadao buscaCidadao = ciarBuscaCidadao();
		cidadaoReceitaFederalSalvo = criarCidadaoSistema(buscaCidadao, TipoOrgao.FEDERAL, criarSistemaReceitaFederal());;
		cidadaoEstadoAcreSalvo = criarCidadaoSistema(buscaCidadao, TipoOrgao.ESTADUAL, criarSistemaEstadoAcre());
		cidadaoMunicipioSaoPauloSalvo = criarCidadaoSistema(buscaCidadao, TipoOrgao.MUNICIPAL, criarSistemaMunicipio());
	}

	private Cidadao criarCidadaoSistema(BuscaCidadao buscaCidadao, TipoOrgao orgao, Sistema sistema) {
		Cidadao cidadao = criarCidadao();
		cidadao.setPrincipal(false);
		CidadaoSistemas cidadaoSistema = new CidadaoSistemas();
		cidadao.setCidadaoSistema(cidadaoSistema);
		cidadaoSistema.setCidadao(cidadao);
		cidadaoSistema.setCpf(cpfInformado);
		cidadaoSistema.setDataBusca(LocalDateTime.now());
		cidadaoSistema.setTipoSistema(orgao);
		SistemaBuscaDados sistemaBuscaCidadao = new SistemaBuscaDados();
		sistemaBuscaCidadao.setBuscaCidadao(buscaCidadao);
		sistemaBuscaCidadao.setSistema(sistema);
		sistemaBuscaCidadao = sistemaBuscaDadosRepository.save(sistemaBuscaCidadao);
		cidadaoSistema.setSistema(sistemaBuscaCidadao);
		cidadaoSistema = cidadaoSistemasRepository.save(cidadaoSistema);
		return cidadaoSistema.getCidadao();
	}

	private Sistema criarSistemaMunicipio() {
		Sistema sistema = new Sistema();
		sistema.setNome("Município São Paulo");
		sistema = sistemaRepository.save(sistema);
		return sistema;
	}

	private Sistema criarSistemaEstadoAcre() {
		Sistema sistema = new Sistema();
		sistema.setNome("Estado Acre");
		sistema = sistemaRepository.save(sistema);
		return sistema;
	}

	private Sistema criarSistemaReceitaFederal() {
		Sistema sistema = new Sistema();
		sistema.setNome("Receita Federal");
		sistema = sistemaRepository.save(sistema);
		return sistema;
	}

	private BuscaCidadao ciarBuscaCidadao() {
		BuscaCidadao buscaCidadao = new BuscaCidadao();
		buscaCidadao.setCpf(cpfInformado);
		buscaCidadao.setIdSolicitacao(UUID.randomUUID().toString());
		buscaCidadao = buscaCidadaoRepository.save(buscaCidadao);
		return buscaCidadao;
	}
	
	@E("o cidadão do órgão de tipo (.*?) tenha nome (.*?)")
	public void o_cidadão_do_orgao_de_tipo_tenha_nome (String tipoOrgao, String nome) {
		if(TipoOrgao.MUNICIPAL.name().equals(tipoOrgao)) {
			cidadaoMunicipioSaoPauloSalvo.setNome(nome);
			cidadaoMunicipioSaoPauloSalvo = cidadaoRepository.save(cidadaoMunicipioSaoPauloSalvo);
		}else if(TipoOrgao.ESTADUAL.name().equals(tipoOrgao)) {
			cidadaoEstadoAcreSalvo.setNome(nome);
			cidadaoEstadoAcreSalvo = cidadaoRepository.save(cidadaoEstadoAcreSalvo);
		}else {
			cidadaoReceitaFederalSalvo.setNome(nome);
			cidadaoReceitaFederalSalvo = cidadaoRepository.save(cidadaoReceitaFederalSalvo);
		}
	}
	
	@E("não tenha nome da mãe cadastrada para o órgão (.*?)")
	public void nao_tenha_nome_da_mae_cadastrada_para_o_orgao (String tipoOrgao) {
		if(TipoOrgao.MUNICIPAL.name().equals(tipoOrgao)) {
			cidadaoMunicipioSaoPauloSalvo.setNomeMae(null);
			cidadaoMunicipioSaoPauloSalvo = cidadaoRepository.save(cidadaoMunicipioSaoPauloSalvo);
		}else if(TipoOrgao.ESTADUAL.name().equals(tipoOrgao)) {
			cidadaoEstadoAcreSalvo.setNomeMae(null);
			cidadaoEstadoAcreSalvo = cidadaoRepository.save(cidadaoEstadoAcreSalvo);
		}else {
			cidadaoReceitaFederalSalvo.setNomeMae(null);
			cidadaoReceitaFederalSalvo = cidadaoRepository.save(cidadaoReceitaFederalSalvo);
		}
	}
	
	@E("a mãe se chame (.*?) no órgão (.*?)")
	public void a_mae_se_chame_no_orgao (String nomeMae, String tipoOrgao) {
		if(TipoOrgao.MUNICIPAL.name().equals(tipoOrgao)) {
			cidadaoMunicipioSaoPauloSalvo.setNomeMae(nomeMae);
			cidadaoMunicipioSaoPauloSalvo = cidadaoRepository.save(cidadaoMunicipioSaoPauloSalvo);
		}else if(TipoOrgao.ESTADUAL.name().equals(tipoOrgao)) {
			cidadaoEstadoAcreSalvo.setNomeMae(nomeMae);
			cidadaoEstadoAcreSalvo = cidadaoRepository.save(cidadaoEstadoAcreSalvo);
		}else {
			cidadaoReceitaFederalSalvo.setNomeMae(nomeMae);
			cidadaoReceitaFederalSalvo = cidadaoRepository.save(cidadaoReceitaFederalSalvo);
		}
	}
	
	@E("não exista o nome do pai cadastrado para o órgão (.*?)")
	public void nao_exista_o_nome_do_pai_cadastrado_para_o_orgao (String tipoOrgao) {
		if(TipoOrgao.MUNICIPAL.name().equals(tipoOrgao)) {
			cidadaoMunicipioSaoPauloSalvo.setNomePai(null);
			cidadaoMunicipioSaoPauloSalvo = cidadaoRepository.save(cidadaoMunicipioSaoPauloSalvo);
		}else if(TipoOrgao.ESTADUAL.name().equals(tipoOrgao)) {
			cidadaoEstadoAcreSalvo.setNomePai(null);
			cidadaoEstadoAcreSalvo = cidadaoRepository.save(cidadaoEstadoAcreSalvo);
		}else {
			cidadaoReceitaFederalSalvo.setNomePai(null);
			cidadaoReceitaFederalSalvo = cidadaoRepository.save(cidadaoReceitaFederalSalvo);
		}
	}
	
	@E("o pai se chame (.*?) no órgão (.*?)")
	public void o_cidadão_do_orgao_de_tipo_nao_tenha_nome_do_pai_cadastrado (String nomePai, String tipoOrgao) {
		if(TipoOrgao.MUNICIPAL.name().equals(tipoOrgao)) {
			cidadaoMunicipioSaoPauloSalvo.setNomePai(nomePai);
			cidadaoMunicipioSaoPauloSalvo = cidadaoRepository.save(cidadaoMunicipioSaoPauloSalvo);
		}else if(TipoOrgao.ESTADUAL.name().equals(tipoOrgao)) {
			cidadaoEstadoAcreSalvo.setNomePai(nomePai);
			cidadaoEstadoAcreSalvo = cidadaoRepository.save(cidadaoEstadoAcreSalvo);
		}else {
			cidadaoReceitaFederalSalvo.setNomePai(nomePai);
			cidadaoReceitaFederalSalvo = cidadaoRepository.save(cidadaoReceitaFederalSalvo);
		}
	}
	
	@E("o M3 deve ter executado o algoritmo de unificação")
	public void o_M3_deve_ter_executado_o_algoritmo_de_unificacao () {
		System.out.println(NAO_EXISTE_PASSO);
	}
	
	@E("o cidadão principal com o CPF deve ter o nome igual o passado pelo órgão (.*?)")
	public void o_cidadao_principal_com_o_cpf_deve_ter_o_nome_igual_o_passado_pelo_orgao (String tipoOrgao) {
		if(TipoOrgao.MUNICIPAL.name().equals(tipoOrgao)) {
			Assert.assertEquals(cidadaoMunicipioSaoPauloSalvo.getNome(), cidadaoRetornado.getNome());
		}else if(TipoOrgao.ESTADUAL.name().equals(tipoOrgao)) {
			Assert.assertEquals(cidadaoEstadoAcreSalvo.getNome(), cidadaoRetornado.getNome());
		}else {
			Assert.assertEquals(cidadaoReceitaFederalSalvo.getNome(), cidadaoRetornado.getNome());
		}
	}
	
	@E("o cidadão principal com o CPF deve ter o nome da mãe igual o passado pelo órgão (.*?)")
	public void o_cidadao_principal_com_o_cpf_deve_ter_o_nome_da_mae_igual_o_passado_pelo_orgao (String tipoOrgao) {
		if(TipoOrgao.MUNICIPAL.name().equals(tipoOrgao)) {
			Assert.assertEquals(cidadaoMunicipioSaoPauloSalvo.getNomeMae(), cidadaoRetornado.getNomeMae());
		}else if(TipoOrgao.ESTADUAL.name().equals(tipoOrgao)) {
			Assert.assertEquals(cidadaoEstadoAcreSalvo.getNomeMae(), cidadaoRetornado.getNomeMae());
		}else {
			Assert.assertEquals(cidadaoReceitaFederalSalvo.getNomeMae(), cidadaoRetornado.getNomeMae());
		}
	}
	
	@E("o cidadão principal com o CPF deve ter o nome do pai igual o passado pelo órgão (.*?)")
	public void o_cidadao_principal_com_o_cpf_deve_ter_o_nome_do_pai_igual_o_passado_pelo_orgao (String tipoOrgao) {
		if(TipoOrgao.MUNICIPAL.name().equals(tipoOrgao)) {
			Assert.assertEquals(cidadaoMunicipioSaoPauloSalvo.getNomePai(), cidadaoRetornado.getNomePai());
		}else if(TipoOrgao.ESTADUAL.name().equals(tipoOrgao)) {
			Assert.assertEquals(cidadaoEstadoAcreSalvo.getNomePai(), cidadaoRetornado.getNomePai());
		}else {
			Assert.assertEquals(cidadaoReceitaFederalSalvo.getNomePai(), cidadaoRetornado.getNomePai());
		}
	}

	
	//----------------------------------------------------------------------------//
	//--Login não existe no cache do M1, não existe na base de dados do M2,-------//
	//--não existe um cidadão com o CPF informado na base de dados do M2,---------//
	//--não existe nenhum cidadão com esse CPF na base de dados do M3-------------//
	//----------------------------------------------------------------------------//
	
	
	@E("não exista nenhum cidadão cadastrado no M3")
	public void nao_exista_nenhum_cidadao_cadastrado_no_M3 () {
		executarRequisicao = true;
		cidadaoRepository.deleteAll();
	}
	
	@E("exista três sistemas órgãos dentro da solução integrada")
	public void nao_exista_nenhum_cidadao_cadastrado_no_M3 (Map<String, String> tipoOrgaos) {
		tipoOrgaos.forEach((key,value)->{
			Sistema sistema = new Sistema();
			sistema.setId(Long.valueOf(key));
			sistema.setNome(value+key);
			if(TipoOrgao.ESTADUAL.name().equals(value)) {
				sistema = sistemaRepository.save(sistema);
				sistemasEstaduaisSalvos.add(sistema);
				sistemasSalvos.put(Long.valueOf(key), sistema);
				sistemasSalvosCodigo .put(sistema.getId(), Long.valueOf(key));
				idTipoOrgaoSalvo.put(Long.valueOf(key), TipoOrgao.ESTADUAL);
			}else if(TipoOrgao.FEDERAL.name().equals(value)){
				sistema = sistemaRepository.save(sistema);
				sistemasFederaisSalvos.add(sistema);
				sistemasSalvos.put(Long.valueOf(key), sistema);
				sistemasSalvosCodigo.put(sistema.getId(), Long.valueOf(key));
				idTipoOrgaoSalvo.put(Long.valueOf(key), TipoOrgao.FEDERAL);
			}
		});
	}
	
	@KafkaListener(topics = "${topico.requisicao.cidadao}", groupId = "FEDERAL")
	public void federal(String requisicao) {
		idTipoOrgaoSalvo.forEach((key,value)->{
			if(TipoOrgao.FEDERAL.equals(value)) {
				enviarMensagemResposta(requisicao, key, TipoOrgao.FEDERAL);
			}
		});
	}
	
	@KafkaListener(topics = "${topico.requisicao.cidadao}", groupId = "ESTADUAL")
	public void estadual(String requisicao) {
		idTipoOrgaoSalvo.forEach((key,value)->{
			if(TipoOrgao.ESTADUAL.equals(value)) {
				enviarMensagemResposta(requisicao, key, TipoOrgao.ESTADUAL);
			}
		});
	}

	private void enviarMensagemResposta(String requisicao, Long key, TipoOrgao tipoOrgao) {
		if(sistemasCidadaoInformados.containsKey(key)) {
			try {
				RequisicaoCidadao requisicaoCidadao = objectMapper.readValue(requisicao, RequisicaoCidadao.class);
				RespostaCidadao respostaCidadao = new RespostaCidadao();
				respostaCidadao.setCidadao(sistemasCidadaoInformados.get(key));
				Header header = new Header();
				header.setData(LocalDateTime.now());
				header.setId(UUID.randomUUID().toString());
				header.setNomeSistema(sistemasSalvos.get(key).getNome());
				header.setTipoOrgao(tipoOrgao);
				requisicaoCidadao.getHeaders().add(header);
				respostaCidadao.setHeaders(requisicaoCidadao.getHeaders());
				kafkaTemplate.send(topic, objectMapper.writeValueAsString(respostaCidadao));
			} catch (JsonProcessingException e) {
				throw new RuntimeException("Erro ao enviar mensagem", e);
			}
		}
	}
	
	@E("o nome desse cidadão dentro do órgão (.*?) é (.*?)")
	public void o_nome_desse_cidadao_dentro_do_orgao_e (String orgao, String nomeCidadao) {
		Long idOrgao = Long.valueOf(orgao);
		CidadaoRespostaFila cidadao = new CidadaoRespostaFila();
		if(sistemasCidadaoInformados.containsKey(idOrgao)) {
			cidadao = sistemasCidadaoInformados.get(idOrgao);
		}
		cidadao.setCpf(cpfInformado);
		cidadao.setNome(nomeCidadao);
		sistemasCidadaoInformados.put(idOrgao, cidadao);
	}
	
	@E("o RG desse cidadão dentro do órgão (.*?) é (.*?)")
	public void o_rg_desse_cidadao_dentro_do_orgao_e (String orgao, String rgCidadao) {
		Long idOrgao = Long.valueOf(orgao);
		CidadaoRespostaFila cidadao = new CidadaoRespostaFila();
		if(sistemasCidadaoInformados.containsKey(idOrgao)) {
			cidadao = sistemasCidadaoInformados.get(idOrgao);
		}
		cidadao.setCpf(cpfInformado);
		cidadao.setRg(rgCidadao);
		cidadao.setSecretariaEmissaoRG(orgao);
		cidadao.setUfRg(orgao);
		sistemasCidadaoInformados.put(idOrgao, cidadao);
	}
	
	@E("o órgão (.*?) não possua esse cidadão cadastrado")
	public void o_orgao_nao_possua_esse_cidadao_cadastrado (String orgao) {
		sistemasCidadaoInformados.remove(Long.valueOf(orgao));
	}
	
	@E("deve conter o código solicitação de busca")
	public void deve_conter_o_codigo_solicitacao_de_busca () {
		Assert.assertTrue(erroRetornado.contains(buscaCidadaoRepository.findAll().iterator().next().getIdSolicitacao()));
	}
	
	@Quando("os órgãos existentes na solução integrada retornarem os dados de seus cidadãos")
	public void os_orgaos_existentes_na_solucao_integrada_retornarem_os_dados_de_seus_cidadaos () {
		Awaitility.await()
		.atMost(20l, TimeUnit.SECONDS)
		.with()
		.pollInterval(1l, TimeUnit.SECONDS)
		.until(() -> quantidadeRetornoIgualEsperado());
		Assert.assertTrue(quantidadeRetornoIgualEsperado());
	}
	
	private boolean quantidadeRetornoIgualEsperado() {
		if(sistemaBuscaDadosRepository.findAll() == null) {
			return false;
		}
		List<SistemaBuscaDados> lista = StreamSupport
				  .stream(sistemaBuscaDadosRepository.findAll().spliterator(), false)
				  .collect(Collectors.toList());
		return sistemasCidadaoInformados.size() == lista.size();
	}
	
	@Entao("o M3 deve persistir cada cidadão retornado")
	public void o_M3_deve_persistir_cada_cidadao_retornado () {
		Assert.assertTrue(quantidadeRetornoIgualEsperado());
	}
	
	private boolean quantidadeRetornoCidadaosIgualEsperado() {
		if(cidadaoSistemasRepository.findAll() == null) {
			return false;
		}
		List<CidadaoSistemas> lista = StreamSupport
				  .stream(cidadaoSistemasRepository.findAll().spliterator(), false)
				  .collect(Collectors.toList());
		return sistemasCidadaoInformados.size() == lista.size();
	}
	
	@E("os dados devem ser iguais aos existentes em cada órgão")
	public void os_dados_devem_ser_iguais_aos_existentes_em_cada_orgao () {
		Awaitility.await()
		.atMost(20l, TimeUnit.SECONDS)
		.with()
		.pollInterval(1l, TimeUnit.SECONDS)
		.until(() -> quantidadeRetornoCidadaosIgualEsperado());
		Assert.assertTrue(quantidadeRetornoCidadaosIgualEsperado());
		
		List<CidadaoSistemas> lista = StreamSupport
				  .stream(cidadaoSistemasRepository.findAll().spliterator(), false)
				  .collect(Collectors.toList());
		for(CidadaoSistemas sistema : lista) {
			CidadaoRespostaFila cidadaoRespostaFila = sistemasCidadaoInformados.get(sistemasSalvosCodigo.get(sistema.getSistema().getSistema().getId()));
			Assert.assertNotNull(cidadaoRespostaFila);
			Assert.assertNotNull(sistema);
			Assert.assertNotNull(sistema.getCidadao());
			Assert.assertEquals(cidadaoRespostaFila.getNome(), sistema.getCidadao().getNome());
			if(cidadaoRespostaFila.getRg() != null){
				RG rg = new ArrayList<>(sistema.getCidadao().getRgs()).get(0);
				Assert.assertEquals(cidadaoRespostaFila.getRg(), rg.getNumero());
			}
		}
	}
	
	@E("a quantidade de cidadãos cadastradas devem ser iguais aos de cidadãos retornados")
	public void a_quantidade_de_cidadãos_cadastradas_devem_ser_iguais_aos_de_cidadãos_retornados () {
		List<Cidadao> lista = StreamSupport
				  .stream(cidadaoRepository.findAll().spliterator(), false)
				  .collect(Collectors.toList());
		Assert.assertEquals(sistemasCidadaoInformados.size(), lista.size());
	}
	
	@E("não deve existir nenhum cidadão como principal")
	public void nao_deve_existir_nenhum_cidadao_como_principal () {
		Cidadao cidadao = cidadaoRepository.findByCpfAndPrincipal(cpfInformado, true);
		Assert.assertNull(cidadao);
	}

}