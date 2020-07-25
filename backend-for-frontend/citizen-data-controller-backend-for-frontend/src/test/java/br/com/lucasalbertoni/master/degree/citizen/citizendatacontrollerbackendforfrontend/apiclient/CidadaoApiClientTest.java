package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder.EmpresaDTOBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.SolicitarLiberacao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TokenLiberacaoAcesso;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.EmpresaDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.PermissaoDadosCidadaoEmpresaDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.PermissaoDadosCidadaoEmpresaDTOToTokenLiberacaoAcesso;

@RunWith(SpringRunner.class)
public class CidadaoApiClientTest {
	
	@InjectMocks
	private CidadaoApiClient cidadaoApiClient;
	
	@Mock
	private GenericApiClient genericApiClient;
	
	@Mock
	private EmpresaDTOBuilder empresaDTOBuilder;
	
	@Mock
	private PermissaoDadosCidadaoEmpresaDTOToTokenLiberacaoAcesso permissaoDadosCidadaoEmpresaDTOToTokenLiberacaoAcesso;
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarLiberacaoDadosNull() {
		cidadaoApiClient.solicitarLiberacaoDados(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSolicitarLiberacaoDadosTokenNull() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		cidadaoApiClient.solicitarLiberacaoDados(solicitarLiberacao);
	}
	
	@Test
	public void testSolicitarLiberacaoDados() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		LoginToken token = new LoginToken();
		token.setToken("aa785s62");
		token.setTipo("Bearer ");
		solicitarLiberacao.setToken(token);
		solicitarLiberacao.setCnpj("636.262.300-23");
		
		String url = "bla-666";
		ReflectionTestUtils.setField(cidadaoApiClient, "url", url);
		
		EmpresaDTO empresaDTO = new EmpresaDTO();
		Mockito.when(empresaDTOBuilder.build(solicitarLiberacao.getCnpj())).thenReturn(empresaDTO);
		
		PermissaoDadosCidadaoEmpresaDTO permissaoDTO = new PermissaoDadosCidadaoEmpresaDTO();
		Mockito.when(genericApiClient.postRequest(url, token.getToken(), empresaDTO, PermissaoDadosCidadaoEmpresaDTO.class)).thenReturn(permissaoDTO);
		
		TokenLiberacaoAcesso permissao = new TokenLiberacaoAcesso();
		Mockito.when(permissaoDadosCidadaoEmpresaDTOToTokenLiberacaoAcesso.parse(permissaoDTO)).thenReturn(permissao);
		
		TokenLiberacaoAcesso permissaoRetornada = cidadaoApiClient.solicitarLiberacaoDados(solicitarLiberacao);
		Assert.assertEquals(permissao, permissaoRetornada);
	}

}
