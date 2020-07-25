package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.AutenticadorApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginTokenInterno;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.LoginTokenInternoRepository;

@Service
public class AutenticadorInternoService {
	
	@Value("${credenciais.sistema.interno.usuario}")
	private String usuario;
	
	@Value("${credenciais.sistema.interno.senha}")
	private String senha;
	
	@Autowired
	private LoginTokenInternoRepository loginTokenInternoRepository;
	
	@Autowired
	private AutenticadorApiClient autenticadorApiClient;
	
	public LoginTokenInterno getToken() {
		Iterable<LoginTokenInterno> iterable = loginTokenInternoRepository.findAll();
		if(iterable.iterator().hasNext()) {
			LoginTokenInterno loginInterno = iterable.iterator().next();
			if(loginInterno != null) {
				return loginInterno;
			}else {
				loginTokenInternoRepository.deleteAll();
			}
		}
		LoginTokenInterno loginTokenInterno = autenticadorApiClient.findByUsuarioSenhaInterno(usuario, senha);
		return loginTokenInternoRepository.save(loginTokenInterno);
	}

}
