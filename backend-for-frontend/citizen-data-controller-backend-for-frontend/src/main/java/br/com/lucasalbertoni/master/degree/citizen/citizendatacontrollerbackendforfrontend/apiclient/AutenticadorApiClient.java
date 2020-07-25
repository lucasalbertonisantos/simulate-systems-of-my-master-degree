package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder.AutenticacaoDTOBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginTokenInterno;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.AutenticacaoDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.TokenDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.TokenDTOToLoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.TokenDTOToLoginTokenInterno;

@Service
public class AutenticadorApiClient {
	
	@Value("${citizen.controller.autenticador.auth.url}")
	private String url;
	
	@Autowired
	private GenericApiClient genericApiClient;
	
	@Autowired
	private TokenDTOToLoginToken tokenDTOToLoginToken;
	
	@Autowired
	private TokenDTOToLoginTokenInterno tokenDTOToLoginTokenInterno;
	
	@Autowired
	private AutenticacaoDTOBuilder autenticacaoDTOBuilder;
	
	public LoginToken findByUsuarioSenha(String usuario, String senha) {
		TokenDTO resposta = autenticar(usuario, senha, url+"?perfil=CIDADAO");
		return tokenDTOToLoginToken.parse(resposta);
	}
	
	public LoginTokenInterno findByUsuarioSenhaInterno(String usuario, String senha) {
		TokenDTO resposta = autenticar(usuario, senha, url);
		return tokenDTOToLoginTokenInterno.parse(resposta);
	}

	private TokenDTO autenticar(String usuario, String senha, String url) {
		AutenticacaoDTO autenticacaoDTO = autenticacaoDTOBuilder.build(usuario, senha);
		TokenDTO resposta = genericApiClient.postRequest(url, autenticacaoDTO, TokenDTO.class);
		return resposta;
	}

}
