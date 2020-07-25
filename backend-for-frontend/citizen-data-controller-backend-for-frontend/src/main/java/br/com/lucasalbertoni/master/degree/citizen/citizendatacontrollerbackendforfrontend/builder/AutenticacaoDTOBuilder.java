package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.AutenticacaoDTO;

@Component
public class AutenticacaoDTOBuilder {
	
	public AutenticacaoDTO build(String usuario, String senha) {
		AutenticacaoDTO autenticacaoDTO = new AutenticacaoDTO();
		autenticacaoDTO.setUsername(usuario);
		autenticacaoDTO.setSenha(senha);
		return autenticacaoDTO;
	}

}
