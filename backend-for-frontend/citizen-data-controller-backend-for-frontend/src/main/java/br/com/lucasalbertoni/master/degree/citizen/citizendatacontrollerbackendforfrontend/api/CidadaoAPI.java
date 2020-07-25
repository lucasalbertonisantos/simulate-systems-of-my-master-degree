package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.Permissao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.SolicitarLiberacao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.service.CidadaoService;

@RestController
@RequestMapping("/cidadaos")
public class CidadaoAPI {
	
	@Autowired
	private CidadaoService cidadaoService;
	
	@PostMapping("/liberar-dados-empresa")
	public Permissao liberarDados(@RequestBody SolicitarLiberacao solicitarLiberacao) {
		return cidadaoService.liberarDados(solicitarLiberacao);
	}

}
