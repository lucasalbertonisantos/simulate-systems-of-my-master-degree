package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.parse;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Emprego;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.CidadaoDadosProfissionaisDadosIntegrados;

@RunWith(SpringRunner.class)
public class CidadaoDadosProfissionaisDadosIntegradosToEmpregoTest {
	
	@InjectMocks
	private CidadaoDadosProfissionaisDadosIntegradosToEmprego cidadaoDadosProfissionaisDadosIntegradosToEmprego;
	
	@Test
	public void testParseNull() {
		Emprego emprego = cidadaoDadosProfissionaisDadosIntegradosToEmprego.parse(null, null);
		Assert.assertNotNull(emprego);
	}
	
	@Test
	public void testParse() {
		CidadaoDadosProfissionaisDadosIntegrados dadoProfissional = new CidadaoDadosProfissionaisDadosIntegrados();
		dadoProfissional.setCargo("bla");
		dadoProfissional.setCnpjEmpresaPagadora("90.234.260/0001-14");
		dadoProfissional.setDataFim(LocalDate.now());
		dadoProfissional.setDataInicio(LocalDate.now().minusYears(10l));
		dadoProfissional.setId(1l);
		dadoProfissional.setNomeEmpresaPagadora("blablablabla");
		dadoProfissional.setSalarioAnual(200000.0);
		dadoProfissional.setSalarioMensal(15000.0);
		Cidadao cidadao = new Cidadao();
		
		Emprego emprego = cidadaoDadosProfissionaisDadosIntegradosToEmprego.parse(dadoProfissional,  cidadao);
		Assert.assertNotNull(emprego);
		Assert.assertEquals(dadoProfissional.getCargo(), emprego.getCargo());
		Assert.assertEquals(cidadao, emprego.getCidadao());
		Assert.assertEquals(dadoProfissional.getCnpjEmpresaPagadora(), emprego.getCnpjEmpresaPagadora());
		Assert.assertEquals(dadoProfissional.getDataFim(), emprego.getDataFim());
		Assert.assertEquals(dadoProfissional.getDataInicio(), emprego.getDataInicio());
		Assert.assertEquals(dadoProfissional.getNomeEmpresaPagadora(), emprego.getNomeEmpresaPagadora());
		Assert.assertEquals(dadoProfissional.getSalarioAnual(), emprego.getSalarioAnual());
		Assert.assertEquals(dadoProfissional.getSalarioMensal(), emprego.getSalarioMensal());
	}

}
