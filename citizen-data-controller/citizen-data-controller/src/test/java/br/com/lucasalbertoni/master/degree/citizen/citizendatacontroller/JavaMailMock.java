package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller;

import java.io.InputStream;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class JavaMailMock implements JavaMailSender{
	
	private SimpleMailMessage mailSent;
	public SimpleMailMessage getMailSent() {
		return mailSent;
	}

	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		this.mailSent = simpleMessage;
	}
	@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public MimeMessage createMimeMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void send(MimeMessage mimeMessage) throws MailException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void send(MimeMessage... mimeMessages) throws MailException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
		// TODO Auto-generated method stub
		
	}

}
