package br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.entity.Endereco;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.repository.EnderecoRepository;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Endereco deleteById(Long id) {
		Endereco endereco = findById(id);
		enderecoRepository.deleteById(id);
		return endereco;
	}
	
	public Endereco findById(Long id) {
		Optional<Endereco> endereco = enderecoRepository.findById(id);
		if(!endereco.isPresent()) {
			throw new RuntimeException("Endereco não encontrado");
		}
		return endereco.get();
	}

}
