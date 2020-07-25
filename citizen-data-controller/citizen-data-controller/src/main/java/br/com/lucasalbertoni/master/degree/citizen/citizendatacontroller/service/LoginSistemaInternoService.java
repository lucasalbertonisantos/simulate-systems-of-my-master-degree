package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.LoginRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.utils.Encryption;

@Service
public class LoginSistemaInternoService {
	
	@Autowired
	private Encryption encryption;
	
	@Autowired
	private LoginRepository loginRepository;
	
	public Login create(Login login) {
		if(login == null) {
			throw new RuntimeException("Login não pode ser nulo!");
		}
		if(login.getSenha() == null || login.getSenha().isBlank()) {
			throw new RuntimeException("Senha não pode ser nula!");
		}
		login.setSenha(encryption.encrypt(login.getSenha()));
		login.setDataCriacao(LocalDateTime.now());
		Login loginSalvo = loginRepository.save(login);
		loginSalvo.setSenha(null);
		return loginSalvo;
	}

}
