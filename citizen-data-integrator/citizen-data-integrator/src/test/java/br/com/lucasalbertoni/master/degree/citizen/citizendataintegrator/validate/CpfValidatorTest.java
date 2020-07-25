package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.validate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CpfValidatorTest {
	
	@InjectMocks
	private CpfValidator cpfValidator;
	
	@Test(expected = RuntimeException.class)
	public void testValidateNull() {
		cpfValidator.validate(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateVazio() {
		cpfValidator.validate("   ");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateZero() {
		cpfValidator.validate("00000000000");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateUm() {
		cpfValidator.validate("11111111111");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateDois() {
		cpfValidator.validate("22222222222");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateTres() {
		cpfValidator.validate("33333333333");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateQuatro() {
		cpfValidator.validate("44444444444");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateCinco() {
		cpfValidator.validate("55555555555");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateSeis() {
		cpfValidator.validate("66666666666");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateSete() {
		cpfValidator.validate("77777777777");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateOito() {
		cpfValidator.validate("88888888888");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateNove() {
		cpfValidator.validate("99999999999");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateDiferenteOnze() {
		cpfValidator.validate("111111111112");
	}
	
	@Test(expected = RuntimeException.class)
	public void testValidateInvalido() {
		cpfValidator.validate("768.630.310-99");
	}

}
