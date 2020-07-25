package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Email;

@Component
public class EmailBuilder {
	
	public Email build(String emailString) {
		return build(emailString, null);
	}

	public Email build(String emailString, Cidadao cidadao) {
		Email email = new Email();
		email.setAtivo(true);
		email.setEmail(emailString);
		email.setCidadao(cidadao);
		return email;
	}

}
