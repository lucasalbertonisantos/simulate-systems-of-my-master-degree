package br.com.lucasalbertoni.master.degree.uf.ac.acufdataapi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.uf.ac.acufdataapi.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.uf.ac.acufdataapi.repository.CidadaoRepository;
import br.com.lucasalbertoni.master.degree.uf.ac.acufdataapi.validate.CpfValidator;

@Service
public class CidadaoService {
	
	@Autowired
	private CidadaoRepository cidadaoRepository;
	
	@Autowired
	private CpfValidator cpfValidator;
	
	public Cidadao save(Cidadao cidadao) {
		if(cidadao == null) {
			throw new RuntimeException("Cidadao nao pode ser nulo");
		}
		validaDadosBasicosCidadao(cidadao);
		if(cidadao.getId() != null) {
			throw new RuntimeException("O ID deve ser nulo");
		}
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now());
		return cidadaoRepository.save(cidadao);
	}
	
	public Cidadao update(Cidadao cidadao) {
		if(cidadao == null) {
			throw new RuntimeException("Cidadao não pode ser nulo");
		}
		validaDadosBasicosCidadao(cidadao);
		if(cidadao.getId() == null) {
			throw new RuntimeException("O ID não pode ser nulo");
		}
		findById(cidadao.getId());
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now());
		return cidadaoRepository.save(cidadao);
	}
	
	public List<Cidadao> findAll(){
		List<Cidadao> cidadoes = new ArrayList<>();
		cidadaoRepository.findAll().forEach(cidadoes::add);
		return cidadoes;
	}
	
	public Cidadao findByCpf(String cpf){
		Cidadao cidadao = cidadaoRepository.findByCpf(cpf);
		if(cidadao == null) {
			throw new RuntimeException("Cidadao não encontrado");
		}
		return cidadao;
	}
	
	public Cidadao findById(Long id) {
		Optional<Cidadao> cidadao = cidadaoRepository.findById(id);
		if(!cidadao.isPresent()) {
			throw new RuntimeException("Cidadao não encontrado");
		}
		return cidadao.get();
	}

	private void validaDadosBasicosCidadao(Cidadao cidadao) {
		if(cidadao.getCpf() == null || cidadao.getCpf().isBlank()) {
			throw new RuntimeException("O CPF do Cidadao nao pode ser nulo nem vazio");
		}
		if(cidadao.getNome() == null || cidadao.getNome().isBlank()) {
			throw new RuntimeException("O Nome do Cidadao nao pode ser nulo nem vazio");
		}
		if(cidadao.getRg() == null || cidadao.getRg().isBlank()) {
			throw new RuntimeException("O RG do Cidadao nao pode ser nulo nem vazio");
		}
		if(cidadao.getUfRg() == null || cidadao.getUfRg().isBlank()) {
			throw new RuntimeException("A UF do RG do Cidadao nao pode ser nulo nem vazio");
		}
		cidadao.setCpf(cpfValidator.validate(cidadao.getCpf()));
	}

	public Cidadao deleteById(Long id) {
		Cidadao cidadao = findById(id);
		cidadaoRepository.deleteById(id);
		return cidadao;
	}

}
