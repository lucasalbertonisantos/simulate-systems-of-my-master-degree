package br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.entity.Telefone;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.repository.TelefoneRepository;

@Service
public class TelefoneService {
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	public Telefone deleteById(Long id) {
		Telefone telefone = findById(id);
		telefoneRepository.deleteById(id);
		return telefone;
	}
	
	public Telefone findById(Long id) {
		Optional<Telefone> telefone = telefoneRepository.findById(id);
		if(!telefone.isPresent()) {
			throw new RuntimeException("Telefone não encontrado");
		}
		return telefone.get();
	}

}
