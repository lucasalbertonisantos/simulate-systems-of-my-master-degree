package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Sistema;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.SistemaRepository;

@Service
public class SistemaService {
	
	@Autowired
	private SistemaRepository sistemaRepository;
	
	public Sistema findByNomeOrCreate(String nome) {
		if(nome == null || nome.isBlank()) {
			throw new RuntimeException("O nome é obrigatório!");
		}
		Optional<Sistema> optionalSistema = sistemaRepository.findByNome(nome);
		if(optionalSistema.isEmpty()) {
			Sistema sistema = new Sistema();
			sistema.setNome(nome);
			return sistemaRepository.save(sistema);
		}
		return optionalSistema.get();
	}

}
