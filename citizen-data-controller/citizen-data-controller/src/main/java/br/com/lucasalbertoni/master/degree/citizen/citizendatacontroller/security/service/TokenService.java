package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${data.token.secret.key}")
	private String secretKey;
	
	@Value("${expiration.time.in.minutes}")
	private Integer expirationTime;
	
	@Value("${nome.sistema.token}")
	private String nomeSistema;

	public String gerarToken(Authentication auth) {
		Login login = (Login) auth.getPrincipal();
		return Jwts.builder()
				.setIssuer(nomeSistema)
				.setSubject(login.getEmail())
				.setIssuedAt(new Date())
				.setExpiration(Date.from(LocalDateTime.now().plusMinutes(expirationTime).atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public String getEmailUsuarioToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

}
