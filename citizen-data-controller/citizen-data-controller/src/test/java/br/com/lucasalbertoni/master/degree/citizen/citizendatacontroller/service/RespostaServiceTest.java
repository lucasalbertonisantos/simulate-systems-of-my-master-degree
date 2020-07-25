package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.ColunaEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.RespostaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenConfirmarCadastroService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CpfValidator;

@RunWith(SpringRunner.class)
public class RespostaServiceTest {
	
	@InjectMocks
	private RespostaService respostaService;
	
	@Mock
	private RespostaRepository respostaRepository;
	
	@Mock
	private PerguntaService perguntaService;
	
	@Mock
	private CidadaoService cidadaoService;
	
	@Mock
	private TokenConfirmarCadastroService tokenConfirmarCadastroService;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private GeradorSenhaService geradorSenhaService;
	
	@Mock
	private LoginService loginService;
	
	@Mock
	private CpfValidator cpfValidator;
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarCidadaoNaoExiste() {
		String cpf = "053.109.380-87";
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(null);
		
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(null);
		
		respostaService.avaliarSalvar(cpf, null , email);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarPerguntasNull() {
		String cpf = "053.109.380-87";
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(null);

		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		respostaService.avaliarSalvar(cpf, null , email);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarRespostasNull() {
		String cpf = "053.109.380-87";
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);

		List<Pergunta> perguntas = new ArrayList<>();
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(perguntas);

		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		respostaService.avaliarSalvar(cpf, null , email);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarPerguntasVazias() {
		String cpf = "053.109.380-87";
		List<Resposta> respostas = new ArrayList<>();
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		List<Pergunta> perguntas = new ArrayList<>();
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(perguntas);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		respostaService.avaliarSalvar(cpf, respostas , email);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarPerguntasDiferentesRespostas() {
		String cpf = "053.109.380-87";
		List<Resposta> respostas = new ArrayList<>();
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(perguntas);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		respostaService.avaliarSalvar(cpf, respostas , email);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarPerguntaRespostaNull() {
		String cpf = "053.109.380-87";
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(perguntas);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		respostaService.avaliarSalvar(cpf, respostas , email);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarPerguntaIdRespostaNull() {
		String cpf = "053.109.380-87";
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		respostas.get(0).setPergunta(new Pergunta());
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(perguntas);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		respostaService.avaliarSalvar(cpf, respostas , email);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarStringRespostaRespostaNull() {
		String cpf = "053.109.380-87";
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		respostas.get(0).setPergunta(new Pergunta());
		respostas.get(0).getPergunta().setId(1l);
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(perguntas);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		respostaService.avaliarSalvar(cpf, respostas , email);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarStringRespostaRespostaVazia() {
		String cpf = "053.109.380-87";
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		respostas.get(0).setPergunta(new Pergunta());
		respostas.get(0).getPergunta().setId(1l);
		respostas.get(0).setResposta("   ");
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(perguntas);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		respostaService.avaliarSalvar(cpf, respostas , email);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarIdRespostaNull() {
		String cpf = "053.109.380-87";
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		respostas.get(0).setPergunta(new Pergunta());
		respostas.get(0).getPergunta().setId(1l);
		respostas.get(0).setResposta("blablabla");
		respostas.get(0).setId(1l);
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(perguntas);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		respostaService.avaliarSalvar(cpf, respostas , email);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarPerguntaRespostaNaoEncontrada() {
		String cpf = "053.109.380-87";
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		respostas.get(0).setPergunta(new Pergunta());
		respostas.get(0).getPergunta().setId(1l);
		respostas.get(0).setResposta("blablabla");
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(perguntas);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		Mockito.when(perguntaService.findById(respostas.get(0).getPergunta().getId())).thenReturn(null);
		
		respostaService.avaliarSalvar(cpf, respostas , email);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarSalvarPerguntaRespostaColunaNull() {
		String cpf = "053.109.380-87";
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		respostas.get(0).setPergunta(new Pergunta());
		respostas.get(0).getPergunta().setId(1l);
		respostas.get(0).setResposta("blablabla");
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(perguntas);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		Pergunta pergunta = new Pergunta();
		Mockito.when(perguntaService.findById(respostas.get(0).getPergunta().getId())).thenReturn(pergunta);
		
		respostaService.avaliarSalvar(cpf, respostas , email);
	}
	
	@Test
	public void testAvaliarSalvarComRespostasJaExistentes() {
		String cpf = "053.109.380-87";
		String nome = "blablabla";
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		respostas.get(0).setPergunta(new Pergunta());
		respostas.get(0).getPergunta().setId(1l);
		respostas.get(0).setResposta(nome);
		String email = "aaaa@bla.com";
		
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(cpfModificado)).thenReturn(perguntas);
		
		Cidadao cidadao = new Cidadao();
		cidadao.setNome(nome);
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		Pergunta pergunta = new Pergunta();
		pergunta.setColuna(ColunaEnum.NOME);
		Mockito.when(perguntaService.findById(respostas.get(0).getPergunta().getId())).thenReturn(pergunta);
		
		List<Resposta> respostaExistentes = new ArrayList<>();
		respostaExistentes.add(new Resposta());
		Mockito.when(respostaRepository.findByCpfAndAtiva(cpfModificado, true)).thenReturn(respostaExistentes);
		
		List<Resposta> respostasRetornadas = respostaService.avaliarSalvar(cpf, respostas , email);
		Assert.assertNotNull(respostasRetornadas);
	}

}
