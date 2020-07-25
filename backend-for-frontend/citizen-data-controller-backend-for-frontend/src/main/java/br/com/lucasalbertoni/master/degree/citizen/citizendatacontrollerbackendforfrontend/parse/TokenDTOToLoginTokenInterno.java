package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginTokenInterno;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.TokenDTO;

@Component
public class TokenDTOToLoginTokenInterno {
	
	public LoginTokenInterno parse(TokenDTO tokenDTO) {
		LoginTokenInterno loginTokenInterno = new LoginTokenInterno();
		if(tokenDTO != null) {
			loginTokenInterno.setTipo(tokenDTO.getTipo());
			loginTokenInterno.setToken(tokenDTO.getToken());
		}
		return loginTokenInterno;
	}

}
