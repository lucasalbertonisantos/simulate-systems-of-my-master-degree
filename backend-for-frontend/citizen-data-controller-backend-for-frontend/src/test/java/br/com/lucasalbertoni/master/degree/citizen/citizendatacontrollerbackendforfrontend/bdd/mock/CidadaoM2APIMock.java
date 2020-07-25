package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.bdd.mock;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.EmpresaDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.PermissaoDadosCidadaoEmpresaDTO;

@RestController
@RequestMapping("/cidadaom2mock")
public class CidadaoM2APIMock {
	
	private boolean empresaCadastrada = true;
	
	@PostMapping
	public PermissaoDadosCidadaoEmpresaDTO cadastrarPermissao(@RequestBody EmpresaDTO empresaDTO) {
		if(!empresaCadastrada) {
			throw new RuntimeException("Empresa não cadastrada");
		}
		PermissaoDadosCidadaoEmpresaDTO permissao = new PermissaoDadosCidadaoEmpresaDTO();
		permissao.setConfirmado(false);
		permissao.setDataExpiracaoToken(LocalDateTime.now().plusMinutes(60));
		permissao.setTokenAcesso(UUID.randomUUID().toString());
		return permissao;
	}

	public void setEmpresaNaoCadastrada() {
		empresaCadastrada = false;
	}

}
