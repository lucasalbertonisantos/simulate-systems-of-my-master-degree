package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.BuscaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Sistema;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.SistemaBuscaDados;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.SistemaBuscaDadosRepository;

@RunWith(SpringRunner.class)
public class SistemaBuscaDadosServiceTest {
	
	@InjectMocks
	private SistemaBuscaDadosService sistemaBuscaDadosService;
	
	@Mock
	private SistemaBuscaDadosRepository sistemaBuscaDadosRepository;
	
	@Test(expected = RuntimeException.class)
	public void testSaveNull() {
		sistemaBuscaDadosService.save(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveBuscaCidadaoNull() {
		SistemaBuscaDados sistemaBuscaDados = new SistemaBuscaDados();
		sistemaBuscaDadosService.save(sistemaBuscaDados);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveIdBuscaCidadaoNull() {
		SistemaBuscaDados sistemaBuscaDados = new SistemaBuscaDados();
		sistemaBuscaDados.setBuscaCidadao(new BuscaCidadao());
		sistemaBuscaDadosService.save(sistemaBuscaDados);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveSistemaNull() {
		SistemaBuscaDados sistemaBuscaDados = new SistemaBuscaDados();
		sistemaBuscaDados.setBuscaCidadao(new BuscaCidadao());
		sistemaBuscaDados.getBuscaCidadao().setId(666L);
		sistemaBuscaDadosService.save(sistemaBuscaDados);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveIdSistemaNull() {
		SistemaBuscaDados sistemaBuscaDados = new SistemaBuscaDados();
		sistemaBuscaDados.setBuscaCidadao(new BuscaCidadao());
		sistemaBuscaDados.getBuscaCidadao().setId(666L);
		sistemaBuscaDados.setSistema(new Sistema());
		sistemaBuscaDadosService.save(sistemaBuscaDados);
	}
	
	@Test
	public void testSavePassandoBuscaCidadaoESistema() {
		Long idBusca = 666l;
		BuscaCidadao busca = new BuscaCidadao();
		busca.setId(idBusca);
		Long idSistema = 777l;
		Sistema sistema = new Sistema();
		sistema.setId(777l);
		

		SistemaBuscaDados sistemaBuscaDadosSalvo = new SistemaBuscaDados();
		sistemaBuscaDadosSalvo.setBuscaCidadao(busca);
		sistemaBuscaDadosSalvo.setSistema(sistema);
		sistemaBuscaDadosSalvo.setId(888L);
		Mockito.when(sistemaBuscaDadosRepository.save(Mockito.any())).thenReturn(sistemaBuscaDadosSalvo);
		
		SistemaBuscaDados sistemaBuscaDadosRetornado = sistemaBuscaDadosService.save(busca, sistema);
		Assert.assertEquals(sistemaBuscaDadosSalvo, sistemaBuscaDadosRetornado);
		Assert.assertEquals(sistemaBuscaDadosSalvo.getId(), sistemaBuscaDadosRetornado.getId());
		Assert.assertEquals(sistemaBuscaDadosSalvo.getBuscaCidadao(), sistemaBuscaDadosRetornado.getBuscaCidadao());
		Assert.assertEquals(sistemaBuscaDadosSalvo.getSistema(), sistemaBuscaDadosRetornado.getSistema());
		
		ArgumentCaptor<SistemaBuscaDados> sistemaBuscaDadosCaptor = ArgumentCaptor.forClass(SistemaBuscaDados.class);
		Mockito.verify(sistemaBuscaDadosRepository).save(sistemaBuscaDadosCaptor.capture());
		Assert.assertEquals(busca, sistemaBuscaDadosCaptor.getValue().getBuscaCidadao());
		Assert.assertEquals(idBusca, sistemaBuscaDadosCaptor.getValue().getBuscaCidadao().getId());
		Assert.assertEquals(sistema, sistemaBuscaDadosCaptor.getValue().getSistema());
		Assert.assertEquals(idSistema, sistemaBuscaDadosCaptor.getValue().getSistema().getId());
	}
	
	@Test
	public void testSave() {
		SistemaBuscaDados sistemaBuscaDados = new SistemaBuscaDados();
		sistemaBuscaDados.setBuscaCidadao(new BuscaCidadao());
		sistemaBuscaDados.getBuscaCidadao().setId(666L);
		sistemaBuscaDados.setSistema(new Sistema());
		sistemaBuscaDados.getSistema().setId(777L);
		SistemaBuscaDados sistemaBuscaDadosSalvo = new SistemaBuscaDados();
		sistemaBuscaDadosSalvo.setBuscaCidadao(sistemaBuscaDados.getBuscaCidadao());
		sistemaBuscaDadosSalvo.setSistema(sistemaBuscaDados.getSistema());
		sistemaBuscaDadosSalvo.setId(888L);
		
		Mockito.when(sistemaBuscaDadosRepository.save(sistemaBuscaDados)).thenReturn(sistemaBuscaDadosSalvo);
		
		SistemaBuscaDados sistemaBuscaDadosRetornado = sistemaBuscaDadosService.save(sistemaBuscaDados);
		Assert.assertEquals(sistemaBuscaDadosSalvo, sistemaBuscaDadosRetornado);
		Assert.assertEquals(sistemaBuscaDadosSalvo.getId(), sistemaBuscaDadosRetornado.getId());
		Assert.assertEquals(sistemaBuscaDadosSalvo.getBuscaCidadao(), sistemaBuscaDadosRetornado.getBuscaCidadao());
		Assert.assertEquals(sistemaBuscaDadosSalvo.getSistema(), sistemaBuscaDadosRetornado.getSistema());
	}

}
