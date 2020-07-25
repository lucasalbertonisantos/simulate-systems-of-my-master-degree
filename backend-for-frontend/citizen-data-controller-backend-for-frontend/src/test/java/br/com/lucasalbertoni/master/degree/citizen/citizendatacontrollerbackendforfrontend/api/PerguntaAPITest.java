package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.StatusPerguntaFlow;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service.PerguntaService;

@RunWith(SpringRunner.class)
public class PerguntaAPITest {
	
	@InjectMocks
	private PerguntaAPI perguntaAPI;
	
	@Mock
	private PerguntaService perguntaService;
	
	@Test
	public void testSolicitarProximaPergunta() {
		String cpf = "479.635.250-30";
		Resposta resposta = new Resposta();
		StatusPerguntaFlow statusPerguntaFlow = new StatusPerguntaFlow();
		Mockito.when(perguntaService.solicitarPergunta(cpf, resposta)).thenReturn(statusPerguntaFlow);
		
		StatusPerguntaFlow statusPerguntaFlowRetornado = perguntaAPI.solicitarProximaPergunta(cpf, resposta);
		Assert.assertEquals(statusPerguntaFlow, statusPerguntaFlowRetornado);
	}
	
	@Test
	public void testIniciar() {
		String cpf = "479.635.250-30";
		StatusPerguntaFlow statusPerguntaFlow = new StatusPerguntaFlow();
		Mockito.when(perguntaService.iniciar(cpf)).thenReturn(statusPerguntaFlow);
		
		StatusPerguntaFlow statusPerguntaFlowRetornado = perguntaAPI.iniciar(cpf);
		Assert.assertEquals(statusPerguntaFlow, statusPerguntaFlowRetornado);
	}

}
