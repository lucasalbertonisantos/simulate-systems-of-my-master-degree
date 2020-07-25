package br.com.lucasalbertoni.master.degree.uf.ac.acufdataapi.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.uf.ac.acufdataapi.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.uf.ac.acufdataapi.service.CidadaoService;

@RestController
@RequestMapping("/cidadaos")
public class CidadaoAPI {
	
	@Autowired
	private CidadaoService cidadaoService;
	
	@PostMapping
	public Cidadao save(@RequestBody Cidadao cidadao) {
		return cidadaoService.save(cidadao);
	}
	
	@PatchMapping
	public Cidadao update(@RequestBody Cidadao cidadao) {
		return cidadaoService.update(cidadao);
	}
	
	@DeleteMapping
	public Cidadao delete(@RequestParam Long id) {
		return cidadaoService.deleteById(id);
	}
	
	@GetMapping
	public List<Cidadao> find(@RequestParam(name = "cpf", required = false) String cpf,
			@RequestParam(name = "id", required = false) Long id) {
		if(cpf != null) {
			return Arrays.asList(cidadaoService.findByCpf(cpf));
		}else if(id != null) {
			return Arrays.asList(cidadaoService.findById(id));
		}else {
			return cidadaoService.findAll();
		}
	}

}
