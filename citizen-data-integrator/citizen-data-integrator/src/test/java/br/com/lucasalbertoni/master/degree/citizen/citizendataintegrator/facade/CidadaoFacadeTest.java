package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoSistemas;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service.CidadaoService;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service.CidadaoSistemasService;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service.DefinirCidadaoPrincipal;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.validate.CpfValidator;

@RunWith(SpringRunner.class)
public class CidadaoFacadeTest {
	
	@InjectMocks
	private CidadaoFacade cidadaoFacade;
	
	@Mock
	private CidadaoService cidadaoService;
	
	@Mock
	private CidadaoSistemasService cidadaoSistemasService;
	
	@Mock
	private CpfValidator cpfValidator;
	
	@Mock
	private DefinirCidadaoPrincipal definirCidadaoPrincipal;
	
	@Rule
    public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void testFindExisteCidadaoPrincipal() {
		String cpf = "27457279075";
		
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpf);
		Mockito.when(cidadaoService.existsPrincipalByCpf(cpf)).thenReturn(true);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.findPrincipalByCpf(cpf)).thenReturn(cidadao);
		
		Cidadao cidadaoRetornado = cidadaoFacade.find(cpf);
		Assert.assertNotNull(cidadaoRetornado);
		Assert.assertEquals(cidadao, cidadaoRetornado);
		Mockito.verify(cidadaoService, Mockito.times(0)).existsByCpf(cpf);
		Mockito.verify(cidadaoSistemasService, Mockito.times(0)).findByCpf(cpf);
		Mockito.verify(definirCidadaoPrincipal, Mockito.times(0)).definir(Mockito.any());
		Mockito.verify(cidadaoService, Mockito.times(0)).save(Mockito.any());
		Mockito.verify(cidadaoSistemasService, Mockito.times(0)).buscar(cpf);
	}
	
	@Test
	public void testFindNaoExisteCidadaoPrincipalMasExisteCidadoes() {
		String cpf = "27457279075";
		
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpf);
		Mockito.when(cidadaoService.existsPrincipalByCpf(cpf)).thenReturn(false);
		
		Mockito.when(cidadaoService.existsByCpf(cpf)).thenReturn(true);
		
		List<CidadaoSistemas> cidadaos = new ArrayList<>();
		Mockito.when(cidadaoSistemasService.findByCpf(cpf)).thenReturn(cidadaos);
		
		Cidadao cidadaoPrincipal = new Cidadao();
		Mockito.when(definirCidadaoPrincipal.definir(cidadaos)).thenReturn(cidadaoPrincipal);
		
		Cidadao cidadao = new Cidadao();
		Mockito.when(cidadaoService.save(cidadaoPrincipal)).thenReturn(cidadao);
		
		Cidadao cidadaoRetornado = cidadaoFacade.find(cpf);
		Assert.assertNotNull(cidadaoRetornado);
		Assert.assertEquals(cidadao, cidadaoRetornado);
		Mockito.verify(cidadaoService, Mockito.times(0)).findPrincipalByCpf(cpf);
		Mockito.verify(cidadaoSistemasService, Mockito.times(0)).buscar(cpf);
	}
	
	@Test
	public void testFindNaoExisteCidadaos() {
		String cpf = "27457279075";
		
		Mockito.when(cpfValidator.validate(cpf)).thenReturn(cpf);
		Mockito.when(cidadaoService.existsPrincipalByCpf(cpf)).thenReturn(false);
		Mockito.when(cidadaoService.existsByCpf(cpf)).thenReturn(false);
		
		String numeroSolicitacaoBusca = UUID.randomUUID().toString();
		Mockito.when(cidadaoSistemasService.buscar(cpf)).thenReturn(numeroSolicitacaoBusca);
		
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Buscando cidadão: " + numeroSolicitacaoBusca);
		
		cidadaoFacade.find(cpf);
	}

}
