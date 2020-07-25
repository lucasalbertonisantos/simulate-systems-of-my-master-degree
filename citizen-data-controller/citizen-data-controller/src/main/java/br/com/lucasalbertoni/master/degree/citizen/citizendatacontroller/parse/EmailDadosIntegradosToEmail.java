package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Email;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.integrateddata.EmailDadosIntegrados;

@Component
public class EmailDadosIntegradosToEmail {
	
	public Email parse(EmailDadosIntegrados emailDadosIntegrados, Cidadao cidadao) {
		Email email = new Email();
		if(emailDadosIntegrados != null) {
			email.setEmail(emailDadosIntegrados.getEmail());
			email.setAtivo(emailDadosIntegrados.isAtivo());
			email.setCidadao(cidadao);
		}
		return email;
	}

}
