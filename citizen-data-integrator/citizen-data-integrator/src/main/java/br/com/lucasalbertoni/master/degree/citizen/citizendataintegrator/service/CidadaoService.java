package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.CidadaoRepository;

@Service
public class CidadaoService {
	
	@Autowired
	private CidadaoRepository cidadaoRepository;
	
	public Boolean existsPrincipalByCpf(String cpf) {
		return cidadaoRepository.existsByCpfAndPrincipal(cpf, true);
	}
	
	public Boolean existsByCpf(String cpf) {
		return cidadaoRepository.existsByCpf(cpf);
	}
	
	public Cidadao save(Cidadao cidadao) {
		if(cidadao == null) {
			throw new RuntimeException("Cidadao não pode ser nulo!");
		} else if(cidadao.getId() != null) {
			throw new RuntimeException("Id deve ser nulo");
		}else if(cidadao.getNome() == null || cidadao.getNome().isBlank()) {
			throw new RuntimeException("Nome deve estar preenchido");
		}else if(cidadao.getCpf() == null || cidadao.getCpf().isBlank()) {
			throw new RuntimeException("CPF deve estar preenchido");
		}
		return cidadaoRepository.save(cidadao);
	}

	public Cidadao findPrincipalByCpf(String cpf) {
		return cidadaoRepository.findByCpfAndPrincipal(cpf, true);
	}

}
