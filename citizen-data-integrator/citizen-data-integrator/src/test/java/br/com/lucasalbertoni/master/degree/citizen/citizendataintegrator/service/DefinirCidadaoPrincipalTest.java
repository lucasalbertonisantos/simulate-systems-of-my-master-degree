package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder.EmailBuilder;
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
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TipoOrgao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.utils.OrdenacaoTipoSistema;

@RunWith(SpringRunner.class)
public class DefinirCidadaoPrincipalTest {
	
	@InjectMocks
	private DefinirCidadaoPrincipal definirCidadaoPrincipal;
	
	@Spy
	private EmailBuilder emailBuilder;
	
	@Spy
	private OrdenacaoTipoSistema ordenacaoTipoSistema;
	
	@Test(expected = RuntimeException.class)
	public void testDefinirNull() {
		definirCidadaoPrincipal.definir(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testDefinirVazio() {
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		definirCidadaoPrincipal.definir(cidadaosSistemas);
	}
	
	@Test(expected = RuntimeException.class)
	public void testDefinirSistemaBuscaDadosNulo() {
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		cidadaosSistemas.add(new CidadaoSistemas());
		definirCidadaoPrincipal.definir(cidadaosSistemas);
	}
	
	@Test(expected = RuntimeException.class)
	public void testDefinirSistemaNulo() {
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		cidadaosSistemas.add(new CidadaoSistemas());
		cidadaosSistemas.get(0).setSistema(new SistemaBuscaDados());
		definirCidadaoPrincipal.definir(cidadaosSistemas);
	}
	
	@Test(expected = RuntimeException.class)
	public void testDefinirNomeSistemaNulo() {
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		cidadaosSistemas.add(new CidadaoSistemas());
		cidadaosSistemas.get(0).setSistema(new SistemaBuscaDados());
		cidadaosSistemas.get(0).getSistema().setSistema(new Sistema());
		definirCidadaoPrincipal.definir(cidadaosSistemas);
	}
	
	@Test(expected = RuntimeException.class)
	public void testDefinirNomeSistemaVazio() {
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		cidadaosSistemas.add(new CidadaoSistemas());
		cidadaosSistemas.get(0).setSistema(new SistemaBuscaDados());
		cidadaosSistemas.get(0).getSistema().setSistema(new Sistema());
		cidadaosSistemas.get(0).getSistema().getSistema().setNome("  ");
		definirCidadaoPrincipal.definir(cidadaosSistemas);
	}
	
	@Test(expected = RuntimeException.class)
	public void testDefinirTipoSistemaNulo() {
		String nomeSistema = "TESTANDOO";
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		cidadaosSistemas.add(new CidadaoSistemas());
		cidadaosSistemas.get(0).setSistema(new SistemaBuscaDados());
		cidadaosSistemas.get(0).getSistema().setSistema(new Sistema());
		cidadaosSistemas.get(0).getSistema().getSistema().setNome(nomeSistema);
		definirCidadaoPrincipal.definir(cidadaosSistemas);
	}
	
	@Test(expected = RuntimeException.class)
	public void testDefinirDataBuscaNula() {
		String nomeSistema = "TESTANDOO";
		TipoOrgao tipoOrgao = TipoOrgao.FEDERAL;
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		cidadaosSistemas.add(new CidadaoSistemas());
		cidadaosSistemas.get(0).setSistema(new SistemaBuscaDados());
		cidadaosSistemas.get(0).getSistema().setSistema(new Sistema());
		cidadaosSistemas.get(0).getSistema().getSistema().setNome(nomeSistema);
		cidadaosSistemas.get(0).setTipoSistema(tipoOrgao);
		definirCidadaoPrincipal.definir(cidadaosSistemas);
	}
	
	@Test(expected = RuntimeException.class)
	public void testDefinirCidadaoNulo() {
		String nomeSistema = "TESTANDOO";
		TipoOrgao tipoOrgao = TipoOrgao.FEDERAL;
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		cidadaosSistemas.add(new CidadaoSistemas());
		cidadaosSistemas.get(0).setSistema(new SistemaBuscaDados());
		cidadaosSistemas.get(0).getSistema().setSistema(new Sistema());
		cidadaosSistemas.get(0).getSistema().getSistema().setNome(nomeSistema);
		cidadaosSistemas.get(0).setTipoSistema(tipoOrgao);
		cidadaosSistemas.get(0).setDataBusca(LocalDateTime.now());
		definirCidadaoPrincipal.definir(cidadaosSistemas);
	}
	
	@Test
	public void testDefinir1CidadaoCamposNulos() {
		String nomeSistema = "TESTANDOO";
		TipoOrgao tipoOrgao = TipoOrgao.FEDERAL;
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		cidadaosSistemas.add(new CidadaoSistemas());
		cidadaosSistemas.get(0).setSistema(new SistemaBuscaDados());
		cidadaosSistemas.get(0).getSistema().setSistema(new Sistema());
		cidadaosSistemas.get(0).getSistema().getSistema().setNome(nomeSistema);
		cidadaosSistemas.get(0).setTipoSistema(tipoOrgao);
		cidadaosSistemas.get(0).setDataBusca(LocalDateTime.now());
		Cidadao cidadao = new Cidadao();
		cidadaosSistemas.get(0).setCidadao(cidadao);
		Cidadao cidadaoRetornado = definirCidadaoPrincipal.definir(cidadaosSistemas);
		Assert.assertNotNull(cidadaoRetornado);
		Assert.assertNull(cidadaoRetornado.getCpf());
		Assert.assertNull(cidadaoRetornado.getFormacao());
		Assert.assertNull(cidadaoRetornado.getNome());
		Assert.assertNull(cidadaoRetornado.getNomeMae());
		Assert.assertNull(cidadaoRetornado.getNomePai());
		Assert.assertNull(cidadaoRetornado.getProfissao());
		Assert.assertNull(cidadaoRetornado.getCidadaoReceitaFederal());
		Assert.assertNull(cidadaoRetornado.getCidadaoSistema());
		Assert.assertNull(cidadaoRetornado.getDataNascimento());
		Assert.assertNull(cidadaoRetornado.getId());
		Assert.assertNull(cidadaoRetornado.getRgs());
		Assert.assertNull(cidadaoRetornado.getEnderecos());
		Assert.assertNull(cidadaoRetornado.getTelefones());
		Assert.assertTrue(cidadaoRetornado.isPrincipal());
		Assert.assertNotNull(cidadaoRetornado.getDataUltimaAtualizacao());
		Assert.assertNotNull(cidadaoRetornado.getDadosProfissionais());
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().isEmpty());
		Assert.assertNotNull(cidadaoRetornado.getEmails());
		Assert.assertTrue(cidadaoRetornado.getEmails().isEmpty());
	}
	
	@Test
	public void testDefinirCidadao3TiposOrgaosDiferentes4Cidados2MunicipaisTesteDatas() {
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		String cpf = "53432219040";
		String nomeSistemaFederal = "sistema-federal-1";
		String nomeSistemaEstadual = "sistema-estadual-1";
		String nomeSistemaMunicipal = "sistema-municipal-1";
		String nomeSistemaMunicipal2 = "sistema-municipal-2";
		CidadaoSistemas cidadaoFederal = cidadaoFederal(cpf, nomeSistemaFederal);
		cidadaoFederal.getCidadao().setNome(null);
		cidadaoFederal.getCidadao().setNomeMae("  ");
		CidadaoSistemas cidadaoEstadual = cidadaoEstadual(cpf, nomeSistemaEstadual);
		cidadaoEstadual.getCidadao().setNome("  ");
		CidadaoSistemas cidadaoMunicipal = cidadaoMunicipal(cpf, nomeSistemaMunicipal);
		cidadaoMunicipal.setDataBusca(LocalDateTime.now().minusDays(2l));
		CidadaoSistemas cidadaoMunicipal2 = cidadaoMunicipal(cpf, nomeSistemaMunicipal2);
		cidadaoMunicipal2.getCidadao().setNome("TesteMunicipalDataMaisRecente");
		cidadaosSistemas.add(cidadaoMunicipal);
		cidadaosSistemas.add(cidadaoEstadual);
		cidadaosSistemas.add(cidadaoFederal);
		cidadaosSistemas.add(cidadaoMunicipal2);
		Cidadao cidadaoRetornado = definirCidadaoPrincipal.definir(cidadaosSistemas);
		Assert.assertNotNull(cidadaoRetornado);
		Assert.assertTrue(cidadaoRetornado.isPrincipal());
		Assert.assertEquals(cidadaoMunicipal2.getCidadao().getNome(), cidadaoRetornado.getNome());
		Assert.assertEquals(cidadaoEstadual.getCidadao().getNomeMae(), cidadaoRetornado.getNomeMae());
		Assert.assertEquals(cidadaoFederal.getCidadao().getNomePai(), cidadaoRetornado.getNomePai());
		Assert.assertEquals(cidadaoFederal.getCidadao().getCidadaoReceitaFederal().getDataInscricao(), cidadaoRetornado.getCidadaoReceitaFederal().getDataInscricao());
		Assert.assertEquals(cidadaoFederal.getCidadao().getCidadaoReceitaFederal().getSituacaoCadastral(), cidadaoRetornado.getCidadaoReceitaFederal().getSituacaoCadastral());
		Assert.assertEquals(cidadaoFederal.getCidadao().getCidadaoReceitaFederal().getDigitoVerificador(), cidadaoRetornado.getCidadaoReceitaFederal().getDigitoVerificador());
		Assert.assertEquals(3, cidadaoRetornado.getEnderecos().size());
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoMunicipal.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoMunicipal2.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoEstadual.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoFederal.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertEquals(cidadaoFederal.getCidadao().getCpf(), cidadaoRetornado.getCpf());
		Assert.assertEquals(cidadaoFederal.getCidadao().getDataNascimento(), cidadaoRetornado.getDataNascimento());
		Assert.assertEquals(cidadaoFederal.getCidadao().getFormacao(), cidadaoRetornado.getFormacao());
		Assert.assertEquals(cidadaoFederal.getCidadao().getProfissao(), cidadaoRetornado.getProfissao());
		Assert.assertEquals(3, cidadaoRetornado.getTelefones().size());
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoMunicipal.getCidadao().getTelefones().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoMunicipal2.getCidadao().getTelefones().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoEstadual.getCidadao().getTelefones().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoFederal.getCidadao().getTelefones().toArray()[0]));
		Assert.assertEquals(1, cidadaoRetornado.getRgs().size());
		Assert.assertTrue(cidadaoRetornado.getRgs().contains(cidadaoEstadual.getCidadao().getRgs().toArray()[0]));
		Assert.assertEquals(3, cidadaoRetornado.getEmails().size());
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoMunicipal.getCidadao().getEmails().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoMunicipal2.getCidadao().getEmails().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoEstadual.getCidadao().getEmails().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoFederal.getCidadao().getEmails().toArray()[0]));
		Assert.assertEquals(3, cidadaoRetornado.getDadosProfissionais().size());
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoMunicipal.getCidadao().getDadosProfissionais().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoMunicipal2.getCidadao().getDadosProfissionais().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoEstadual.getCidadao().getDadosProfissionais().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoFederal.getCidadao().getDadosProfissionais().toArray()[0]));
	}
	
	@Test
	public void testDefinirCidadao5Federais2MesmoSistema() {
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		String cpf = "53432219040";
		String nomeSistemaFederal1 = "sistema-federal-1";
		String nomeSistemaFederal2 = "sistema-federal-2";
		String nomeSistemaFederal3 = "sistema-federal-3";
		String nomeSistemaFederal4 = "sistema-federal-3";
		String nomeSistemaFederal5 = "sistema-federal-4";
		CidadaoSistemas cidadaoFederal1 = cidadaoFederal(cpf, nomeSistemaFederal1);
		CidadaoSistemas cidadaoFederal2 = cidadaoFederal(cpf, nomeSistemaFederal2);
		CidadaoSistemas cidadaoFederal3 = cidadaoFederal(cpf, nomeSistemaFederal3);
		CidadaoSistemas cidadaoFederal4 = cidadaoFederal(cpf, nomeSistemaFederal4);
		CidadaoSistemas cidadaoFederal5 = cidadaoFederal(cpf, nomeSistemaFederal5);
		
		cidadaoFederal4.getCidadao().getCidadaoReceitaFederal().setDataInscricao(null);
		cidadaoFederal4.getCidadao().getCidadaoReceitaFederal().setDigitoVerificador(null);
		cidadaoFederal4.getCidadao().getCidadaoReceitaFederal().setSituacaoCadastral(null);
		
		cidadaoFederal2.getCidadao().getCidadaoReceitaFederal().setDataInscricao(null);
		cidadaoFederal2.getCidadao().getCidadaoReceitaFederal().setDigitoVerificador("   ");
		cidadaoFederal2.getCidadao().getCidadaoReceitaFederal().setSituacaoCadastral("  ");
		
		cidadaoFederal4.setDataBusca(LocalDateTime.now());
		cidadaoFederal3.setDataBusca(LocalDateTime.now().minusDays(1l));
		cidadaoFederal2.setDataBusca(LocalDateTime.now().minusDays(2l));
		cidadaoFederal1.setDataBusca(LocalDateTime.now().minusDays(3l));
		cidadaoFederal5.setDataBusca(LocalDateTime.now().minusDays(4l));
		
		cidadaosSistemas.add(cidadaoFederal1);
		cidadaosSistemas.add(cidadaoFederal2);
		cidadaosSistemas.add(cidadaoFederal3);
		cidadaosSistemas.add(cidadaoFederal4);
		cidadaosSistemas.add(cidadaoFederal5);

		Cidadao cidadaoRetornado = definirCidadaoPrincipal.definir(cidadaosSistemas);
		Assert.assertNotNull(cidadaoRetornado);
		Assert.assertTrue(cidadaoRetornado.isPrincipal());
		Assert.assertEquals(cidadaoFederal4.getCidadao().getNome(), cidadaoRetornado.getNome());
		Assert.assertEquals(cidadaoFederal4.getCidadao().getNomeMae(), cidadaoRetornado.getNomeMae());
		Assert.assertEquals(cidadaoFederal4.getCidadao().getNomePai(), cidadaoRetornado.getNomePai());
		Assert.assertEquals(cidadaoFederal1.getCidadao().getCidadaoReceitaFederal().getDataInscricao(), cidadaoRetornado.getCidadaoReceitaFederal().getDataInscricao());
		Assert.assertEquals(cidadaoFederal1.getCidadao().getCidadaoReceitaFederal().getSituacaoCadastral(), cidadaoRetornado.getCidadaoReceitaFederal().getSituacaoCadastral());
		Assert.assertEquals(cidadaoFederal1.getCidadao().getCidadaoReceitaFederal().getDigitoVerificador(), cidadaoRetornado.getCidadaoReceitaFederal().getDigitoVerificador());
		Assert.assertEquals(1, cidadaoRetornado.getEnderecos().size());
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoFederal1.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoFederal2.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoFederal3.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoFederal4.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoFederal5.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertEquals(cidadaoFederal4.getCidadao().getCpf(), cidadaoRetornado.getCpf());
		Assert.assertEquals(cidadaoFederal4.getCidadao().getDataNascimento(), cidadaoRetornado.getDataNascimento());
		Assert.assertEquals(cidadaoFederal4.getCidadao().getFormacao(), cidadaoRetornado.getFormacao());
		Assert.assertEquals(cidadaoFederal4.getCidadao().getProfissao(), cidadaoRetornado.getProfissao());
		Assert.assertEquals(1, cidadaoRetornado.getTelefones().size());
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoFederal1.getCidadao().getTelefones().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoFederal2.getCidadao().getTelefones().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoFederal3.getCidadao().getTelefones().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoFederal4.getCidadao().getTelefones().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoFederal5.getCidadao().getTelefones().toArray()[0]));
		Assert.assertNull(cidadaoRetornado.getRgs());
		Assert.assertEquals(1, cidadaoRetornado.getEmails().size());
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoFederal1.getCidadao().getEmails().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoFederal2.getCidadao().getEmails().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoFederal3.getCidadao().getEmails().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoFederal4.getCidadao().getEmails().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoFederal5.getCidadao().getEmails().toArray()[0]));
		Assert.assertEquals(1, cidadaoRetornado.getDadosProfissionais().size());
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoFederal1.getCidadao().getDadosProfissionais().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoFederal2.getCidadao().getDadosProfissionais().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoFederal3.getCidadao().getDadosProfissionais().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoFederal4.getCidadao().getDadosProfissionais().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoFederal5.getCidadao().getDadosProfissionais().toArray()[0]));
	}
	
	@Test
	public void testDefinirCidadao6Estaduais2MesmoSistema() {
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		String cpf = "53432219040";
		String nomeSistemaEstadual1 = "sistema-estadual-1";
		String nomeSistemaEstadual2 = "sistema-estadual-2";
		String nomeSistemaEstadual3 = "sistema-estadual-3";
		String nomeSistemaEstadual4 = "sistema-estadual-4";
		String nomeSistemaEstadual5 = "sistema-estadual-5";
		String nomeSistemaEstadual6 = "sistema-estadual-5";
		CidadaoSistemas cidadaoEstadual1 = cidadaoEstadual(cpf, nomeSistemaEstadual1);
		CidadaoSistemas cidadaoEstadual2 = cidadaoEstadual(cpf, nomeSistemaEstadual2);
		CidadaoSistemas cidadaoEstadual3 = cidadaoEstadual(cpf, nomeSistemaEstadual3);
		CidadaoSistemas cidadaoEstadual4 = cidadaoEstadual(cpf, nomeSistemaEstadual4);
		CidadaoSistemas cidadaoEstadual5 = cidadaoEstadual(cpf, nomeSistemaEstadual5);
		CidadaoSistemas cidadaoEstadual6 = cidadaoEstadual(cpf, nomeSistemaEstadual6);
		
		cidadaoEstadual1.getCidadao().setNome(null);
		cidadaoEstadual1.getCidadao().setNomeMae(null);
		cidadaoEstadual1.getCidadao().setNomePai(null);
		cidadaoEstadual1.getCidadao().setCpf(null);
		cidadaoEstadual1.getCidadao().setDataNascimento(null);
		cidadaoEstadual1.getCidadao().setProfissao(null);
		cidadaoEstadual1.getCidadao().setDadosProfissionais(null);
		cidadaoEstadual1.getCidadao().setEmails(null);
		cidadaoEstadual1.getCidadao().setEnderecos(null);
		cidadaoEstadual1.getCidadao().setFormacao(null);
		cidadaoEstadual1.getCidadao().setRgs(null);
		cidadaoEstadual1.getCidadao().setTelefones(null);
		
		cidadaoEstadual2.getCidadao().setNome("   ");
		cidadaoEstadual2.getCidadao().setNomeMae("   ");
		cidadaoEstadual2.getCidadao().setNomePai("   ");
		cidadaoEstadual2.getCidadao().setCpf("   ");
		cidadaoEstadual2.getCidadao().setDataNascimento(null);
		cidadaoEstadual2.getCidadao().setProfissao("   ");
		cidadaoEstadual2.getCidadao().setDadosProfissionais(new HashSet<>());
		cidadaoEstadual2.getCidadao().setEmails(new HashSet<>());
		cidadaoEstadual2.getCidadao().setEnderecos(new HashSet<>());
		cidadaoEstadual2.getCidadao().setFormacao("   ");
		cidadaoEstadual2.getCidadao().setRgs(new HashSet<>());
		cidadaoEstadual2.getCidadao().setTelefones(new HashSet<>());
		
		cidadaoEstadual1.setDataBusca(LocalDateTime.now());
		cidadaoEstadual2.setDataBusca(LocalDateTime.now().minusDays(1l));
		cidadaoEstadual3.setDataBusca(LocalDateTime.now().minusDays(2l));
		cidadaoEstadual4.setDataBusca(LocalDateTime.now().minusDays(3l));
		cidadaoEstadual5.setDataBusca(LocalDateTime.now().minusDays(4l));
		cidadaoEstadual6.setDataBusca(LocalDateTime.now().minusDays(5l));
		
		cidadaosSistemas.add(cidadaoEstadual1);
		cidadaosSistemas.add(cidadaoEstadual2);
		cidadaosSistemas.add(cidadaoEstadual3);
		cidadaosSistemas.add(cidadaoEstadual4);
		cidadaosSistemas.add(cidadaoEstadual5);
		cidadaosSistemas.add(cidadaoEstadual6);
		
		Cidadao cidadaoRetornado = definirCidadaoPrincipal.definir(cidadaosSistemas);
		Assert.assertNotNull(cidadaoRetornado);
		Assert.assertTrue(cidadaoRetornado.isPrincipal());
		Assert.assertEquals(cidadaoEstadual3.getCidadao().getNome(), cidadaoRetornado.getNome());
		Assert.assertEquals(cidadaoEstadual3.getCidadao().getNomeMae(), cidadaoRetornado.getNomeMae());
		Assert.assertEquals(cidadaoEstadual3.getCidadao().getNomePai(), cidadaoRetornado.getNomePai());
		Assert.assertNull(cidadaoRetornado.getCidadaoReceitaFederal());
		Assert.assertEquals(1, cidadaoRetornado.getEnderecos().size());
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoEstadual3.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoEstadual4.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoEstadual5.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEnderecos().contains(cidadaoEstadual6.getCidadao().getEnderecos().toArray()[0]));
		Assert.assertEquals(cidadaoEstadual3.getCidadao().getCpf(), cidadaoRetornado.getCpf());
		Assert.assertEquals(cidadaoEstadual3.getCidadao().getDataNascimento(), cidadaoRetornado.getDataNascimento());
		Assert.assertEquals(cidadaoEstadual3.getCidadao().getFormacao(), cidadaoRetornado.getFormacao());
		Assert.assertEquals(cidadaoEstadual3.getCidadao().getProfissao(), cidadaoRetornado.getProfissao());
		Assert.assertEquals(1, cidadaoRetornado.getTelefones().size());
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoEstadual3.getCidadao().getTelefones().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoEstadual4.getCidadao().getTelefones().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoEstadual5.getCidadao().getTelefones().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getTelefones().contains(cidadaoEstadual6.getCidadao().getTelefones().toArray()[0]));
		Assert.assertNotNull(cidadaoRetornado.getRgs());
		Assert.assertEquals(1, cidadaoRetornado.getRgs().size());
		Assert.assertTrue(cidadaoRetornado.getRgs().contains(cidadaoEstadual3.getCidadao().getRgs().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getRgs().contains(cidadaoEstadual4.getCidadao().getRgs().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getRgs().contains(cidadaoEstadual5.getCidadao().getRgs().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getRgs().contains(cidadaoEstadual6.getCidadao().getRgs().toArray()[0]));
		Assert.assertEquals(1, cidadaoRetornado.getEmails().size());
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoEstadual3.getCidadao().getEmails().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoEstadual4.getCidadao().getEmails().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoEstadual5.getCidadao().getEmails().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getEmails().contains(cidadaoEstadual6.getCidadao().getEmails().toArray()[0]));
		Assert.assertEquals(1, cidadaoRetornado.getDadosProfissionais().size());
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoEstadual3.getCidadao().getDadosProfissionais().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoEstadual4.getCidadao().getDadosProfissionais().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoEstadual5.getCidadao().getDadosProfissionais().toArray()[0]));
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().contains(cidadaoEstadual6.getCidadao().getDadosProfissionais().toArray()[0]));
	}
	
	@Test
	public void testDefinirCidadao2Estaduais2RGs() {
		List<CidadaoSistemas> cidadaosSistemas = new ArrayList<>();
		String cpf = "53432219040";
		String nomeSistemaEstadual1 = "sistema-estadual-1";
		String nomeSistemaEstadual2 = "sistema-estadual-2";
		CidadaoSistemas cidadaoEstadual1 = cidadaoEstadual(cpf, nomeSistemaEstadual1);
		CidadaoSistemas cidadaoEstadual2 = cidadaoEstadual(cpf, nomeSistemaEstadual2);
		
		cidadaoEstadual2.getCidadao().setRgs(new HashSet<>());
		RG rg = new RG();
		rg.setCidadao(cidadaoEstadual2.getCidadao());
		rg.setDataEmissao(LocalDate.now().minusYears(5l));
		rg.setId(2l);
		rg.setNumero("XXXXX-XX");
		rg.setSecretariaEmissao("SAC");
		rg.setUf("AC");
		cidadaoEstadual2.getCidadao().getRgs().add(rg);
		
		cidadaosSistemas.add(cidadaoEstadual1);
		cidadaosSistemas.add(cidadaoEstadual2);
		
		Cidadao cidadaoRetornado = definirCidadaoPrincipal.definir(cidadaosSistemas);
		Assert.assertNotNull(cidadaoRetornado);
		Assert.assertEquals(2, cidadaoRetornado.getRgs().size());
		Assert.assertTrue(cidadaoRetornado.getRgs().contains(rg));
		Assert.assertTrue(cidadaoRetornado.getRgs().contains(cidadaoEstadual1.getCidadao().getRgs().toArray()[0]));
	}
	
	private SistemaBuscaDados sistema(String nomeOrgao) {
		SistemaBuscaDados sistemaBuscaDados = new SistemaBuscaDados();
		sistemaBuscaDados.setSistema(new Sistema());
		sistemaBuscaDados.setId(1l);
		sistemaBuscaDados.getSistema().setNome(nomeOrgao);
		return sistemaBuscaDados;
	}
	
	private CidadaoSistemas cidadaoFederal(String cpf, String nomeSistema) {
		CidadaoSistemas cidadaoSistemas = new CidadaoSistemas();
		cidadaoSistemas.setCpf(cpf);
		cidadaoSistemas.setDataBusca(LocalDateTime.now());
		cidadaoSistemas.setId(1l);
		cidadaoSistemas.setSistema(sistema(nomeSistema));
		cidadaoSistemas.setTipoSistema(TipoOrgao.FEDERAL);
		Cidadao cidadao = new Cidadao();
		cidadao.setCidadaoReceitaFederal(new CidadaoReceitaFederal());
		cidadao.getCidadaoReceitaFederal().setDataInscricao(LocalDate.now());
		cidadao.getCidadaoReceitaFederal().setDigitoVerificador(cpf.substring(cpf.length()-2));
		cidadao.getCidadaoReceitaFederal().setSituacaoCadastral("NORMAL");
		cidadao.getCidadaoReceitaFederal().setId(1l);
		cidadao.setCpf(cpf);
		cidadao.setDadosProfissionais(new HashSet<>());
		CidadaoDadosProfissionais profissao = new CidadaoDadosProfissionais();
		profissao.setCargo("Analista de Sistemas");
		profissao.setCnpjEmpresaPagadora("17713859000140");
		profissao.setDataFim(LocalDate.now());
		profissao.setDataInicio(LocalDate.now().minusYears(1l));
		profissao.setNomeEmpresaPagadora("EMPRESA TESTE");
		profissao.setSalarioAnual(60000.00);
		profissao.setSalarioMensal(5000.00);
		cidadao.getDadosProfissionais().add(profissao);
		cidadao.setDataNascimento(LocalDate.now());
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now());
		cidadao.setEmails(new HashSet<>());
		Email email = new Email();
		email.setAtivo(true);
		email.setEmail("testando@bla.com");
		email.setId(1l);
		cidadao.getEmails().add(email);
		cidadao.setEnderecos(new HashSet<>());
		Endereco endereco = new Endereco();
		endereco.setCep("89778745");
		endereco.setComplemento("APTO BLA");
		endereco.setId(1l);
		endereco.setLogradouro("Rua lugar nenhum");
		endereco.setMunicipio("São Paulo");
		endereco.setNumero("S/N");
		endereco.setPais("BR");
		endereco.setUf("SP");
		cidadao.getEnderecos().add(endereco);
		cidadao.setFormacao("PÓS-GRADUADO");
		cidadao.setId(1l);
		cidadao.setNome("CIDADO FEDERAL");
		cidadao.setNomeMae("MAE CIDADAO FEDERAL");
		cidadao.setNomePai("PAI CIDADAO FEDERAL");
		cidadao.setPrincipal(false);
		cidadao.setProfissao("ANALISTA DE SISTEMAS");
		cidadao.setTelefones(new HashSet<>());
		Telefone telefone = new Telefone();
		telefone.setCelular(true);
		telefone.setDdd(11);
		telefone.setDdi(55);
		telefone.setNumero(998786547l);
		telefone.setId(1l);
		cidadao.getTelefones().add(telefone);
		cidadaoSistemas.setCidadao(cidadao);
		return cidadaoSistemas;
	}
	
	private CidadaoSistemas cidadaoEstadual(String cpf, String nomeSistema) {
		CidadaoSistemas cidadaoSistemas = new CidadaoSistemas();
		cidadaoSistemas.setCpf(cpf);
		cidadaoSistemas.setDataBusca(LocalDateTime.now());
		cidadaoSistemas.setId(1l);
		cidadaoSistemas.setSistema(sistema(nomeSistema));
		cidadaoSistemas.setTipoSistema(TipoOrgao.ESTADUAL);
		Cidadao cidadao = new Cidadao();
		cidadao.setCpf(cpf);
		cidadao.setDadosProfissionais(new HashSet<>());
		CidadaoDadosProfissionais profissao = new CidadaoDadosProfissionais();
		profissao.setCargo("Engenheiro de Software");
		profissao.setCnpjEmpresaPagadora("75550140000132");
		profissao.setDataInicio(LocalDate.now().minusYears(1l));
		profissao.setNomeEmpresaPagadora("EMPRESA TESTE ESTADUAL");
		profissao.setSalarioAnual(60000.00);
		profissao.setSalarioMensal(5000.00);
		cidadao.getDadosProfissionais().add(profissao);
		cidadao.setDataNascimento(LocalDate.now());
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now());
		cidadao.setEmails(new HashSet<>());
		Email email = new Email();
		email.setAtivo(true);
		email.setEmail("testandoestadual@bla.com");
		email.setId(1l);
		cidadao.getEmails().add(email);
		cidadao.setEnderecos(new HashSet<>());
		Endereco endereco = new Endereco();
		endereco.setCep("55578745");
		endereco.setComplemento("APTO BLA2");
		endereco.setId(1l);
		endereco.setLogradouro("Rua algum");
		endereco.setMunicipio("São Paulo");
		endereco.setNumero("55");
		endereco.setPais("BR");
		endereco.setUf("SP");
		cidadao.getEnderecos().add(endereco);
		cidadao.setFormacao("PÓS-DOUTORADO");
		cidadao.setId(1l);
		cidadao.setNome("CIDADO ESTADUAL");
		cidadao.setNomeMae("MAE CIDADAO ESTADUAL");
		cidadao.setNomePai("PAI CIDADAO ESTADUAL");
		cidadao.setPrincipal(false);
		cidadao.setProfissao("ENGENHEIRO DE SOFTWARE");
		cidadao.setTelefones(new HashSet<>());
		Telefone telefone = new Telefone();
		telefone.setCelular(true);
		telefone.setDdd(15);
		telefone.setDdi(55);
		telefone.setNumero(998755547l);
		telefone.setId(1l);
		cidadao.getTelefones().add(telefone);
		cidadao.setRgs(new HashSet<>());
		RG rg = new RG();
		rg.setDataEmissao(LocalDate.now());
		rg.setId(1l);
		rg.setNumero("897568487");
		rg.setSecretariaEmissao("SSP");
		rg.setUf("SP");
		cidadao.getRgs().add(rg);
		cidadaoSistemas.setCidadao(cidadao);
		return cidadaoSistemas;
	}
	
	private CidadaoSistemas cidadaoMunicipal(String cpf, String nomeSistema) {
		CidadaoSistemas cidadaoSistemas = new CidadaoSistemas();
		cidadaoSistemas.setCpf(cpf);
		cidadaoSistemas.setDataBusca(LocalDateTime.now());
		cidadaoSistemas.setId(1l);
		cidadaoSistemas.setSistema(sistema(nomeSistema));
		cidadaoSistemas.setTipoSistema(TipoOrgao.MUNICIPAL);
		Cidadao cidadao = new Cidadao();
		cidadao.setCpf(cpf);
		cidadao.setDadosProfissionais(new HashSet<>());
		CidadaoDadosProfissionais profissao = new CidadaoDadosProfissionais();
		profissao.setCargo("Arquiteto de Soluções");
		profissao.setCnpjEmpresaPagadora("15775271000196");
		profissao.setDataInicio(LocalDate.now().minusYears(1l));
		profissao.setNomeEmpresaPagadora("EMPRESA TESTE MUNICIPAL");
		profissao.setSalarioAnual(60000.00);
		profissao.setSalarioMensal(5000.00);
		cidadao.getDadosProfissionais().add(profissao);
		cidadao.setDataNascimento(LocalDate.now());
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now());
		cidadao.setEmails(new HashSet<>());
		Email email = new Email();
		email.setAtivo(true);
		email.setEmail("testandomunicipal@bla.com");
		email.setId(1l);
		cidadao.getEmails().add(email);
		cidadao.setEnderecos(new HashSet<>());
		Endereco endereco = new Endereco();
		endereco.setCep("89766645");
		endereco.setComplemento("APTO BLA-3 BLOCO 4");
		endereco.setId(1l);
		endereco.setLogradouro("Rua nenhuma especifica");
		endereco.setMunicipio("São Paulo");
		endereco.setNumero("666");
		endereco.setPais("BR");
		endereco.setUf("SP");
		cidadao.getEnderecos().add(endereco);
		cidadao.setFormacao("GRADUADO");
		cidadao.setId(1l);
		cidadao.setNome("CIDADO MUNICIPAL");
		cidadao.setNomeMae("MAE CIDADAO MUNICIPAL");
		cidadao.setNomePai("PAI CIDADAO MUNICIPAL");
		cidadao.setPrincipal(false);
		cidadao.setProfissao("ARQUITETO DE SOLUÇÕES");
		cidadao.setTelefones(new HashSet<>());
		Telefone telefone = new Telefone();
		telefone.setCelular(true);
		telefone.setDdd(14);
		telefone.setDdi(55);
		telefone.setNumero(998786666l);
		telefone.setId(1l);
		cidadao.getTelefones().add(telefone);
		cidadaoSistemas.setCidadao(cidadao);
		return cidadaoSistemas;
	}

}
