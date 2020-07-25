package br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.entity.EmpresaPagadora;
import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.repository.EmpresaPagadoraRepository;

@Service
public class EmpresaPagadoraService {
	
	@Autowired
	private EmpresaPagadoraRepository empresaPagadoraRepository;
	
	public EmpresaPagadora deleteById(Long id) {
		EmpresaPagadora empresaPagadora = findById(id);
		empresaPagadoraRepository.deleteById(id);
		return empresaPagadora;
	}
	
	public EmpresaPagadora findById(Long id) {
		Optional<EmpresaPagadora> empresaPagadora = empresaPagadoraRepository.findById(id);
		if(!empresaPagadora.isPresent()) {
			throw new RuntimeException("Empresa Pagadora não encontrada");
		}
		return empresaPagadora.get();
	}

}
