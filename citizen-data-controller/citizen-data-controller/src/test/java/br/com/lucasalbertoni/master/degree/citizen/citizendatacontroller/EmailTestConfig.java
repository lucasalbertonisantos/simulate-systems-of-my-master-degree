package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("test")
public class EmailTestConfig {
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSender mailSender = new JavaMailMock();
		return mailSender;
	}
	

}
