package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.AutenticadorApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.LoginApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginCheckStatus;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginStatusEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.RecuperarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TrocarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.LoginCheckStatusRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.LoginTokenRepository;

@Service
public class LoginService {
	
	@Autowired
	private LoginTokenRepository loginTokenRepository;
	
	@Autowired
	private LoginCheckStatusRepository loginCheckStatusRepository;
	
	@Autowired
	private LoginApiClient loginApiClient;
	
	@Autowired
	private AutenticadorApiClient autenticadorApiClient;
	
	public LoginCheckStatus findCheckStatus(String cpf) {
		Optional<LoginCheckStatus> optionalLoginCheckStatus = loginCheckStatusRepository.findById(cpf);
		if(!optionalLoginCheckStatus.isPresent()) {
			LoginCheckStatus loginCheckStatus = loginApiClient.findByCpf(cpf);
			if(LoginStatusEnum.CREATED_LOGIN.equals(loginCheckStatus.getStatus())) {
				return loginCheckStatusRepository.save(loginCheckStatus);
			}else {
				return loginCheckStatus;
			}
		}else {
			return optionalLoginCheckStatus.get();
		}
	}

	public LoginToken logar(Login login) {
		if(login == null 
				|| login.getUsuario() == null 
				|| login.getUsuario().isBlank()
				|| login.getSenha() == null
				|| login.getSenha().isBlank()) {
			throw new RuntimeException("Login ou senha inválida!");
		}
		LoginToken loginToken = autenticadorApiClient.findByUsuarioSenha(login.getUsuario(), login.getSenha());
		return loginTokenRepository.save(loginToken);
	}

	public LoginCheckStatus recuperarSenha(RecuperarSenhaLogin recuperarSenha) {
		if(recuperarSenha == null 
				|| recuperarSenha.getEmail() == null 
				|| recuperarSenha.getEmail().isBlank()
				|| recuperarSenha.getCpf() == null
				|| recuperarSenha.getCpf().isBlank()) {
			throw new RuntimeException("Dados inválidos!");
		}
		LoginCheckStatus loginCheckStatus = loginApiClient.recuperarSenha(recuperarSenha.getCpf(), recuperarSenha.getEmail());
		return loginCheckStatusRepository.save(loginCheckStatus);
	}

	public LoginCheckStatus trocarSenha(TrocarSenhaLogin trocarSenhaLogin) {
		if(trocarSenhaLogin == null 
				|| trocarSenhaLogin.getToken() == null 
				|| trocarSenhaLogin.getToken().getToken() == null 
				|| trocarSenhaLogin.getToken().getToken().isBlank() 
				|| trocarSenhaLogin.getToken().getTipo() == null 
				|| trocarSenhaLogin.getToken().getTipo().isBlank()
				|| trocarSenhaLogin.getSenhaAtual() == null 
				|| trocarSenhaLogin.getSenhaAtual().isBlank()
				|| trocarSenhaLogin.getNovaSenha() == null 
				|| trocarSenhaLogin.getNovaSenha().isBlank()) {
			throw new RuntimeException("Dados inválidos!");
		}
		return loginApiClient.trocarSenha(trocarSenhaLogin);
	}

}
