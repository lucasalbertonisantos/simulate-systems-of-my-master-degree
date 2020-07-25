package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginCheckStatus;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginToken;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.RecuperarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TrocarSenhaLogin;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginAPI {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping
	public LoginToken logar(@RequestBody Login login){
		return loginService.logar(login);
	}
	
	@GetMapping
	public LoginCheckStatus findCheckStatus(@RequestParam String cpf){
		return loginService.findCheckStatus(cpf);
	}
	
	@PutMapping("/recuperar-senha")
	public LoginCheckStatus recuperarSenha(@RequestBody RecuperarSenhaLogin recuperarSenha){
		return loginService.recuperarSenha(recuperarSenha);
	}
	
	@PutMapping("/trocar-senha")
	public LoginCheckStatus trocarSenha(@RequestBody TrocarSenhaLogin trocarSenhaLogin){
		return loginService.trocarSenha(trocarSenhaLogin);
	}

}
