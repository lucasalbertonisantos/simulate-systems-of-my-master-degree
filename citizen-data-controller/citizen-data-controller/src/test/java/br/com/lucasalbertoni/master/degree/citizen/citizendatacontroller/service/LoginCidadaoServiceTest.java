package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.LoginRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.utils.Encryption;

@RunWith(SpringRunner.class)
public class LoginCidadaoServiceTest {
	
	@InjectMocks
	private LoginCidadaoService loginCidadaoService;
	
	@Mock
	private CidadaoService cidadaoService;
	
	@Mock
	private RespostaService respostaService;
	
	@Mock
	private PerguntaService perguntaService;
	
	@Mock
	private Encryption encryption;
	
	@Mock
	private LoginRepository loginRepository;
	
	@Test(expected = RuntimeException.class)
	public void testCreateLoginNull() {
		loginCidadaoService.create(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateRespostasNulas() {
		Login login = new Login();
		login.setCpfCnpj("542.779.460-38");
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(login.getCpfCnpj())).thenReturn(cidadao);
		
		Mockito.when(respostaService.findByCpf(login.getCpfCnpj())).thenReturn(null);
		
		loginCidadaoService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateRespostasVazias() {
		Login login = new Login();
		login.setCpfCnpj("542.779.460-38");
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(login.getCpfCnpj())).thenReturn(cidadao);
		
		List<Resposta> respostas = new ArrayList<>();
		Mockito.when(respostaService.findByCpf(login.getCpfCnpj())).thenReturn(respostas);
		
		loginCidadaoService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreatePerguntasNulas() {
		Login login = new Login();
		login.setCpfCnpj("542.779.460-38");
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(login.getCpfCnpj())).thenReturn(cidadao);
		
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		Mockito.when(respostaService.findByCpf(login.getCpfCnpj())).thenReturn(respostas);
		
		Mockito.when(perguntaService.findByCpf(login.getCpfCnpj())).thenReturn(null);
		
		loginCidadaoService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreatePerguntasVazias() {
		Login login = new Login();
		login.setCpfCnpj("542.779.460-38");
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(login.getCpfCnpj())).thenReturn(cidadao);
		
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		Mockito.when(respostaService.findByCpf(login.getCpfCnpj())).thenReturn(respostas);
		
		List<Pergunta> perguntas = new ArrayList<>();
		Mockito.when(perguntaService.findByCpf(login.getCpfCnpj())).thenReturn(perguntas);
		
		loginCidadaoService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateMaisPerguntasDoQueRespostas() {
		Login login = new Login();
		login.setCpfCnpj("542.779.460-38");
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(login.getCpfCnpj())).thenReturn(cidadao);
		
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		Mockito.when(respostaService.findByCpf(login.getCpfCnpj())).thenReturn(respostas);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(login.getCpfCnpj())).thenReturn(perguntas);
		
		loginCidadaoService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmailNull() {
		Login login = new Login();
		login.setCpfCnpj("542.779.460-38");
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(login.getCpfCnpj())).thenReturn(cidadao);
		
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		Mockito.when(respostaService.findByCpf(login.getCpfCnpj())).thenReturn(respostas);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(login.getCpfCnpj())).thenReturn(perguntas);
		
		loginCidadaoService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateEmailVazio() {
		Login login = new Login();
		login.setCpfCnpj("542.779.460-38");
		login.setEmail("  ");
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(login.getCpfCnpj())).thenReturn(cidadao);
		
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		Mockito.when(respostaService.findByCpf(login.getCpfCnpj())).thenReturn(respostas);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(login.getCpfCnpj())).thenReturn(perguntas);
		
		loginCidadaoService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateSenhaNull() {
		Login login = new Login();
		login.setCpfCnpj("542.779.460-38");
		login.setEmail("xxx@xxx.com");
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(login.getCpfCnpj())).thenReturn(cidadao);
		
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		Mockito.when(respostaService.findByCpf(login.getCpfCnpj())).thenReturn(respostas);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(login.getCpfCnpj())).thenReturn(perguntas);
		
		loginCidadaoService.create(login);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateSenhaVazia() {
		Login login = new Login();
		login.setCpfCnpj("542.779.460-38");
		login.setEmail("xxx@xxx.com");
		login.setSenha("  ");
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(login.getCpfCnpj())).thenReturn(cidadao);
		
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		Mockito.when(respostaService.findByCpf(login.getCpfCnpj())).thenReturn(respostas);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(login.getCpfCnpj())).thenReturn(perguntas);
		
		loginCidadaoService.create(login);
	}
	
	@Test
	public void testCreate() {
		Login login = new Login();
		login.setCpfCnpj("542.779.460-38");
		login.setEmail("xxx@xxx.com");
		login.setSenha("123");
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findByCpf(login.getCpfCnpj())).thenReturn(cidadao);
		
		List<Resposta> respostas = new ArrayList<>();
		respostas.add(new Resposta());
		Mockito.when(respostaService.findByCpf(login.getCpfCnpj())).thenReturn(respostas);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		Mockito.when(perguntaService.findByCpf(login.getCpfCnpj())).thenReturn(perguntas);
		
		String senhaCriptografada = "blablabla";
		Mockito.when(encryption.encrypt(login.getSenha())).thenReturn(senhaCriptografada);
		
		Login loginSalvo = new Login();
		Mockito.when(loginRepository.save(login)).thenReturn(loginSalvo);
		
		Login loginRetornado = loginCidadaoService.create(login);
		Assert.assertNotNull(loginRetornado);
		Assert.assertEquals(loginSalvo, loginRetornado);
		
		ArgumentCaptor<Cidadao> cidadaoCapture = ArgumentCaptor.forClass(Cidadao.class);
		Mockito.verify(cidadaoService).save(cidadaoCapture.capture());
		Assert.assertNotNull(cidadaoCapture.getValue().getRespostas());
		Assert.assertNotNull(cidadaoCapture.getValue().getPerguntas());
		Assert.assertTrue(cidadaoCapture.getValue().getPerguntas().contains(perguntas.get(0)));
		Assert.assertTrue(cidadaoCapture.getValue().getRespostas().contains(respostas.get(0)));
		
		ArgumentCaptor<Login> loginCapture = ArgumentCaptor.forClass(Login.class);
		Mockito.verify(loginRepository).save(loginCapture.capture());
		Assert.assertEquals(senhaCriptografada, loginCapture.getValue().getSenha());
	}

}
