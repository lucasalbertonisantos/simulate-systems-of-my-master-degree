package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.RG;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;

@RunWith(SpringRunner.class)
public class RespostaColunaQuaisRGsTest {
	
	@InjectMocks
	private RespostaColunaQuaisRGs respostaColunaQuaisRGs;
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarNull() {
		respostaColunaQuaisRGs.avaliar(null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarCidadaoNull() {
		Resposta resposta = new Resposta();
		respostaColunaQuaisRGs.avaliar(resposta, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarStringRespostaNull() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		respostaColunaQuaisRGs.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarRespostaVazia() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("");
		respostaColunaQuaisRGs.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarRGsCidadaoNull() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("Teste");
		respostaColunaQuaisRGs.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarRGsCidadaoVazio() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("Teste");
		cidadao.setRgs(new HashSet<>());
		respostaColunaQuaisRGs.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarQuantidadeRGsDiferente() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("Teste");
		cidadao.setRgs(new HashSet<>());
		RG rg1 = new RG();
		cidadao.getRgs().add(rg1);
		RG rg2 = new RG();
		cidadao.getRgs().add(rg2);
		respostaColunaQuaisRGs.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarQuantidadeRGsInvalido() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("565478975__SP__AC___897854689__AC");
		cidadao.setRgs(new HashSet<>());
		RG rg1 = new RG();
		cidadao.getRgs().add(rg1);
		RG rg2 = new RG();
		cidadao.getRgs().add(rg2);
		respostaColunaQuaisRGs.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarRespostaErrada() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("565478975__SP___897854689__AC");
		cidadao.setRgs(new HashSet<>());
		RG rg1 = new RG();
		cidadao.getRgs().add(rg1);
		RG rg2 = new RG();
		cidadao.getRgs().add(rg2);
		respostaColunaQuaisRGs.avaliar(resposta, cidadao);
	}

}
