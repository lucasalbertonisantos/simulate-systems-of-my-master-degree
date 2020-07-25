package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoReceitaFederal;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.DadosReceitaFederal;

@RunWith(SpringRunner.class)
public class DadosReceitaFederalToCidadaoReceitaFederalTest {
	
	@InjectMocks
	private DadosReceitaFederalToCidadaoReceitaFederal dadosReceitaFederalToCidadaoReceitaFederal;
	
	@Test
	public void testParseNull() {
		Assert.assertNotNull(dadosReceitaFederalToCidadaoReceitaFederal.parse(null));
	}
	
	@Test
	public void testParse() {
		DadosReceitaFederal dadosReceitaFederal = new DadosReceitaFederal();
		dadosReceitaFederal.setDataInscricao(LocalDate.now());
		dadosReceitaFederal.setDigitoVerificador("0");
		dadosReceitaFederal.setSituacaoCadastral("NORMAL");
		CidadaoReceitaFederal cidadaoReceitaFederal = dadosReceitaFederalToCidadaoReceitaFederal.parse(dadosReceitaFederal);
		Assert.assertNotNull(cidadaoReceitaFederal);
		Assert.assertNull(cidadaoReceitaFederal.getCidadao());
		Assert.assertEquals(dadosReceitaFederal.getDigitoVerificador(), cidadaoReceitaFederal.getDigitoVerificador());
		Assert.assertEquals(dadosReceitaFederal.getDataInscricao(), cidadaoReceitaFederal.getDataInscricao());
		Assert.assertEquals(dadosReceitaFederal.getSituacaoCadastral(), cidadaoReceitaFederal.getSituacaoCadastral());
	}

}
