package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.TokenDTO;

@Component
public class TokenDTOToLoginToken {
	
	public LoginToken parse(TokenDTO tokenDTO) {
		LoginToken loginToken = new LoginToken();
		if(tokenDTO != null) {
			loginToken.setTipo(tokenDTO.getTipo());
			loginToken.setToken(tokenDTO.getToken());
		}
		return loginToken;
	}

}
