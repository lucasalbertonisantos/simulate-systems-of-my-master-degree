package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.api;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Permissao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.SolicitarLiberacao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service.CidadaoService;

@RunWith(SpringRunner.class)
public class CidadaoAPITest {
	
	@InjectMocks
	private CidadaoAPI cidadaoAPI;
	
	@Mock
	private CidadaoService cidadaoService;
	
	@Test
	public void testLiberarDados() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		Permissao permissao = new Permissao();
		Mockito.when(cidadaoService.liberarDados(solicitarLiberacao)).thenReturn(permissao);
		Permissao permissaoRetornada = cidadaoAPI.liberarDados(solicitarLiberacao);
		Assert.assertEquals(permissao, permissaoRetornada);
	}

}
