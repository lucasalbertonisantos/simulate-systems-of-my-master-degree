package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd.mock;

import org.junit.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.AutenticacaoDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.TokenDTO;

@RestController
@RequestMapping("/authm2mock")
public class AutenticadorM2APIMock {
	
	private String perfil;
	private String usuario;
	private String senha;
	
	@PostMapping
	public TokenDTO getToken(
			@RequestParam(required = false) String perfil,
				@RequestBody AutenticacaoDTO autenticacaoDTO) {
		if(usuario != null && senha != null) {
			if(!usuario.equals(autenticacaoDTO.getUsername())
					|| !senha.equals(autenticacaoDTO.getSenha())) {
				throw new RuntimeException("Não é igual");
			}
		}
		if(this.perfil != null && !this.perfil.equals(perfil)) {
			throw new RuntimeException("Não é igual");
		}
		Assert.assertNotNull(autenticacaoDTO);
		Assert.assertNotNull(autenticacaoDTO.getUsername());
		Assert.assertNotNull(autenticacaoDTO.getSenha());
		TokenDTO token = new TokenDTO();
		token.setTipo("Bearer ");
		token.setToken("123456789");
		return token;
	}

	public String getPerfil() {
		return perfil;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

}
