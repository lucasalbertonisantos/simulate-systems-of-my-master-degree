package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.PerguntaDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.ListaPerguntaDTOToListaPergunta;

@RunWith(SpringRunner.class)
public class PerguntaApiClientTest {
	
	@InjectMocks
	private PerguntaApiClient perguntaApiClient;
	
	@Mock
	private GenericApiClient genericApiClient;
	
	@Mock
	private ListaPerguntaDTOToListaPergunta listaPerguntaDTOToListaPergunta;
	
	@Test
	public void testSolicitar() {
		String cpf = "159.042.620-78";
		String url = "bla-666";
		ReflectionTestUtils.setField(perguntaApiClient, "url", url);
		
		PerguntaDTO[] resposta = new PerguntaDTO[10];
		Mockito.when(genericApiClient.putRequestWithInternalCredentials(url+"?cpf="+cpf, PerguntaDTO[].class)).thenReturn(resposta);
		
		List<Pergunta> perguntas = new ArrayList<>();
		Mockito.when(listaPerguntaDTOToListaPergunta.parse(resposta)).thenReturn(perguntas);
		
		List<Pergunta> perguntasRetornadas = perguntaApiClient.solicitar(cpf);
		Assert.assertEquals(perguntas, perguntasRetornadas);
	}

}
