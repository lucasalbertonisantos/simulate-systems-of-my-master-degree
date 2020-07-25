package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.parse;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoDadosProfissionais;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.ProfissaoRespostaFila;

@RunWith(SpringRunner.class)
public class ProfissaoRespostaFilaToCidadaoDadosProfissionaisTest {
	
	@InjectMocks
	private ProfissaoRespostaFilaToCidadaoDadosProfissionais profissaoRespostaFilaToCidadaoDadosProfissionais;
	
	@Test
	public void testParseNull() {
		Assert.assertNotNull(profissaoRespostaFilaToCidadaoDadosProfissionais.parse(null));
	}
	
	@Test
	public void testParse() {
		ProfissaoRespostaFila profissaoRespostaFila = new ProfissaoRespostaFila();
		profissaoRespostaFila.setCargo("Analista de Sistemas");
		profissaoRespostaFila.setCnpjEmpresaPagadora("23.416.163/0001-55");
		profissaoRespostaFila.setDataFim(LocalDate.now());
		profissaoRespostaFila.setDataInicio(LocalDate.now().minusYears(5l));
		profissaoRespostaFila.setNomeEmpresaPagadora("BLA");
		profissaoRespostaFila.setSalarioAnual(650000.50);
		profissaoRespostaFila.setSalarioMensal(50000.04);
		
		CidadaoDadosProfissionais cidadaoDadosProfissionais = profissaoRespostaFilaToCidadaoDadosProfissionais.parse(profissaoRespostaFila);
		Assert.assertNotNull(cidadaoDadosProfissionais);
		Assert.assertNull(cidadaoDadosProfissionais.getCidadao());
		Assert.assertEquals(profissaoRespostaFila.getCargo(), cidadaoDadosProfissionais.getCargo());
		Assert.assertEquals(profissaoRespostaFila.getCnpjEmpresaPagadora(), cidadaoDadosProfissionais.getCnpjEmpresaPagadora());
		Assert.assertEquals(profissaoRespostaFila.getDataFim(), cidadaoDadosProfissionais.getDataFim());
		Assert.assertEquals(profissaoRespostaFila.getDataInicio(), cidadaoDadosProfissionais.getDataInicio());
		Assert.assertEquals(profissaoRespostaFila.getNomeEmpresaPagadora(), cidadaoDadosProfissionais.getNomeEmpresaPagadora());
		Assert.assertEquals(profissaoRespostaFila.getSalarioAnual(), cidadaoDadosProfissionais.getSalarioAnual());
		Assert.assertEquals(profissaoRespostaFila.getSalarioMensal(), cidadaoDadosProfissionais.getSalarioMensal());
	}

}
