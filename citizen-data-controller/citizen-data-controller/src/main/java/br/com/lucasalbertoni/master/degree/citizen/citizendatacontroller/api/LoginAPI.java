package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.dto.TrocaSenhaLoginDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.facade.LoginFacade;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginAPI {
	
	@Autowired
	private LoginFacade loginFacade;
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping("/situacao")
	public Login situacao(@RequestParam(name = "cpf") String cpf) {
		return loginFacade.checkStatus(cpf);
	}
	
	@PostMapping
	public Login create(@RequestBody Login login) {
		return loginService.create(login);
	}
	
	@PostMapping("/empresa")
	public Login createLoginEmpresa(@RequestBody Login login) {
		return loginService.createEmpresa(login);
	}
	
	@PutMapping("/empresa")
	public Login updateEmpresa(@RequestBody Login login) {
		return loginService.updateEmpresa(login);
	}
	
	@GetMapping("/liberar-cidadao")
	public String liberarCidadao(@RequestParam(name = "acesso") String urlToken) {
		try {
			loginService.liberarLoginCidadao(urlToken);
			return "Login liberado!";
		}catch (Exception e) {
			return "Token expirado. Necessário refazer o procedimento.";
		}
	}
	
	@PutMapping("/trocar-senha")
	public Login trocaSenha(@RequestHeader("Authorization") String token, @RequestBody TrocaSenhaLoginDTO trocaSenhaLoginDTO) {
		return loginService.trocarSenha(trocaSenhaLoginDTO, token);
	}
	
	@PutMapping("/recuperar-senha")
	public Login recuperarSenha(@RequestBody Login login) {
		return loginService.recuperarSenha(login);
	}

}
