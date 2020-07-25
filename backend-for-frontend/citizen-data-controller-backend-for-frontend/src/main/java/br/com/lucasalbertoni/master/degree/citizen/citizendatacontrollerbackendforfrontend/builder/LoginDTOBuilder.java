package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.LoginDTO;

@Component
public class LoginDTOBuilder {
	
	public LoginDTO build(String cpfCnpj, String email) {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setCpfCnpj(cpfCnpj);
		loginDTO.setEmail(email);
		return loginDTO;
	}

}
