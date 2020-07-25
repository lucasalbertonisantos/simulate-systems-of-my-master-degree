package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Sistema;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.SistemaRepository;

@RunWith(SpringRunner.class)
public class SistemaServiceTest {
	
	@InjectMocks
	private SistemaService sistemaService;
	
	@Mock
	private SistemaRepository sistemaRepository;
	
	@Test(expected = RuntimeException.class)
	public void testFindByNomeOrCreateNull() {
		sistemaService.findByNomeOrCreate(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindByNomeOrCreateVazio() {
		sistemaService.findByNomeOrCreate("  ");
	}
	
	@Test
	public void testFindByNomeOrCreateNaoExiste() {
		String nome = "blablabla";
		Mockito.when(sistemaRepository.findByNome(nome)).thenReturn(Optional.empty());
		Sistema sistema = new Sistema();
		Mockito.when(sistemaRepository.save(Mockito.any())).thenReturn(sistema);
		Sistema sistemaRetornado = sistemaService.findByNomeOrCreate(nome);
		Assert.assertEquals(sistema, sistemaRetornado);
		ArgumentCaptor<Sistema> sistemaCaptor = ArgumentCaptor.forClass(Sistema.class);
		Mockito.verify(sistemaRepository).save(sistemaCaptor.capture());
		Assert.assertEquals(nome, sistemaCaptor.getValue().getNome());
	}
	
	@Test
	public void testFindByNomeOrCreateExiste() {
		String nome = "blablabla";
		Sistema sistema = new Sistema();
		Mockito.when(sistemaRepository.findByNome(nome)).thenReturn(Optional.of(sistema));
		Sistema sistemaRetornado = sistemaService.findByNomeOrCreate(nome);
		Assert.assertEquals(sistema, sistemaRetornado);
		Mockito.verify(sistemaRepository, Mockito.times(0)).save(Mockito.any());
	}

}
