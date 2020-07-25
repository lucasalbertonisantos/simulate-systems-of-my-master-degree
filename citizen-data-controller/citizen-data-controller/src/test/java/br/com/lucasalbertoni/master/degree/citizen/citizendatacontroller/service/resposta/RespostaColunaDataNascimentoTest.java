package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;

@RunWith(SpringRunner.class)
public class RespostaColunaDataNascimentoTest {
	
	@InjectMocks
	private RespostaColunaDataNascimento respostaColunaDataNascimento;
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarNull() {
		respostaColunaDataNascimento.avaliar(null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarCidadaoNull() {
		Resposta resposta = new Resposta();
		respostaColunaDataNascimento.avaliar(resposta, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarStringRespostaNull() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		respostaColunaDataNascimento.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarStringRespostaVazia() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("   ");
		respostaColunaDataNascimento.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarDateTimeParseException() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("AAAAAAAA");
		respostaColunaDataNascimento.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarRespostaInvalida() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("2000-12-03");
		respostaColunaDataNascimento.avaliar(resposta, cidadao);
	}

}
