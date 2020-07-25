package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;

@RunWith(SpringRunner.class)
public class RespostaColunaNomePaiTest {
	
	@InjectMocks
	private RespostaColunaNomePai respostaColunaNomePai;
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarNull() {
		respostaColunaNomePai.avaliar(null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarCidadaoNull() {
		Resposta resposta = new Resposta();
		respostaColunaNomePai.avaliar(resposta, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarStringRespostaNull() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		respostaColunaNomePai.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarRespostaVazia() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("");
		respostaColunaNomePai.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarRespostaErrada() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("Pai do Teste");
		respostaColunaNomePai.avaliar(resposta, cidadao);
	}

}
