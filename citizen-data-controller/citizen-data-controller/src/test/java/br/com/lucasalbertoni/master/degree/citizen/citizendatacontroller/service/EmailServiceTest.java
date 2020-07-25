package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EmailServiceTest {
	
	@InjectMocks
	private EmailService emailService;
	
	@Mock
    private JavaMailSender javaMailSender;
	
	@Test
	public void testSendToken() {
		String url = "xxx";
		String jwtToken = "bla";
		String senha = "123";
		String email = "a@a.com";
		
		emailService.sendToken(url , jwtToken , senha , email);
		ArgumentCaptor<SimpleMailMessage> msgCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
		Mockito.verify(javaMailSender).send(msgCaptor.capture());
		Assert.assertEquals(email, msgCaptor.getValue().getTo()[0]);
		Assert.assertTrue(msgCaptor.getValue().getText().contains(url));
		Assert.assertTrue(msgCaptor.getValue().getText().contains(jwtToken));
		Assert.assertTrue(msgCaptor.getValue().getText().contains(senha));
	}

}
