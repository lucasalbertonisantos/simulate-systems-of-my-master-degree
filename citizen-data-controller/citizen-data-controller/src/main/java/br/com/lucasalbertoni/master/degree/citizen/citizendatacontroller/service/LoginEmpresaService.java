package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Empresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.LoginStatusEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.LoginRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.utils.Encryption;

@Service
public class LoginEmpresaService {
	
	private static final String LOGIN_INCONSISTENTE = "Login já existe para esse Documento!";
	private static final String LOGIN_NULO = "Login Não pode ser nulo!";
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private Encryption encryption;
	
	public Login create(Login login) {
		if(login == null) {
			throw new RuntimeException(LOGIN_NULO);
		}
		Empresa empresa = empresaService.findByCnpj(login.getCpfCnpj());
		if(empresa == null) {
			throw new RuntimeException(LOGIN_INCONSISTENTE);
		}
		login.setSenha(encryption.encrypt(login.getSenha()));
		login.setDataCriacao(LocalDateTime.now());
		login.setStatus(LoginStatusEnum.CREATED_LOGIN);
		login.setLoginAtivo(true);
		login.setSenhaGeradaAutomaticamente(false);
		Login loginSalvo = loginRepository.save(login);
		loginSalvo.setSenha(null);
		return loginSalvo;
	}

}
