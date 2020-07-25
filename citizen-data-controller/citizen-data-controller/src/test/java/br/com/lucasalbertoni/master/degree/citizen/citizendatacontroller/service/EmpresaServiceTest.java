package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Empresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.EmpresaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CnpjValidator;

@RunWith(SpringRunner.class)
public class EmpresaServiceTest {
	
	@InjectMocks
	private EmpresaService empresaService;
	
	@Mock
	private EmpresaRepository empresaRepository;
	
	@Mock
	private CnpjValidator cnpjValidator;
	
	@Test(expected = RuntimeException.class)
	public void testSaveNull() {
		empresaService.save(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveCnpjNull() {
		Empresa empresa = new Empresa();
		empresaService.save(empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveCnpjVazio() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("    ");
		empresaService.save(empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveIdNotNull() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("71.163.014/0001-74");
		empresa.setId(666l);
		
		String cnpj = empresa.getCnpj().replace(".", "").replace("/", "").replace("-", "");
		Mockito.when(cnpjValidator.validate(empresa.getCnpj())).thenReturn(cnpj);
		
		empresaService.save(empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveNomeNull() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("71.163.014/0001-74");
		
		String cnpj = empresa.getCnpj().replace(".", "").replace("/", "").replace("-", "");
		Mockito.when(cnpjValidator.validate(empresa.getCnpj())).thenReturn(cnpj);
		
		empresaService.save(empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveNomeVazio() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("71.163.014/0001-74");
		empresa.setNome("  ");
		
		String cnpj = empresa.getCnpj().replace(".", "").replace("/", "").replace("-", "");
		Mockito.when(cnpjValidator.validate(empresa.getCnpj())).thenReturn(cnpj);
		
		empresaService.save(empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSaveCnpjDeOutroEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setCnpj("71.163.014/0001-74");
		empresa.setNome("aaa");
		
		String cnpj = empresa.getCnpj().replace(".", "").replace("/", "").replace("-", "");
		Mockito.when(cnpjValidator.validate(empresa.getCnpj())).thenReturn(cnpj);
		
		Empresa empresaExistente = new Empresa();
		Mockito.when(empresaRepository.findByCnpj(cnpj)).thenReturn(empresaExistente);
		
		empresaService.save(empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateNull() {
		empresaService.update(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateIdNull() {
		Empresa empresa = new Empresa();
		empresaService.update(empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateIdNaoExiste() {
		Empresa empresa = new Empresa();
		empresa.setId(666l);
		
		Mockito.when(empresaRepository.existsById(empresa.getId())).thenReturn(false);
		
		empresaService.update(empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateNomeNull() {
		Empresa empresa = new Empresa();
		empresa.setId(666l);
		
		Mockito.when(empresaRepository.existsById(empresa.getId())).thenReturn(true);
		
		empresaService.update(empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateNomeVazio() {
		Empresa empresa = new Empresa();
		empresa.setId(666l);
		empresa.setNome("   ");
		
		Mockito.when(empresaRepository.existsById(empresa.getId())).thenReturn(true);
		
		empresaService.update(empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdate() {
		Empresa empresa = new Empresa();
		empresa.setId(666l);
		empresa.setNome("blablabla");
		empresa.setCnpj("69.821.023/0001-63");
		
		Mockito.when(empresaRepository.existsById(empresa.getId())).thenReturn(true);
		
		String cnpj = empresa.getCnpj().replace(".", "").replace("/", "").replace("-", "");
		Mockito.when(cnpjValidator.validate(empresa.getCnpj())).thenReturn(cnpj);
		
		Empresa empresaExistente = new Empresa();
		Mockito.when(empresaRepository.findByCnpj(cnpj)).thenReturn(empresaExistente);
		
		empresaService.update(empresa);
	}
	
	@Test(expected = RuntimeException.class)
	public void testFindByCnpjNaoExiste() {
		String cnpj = "91.279.033/0001-78";
		
		String cnpjModificado = cnpj.replace(".", "").replace("/", "").replace("-", "");
		Mockito.when(cnpjValidator.validate(cnpj)).thenReturn(cnpjModificado);
		
		Mockito.when(empresaRepository.findByCnpj(cnpjModificado)).thenReturn(null);
		
		empresaService.findByCnpj(cnpj);
	}
	
	@Test
	public void testFindAll() {
		Empresa[] listaEmpresa = new Empresa[2];
		listaEmpresa[0] = new Empresa();
		listaEmpresa[1] = new Empresa();
		Iterable<Empresa> empresas = Arrays.asList(listaEmpresa);
		Mockito.when(empresaRepository.findAll()).thenReturn(empresas);
		List<Empresa> empresasRetornadas = empresaService.findAll();
		Assert.assertNotNull(empresasRetornadas);
		Assert.assertEquals(listaEmpresa.length, empresasRetornadas.size());
	}

}
