package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder.LoginDTOBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginCheckStatus;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TrocarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.LoginDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.TrocaSenhaLoginDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.LoginDTOToLoginCheckStatus;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.TrocarSenhaLoginToTrocaSenhaLoginDTO;

@Service
public class LoginApiClient {
	
	@Value("${citizen.controller.login.check.status.url}")
	private String url;
	
	@Value("${citizen.controller.login.recuperar.senha.url}")
	private String urlRecuperarSenha;
	
	@Value("${citizen.controller.login.trocar.senha.url}")
	private String urlTrocarSenha;
	
	@Autowired
	private GenericApiClient genericApiClient;
	
	@Autowired
	private LoginDTOToLoginCheckStatus loginDTOToLoginCheckStatus;
	
	@Autowired
	private LoginDTOBuilder loginDTOBuilder;
	
	@Autowired
	private TrocarSenhaLoginToTrocaSenhaLoginDTO trocarSenhaLoginToTrocaSenhaLoginDTO;
	
	public LoginCheckStatus findByCpf(String cpf) {
		LoginDTO resposta = genericApiClient.getRequestWithInternalCredentials(url+"?cpf="+cpf, LoginDTO.class);
		return loginDTOToLoginCheckStatus.parse(resposta);
	}
	
	public LoginCheckStatus recuperarSenha(String cpf, String email) {
		LoginDTO loginDTO = loginDTOBuilder.build(cpf, email);
		LoginDTO resposta = genericApiClient.putRequestWithInternalCredentials(urlRecuperarSenha, loginDTO, LoginDTO.class);
		return loginDTOToLoginCheckStatus.parse(resposta);
	}
	
	public LoginCheckStatus trocarSenha(TrocarSenhaLogin trocarSenhaLogin) {
		TrocaSenhaLoginDTO trocaSenhaLoginDTO = trocarSenhaLoginToTrocaSenhaLoginDTO.parse(trocarSenhaLogin);
		LoginDTO resposta = genericApiClient.putRequestWithInternalCredentials(urlTrocarSenha, trocaSenhaLoginDTO, LoginDTO.class);
		return loginDTOToLoginCheckStatus.parse(resposta);
	}

}
