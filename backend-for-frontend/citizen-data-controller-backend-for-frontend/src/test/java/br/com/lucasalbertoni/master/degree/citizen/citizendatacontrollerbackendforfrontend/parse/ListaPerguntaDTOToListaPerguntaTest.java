package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Pergunta;

@RunWith(SpringRunner.class)
public class ListaPerguntaDTOToListaPerguntaTest {
	
	@InjectMocks
	private ListaPerguntaDTOToListaPergunta listaPerguntaDTOToListaPergunta;
	
	@Test
	public void testParseNull() {
		List<Pergunta> perguntas = listaPerguntaDTOToListaPergunta.parse(null);
		Assert.assertNotNull(perguntas);
		Assert.assertTrue(perguntas.isEmpty());
	}

}
