package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.CidadaoApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Permissao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.SolicitarLiberacao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TokenLiberacaoAcesso;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.LoginTokenRepository;

@RunWith(SpringRunner.class)
public class CidadaoServiceTest {
	
	@InjectMocks
	private CidadaoService cidadaoService;
	
	@Mock
	private CidadaoApiClient cidadaoApiClient;
	
	@Mock
	private LoginTokenRepository loginTokenRepository;
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosNull() {
		cidadaoService.liberarDados(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosCnpjNull() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		cidadaoService.liberarDados(solicitarLiberacao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosCnpjVazio() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		solicitarLiberacao.setCnpj("   ");
		cidadaoService.liberarDados(solicitarLiberacao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosTokenNull() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		solicitarLiberacao.setCnpj("30179079000120");
		cidadaoService.liberarDados(solicitarLiberacao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosStringTokenNull() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		solicitarLiberacao.setCnpj("30179079000120");
		solicitarLiberacao.setToken(new LoginToken());
		cidadaoService.liberarDados(solicitarLiberacao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosStringTokenVazio() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		solicitarLiberacao.setCnpj("30179079000120");
		solicitarLiberacao.setToken(new LoginToken());
		solicitarLiberacao.getToken().setToken("   ");
		cidadaoService.liberarDados(solicitarLiberacao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosTokenLiberacaoAcessoNull() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		solicitarLiberacao.setCnpj("30179079000120");
		solicitarLiberacao.setToken(new LoginToken());
		solicitarLiberacao.getToken().setToken("123");
		Mockito.when(cidadaoApiClient.solicitarLiberacaoDados(solicitarLiberacao)).thenReturn(null);
		cidadaoService.liberarDados(solicitarLiberacao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosStringTokenLiberacaoAcessoNull() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		solicitarLiberacao.setCnpj("30179079000120");
		solicitarLiberacao.setToken(new LoginToken());
		solicitarLiberacao.getToken().setToken("123");
		TokenLiberacaoAcesso tokenLiberacaoAcesso = new TokenLiberacaoAcesso();
		Mockito.when(cidadaoApiClient.solicitarLiberacaoDados(solicitarLiberacao)).thenReturn(tokenLiberacaoAcesso);
		cidadaoService.liberarDados(solicitarLiberacao);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLiberarDadosStringTokenLiberacaoAcessoVazio() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		solicitarLiberacao.setCnpj("30179079000120");
		solicitarLiberacao.setToken(new LoginToken());
		solicitarLiberacao.getToken().setToken("123");
		TokenLiberacaoAcesso tokenLiberacaoAcesso = new TokenLiberacaoAcesso();
		tokenLiberacaoAcesso.setTokenAcesso("   ");
		Mockito.when(cidadaoApiClient.solicitarLiberacaoDados(solicitarLiberacao)).thenReturn(tokenLiberacaoAcesso);
		cidadaoService.liberarDados(solicitarLiberacao);
	}
	
	@Test
	public void testLiberarDadosLoginTokenJaExiste() {
		SolicitarLiberacao solicitarLiberacao = new SolicitarLiberacao();
		solicitarLiberacao.setCnpj("30179079000120");
		solicitarLiberacao.setToken(new LoginToken());
		solicitarLiberacao.getToken().setToken("123");
		
		TokenLiberacaoAcesso tokenLiberacaoAcesso = new TokenLiberacaoAcesso();
		tokenLiberacaoAcesso.setTokenAcesso("blablabla");
		Mockito.when(cidadaoApiClient.solicitarLiberacaoDados(solicitarLiberacao)).thenReturn(tokenLiberacaoAcesso);
		
		LoginToken loginToken = new LoginToken();
		Optional<LoginToken> optionalLoginToken = Optional.of(loginToken);
		Mockito.when(loginTokenRepository.findById(solicitarLiberacao.getToken().getToken())).thenReturn(optionalLoginToken);
		
		Permissao permissao = cidadaoService.liberarDados(solicitarLiberacao);
		Assert.assertNotNull(permissao);
		Assert.assertEquals(loginToken, permissao.getLoginToken());
		Assert.assertEquals(solicitarLiberacao.getCnpj(), permissao.getCnpj());
		Assert.assertEquals(tokenLiberacaoAcesso, permissao.getTokenLiberacaoAcesso());
	}

}
