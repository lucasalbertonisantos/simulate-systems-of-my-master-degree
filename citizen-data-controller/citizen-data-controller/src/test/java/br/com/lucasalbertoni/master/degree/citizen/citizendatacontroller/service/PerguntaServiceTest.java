package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.builder.PerguntaBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.ColunaEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Email;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Pergunta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.PerguntaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CpfValidator;

@RunWith(SpringRunner.class)
public class PerguntaServiceTest {
	
	@InjectMocks
	private PerguntaService perguntaService;
	
	@Mock
	private CidadaoService cidadaoService;
	
	@Mock
	private CpfValidator cpfValidator;
	
	@Mock
	private PerguntaRepository perguntaRepository;
	
	@Spy
	private PerguntaBuilder perguntaBuilder;
	
	@Test(expected = RuntimeException.class)
	public void testCreateIfNotExistOrNotUpdatedPerguntasNull() {
		String cpf = "232.766.730-53";
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		Mockito.when(cpfValidator.validate(cpfModificado)).thenReturn(cpfModificado);
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(new Cidadao());
		Mockito.when(perguntaRepository.findByCpfAndAtivaOrderByOrdemAsc(cpfModificado, true)).thenReturn(null);
		perguntaService.createIfNotExistOrNotUpdated(cpf);
	}
	
	@Test(expected = RuntimeException.class)
	public void testCreateIfNotExistOrNotUpdatedCidadaoNull() {
		String cpf = "232.766.730-53";
		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		Mockito.when(cpfValidator.validate(cpfModificado)).thenReturn(cpfModificado);
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(null);
		Mockito.when(perguntaRepository.findByCpfAndAtivaOrderByOrdemAsc(cpfModificado, true)).thenReturn(null);
		perguntaService.createIfNotExistOrNotUpdated(cpf);
	}
	
	@Test
	public void testCreateIfNotExistOrNotUpdatedJaExiste() {
		String cpf = "232.766.730-53";

		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		Mockito.when(cpfValidator.validate(cpfModificado)).thenReturn(cpfModificado);
		
		Cidadao cidadao = new Cidadao();
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now().minusDays(2l));
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		perguntas.get(0).setDataCriacao(LocalDateTime.now());
		Mockito.when(perguntaRepository.findByCpfAndAtivaOrderByOrdemAsc(cpfModificado, true)).thenReturn(perguntas);
		
		List<Pergunta> perguntasRetornadas = perguntaService.createIfNotExistOrNotUpdated(cpf);
		Assert.assertNotNull(perguntasRetornadas);
		Assert.assertEquals(perguntas.get(0), perguntasRetornadas.get(0));
	}
	
	@Test
	public void testCreatePerguntasJaExistem() {
		String cpf = "232.766.730-53";

		String cpfModificado = cpf.replace(".", "").replace("-", "");
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpfModificado);
		Mockito.when(cpfValidator.validate(cpfModificado)).thenReturn(cpfModificado);
		
		Cidadao cidadao = new Cidadao();
		cidadao.setDataNascimento(LocalDate.now().minusYears(30l));
		cidadao.setNome("Teste");
		cidadao.setNomeMae("Mae do Teste");
		cidadao.setNomePai("Pai do Teste");
		cidadao.setEmails(new HashSet<>());
		Email email = new Email();
		email.setEmail("aaa@bla.com");
		cidadao.getEmails().add(email);
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now());
		Mockito.when(cidadaoService.findByCpf(cpfModificado)).thenReturn(cidadao);
		
		List<Pergunta> perguntas = new ArrayList<>();
		perguntas.add(new Pergunta());
		perguntas.get(0).setDataCriacao(LocalDateTime.now().minusDays(2l));
		Mockito.when(perguntaRepository.findByCpfAndAtivaOrderByOrdemAsc(cpfModificado, true)).thenReturn(perguntas);
		
		Pergunta[] perguntasGeradas = new Pergunta[2];
		Mockito.when(perguntaRepository.saveAll(Mockito.any())).thenReturn(Arrays.asList(perguntasGeradas));
		
		List<Pergunta> perguntasRetornadas = perguntaService.createIfNotExistOrNotUpdated(cpf);
		Assert.assertNotNull(perguntasRetornadas);
		Assert.assertEquals(perguntasGeradas.length, perguntasRetornadas.size());
		
		@SuppressWarnings("unchecked")
		ArgumentCaptor<List<Pergunta>> perguntasCaptor = ArgumentCaptor.forClass(List.class);
		Mockito.verify(perguntaRepository, Mockito.times(2)).saveAll(perguntasCaptor.capture());
		Assert.assertNotNull(perguntasCaptor.getAllValues());
		Assert.assertEquals(2, perguntasCaptor.getAllValues().size());
		Assert.assertNotNull(perguntasCaptor.getAllValues().get(0));
		Assert.assertNotNull(perguntasCaptor.getAllValues().get(0).get(0));
		Assert.assertFalse(perguntasCaptor.getAllValues().get(0).get(0).isAtiva());
		Assert.assertNotNull(perguntasCaptor.getValue());
		Assert.assertEquals(5, perguntasCaptor.getValue().size());
		Assert.assertEquals(ColunaEnum.DATA_NASCIMENTO, perguntasCaptor.getValue().get(0).getColuna());
		Assert.assertEquals(ColunaEnum.NOME, perguntasCaptor.getValue().get(1).getColuna());
		Assert.assertEquals(ColunaEnum.NOME_MAE, perguntasCaptor.getValue().get(2).getColuna());
		Assert.assertEquals(ColunaEnum.NOME_PAI, perguntasCaptor.getValue().get(3).getColuna());
		Assert.assertEquals(ColunaEnum.EMAIL, perguntasCaptor.getValue().get(4).getColuna());
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindByIdNaoExiste() {
		Long id = 666l;
		Mockito.when(perguntaRepository.findById(id)).thenReturn(Optional.empty());
		perguntaService.findById(id);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateNull() {
		perguntaService.update(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateIdNull() {
		Pergunta pergunta = new Pergunta();
		perguntaService.update(pergunta);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateColunaNull() {
		Pergunta pergunta = new Pergunta();
		pergunta.setId(666l);
		perguntaService.update(pergunta);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateCpfNull() {
		Pergunta pergunta = new Pergunta();
		pergunta.setId(666l);
		pergunta.setColuna(ColunaEnum.CELULAR);
		perguntaService.update(pergunta);
	}

}
