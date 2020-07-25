package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenConfirmarCadastroService {
	
	@Value("${data.token.secret.key.liberar.login}")
	private String secretKeLiberarLogin;
	
	@Value("${expiration.time.in.minutes.liberar.login}")
	private Integer expirationTimeLiberarLogin;
	
	@Value("${nome.sistema.token}")
	private String nomeSistema;

	public String gerarTokenEnvioEmail(String cpf) {
		return Jwts.builder()
				.setIssuer(nomeSistema)
				.setSubject(cpf)
				.setIssuedAt(new Date())
				.setExpiration(Date.from(LocalDateTime.now().plusMinutes(expirationTimeLiberarLogin).atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(SignatureAlgorithm.HS256, secretKeLiberarLogin)
				.compact();
	}
	
	public String getCpfUsuarioToken(String token) {
		return Jwts.parser().setSigningKey(secretKeLiberarLogin).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean isTokenValidoLiberarLogin(String token) {
		try {
			Jwts.parser().setSigningKey(secretKeLiberarLogin).parseClaimsJws(token);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

}
