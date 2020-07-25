package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Endereco;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.EnderecoRespostaFila;

@RunWith(SpringRunner.class)
public class EnderecoRespostaFilaToEnderecoTest {
	
	@InjectMocks
	private EnderecoRespostaFilaToEndereco enderecoRespostaFilaToEndereco;
	
	@Test
	public void testParseNull() {
		Assert.assertNotNull(enderecoRespostaFilaToEndereco.parse(null));
	}
	
	@Test
	public void testParse() {
		EnderecoRespostaFila enderecoRespostaFila = new EnderecoRespostaFila();
		enderecoRespostaFila.setBairro("CENTRO");
		enderecoRespostaFila.setCep("01112540");
		enderecoRespostaFila.setComplemento("APTO 666");
		enderecoRespostaFila.setLogradouro("Rua lugar nenhum");
		enderecoRespostaFila.setMunicipio("São Paulo");
		enderecoRespostaFila.setNumero("666");
		enderecoRespostaFila.setPais("BR");
		enderecoRespostaFila.setUf("SP");
		
		Endereco endereco = enderecoRespostaFilaToEndereco.parse(enderecoRespostaFila);
		Assert.assertNotNull(endereco);
		Assert.assertNull(endereco.getCidadao());
		Assert.assertEquals(enderecoRespostaFila.getCep(), endereco.getCep());
		Assert.assertEquals(enderecoRespostaFila.getComplemento(), endereco.getComplemento());
		Assert.assertEquals(enderecoRespostaFila.getLogradouro(), endereco.getLogradouro());
		Assert.assertEquals(enderecoRespostaFila.getMunicipio(), endereco.getMunicipio());
		Assert.assertEquals(enderecoRespostaFila.getNumero(), endereco.getNumero());
		Assert.assertEquals(enderecoRespostaFila.getPais(), endereco.getPais());
		Assert.assertEquals(enderecoRespostaFila.getUf(), endereco.getUf());
		Assert.assertEquals(enderecoRespostaFila.getBairro(), endereco.getBairro());
	}

}
