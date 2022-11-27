package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	public void sendToken(String url, String jwtToken, String senha, String email) {
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Definição de senha");
        msg.setText("Acesse o link :" + url+"?acesso="+jwtToken+" para liberar o seu login! Mas atenção, esse link só ficará válido pelos próximos 9 minutos! A senha gerada foi: " + senha + ". É aconselhável troca-la no primeiro acesso.");

        javaMailSender.send(msg);
	}
	
	public void sendTokenLiberarAcessoDados(
			String nomeEmpresa,
			String cnpjEmpresa,
			String url, 
			String jwtToken, 
			String codigoLiberacaoEmpresa, 
			String email) {
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);

        msg.setSubject("Liberação de dados para empresa "+ nomeEmpresa);
        msg.setText("Para liberar os seus dados para a empresa de CNPJ: " + cnpjEmpresa + " e nome: " + nomeEmpresa + " acesse o link :" + url+"?acesso="+jwtToken+". Esse link só ficará válido pelos próximos 9 minutos. O código necessário para utilização do serviço da empresa é: "+codigoLiberacaoEmpresa);

        javaMailSender.send(msg);
	}

}
