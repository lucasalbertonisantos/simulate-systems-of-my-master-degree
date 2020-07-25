package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient.CidadaoApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Permissao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.SolicitarLiberacao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TokenLiberacaoAcesso;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository.LoginTokenRepository;

@Service
public class CidadaoService {
	
	@Autowired
	private CidadaoApiClient cidadaoApiClient;
	
	@Autowired
	private LoginTokenRepository loginTokenRepository;
	
	public Permissao liberarDados(SolicitarLiberacao solicitarLiberacao) {
		if(solicitarLiberacao == null) {
			throw new RuntimeException("Solicitação não pode ser nula!");
		}
		if(solicitarLiberacao.getCnpj() == null
				|| solicitarLiberacao.getCnpj().isBlank()) {
			throw new RuntimeException("Cnpj inválido!");
		}
		if(solicitarLiberacao.getToken() == null
				|| solicitarLiberacao.getToken().getToken() == null
				|| solicitarLiberacao.getToken().getToken().isBlank()) {
			throw new RuntimeException("Token vazio!");
		}
		TokenLiberacaoAcesso tokenLiberacaoAcesso = cidadaoApiClient.solicitarLiberacaoDados(solicitarLiberacao);
		if(tokenLiberacaoAcesso == null 
				|| tokenLiberacaoAcesso.getTokenAcesso() == null
				|| tokenLiberacaoAcesso.getTokenAcesso().isBlank()) {
			throw new RuntimeException("Ocorreu um erro na liberação, tente novamente mais tarde.");
		}
		Permissao permissao = new Permissao();
		permissao.setCnpj(solicitarLiberacao.getCnpj());
		permissao.setTokenLiberacaoAcesso(tokenLiberacaoAcesso);
		Optional<LoginToken> loginToken = loginTokenRepository.findById(solicitarLiberacao.getToken().getToken());
		if(loginToken.isPresent()) {
			permissao.setLoginToken(loginToken.get());
		}else {
			permissao.setLoginToken(solicitarLiberacao.getToken());
		}
		return permissao;
	}

}
