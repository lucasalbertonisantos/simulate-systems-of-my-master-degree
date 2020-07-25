package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder.CidadaoSistemasBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder.HeaderBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.BuscaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoSistemas;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Sistema;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.SistemaBuscaDados;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.CidadaoRespostaFila;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.Header;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.RequisicaoCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.RespostaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TipoOrgao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse.CidadaoRespostaFilaToCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.producer.RequisicaoBuscaCidadaoProducer;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.CidadaoSistemasRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.validate.CpfValidator;

@RunWith(SpringRunner.class)
public class CidadaoSistemasServiceTest {
	
	@InjectMocks
	private CidadaoSistemasService cidadaoSistemasService;
	
	@Mock
	private RequisicaoBuscaCidadaoProducer requisicaoBuscaCidadaoProducer;
	
	@Spy
	private HeaderBuilder headerBuilder;
	
	@Mock
	private BuscaCidadaoService buscaCidadaoService;
	
	@Mock
	private SistemaService sistemaService;
	
	@Mock
	private SistemaBuscaDadosService sistemaBuscaDadosService;
	
	@Mock
	private CidadaoSistemasRepository cidadaoSistemasRepository;
	
	@Mock
	private CidadaoRespostaFilaToCidadao cidadaoRespostaFilaToCidadao;
	
	@Mock
	private CpfValidator cpfValidator;
	
	@Mock
	private CidadaoSistemasBuilder cidadaoSistemasBuilder;

	private String nomeSistema = "testando-sistema";
	
	@Before
	public void init() {
		ReflectionTestUtils.setField(headerBuilder, "nomeSistema", nomeSistema);
	}
	
	@Test
	public void testBuscar() {
		String cpf = "83112690095";
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpf);
		String idSolicitacao = cidadaoSistemasService.buscar(cpf);
		ArgumentCaptor<RequisicaoCidadao> requisicaoCidadaoCaptor = ArgumentCaptor.forClass(RequisicaoCidadao.class);
		Mockito.verify(requisicaoBuscaCidadaoProducer).send(requisicaoCidadaoCaptor.capture());
		Assert.assertEquals(requisicaoCidadaoCaptor.getValue().getHeaders().get(0).getId(), idSolicitacao);
		ArgumentCaptor<BuscaCidadao> buscaCidadaoCaptor = ArgumentCaptor.forClass(BuscaCidadao.class);
		Mockito.verify(buscaCidadaoService).save(buscaCidadaoCaptor.capture());
		Assert.assertEquals(buscaCidadaoCaptor.getValue().getIdSolicitacao(), idSolicitacao);
	}
	
	@Test
	public void testConsultarStatus() {
		String idSolicitacao = "id-666";
		BuscaCidadao buscaCidadao = new BuscaCidadao();
		Mockito.when(buscaCidadaoService.findByIdSolicitacao(idSolicitacao)).thenReturn(buscaCidadao);
		BuscaCidadao buscaCidadaoRetornado = cidadaoSistemasService.consultarStatus(idSolicitacao);
		Assert.assertEquals(buscaCidadao, buscaCidadaoRetornado);
	}
	
	@Test
	public void testFindByCpf() {
		String cpf = "51303838001";
		List<CidadaoSistemas> cidadaosSistema = new ArrayList<>();
		Mockito.when(cidadaoSistemasRepository.findByCpf(cpf)).thenReturn(cidadaosSistema);
		List<CidadaoSistemas> cidadaosSistemaRetornado = cidadaoSistemasService.findByCpf(cpf);
		Assert.assertEquals(cidadaosSistema, cidadaosSistemaRetornado);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarNull() {
		cidadaoSistemasService.receberRetornoBuscaSalvar(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarHeadersNull() {
		cidadaoSistemasService.receberRetornoBuscaSalvar(new RespostaCidadao());
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarHeadersNotSize2() {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		respostaCidadao.setHeaders(new ArrayList<>());
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarCidadaoNulo() {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().add(new Header());
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarCpfCidadaoNulo() {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.setCidadao(new CidadaoRespostaFila());
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarCpfCidadaoVazio() {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.setCidadao(new CidadaoRespostaFila());
		respostaCidadao.getCidadao().setCpf("   ");
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarNomeSistemaNulo() {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.setCidadao(new CidadaoRespostaFila());
		respostaCidadao.getCidadao().setCpf("23780621096");
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarNomeSistemaVazio() {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().get(0).setNomeSistema("  ");
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().get(1).setNomeSistema("  ");
		respostaCidadao.setCidadao(new CidadaoRespostaFila());
		respostaCidadao.getCidadao().setCpf("23780621096");
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarTipoOrgaoNulo() {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().get(0).setNomeSistema(nomeSistema);
		respostaCidadao.getHeaders().add(new Header());
		String nomeSistemaExterno = "sistema-externo";
		respostaCidadao.getHeaders().get(1).setNomeSistema(nomeSistemaExterno);
		respostaCidadao.setCidadao(new CidadaoRespostaFila());
		respostaCidadao.getCidadao().setCpf("23780621096");
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarIdSolicitacaoNulo() {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().get(0).setNomeSistema(nomeSistema);
		respostaCidadao.getHeaders().add(new Header());
		String nomeSistemaExterno = "sistema-externo";
		respostaCidadao.getHeaders().get(1).setNomeSistema(nomeSistemaExterno);
		TipoOrgao tipoOrgao = TipoOrgao.FEDERAL;
		respostaCidadao.getHeaders().get(1).setTipoOrgao(tipoOrgao);
		respostaCidadao.setCidadao(new CidadaoRespostaFila());
		respostaCidadao.getCidadao().setCpf("23780621096");
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarIdSolicitacaoVazio() {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().get(0).setNomeSistema(nomeSistema);
		respostaCidadao.getHeaders().get(0).setId("   ");
		respostaCidadao.getHeaders().add(new Header());
		String nomeSistemaExterno = "sistema-externo";
		respostaCidadao.getHeaders().get(1).setNomeSistema(nomeSistemaExterno);
		TipoOrgao tipoOrgao = TipoOrgao.FEDERAL;
		respostaCidadao.getHeaders().get(1).setTipoOrgao(tipoOrgao);
		respostaCidadao.setCidadao(new CidadaoRespostaFila());
		respostaCidadao.getCidadao().setCpf("23780621096");
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarBuscaSolicitacaoNula() {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		String idSolicitacao = UUID.randomUUID().toString();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().get(0).setNomeSistema(nomeSistema);
		respostaCidadao.getHeaders().get(0).setId(idSolicitacao);
		respostaCidadao.getHeaders().add(new Header());
		String nomeSistemaExterno = "sistema-externo";
		respostaCidadao.getHeaders().get(1).setNomeSistema(nomeSistemaExterno);
		TipoOrgao tipoOrgao = TipoOrgao.FEDERAL;
		respostaCidadao.getHeaders().get(1).setTipoOrgao(tipoOrgao);
		respostaCidadao.setCidadao(new CidadaoRespostaFila());
		respostaCidadao.getCidadao().setCpf("23780621096");
		
		Mockito.when(buscaCidadaoService.findByIdSolicitacao(idSolicitacao)).thenReturn(null);
		
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarNomeSistemaRetornadoNulo() {
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		String idSolicitacao = UUID.randomUUID().toString();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().get(0).setNomeSistema(nomeSistema);
		respostaCidadao.getHeaders().get(0).setId(idSolicitacao);
		respostaCidadao.getHeaders().add(new Header());
		String nomeSistemaExterno = "sistema-externo";
		respostaCidadao.getHeaders().get(1).setNomeSistema(nomeSistemaExterno);
		TipoOrgao tipoOrgao = TipoOrgao.FEDERAL;
		respostaCidadao.getHeaders().get(1).setTipoOrgao(tipoOrgao);
		respostaCidadao.setCidadao(new CidadaoRespostaFila());
		respostaCidadao.getCidadao().setCpf("23780621096");
		
		BuscaCidadao buscaCidadao = new BuscaCidadao();
		Mockito.when(buscaCidadaoService.findByIdSolicitacao(idSolicitacao)).thenReturn(buscaCidadao);
		
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testReceberRetornoBuscaSalvarCpfBuscaDiferenteCidadao() {
		String cpf = "23780621096";
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		String idSolicitacao = UUID.randomUUID().toString();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().get(0).setNomeSistema(nomeSistema);
		respostaCidadao.getHeaders().get(0).setId(idSolicitacao);
		respostaCidadao.getHeaders().add(new Header());
		String nomeSistemaRetornado = "sistema-externo";
		respostaCidadao.getHeaders().get(1).setNomeSistema(nomeSistemaRetornado);
		TipoOrgao tipoOrgao = TipoOrgao.FEDERAL;
		respostaCidadao.getHeaders().get(1).setTipoOrgao(tipoOrgao);
		respostaCidadao.setCidadao(new CidadaoRespostaFila());
		respostaCidadao.getCidadao().setCpf(cpf);
		
		BuscaCidadao buscaCidadao = new BuscaCidadao();
		buscaCidadao.setCpf("123");
		Mockito.when(buscaCidadaoService.findByIdSolicitacao(idSolicitacao)).thenReturn(buscaCidadao);
		
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
	}
	
	@Test
	public void testReceberRetornoBuscaSalvar() {
		String cpf = "23780621096";
		RespostaCidadao respostaCidadao = new RespostaCidadao();
		String idSolicitacao = UUID.randomUUID().toString();
		respostaCidadao.setHeaders(new ArrayList<>());
		respostaCidadao.getHeaders().add(new Header());
		respostaCidadao.getHeaders().get(0).setNomeSistema(nomeSistema);
		respostaCidadao.getHeaders().get(0).setId(idSolicitacao);
		respostaCidadao.getHeaders().add(new Header());
		String nomeSistemaRetornado = "sistema-externo";
		respostaCidadao.getHeaders().get(1).setNomeSistema(nomeSistemaRetornado);
		TipoOrgao tipoOrgao = TipoOrgao.FEDERAL;
		respostaCidadao.getHeaders().get(1).setTipoOrgao(tipoOrgao);
		respostaCidadao.setCidadao(new CidadaoRespostaFila());
		respostaCidadao.getCidadao().setCpf(cpf);
		
		BuscaCidadao buscaCidadao = new BuscaCidadao();
		buscaCidadao.setCpf(cpf);
		Mockito.when(buscaCidadaoService.findByIdSolicitacao(idSolicitacao)).thenReturn(buscaCidadao);
		
		Sistema sistema = new Sistema();
		Mockito.when(sistemaService.findByNomeOrCreate(nomeSistemaRetornado)).thenReturn(sistema);
		
		SistemaBuscaDados sistemaBuscaDados = new SistemaBuscaDados();
		Mockito.when(sistemaBuscaDadosService.save(buscaCidadao, sistema)).thenReturn(sistemaBuscaDados);
		
		CidadaoSistemas cidadaoSistemas = new CidadaoSistemas();
		Mockito.when(cidadaoSistemasBuilder.build(sistemaBuscaDados, cpf, tipoOrgao)).thenReturn(cidadaoSistemas);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoRespostaFilaToCidadao.parse(respostaCidadao.getCidadao(), cidadaoSistemas)).thenReturn(cidadao);
		
		cidadaoSistemasService.receberRetornoBuscaSalvar(respostaCidadao);
		
		ArgumentCaptor<CidadaoSistemas> cidadaoSistemasCaptor = ArgumentCaptor.forClass(CidadaoSistemas.class);
		Mockito.verify(cidadaoSistemasRepository).save(cidadaoSistemasCaptor.capture());
		Assert.assertEquals(cidadao, cidadaoSistemasCaptor.getValue().getCidadao());
	}

}
