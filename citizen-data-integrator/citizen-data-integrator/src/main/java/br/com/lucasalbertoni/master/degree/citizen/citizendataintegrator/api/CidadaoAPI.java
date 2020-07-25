package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.BuscaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.facade.CidadaoFacade;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service.CidadaoSistemasService;

@RestController
@RequestMapping("/cidadaos")
public class CidadaoAPI {
	
	@Autowired
	private CidadaoSistemasService cidadaoSistemasService;
	
	@Autowired
	private CidadaoFacade cidadaoFacade;
	
	@GetMapping("/principal")
	public Cidadao findPrincipal(@RequestParam(name = "cpf") String cpf) {
		return cidadaoFacade.find(cpf);
	}
	
	@GetMapping("/buscar")
	public String buscar(@RequestParam(name = "cpf") String cpf){
		return cidadaoSistemasService.buscar(cpf);
	}
	
	@GetMapping("/consultar-status")
	public BuscaCidadao consultarStatus(@RequestParam(name = "idSolicitacao") String idSolicitacao){
		return cidadaoSistemasService.consultarStatus(idSolicitacao);
	}

}
