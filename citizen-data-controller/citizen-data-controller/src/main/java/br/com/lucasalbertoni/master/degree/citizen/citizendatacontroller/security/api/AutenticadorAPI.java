package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.dto.LoginDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.dto.TokenDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.AutenticacaoService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AutenticadorAPI {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(
			@RequestParam(required = false) String perfil, 
			@RequestBody LoginDTO loginDTO){
		UsernamePasswordAuthenticationToken login = 
				new UsernamePasswordAuthenticationToken(
						loginDTO.getUsername(), 
						loginDTO.getSenha());
		try {
			Authentication auth = authManager.authenticate(login);
			autenticacaoService.validateUsuarioPerfil(auth, perfil);
			String token = tokenService.gerarToken(auth);
			return ResponseEntity.ok(new TokenDTO(token));
		}catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
