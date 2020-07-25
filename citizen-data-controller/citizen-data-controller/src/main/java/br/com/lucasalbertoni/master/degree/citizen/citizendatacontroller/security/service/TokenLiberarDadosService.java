package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenLiberarDadosService {
	
	@Value("${data.token.secret.key.liberar.dados}")
	private String secretKeLiberarDados;
	
	@Value("${expiration.time.in.minutes.liberar.dados}")
	private Integer expirationTimeLiberarDados;
	
	@Value("${nome.sistema.token}")
	private String nomeSistema;

	public String gerarTokenEnvioEmail(String idPermissao) {
		return Jwts.builder()
				.setIssuer(nomeSistema)
				.setSubject(idPermissao)
				.setIssuedAt(new Date())
				.setExpiration(Date.from(LocalDateTime.now().plusMinutes(expirationTimeLiberarDados).atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(SignatureAlgorithm.HS256, secretKeLiberarDados)
				.compact();
	}
	
	public String getIdPermissaoToken(String token) {
		return Jwts.parser().setSigningKey(secretKeLiberarDados).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean isTokenValidoLiberarLogin(String token) {
		try {
			Jwts.parser().setSigningKey(secretKeLiberarDados).parseClaimsJws(token);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

}
