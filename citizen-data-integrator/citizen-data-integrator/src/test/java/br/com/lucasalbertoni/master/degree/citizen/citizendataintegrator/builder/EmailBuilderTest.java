package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Email;

@RunWith(SpringRunner.class)
public class EmailBuilderTest {
	
	@InjectMocks
	private EmailBuilder emailBuilder;
	
	@Test
	public void testBuildCidadao() {
		String emailString = "bla@bla.com";
		Cidadao cidadao = new Cidadao();
		
		Email email = emailBuilder.build(emailString, cidadao);
		Assert.assertNotNull(email);
		Assert.assertEquals(emailString, email.getEmail());
		Assert.assertTrue(email.isAtivo());
		Assert.assertNotNull(email.getCidadao());
		Assert.assertEquals(cidadao, email.getCidadao());
	}
	
	@Test
	public void testBuild() {
		String emailString = "bla@bla.com";
		
		Email email = emailBuilder.build(emailString);
		Assert.assertNotNull(email);
		Assert.assertEquals(emailString, email.getEmail());
		Assert.assertTrue(email.isAtivo());
		Assert.assertNull(email.getCidadao());
	}

}
