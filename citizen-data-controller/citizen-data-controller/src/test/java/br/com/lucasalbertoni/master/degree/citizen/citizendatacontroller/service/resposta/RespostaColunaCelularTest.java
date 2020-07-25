package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.resposta;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Telefone;

@RunWith(SpringRunner.class)
public class RespostaColunaCelularTest {
	
	@InjectMocks
	private RespostaColunaCelular respostaColunaCelular;
	
	@Test(expected = RuntimeException.class)
	public void testAvaliarNull() {
		respostaColunaCelular.avaliar(null, null);
	}

	@Test(expected = RuntimeException.class)
	public void testAvaliarCidadaoNull() {
		Resposta resposta = new Resposta();
		respostaColunaCelular.avaliar(resposta, null);
	}

	@Test(expected = RuntimeException.class)
	public void testAvaliarStringRespostaNull() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		respostaColunaCelular.avaliar(resposta, cidadao);
	}

	@Test(expected = RuntimeException.class)
	public void testAvaliarStringRespostaInvalida() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("blablabla");
		respostaColunaCelular.avaliar(resposta, cidadao);
	}

	@Test(expected = RuntimeException.class)
	public void testAvaliarTelefoneCidadaoNull() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		resposta.setResposta("55___11___999999999");
		respostaColunaCelular.avaliar(resposta, cidadao);
	}

	@Test(expected = RuntimeException.class)
	public void testAvaliarTelefoneCidadaoVazio() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		cidadao.setTelefones(new HashSet<>());
		resposta.setResposta("55___11___999999999");
		respostaColunaCelular.avaliar(resposta, cidadao);
	}

	@Test(expected = RuntimeException.class)
	public void testAvaliarTelefoneDiferenteSemCelular() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		cidadao.setTelefones(new HashSet<>());
		Telefone telefone = new Telefone();
		telefone.setCelular(false);
		telefone.setDdi(11);
		telefone.setDdd(12);
		telefone.setDdd(888888888);
		cidadao.getTelefones().add(telefone);
		resposta.setResposta("55___11___999999999");
		respostaColunaCelular.avaliar(resposta, cidadao);
	}

	@Test(expected = RuntimeException.class)
	public void testAvaliarNumeroInvalido() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		cidadao.setTelefones(new HashSet<>());
		Telefone telefone = new Telefone();
		telefone.setCelular(false);
		telefone.setDdi(11);
		telefone.setDdd(12);
		telefone.setDdd(888888888);
		cidadao.getTelefones().add(telefone);
		resposta.setResposta("55___11___99999a999");
		respostaColunaCelular.avaliar(resposta, cidadao);
	}

	@Test(expected = RuntimeException.class)
	public void testAvaliarTelefoneDiferenteDdi() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		cidadao.setTelefones(new HashSet<>());
		Telefone telefone = new Telefone();
		telefone.setCelular(true);
		telefone.setDdi(11);
		telefone.setDdd(12);
		telefone.setDdd(888888888);
		cidadao.getTelefones().add(telefone);
		resposta.setResposta("55___11___999999999");
		respostaColunaCelular.avaliar(resposta, cidadao);
	}

	@Test(expected = RuntimeException.class)
	public void testAvaliarTelefoneDiferenteDdd() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		cidadao.setTelefones(new HashSet<>());
		Telefone telefone = new Telefone();
		telefone.setCelular(true);
		telefone.setDdi(55);
		telefone.setDdd(12);
		telefone.setDdd(888888888);
		cidadao.getTelefones().add(telefone);
		resposta.setResposta("55___11___999999999");
		respostaColunaCelular.avaliar(resposta, cidadao);
	}

	@Test(expected = RuntimeException.class)
	public void testAvaliarTelefoneDiferenteNumero() {
		Resposta resposta = new Resposta();
		Cidadao cidadao = new Cidadao();
		cidadao.setTelefones(new HashSet<>());
		Telefone telefone = new Telefone();
		telefone.setCelular(true);
		telefone.setDdi(55);
		telefone.setDdd(11);
		telefone.setDdd(888888888);
		cidadao.getTelefones().add(telefone);
		resposta.setResposta("55___11___999999999");
		respostaColunaCelular.avaliar(resposta, cidadao);
	}

}
