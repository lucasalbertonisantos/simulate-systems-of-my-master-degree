package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;

@RunWith(SpringRunner.class)
public class RespostaColunaNomeMaeTest {
	
	@InjectMocks
	private RespostaColunaNomeMae respostaColunaNomeMae;
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarNull() {
		respostaColunaNomeMae.avaliar(null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarCidadaoNull() {
		Resposta resposta = new Resposta();
		respostaColunaNomeMae.avaliar(resposta, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarStringRespostaNull() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		respostaColunaNomeMae.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarRespostaVazia() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("");
		respostaColunaNomeMae.avaliar(resposta, cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarRespostaErrada() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("Mae do Teste");
		respostaColunaNomeMae.avaliar(resposta, cidadao);
	}

}
