package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Telefone;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TelefoneRespostaFila;

@RunWith(SpringRunner.class)
public class TelefoneRespostaFilaToTelefoneTest {
	
	@InjectMocks
	private TelefoneRespostaFilaToTelefone telefoneRespostaFilaToTelefone;
	
	@Test
	public void testParseNull() {
		Assert.assertNotNull(telefoneRespostaFilaToTelefone.parse(null));
	}
	
	@Test
	public void testParse() {
		TelefoneRespostaFila telefoneRespostaFila = new TelefoneRespostaFila();
		telefoneRespostaFila.setCelular(true);
		telefoneRespostaFila.setDdd(11);
		telefoneRespostaFila.setDdi(55);
		telefoneRespostaFila.setNumero(888555777l);
		
		Telefone telefone = telefoneRespostaFilaToTelefone.parse(telefoneRespostaFila);
		Assert.assertNotNull(telefone);
		Assert.assertNull(telefone.getCidadao());
		Assert.assertEquals(telefoneRespostaFila.getDdd(), telefone.getDdd());
		Assert.assertEquals(telefoneRespostaFila.getDdi(), telefone.getDdi());
		Assert.assertEquals(telefoneRespostaFila.getNumero(), telefone.getNumero());
		Assert.assertEquals(telefoneRespostaFila.isCelular(), telefone.isCelular());
	}

}
