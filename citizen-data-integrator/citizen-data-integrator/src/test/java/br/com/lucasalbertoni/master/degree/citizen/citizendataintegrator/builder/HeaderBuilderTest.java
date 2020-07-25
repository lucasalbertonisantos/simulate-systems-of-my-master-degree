package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.Header;

@RunWith(SpringRunner.class)
public class HeaderBuilderTest {
	
	@InjectMocks
	private HeaderBuilder headerBuilder;
	
	@Test
	public void testBuild() {
		String nomeSistema = "bla-bla-666";
		ReflectionTestUtils.setField(headerBuilder, "nomeSistema", nomeSistema);
		Header header = headerBuilder.build();
		Assert.assertNotNull(header);
		Assert.assertNotNull(header.getId());
		Assert.assertNotNull(header.getNomeSistema());
		Assert.assertEquals(nomeSistema, header.getNomeSistema());
		Assert.assertNotNull(header.getData());
		Assert.assertNotNull(header.getTipoOrgao());
	}

}
