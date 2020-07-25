package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Empresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.EmpresaService;

@RestController
@RequestMapping("/empresas")
public class EmpresaAPI {
	
	@Autowired
	private EmpresaService empresaService;
	
	@PostMapping
	public Empresa create(@RequestBody Empresa empresa) {
		return empresaService.save(empresa);
	}
	
	@PutMapping
	public Empresa update(@RequestBody Empresa empresa) {
		return empresaService.update(empresa);
	}
	
	@GetMapping
	public List<Empresa> find(@RequestParam(required = false) String cnpj) {
		List<Empresa> empresas = new ArrayList<>();
		if(cnpj != null) {
			empresas.add(empresaService.findByCnpj(cnpj));
			return empresas;
		}
		return empresaService.findAll();
	}

}
