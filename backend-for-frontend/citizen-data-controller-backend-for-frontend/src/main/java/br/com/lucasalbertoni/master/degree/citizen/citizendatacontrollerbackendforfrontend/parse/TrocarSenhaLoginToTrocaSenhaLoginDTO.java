package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TrocarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.TrocaSenhaLoginDTO;

@Component
public class TrocarSenhaLoginToTrocaSenhaLoginDTO {
	
	public TrocaSenhaLoginDTO parse(TrocarSenhaLogin trocarSenhaLogin) {
		TrocaSenhaLoginDTO trocaSenhaLoginDTO = new TrocaSenhaLoginDTO();
		if(trocarSenhaLogin != null) {
			trocaSenhaLoginDTO.setNovaSenha(trocarSenhaLogin.getNovaSenha());
			trocaSenhaLoginDTO.setSenha(trocarSenhaLogin.getSenhaAtual());
		}
		return trocaSenhaLoginDTO;
	}

}
