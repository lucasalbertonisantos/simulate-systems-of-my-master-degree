package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd.mock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginStatusEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.LoginDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.TrocaSenhaLoginDTO;

@RestController
@RequestMapping("/loginm2mock")
public class LoginM2APIMock {
	
	private boolean loginExiste;
	private boolean cidadaoCadastrado;
	private String email;
	private String cpf;
	private String senha;
	
	@GetMapping
	public LoginDTO getLoginDTO(@RequestParam String cpf) {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setCpfCnpj(cpf);
		if(loginExiste) {
			loginDTO.setStatus(LoginStatusEnum.CREATED_LOGIN);
			return loginDTO;
		}
		if(cidadaoCadastrado) {
			loginDTO.setStatus(LoginStatusEnum.PENDING_LOGIN);
			return loginDTO;
		}
		throw new RuntimeException("Não existe nenhum login nem cidadão com o CPF informado");
	}
	
	@PutMapping("/recuperar")
	public LoginDTO recuperarSenha(@RequestBody LoginDTO loginDTO) {
		if(!loginDTO.getEmail().equals(email) || !loginDTO.getCpfCnpj().equals(cpf)) {
			throw new RuntimeException("Email e/ou cpf errado");
		}
		return loginDTO;
	}
	
	@PutMapping("/trocar")
	public LoginDTO recuperarSenha(@RequestBody TrocaSenhaLoginDTO trocaSenhaLoginDTO) {
		LoginDTO loginDTO = new LoginDTO();
		if(cpf == null) {
			loginDTO.setCpfCnpj("MOCK");
		}else {
			loginDTO.setCpfCnpj(cpf);
		}
		loginDTO.setStatus(LoginStatusEnum.CREATED_LOGIN);
		if(!trocaSenhaLoginDTO.getSenha().equals(senha)) {
			throw new RuntimeException("Senha errada!");
		}
		return loginDTO;
	}
	
	public void existeLoginCadastrado() {
		loginExiste = true;
	}
	public void naoExisteLoginCadastrado() {
		loginExiste = false;
	}
	
	public void existeCidadaoCadastrado() {
		cidadaoCadastrado = true;
	}
	public void naoExisteCidadaoCadastrado() {
		cidadaoCadastrado = false;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
