package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.CidadaoRepository;

@RunWith(SpringRunner.class)
public class CidadaoServiceTest {
	
	@InjectMocks
	private CidadaoService cidadaoService;
	
	@Mock
	private CidadaoRepository cidadaoRepository;
	
	@Test
	public void testExistsByCpf() {
		String cpf = "79279240013";
		Mockito.when(cidadaoRepository.existsByCpf(cpf)).thenReturn(false);
		Assert.assertFalse(cidadaoService.existsByCpf(cpf));
		Mockito.when(cidadaoRepository.existsByCpf(cpf)).thenReturn(true);
		Assert.assertTrue(cidadaoService.existsByCpf(cpf));
	}
	
	@Test
	public void testExistsPrincipalByCpf() {
		String cpf = "79279240013";
		Mockito.when(cidadaoRepository.existsByCpfAndPrincipal(cpf, true)).thenReturn(false);
		Assert.assertFalse(cidadaoService.existsPrincipalByCpf(cpf));
		Mockito.when(cidadaoRepository.existsByCpfAndPrincipal(cpf, true)).thenReturn(true);
		Assert.assertTrue(cidadaoService.existsPrincipalByCpf(cpf));
	}
	
	@Test
	public void testFindPrincipalByCpf(){
		String cpf = "79279240013";
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoRepository.findByCpfAndPrincipal(cpf, true)).thenReturn(cidadao);
		Cidadao cidadaoRetornado = cidadaoService.findPrincipalByCpf(cpf);
		Assert.assertEquals(cidadao, cidadaoRetornado);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveNull() {
		cidadaoService.save(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveIdNotNull() {
		Cidadao cidadao = new Cidadao();
		cidadao.setId(1l);
		cidadaoService.save(cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveNomeNulo() {
		Cidadao cidadao = new Cidadao();
		cidadaoService.save(cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveNomeVazio() {
		Cidadao cidadao = new Cidadao();
		cidadao.setNome("   ");
		cidadaoService.save(cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveCpfNulo() {
		Cidadao cidadao = new Cidadao();
		cidadao.setNome("Teste");
		cidadaoService.save(cidadao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveCpfVazio() {
		Cidadao cidadao = new Cidadao();
		cidadao.setNome("Teste");
		cidadao.setCpf("   ");
		cidadaoService.save(cidadao);
	}
	
	@Test
	public void testSave() {
		Cidadao cidadao = new Cidadao();
		cidadao.setNome("Teste");
		cidadao.setCpf("19583700029");
		
		Cidadao cidadaoSalvo = new Cidadao();
		cidadaoSalvo.setNome(cidadao.getNome());
		cidadaoSalvo.setCpf(cidadao.getCpf());
		cidadaoSalvo.setId(1l);
		Mockito.when(cidadaoRepository.save(cidadao)).thenReturn(cidadaoSalvo);
		
		Cidadao cidadaoRetornado = cidadaoService.save(cidadao);
		Assert.assertEquals(cidadaoSalvo, cidadaoRetornado);
	}

}
