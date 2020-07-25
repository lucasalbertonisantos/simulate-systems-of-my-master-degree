package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.PerguntaApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.CidadaoPergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.ColunaEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.StatusPerguntaFlow;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.CidadaoPerguntaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.CidadaoRespostaRepository;

@RunWith(SpringRunner.class)
public class PerguntaServiceTest {
	
	@InjectMocks
	private PerguntaService perguntaService;
	
	@Mock
	private CidadaoPerguntaRepository cidadaoPerguntaRepository;
	
	@Mock
	private CidadaoRespostaRepository cidadaoRespostaRepository;
	
	@Mock
	private PerguntaApiClient perguntaApiClient;
	
	@Mock
	private PerguntaUltimaPosicaoService perguntaUltimaPosicaoService;
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaNull() {
		perguntaService.solicitarPergunta(null, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaCpfVazio() {
		String cpf = "   ";
		perguntaService.solicitarPergunta(cpf, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaRespostaNull() {
		String cpf = "375.512.300-25";
		perguntaService.solicitarPergunta(cpf, null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaStringRespostaNull() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		perguntaService.solicitarPergunta(cpf, resposta);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaStringRespostaVazia() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		resposta.setResposta("   ");
		perguntaService.solicitarPergunta(cpf, resposta);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaStringPerguntaNull() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		resposta.setResposta("blablabla");
		perguntaService.solicitarPergunta(cpf, resposta);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaStringIdPerguntaNull() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		resposta.setResposta("blablabla");
		resposta.setPergunta(new Pergunta());
		perguntaService.solicitarPergunta(cpf, resposta);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaColunaPerguntaNull() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		resposta.setResposta("blablabla");
		resposta.setPergunta(new Pergunta());
		resposta.getPergunta().setId(1l);
		perguntaService.solicitarPergunta(cpf, resposta);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaDataNascimentoInvalida() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		resposta.setResposta("blablabla");
		resposta.setPergunta(new Pergunta());
		resposta.getPergunta().setId(1l);
		resposta.getPergunta().setColuna(ColunaEnum.DATA_NASCIMENTO);
		perguntaService.solicitarPergunta(cpf, resposta);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaCidadaoPerguntaNaoExistePerguntasIniciarNull() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		resposta.setResposta("blablabla");
		resposta.setPergunta(new Pergunta());
		resposta.getPergunta().setId(1l);
		resposta.getPergunta().setColuna(ColunaEnum.EMAIL);
		Mockito.when(cidadaoPerguntaRepository.findById(cpf)).thenReturn(Optional.empty());
		Mockito.when(perguntaApiClient.solicitar(cpf)).thenReturn(null);
		perguntaService.solicitarPergunta(cpf, resposta);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaCidadaoPerguntaNaoExistePerguntasIniciarVazias() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		resposta.setResposta("blablabla");
		resposta.setPergunta(new Pergunta());
		resposta.getPergunta().setId(1l);
		resposta.getPergunta().setColuna(ColunaEnum.EMAIL);
		Mockito.when(cidadaoPerguntaRepository.findById(cpf)).thenReturn(Optional.empty());
		Mockito.when(perguntaApiClient.solicitar(cpf)).thenReturn(new ArrayList<>());
		perguntaService.solicitarPergunta(cpf, resposta);
	}
	
	@Test
	public void testSolicitarPerguntaCidadaoPerguntaNaoExiste() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		resposta.setResposta("blablabla");
		resposta.setPergunta(new Pergunta());
		resposta.getPergunta().setId(1l);
		resposta.getPergunta().setColuna(ColunaEnum.EMAIL);
		
		Mockito.when(cidadaoPerguntaRepository.findById(cpf)).thenReturn(Optional.empty());
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaApiClient.solicitar(cpf)).thenReturn(perguntas);
		
		StatusPerguntaFlow statusPerguntaFlow = perguntaService.solicitarPergunta(cpf, resposta);
		Assert.assertNotNull(statusPerguntaFlow);
		Assert.assertEquals(perguntas.get(0), statusPerguntaFlow.getPergunta());
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaPerguntasNull() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		resposta.setResposta("blablabla");
		resposta.setPergunta(new Pergunta());
		resposta.getPergunta().setId(1l);
		resposta.getPergunta().setColuna(ColunaEnum.EMAIL);
		CidadaoPergunta cidadaoPergunta = new CidadaoPergunta();
		Optional<CidadaoPergunta> optionalCidadaoPergunta = Optional.of(cidadaoPergunta);
		Mockito.when(cidadaoPerguntaRepository.findById(cpf)).thenReturn(optionalCidadaoPergunta);
		perguntaService.solicitarPergunta(cpf, resposta);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaPerguntasVazia() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		resposta.setResposta("blablabla");
		resposta.setPergunta(new Pergunta());
		resposta.getPergunta().setId(1l);
		resposta.getPergunta().setColuna(ColunaEnum.EMAIL);
		CidadaoPergunta cidadaoPergunta = new CidadaoPergunta();
		cidadaoPergunta.setPerguntas(new ArrayList<>());
		Optional<CidadaoPergunta> optionalCidadaoPergunta = Optional.of(cidadaoPergunta);
		Mockito.when(cidadaoPerguntaRepository.findById(cpf)).thenReturn(optionalCidadaoPergunta);
		perguntaService.solicitarPergunta(cpf, resposta);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarPerguntaColunaPerguntaAtualNull() {
		String cpf = "375.512.300-25";
		Resposta resposta = new Resposta();
		resposta.setResposta("blablabla");
		resposta.setPergunta(new Pergunta());
		resposta.getPergunta().setId(1l);
		resposta.getPergunta().setColuna(ColunaEnum.EMAIL);
		CidadaoPergunta cidadaoPergunta = new CidadaoPergunta();
		cidadaoPergunta.setPerguntas(new ArrayList<>());
		cidadaoPergunta.getPerguntas().add(new Pergunta());
		Optional<CidadaoPergunta> optionalCidadaoPergunta = Optional.of(cidadaoPergunta);
		Mockito.when(cidadaoPerguntaRepository.findById(cpf)).thenReturn(optionalCidadaoPergunta);
		perguntaService.solicitarPergunta(cpf, resposta);
	}

}
