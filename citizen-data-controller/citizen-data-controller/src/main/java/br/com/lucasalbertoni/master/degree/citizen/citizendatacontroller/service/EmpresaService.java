package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Empresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.EmpresaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CnpjValidator;

@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private CnpjValidator cnpjValidator;
	
	public Empresa save(Empresa empresa) {
		if(empresa == null) {
			throw new RuntimeException("Empresa nula!");
		}
		if(empresa.getCnpj() == null || empresa.getCnpj().isBlank()) {
			throw new RuntimeException("Empresa não pode ter cnpj nem nulo nem vazio!");
		}
		empresa.setCnpj(cnpjValidator.validate(empresa.getCnpj()));
		if(empresa.getId() != null) {
			throw new RuntimeException("A empresa não pode estar com o id preenchido!");
		}
		if(empresa.getNome() == null || empresa.getNome().isBlank()) {
			throw new RuntimeException("A empresa não pode estar com o nome nem vazio nem nulo!");
		}
		Empresa empresaExistente = empresaRepository.findByCnpj(empresa.getCnpj());
		if(empresaExistente != null) {
			throw new RuntimeException("Já existe esse cnpj para outra empresa!");
		}
		return empresaRepository.save(empresa);
	}
	
	public Empresa update(Empresa empresa) {
		if(empresa == null) {
			throw new RuntimeException("Empresa nula!");
		}
		if(empresa.getId() == null) {
			throw new RuntimeException("ID empresa nula!");
		}
		if(!empresaRepository.existsById(empresa.getId())) {
			throw new RuntimeException("Não existe empresa com esse ID!");
		}
		if(empresa.getNome() == null || empresa.getNome().isBlank()) {
			throw new RuntimeException("A empresa não pode estar com o nome nem vazio nem nulo!");
		}
		empresa.setCnpj(cnpjValidator.validate(empresa.getCnpj()));
		Empresa empresaExistente = empresaRepository.findByCnpj(empresa.getCnpj());
		if(empresaExistente != null && !empresa.getId().equals(empresaExistente.getId())) {
			throw new RuntimeException("Já existe um cnpj para outra empresa!");
		}
		return empresaRepository.save(empresa);
	}
	
	public Empresa findByCnpj(String cnpj) {
		cnpj = cnpjValidator.validate(cnpj);
		Empresa empresa = empresaRepository.findByCnpj(cnpj);
		if(empresa == null) {
			throw new RuntimeException("Empresa não encontrada!");
		}
		return empresa;
	}

	public List<Empresa> findAll() {
		return StreamSupport 
        .stream(empresaRepository.findAll().spliterator(), false) 
        .collect(Collectors.toList()); 
	}

}
