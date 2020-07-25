package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import java.util.HashSet;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.BuscaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.SistemaBuscaDados;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.BuscaCidadaoRepository;

@RunWith(SpringRunner.class)
public class BuscaCidadaoServiceTest {
	
	@InjectMocks
	private BuscaCidadaoService buscaCidadaoService;
	
	@Mock
	private BuscaCidadaoRepository buscaCidadaoRepository;
	
	@Test(expected = RuntimeException.class)
	public void testSaveNull() {
		buscaCidadaoService.save(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveIdSolicitacaoNull() {
		BuscaCidadao buscaCidadao = new BuscaCidadao();
		buscaCidadaoService.save(buscaCidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveIdSolicitacaoVazio() {
		BuscaCidadao buscaCidadao = new BuscaCidadao();
		buscaCidadao.setIdSolicitacao("   ");
		buscaCidadaoService.save(buscaCidadao);
	}
	
	@Test
	public void testSave() {
		BuscaCidadao buscaCidadao = new BuscaCidadao();
		buscaCidadao.setIdSolicitacao("666-xxxx");
		buscaCidadao.setCpf("81124981055");
		BuscaCidadao buscaCidadaoSalva = new BuscaCidadao();
		buscaCidadaoSalva.setIdSolicitacao(buscaCidadao.getIdSolicitacao());
		buscaCidadaoSalva.setCpf(buscaCidadao.getCpf());
		buscaCidadaoSalva.setId(666L);
		Mockito.when(buscaCidadaoRepository.save(buscaCidadao)).thenReturn(buscaCidadaoSalva);
		BuscaCidadao buscaCidadaoRetornada = buscaCidadaoService.save(buscaCidadao);
		Assert.assertEquals(buscaCidadaoRetornada, buscaCidadaoSalva);
		Assert.assertEquals(buscaCidadaoSalva.getId(), buscaCidadaoRetornada.getId());
		Assert.assertEquals(buscaCidadao.getCpf(), buscaCidadaoRetornada.getCpf());
		Assert.assertEquals(buscaCidadao.getIdSolicitacao(), buscaCidadaoRetornada.getIdSolicitacao());
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindByIdSolicitacaoNull() {
		buscaCidadaoService.findByIdSolicitacao(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindByIdSolicitacaoVazio() {
		buscaCidadaoService.findByIdSolicitacao("   ");
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindByIdSolicitacaoNaoEncontrado() {
		String idSolicitacao = "666-xxxx";
		Optional<BuscaCidadao> optional = Optional.empty();
		Mockito.when(buscaCidadaoRepository.findByIdSolicitacao(idSolicitacao)).thenReturn(optional);
		buscaCidadaoService.findByIdSolicitacao(idSolicitacao);
	}
	
	@Test
	public void testFindByIdSolicitacao() {
		String idSolicitacao = "666-xxxx";
		BuscaCidadao buscaCidadao = new BuscaCidadao();
		buscaCidadao.setCpf("81124981055");
		buscaCidadao.setId(666l);
		buscaCidadao.setIdSolicitacao("666-xxxx");
		buscaCidadao.setRetorno(new HashSet<>());
		buscaCidadao.getRetorno().add(new SistemaBuscaDados());
		Optional<BuscaCidadao> optional = Optional.of(buscaCidadao);
		Mockito.when(buscaCidadaoRepository.findByIdSolicitacao(idSolicitacao)).thenReturn(optional);
		BuscaCidadao buscaCidadaoRetornada = buscaCidadaoService.findByIdSolicitacao(idSolicitacao);
		Assert.assertEquals(buscaCidadao, buscaCidadaoRetornada);
		Assert.assertEquals(buscaCidadao.getId(), buscaCidadaoRetornada.getId());
		Assert.assertEquals(buscaCidadao.getCpf(), buscaCidadaoRetornada.getCpf());
		Assert.assertEquals(buscaCidadao.getIdSolicitacao(), buscaCidadaoRetornada.getIdSolicitacao());
		Assert.assertEquals(buscaCidadao.getRetorno(), buscaCidadaoRetornada.getRetorno());
	}

}
