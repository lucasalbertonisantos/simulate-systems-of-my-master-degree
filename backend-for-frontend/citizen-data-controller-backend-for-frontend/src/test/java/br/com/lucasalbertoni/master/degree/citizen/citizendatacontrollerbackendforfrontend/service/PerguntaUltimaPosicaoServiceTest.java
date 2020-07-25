package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.RespostaApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.CidadaoRespostaRepository;

@RunWith(SpringRunner.class)
public class PerguntaUltimaPosicaoServiceTest {
	
	@InjectMocks
	private PerguntaUltimaPosicaoService perguntaUltimaPosicaoService;
	
	@Mock
	private CidadaoRespostaRepository cidadaoRespostaRepository;
	
	@Mock
	private RespostaApiClient respostaApiClient;
	
	@Test(expected = RuntimeException.class)
	public void testProcessarUltimaPosicaoChavesNull() {
		perguntaUltimaPosicaoService.processarUltimaPosicao(null, null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testProcessarUltimaPosicaoChavesVazia() {
		List<String> chaves = new ArrayList<>();
		perguntaUltimaPosicaoService.processarUltimaPosicao(null, null, chaves);
	}
	
	@Test(expected = RuntimeException.class)
	public void testProcessarUltimaPosicaoRespostaNull() {
		List<String> chaves = new ArrayList<>();
		chaves.add("xxxxx");
		perguntaUltimaPosicaoService.processarUltimaPosicao(null, null, chaves);
	}
	
	@Test(expected = RuntimeException.class)
	public void testProcessarUltimaPosicao() {
		String chave = "xxxxx";
		List<String> chaves = new ArrayList<>();
		chaves.add(chave);
		Resposta resposta = new Resposta();
		Mockito.when(cidadaoRespostaRepository.findById(chave)).thenReturn(Optional.empty());
		perguntaUltimaPosicaoService.processarUltimaPosicao(null, resposta, chaves);
	}

}
