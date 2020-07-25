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

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder.RespostaDTOListBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.RespostaDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.ListaRespostaDTOToListaResposta;

@RunWith(SpringRunner.class)
public class RespostaApiClientTest {
	
	@InjectMocks
	private RespostaApiClient respostaApiClient;
	
	@Mock
	private GenericApiClient genericApiClient;
	
	@Mock
	private ListaRespostaDTOToListaResposta listaRespostaDTOToListaResposta;
	
	@Mock
	private RespostaDTOListBuilder respostaDTOListBuilder;
	
	@Test
	public void test() {
		List<Resposta> respostas = new ArrayList<>();
		String cpf = "877.980.030-02";
		String url = "bla-666";
		ReflectionTestUtils.setField(respostaApiClient, "url", url);
		
		List<RespostaDTO> respostasDTO = new ArrayList<>();
		Mockito.when(respostaDTOListBuilder.build(respostas, cpf)).thenReturn(respostasDTO);
		
		RespostaDTO[] resposta = new RespostaDTO[10];
		Mockito.when(genericApiClient.putRequestWithInternalCredentials(url+"?cpf="+cpf, respostasDTO, RespostaDTO[].class)).thenReturn(resposta);
		
		List<Resposta> respostasRetornadasApi = new ArrayList<>();
		Mockito.when(listaRespostaDTOToListaResposta.parse(resposta)).thenReturn(respostasRetornadasApi);
		
		List<Resposta> respostasRetornadas = respostaApiClient.responder(respostas, cpf);
		Assert.assertEquals(respostasRetornadasApi, respostasRetornadas);
	}

}
