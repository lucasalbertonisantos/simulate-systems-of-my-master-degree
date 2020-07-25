package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder.EmailBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoDadosProfissionais;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoReceitaFederal;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoSistemas;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Email;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Endereco;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.RG;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Telefone;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.CidadaoRespostaFila;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.DadosReceitaFederal;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.EnderecoRespostaFila;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.ProfissaoRespostaFila;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TelefoneRespostaFila;

@RunWith(SpringRunner.class)
public class CidadaoRespostaFilaToCidadaoTest {
	
	@InjectMocks
	private CidadaoRespostaFilaToCidadao cidadaoRespostaFilaToCidadao;
	
	@Mock
	private DadosReceitaFederalToCidadaoReceitaFederal dadosReceitaFederalToCidadaoReceitaFederal;
	
	@Mock
	private ProfissaoRespostaFilaToCidadaoDadosProfissionais profissaoRespostaFilaToCidadaoDadosProfissionais;
	
	@Mock
	private EnderecoRespostaFilaToEndereco enderecoRespostaFilaToEndereco;
	
	@Mock
	private EmailBuilder emailBuilder;
	
	@Mock
	private TelefoneRespostaFilaToTelefone telefoneRespostaFilaToTelefone;
	
	@Test
	public void testParseNull() {
		Assert.assertNotNull(cidadaoRespostaFilaToCidadao.parse(null, null));
	}
	
	@Test
	public void testParseObjetosNulos() {
		CidadaoRespostaFila cidadaoRespostaFila = new CidadaoRespostaFila();
		cidadaoRespostaFila.setCpf("25588216016");
		cidadaoRespostaFila.setDataNascimento(LocalDate.now());
		cidadaoRespostaFila.setDataUltimaAtualizacao(cidadaoRespostaFila.getDataUltimaAtualizacao());
		cidadaoRespostaFila.setFormacao("SUPERIOR COMPLETO");
		cidadaoRespostaFila.setNome("CIDADAO DO BLA");
		cidadaoRespostaFila.setNomeMae("MAE DO CIDADAO DO BLA");
		cidadaoRespostaFila.setNomePai("PAI DO CIDADAO DO BLA");
		cidadaoRespostaFila.setProfissao("Analista de Sistemas");
		CidadaoSistemas cidadaoSistemas = new CidadaoSistemas();
		
		Cidadao cidadaoRetornado = cidadaoRespostaFilaToCidadao.parse(cidadaoRespostaFila, cidadaoSistemas);
		Assert.assertNotNull(cidadaoRetornado);
		Assert.assertEquals(cidadaoSistemas, cidadaoRetornado.getCidadaoSistema());
		Assert.assertEquals(cidadaoRespostaFila.getCpf(), cidadaoRetornado.getCpf());
		Assert.assertEquals(cidadaoRespostaFila.getDataNascimento(), cidadaoRetornado.getDataNascimento());
		Assert.assertEquals(cidadaoRespostaFila.getDataUltimaAtualizacao(), cidadaoRetornado.getDataUltimaAtualizacao());
		Assert.assertEquals(cidadaoRespostaFila.getFormacao(), cidadaoRetornado.getFormacao());
		Assert.assertEquals(cidadaoRespostaFila.getNome(), cidadaoRetornado.getNome());
		Assert.assertEquals(cidadaoRespostaFila.getNomeMae(), cidadaoRetornado.getNomeMae());
		Assert.assertEquals(cidadaoRespostaFila.getNomePai(), cidadaoRetornado.getNomePai());
		Assert.assertEquals(cidadaoRespostaFila.getProfissao(), cidadaoRetornado.getProfissao());
		Assert.assertNull(cidadaoRetornado.getCidadaoReceitaFederal());
		Assert.assertNull(cidadaoRetornado.getDadosProfissionais());
		Assert.assertNull(cidadaoRetornado.getEmails());
		Assert.assertNull(cidadaoRetornado.getEnderecos());
		Assert.assertNull(cidadaoRetornado.getRgs());
		Assert.assertNull(cidadaoRetornado.getTelefones());
	}
	
	@Test
	public void testParseListasVazias() {
		CidadaoRespostaFila cidadaoRespostaFila = new CidadaoRespostaFila();
		cidadaoRespostaFila.setCpf("25588216016");
		cidadaoRespostaFila.setDataNascimento(LocalDate.now());
		cidadaoRespostaFila.setDataUltimaAtualizacao(cidadaoRespostaFila.getDataUltimaAtualizacao());
		cidadaoRespostaFila.setFormacao("SUPERIOR COMPLETO");
		cidadaoRespostaFila.setNome("CIDADAO DO BLA");
		cidadaoRespostaFila.setNomeMae("MAE DO CIDADAO DO BLA");
		cidadaoRespostaFila.setNomePai("PAI DO CIDADAO DO BLA");
		cidadaoRespostaFila.setProfissao("Analista de Sistemas");
		cidadaoRespostaFila.setDadosReceitaFederal(new DadosReceitaFederal());
		cidadaoRespostaFila.setEmails(new ArrayList<>());
		cidadaoRespostaFila.setEnderecos(new ArrayList<>());
		cidadaoRespostaFila.setProfissoes(new ArrayList<>());
		cidadaoRespostaFila.setTelefones(new ArrayList<>());
		CidadaoSistemas cidadaoSistemas = new CidadaoSistemas();
		
		CidadaoReceitaFederal cidadaoReceitaFederal = new CidadaoReceitaFederal();
		Mockito.when(dadosReceitaFederalToCidadaoReceitaFederal.parse(cidadaoRespostaFila.getDadosReceitaFederal())).thenReturn(cidadaoReceitaFederal);
		
		Cidadao cidadaoRetornado = cidadaoRespostaFilaToCidadao.parse(cidadaoRespostaFila, cidadaoSistemas);
		Assert.assertNotNull(cidadaoRetornado);
		Assert.assertEquals(cidadaoSistemas, cidadaoRetornado.getCidadaoSistema());
		Assert.assertEquals(cidadaoRespostaFila.getCpf(), cidadaoRetornado.getCpf());
		Assert.assertEquals(cidadaoRespostaFila.getDataNascimento(), cidadaoRetornado.getDataNascimento());
		Assert.assertEquals(cidadaoRespostaFila.getDataUltimaAtualizacao(), cidadaoRetornado.getDataUltimaAtualizacao());
		Assert.assertEquals(cidadaoRespostaFila.getFormacao(), cidadaoRetornado.getFormacao());
		Assert.assertEquals(cidadaoRespostaFila.getNome(), cidadaoRetornado.getNome());
		Assert.assertEquals(cidadaoRespostaFila.getNomeMae(), cidadaoRetornado.getNomeMae());
		Assert.assertEquals(cidadaoRespostaFila.getNomePai(), cidadaoRetornado.getNomePai());
		Assert.assertEquals(cidadaoRespostaFila.getProfissao(), cidadaoRetornado.getProfissao());
		Assert.assertEquals(cidadaoReceitaFederal, cidadaoRetornado.getCidadaoReceitaFederal());
		Assert.assertNull(cidadaoRetornado.getDadosProfissionais());
		Assert.assertNull(cidadaoRetornado.getEmails());
		Assert.assertNull(cidadaoRetornado.getEnderecos());
		Assert.assertNull(cidadaoRetornado.getRgs());
		Assert.assertNull(cidadaoRetornado.getTelefones());
	}
	
	@Test
	public void testParseRetornosNulos() {
		CidadaoRespostaFila cidadaoRespostaFila = new CidadaoRespostaFila();
		cidadaoRespostaFila.setCpf("25588216016");
		cidadaoRespostaFila.setDataNascimento(LocalDate.now());
		cidadaoRespostaFila.setDataUltimaAtualizacao(cidadaoRespostaFila.getDataUltimaAtualizacao());
		cidadaoRespostaFila.setFormacao("SUPERIOR COMPLETO");
		cidadaoRespostaFila.setNome("CIDADAO DO BLA");
		cidadaoRespostaFila.setNomeMae("MAE DO CIDADAO DO BLA");
		cidadaoRespostaFila.setNomePai("PAI DO CIDADAO DO BLA");
		cidadaoRespostaFila.setProfissao("Analista de Sistemas");
		cidadaoRespostaFila.setDadosReceitaFederal(new DadosReceitaFederal());
		cidadaoRespostaFila.setEmails(new ArrayList<>());
		cidadaoRespostaFila.getEmails().add("aaaaa@aaaa.com");
		cidadaoRespostaFila.setEnderecos(new ArrayList<>());
		cidadaoRespostaFila.getEnderecos().add(new EnderecoRespostaFila());
		cidadaoRespostaFila.setProfissoes(new ArrayList<>());
		cidadaoRespostaFila.getProfissoes().add(new ProfissaoRespostaFila());
		cidadaoRespostaFila.setTelefones(new ArrayList<>());
		cidadaoRespostaFila.getTelefones().add(new TelefoneRespostaFila());
		cidadaoRespostaFila.setRg("   ");
		CidadaoSistemas cidadaoSistemas = new CidadaoSistemas();
		
		Mockito.when(dadosReceitaFederalToCidadaoReceitaFederal.parse(cidadaoRespostaFila.getDadosReceitaFederal())).thenReturn(null);
		Mockito.when(profissaoRespostaFilaToCidadaoDadosProfissionais.parse(cidadaoRespostaFila.getProfissoes().get(0))).thenReturn(null);
		Mockito.when(enderecoRespostaFilaToEndereco.parse(cidadaoRespostaFila.getEnderecos().get(0))).thenReturn(null);
		Mockito.when(emailBuilder.build(cidadaoRespostaFila.getEmails().get(0))).thenReturn(null);
		Mockito.when(telefoneRespostaFilaToTelefone.parse(cidadaoRespostaFila.getTelefones().get(0))).thenReturn(null);
		
		Cidadao cidadaoRetornado = cidadaoRespostaFilaToCidadao.parse(cidadaoRespostaFila, cidadaoSistemas);
		Assert.assertNotNull(cidadaoRetornado);
		Assert.assertEquals(cidadaoSistemas, cidadaoRetornado.getCidadaoSistema());
		Assert.assertEquals(cidadaoRespostaFila.getCpf(), cidadaoRetornado.getCpf());
		Assert.assertEquals(cidadaoRespostaFila.getDataNascimento(), cidadaoRetornado.getDataNascimento());
		Assert.assertEquals(cidadaoRespostaFila.getDataUltimaAtualizacao(), cidadaoRetornado.getDataUltimaAtualizacao());
		Assert.assertEquals(cidadaoRespostaFila.getFormacao(), cidadaoRetornado.getFormacao());
		Assert.assertEquals(cidadaoRespostaFila.getNome(), cidadaoRetornado.getNome());
		Assert.assertEquals(cidadaoRespostaFila.getNomeMae(), cidadaoRetornado.getNomeMae());
		Assert.assertEquals(cidadaoRespostaFila.getNomePai(), cidadaoRetornado.getNomePai());
		Assert.assertEquals(cidadaoRespostaFila.getProfissao(), cidadaoRetornado.getProfissao());
		Assert.assertNull(cidadaoRetornado.getCidadaoReceitaFederal());
		Assert.assertNotNull(cidadaoRetornado.getDadosProfissionais());
		Assert.assertTrue(cidadaoRetornado.getDadosProfissionais().isEmpty());
		Assert.assertNotNull(cidadaoRetornado.getEmails());
		Assert.assertTrue(cidadaoRetornado.getEmails().isEmpty());
		Assert.assertNotNull(cidadaoRetornado.getEnderecos());
		Assert.assertTrue(cidadaoRetornado.getEnderecos().isEmpty());
		Assert.assertNotNull(cidadaoRetornado.getTelefones());
		Assert.assertTrue(cidadaoRetornado.getTelefones().isEmpty());
		Assert.assertNull(cidadaoRetornado.getRgs());
	}
	
	@Test
	public void testParse() {
		CidadaoRespostaFila cidadaoRespostaFila = new CidadaoRespostaFila();
		cidadaoRespostaFila.setCpf("25588216016");
		cidadaoRespostaFila.setDataNascimento(LocalDate.now());
		cidadaoRespostaFila.setDataUltimaAtualizacao(cidadaoRespostaFila.getDataUltimaAtualizacao());
		cidadaoRespostaFila.setFormacao("SUPERIOR COMPLETO");
		cidadaoRespostaFila.setNome("CIDADAO DO BLA");
		cidadaoRespostaFila.setNomeMae("MAE DO CIDADAO DO BLA");
		cidadaoRespostaFila.setNomePai("PAI DO CIDADAO DO BLA");
		cidadaoRespostaFila.setProfissao("Analista de Sistemas");
		cidadaoRespostaFila.setDadosReceitaFederal(new DadosReceitaFederal());
		cidadaoRespostaFila.setEmails(new ArrayList<>());
		cidadaoRespostaFila.getEmails().add("aaaaa@aaaa.com");
		cidadaoRespostaFila.setEnderecos(new ArrayList<>());
		cidadaoRespostaFila.getEnderecos().add(new EnderecoRespostaFila());
		cidadaoRespostaFila.setProfissoes(new ArrayList<>());
		cidadaoRespostaFila.getProfissoes().add(new ProfissaoRespostaFila());
		cidadaoRespostaFila.setTelefones(new ArrayList<>());
		cidadaoRespostaFila.getTelefones().add(new TelefoneRespostaFila());
		cidadaoRespostaFila.setRg("666-XXXX");
		cidadaoRespostaFila.setUfRg("SP");
		cidadaoRespostaFila.setDataEmissaoRG(LocalDate.now());
		cidadaoRespostaFila.setSecretariaEmissaoRG("SSP");
		
		CidadaoSistemas cidadaoSistemas = new CidadaoSistemas();
		
		CidadaoReceitaFederal cidadaoReceitaFederal = new CidadaoReceitaFederal();
		Mockito.when(dadosReceitaFederalToCidadaoReceitaFederal.parse(cidadaoRespostaFila.getDadosReceitaFederal())).thenReturn(cidadaoReceitaFederal);
		
		CidadaoDadosProfissionais dadosProfissionais = new CidadaoDadosProfissionais();
		Mockito.when(profissaoRespostaFilaToCidadaoDadosProfissionais.parse(cidadaoRespostaFila.getProfissoes().get(0))).thenReturn(dadosProfissionais);
		
		Endereco endereco = new Endereco();
		Mockito.when(enderecoRespostaFilaToEndereco.parse(cidadaoRespostaFila.getEnderecos().get(0))).thenReturn(endereco);
		
		Email email = new Email();
		Mockito.when(emailBuilder.build(cidadaoRespostaFila.getEmails().get(0))).thenReturn(email);
		
		Telefone telefone = new Telefone();	
		Mockito.when(telefoneRespostaFilaToTelefone.parse(cidadaoRespostaFila.getTelefones().get(0))).thenReturn(telefone);
		
		Cidadao cidadaoRetornado = cidadaoRespostaFilaToCidadao.parse(cidadaoRespostaFila, cidadaoSistemas);
		Assert.assertNotNull(cidadaoRetornado);
		Assert.assertEquals(cidadaoSistemas, cidadaoRetornado.getCidadaoSistema());
		Assert.assertEquals(cidadaoRespostaFila.getCpf(), cidadaoRetornado.getCpf());
		Assert.assertEquals(cidadaoRespostaFila.getDataNascimento(), cidadaoRetornado.getDataNascimento());
		Assert.assertEquals(cidadaoRespostaFila.getDataUltimaAtualizacao(), cidadaoRetornado.getDataUltimaAtualizacao());
		Assert.assertEquals(cidadaoRespostaFila.getFormacao(), cidadaoRetornado.getFormacao());
		Assert.assertEquals(cidadaoRespostaFila.getNome(), cidadaoRetornado.getNome());
		Assert.assertEquals(cidadaoRespostaFila.getNomeMae(), cidadaoRetornado.getNomeMae());
		Assert.assertEquals(cidadaoRespostaFila.getNomePai(), cidadaoRetornado.getNomePai());
		Assert.assertEquals(cidadaoRespostaFila.getProfissao(), cidadaoRetornado.getProfissao());
		Assert.assertNotNull(cidadaoRetornado.getCidadaoReceitaFederal());
		Assert.assertEquals(cidadaoRetornado, cidadaoRetornado.getCidadaoReceitaFederal().getCidadao());
		Assert.assertNotNull(cidadaoRetornado.getDadosProfissionais());
		Assert.assertEquals(1, cidadaoRetornado.getDadosProfissionais().size());
		Assert.assertEquals(cidadaoRetornado, ((CidadaoDadosProfissionais)cidadaoRetornado.getDadosProfissionais().toArray()[0]).getCidadao());
		Assert.assertNotNull(cidadaoRetornado.getEmails());
		Assert.assertEquals(1, cidadaoRetornado.getEmails().size());
		Assert.assertEquals(cidadaoRetornado, ((Email)cidadaoRetornado.getEmails().toArray()[0]).getCidadao());
		Assert.assertNotNull(cidadaoRetornado.getEnderecos());
		Assert.assertEquals(1, cidadaoRetornado.getEnderecos().size());
		Assert.assertEquals(cidadaoRetornado, ((Endereco)cidadaoRetornado.getEnderecos().toArray()[0]).getCidadao());
		Assert.assertNotNull(cidadaoRetornado.getTelefones());
		Assert.assertEquals(1, cidadaoRetornado.getTelefones().size());
		Assert.assertEquals(cidadaoRetornado, ((Telefone)cidadaoRetornado.getTelefones().toArray()[0]).getCidadao());
		Assert.assertNotNull(cidadaoRetornado.getRgs());
		Assert.assertEquals(1, cidadaoRetornado.getRgs().size());
		Assert.assertEquals(cidadaoRespostaFila.getRg(), ((RG)cidadaoRetornado.getRgs().toArray()[0]).getNumero());
		Assert.assertEquals(cidadaoRespostaFila.getDataEmissaoRG(), ((RG)cidadaoRetornado.getRgs().toArray()[0]).getDataEmissao());
		Assert.assertEquals(cidadaoRespostaFila.getSecretariaEmissaoRG(), ((RG)cidadaoRetornado.getRgs().toArray()[0]).getSecretariaEmissao());
		Assert.assertEquals(cidadaoRespostaFila.getUfRg(), ((RG)cidadaoRetornado.getRgs().toArray()[0]).getUf());
	}

}
