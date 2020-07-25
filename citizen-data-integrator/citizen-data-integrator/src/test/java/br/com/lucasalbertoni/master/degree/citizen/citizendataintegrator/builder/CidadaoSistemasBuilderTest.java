package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoSistemas;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.SistemaBuscaDados;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TipoOrgao;

@RunWith(SpringRunner.class)
public class CidadaoSistemasBuilderTest {
	
	@InjectMocks
	private CidadaoSistemasBuilder cidadaoSistemasBuilder;
	
	@Test
	public void testBuildTipoOrgaoNulo() {
		SistemaBuscaDados sistemaBuscaDados = new SistemaBuscaDados();
		String cpf = "83163384080";
		
		CidadaoSistemas cidadaoSistemas = cidadaoSistemasBuilder.build(sistemaBuscaDados, cpf, null);
		Assert.assertNotNull(cidadaoSistemas);
		Assert.assertNull(cidadaoSistemas.getCidadao());
		Assert.assertNull(cidadaoSistemas.getTipoSistema());
		Assert.assertNotNull(cidadaoSistemas.getDataBusca());
		Assert.assertEquals(cpf, cidadaoSistemas.getCpf());
		Assert.assertEquals(sistemaBuscaDados, cidadaoSistemas.getSistema());
	}
	
	@Test
	public void testBuild() {
		SistemaBuscaDados sistemaBuscaDados = new SistemaBuscaDados();
		String cpf = "83163384080";
		TipoOrgao tipoOrgao = TipoOrgao.FEDERAL;
		
		CidadaoSistemas cidadaoSistemas = cidadaoSistemasBuilder.build(sistemaBuscaDados, cpf, tipoOrgao);
		Assert.assertNotNull(cidadaoSistemas);
		Assert.assertNull(cidadaoSistemas.getCidadao());
		Assert.assertNotNull(cidadaoSistemas.getDataBusca());
		Assert.assertEquals(cpf, cidadaoSistemas.getCpf());
		Assert.assertEquals(sistemaBuscaDados, cidadaoSistemas.getSistema());
		Assert.assertEquals(tipoOrgao, cidadaoSistemas.getTipoSistema());
	}

}
