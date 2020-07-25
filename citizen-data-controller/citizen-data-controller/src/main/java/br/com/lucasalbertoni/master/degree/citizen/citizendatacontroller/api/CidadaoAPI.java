package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Empresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PermissaoDadosCidadaoEmpresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.CidadaoService;

@RestController
@RequestMapping("/cidadaos")
public class CidadaoAPI {
	
	@Autowired
	private CidadaoService cidadaoService;
	
	@PostMapping("/liberar-dados")
	public PermissaoDadosCidadaoEmpresa liberarDados(
			@RequestHeader("Authorization") String token, 
			@RequestBody Empresa empresa) {
		return cidadaoService.liberarDados(token, empresa);
	}
	
	@GetMapping("/confirmar-liberacao-dados")
	public String liberarPermissao(
			@RequestParam(name = "acesso") String urlToken) {
		try {
			PermissaoDadosCidadaoEmpresa permissao = cidadaoService.confirmar(urlToken);
			return "Pode utilizar o código: " + permissao.getTokenAcesso() + " para liberar acesso aos seus dados!";
		}catch (Exception e) {
			return "Token expirado. Necessário refazer o procedimento.";
		}
	}
	
	@GetMapping
	public List<Cidadao> find(@RequestHeader("Authorization") String token, 
			@RequestParam String cpf, @RequestParam(required = false) String codigo){
		List<Cidadao> cidadaos = new ArrayList<>();
		cidadaos.add(cidadaoService.findIfAllowed(token, cpf, codigo));
		return cidadaos;
	}

}
