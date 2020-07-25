package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TokenLiberacaoAcesso;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.PermissaoDadosCidadaoEmpresaDTO;

@Component
public class PermissaoDadosCidadaoEmpresaDTOToTokenLiberacaoAcesso {
	
	public TokenLiberacaoAcesso parse(PermissaoDadosCidadaoEmpresaDTO permissaoDadosCidadaoEmpresaDTO) {
		TokenLiberacaoAcesso tokenLiberacaoAcesso = new TokenLiberacaoAcesso();
		if(permissaoDadosCidadaoEmpresaDTO != null) {
			tokenLiberacaoAcesso.setDataExpiracaoToken(permissaoDadosCidadaoEmpresaDTO.getDataExpiracaoToken());
			tokenLiberacaoAcesso.setTokenAcesso(permissaoDadosCidadaoEmpresaDTO.getTokenAcesso());
		}
		return tokenLiberacaoAcesso;
	}

}
