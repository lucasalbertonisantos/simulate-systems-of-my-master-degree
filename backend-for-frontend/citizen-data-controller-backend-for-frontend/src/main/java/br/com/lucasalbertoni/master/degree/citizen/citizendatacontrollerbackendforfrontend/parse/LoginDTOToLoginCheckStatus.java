package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginCheckStatus;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.LoginDTO;

@Component
public class LoginDTOToLoginCheckStatus {
	
	public LoginCheckStatus parse(LoginDTO loginDTO) {
		LoginCheckStatus loginCheckStatus = new LoginCheckStatus();
		if(loginDTO != null) {
			loginCheckStatus.setCpf(loginDTO.getCpfCnpj());
			loginCheckStatus.setStatus(loginDTO.getStatus());
		}
		return loginCheckStatus;
	}

}
