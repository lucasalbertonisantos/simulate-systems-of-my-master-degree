package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CnpjValidatorTest {
	
	@InjectMocks
	private CnpjValidator CnpjValidator;
	
	@Test(expected = RuntimeException.class)
	public void testValidateNull() {
		CnpjValidator.validate(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateVazio() {
		CnpjValidator.validate("  ");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateZero() {
		CnpjValidator.validate("00000000000000");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateUm() {
		CnpjValidator.validate("11111111111111");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateDois() {
		CnpjValidator.validate("22222222222222");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateTres() {
		CnpjValidator.validate("33333333333333");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateQuatro() {
		CnpjValidator.validate("44444444444444");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateCinco() {
		CnpjValidator.validate("55555555555555");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateSeis() {
		CnpjValidator.validate("66666666666666");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateSete() {
		CnpjValidator.validate("77777777777777");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateOito() {
		CnpjValidator.validate("88888888888888");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateNove() {
		CnpjValidator.validate("99999999999999");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateDiferenteQuatorze() {
		CnpjValidator.validate("603295630001505");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateInvalido() {
		CnpjValidator.validate("75951052000199");
	}

}
