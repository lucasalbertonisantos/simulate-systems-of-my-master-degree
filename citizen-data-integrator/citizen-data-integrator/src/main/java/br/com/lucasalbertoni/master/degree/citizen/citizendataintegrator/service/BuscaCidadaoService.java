package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.BuscaCidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository.BuscaCidadaoRepository;

@Service
public class BuscaCidadaoService {
	
	@Autowired
	private BuscaCidadaoRepository buscaCidadaoRepository;
	
	public BuscaCidadao save(BuscaCidadao buscaCidadao) {
		if(buscaCidadao == null) {
			throw new RuntimeException("O objeto não pode ser nulo!");
		}
		if(buscaCidadao.getIdSolicitacao() == null || buscaCidadao.getIdSolicitacao().isBlank()) {
			throw new RuntimeException("O id de solicicao não pode ser nulo nem vazio!");
		}
		return buscaCidadaoRepository.save(buscaCidadao);
	}

	public BuscaCidadao findByIdSolicitacao(String idSolicitacao) {
		if(idSolicitacao == null || idSolicitacao.isBlank()) {
			throw new RuntimeException("Id da solicitação não pode ser nulo nem vazio!");
		}
		Optional<BuscaCidadao> optionalBuscaCidadao = buscaCidadaoRepository.findByIdSolicitacao(idSolicitacao);
		if(optionalBuscaCidadao.isEmpty()) {
			throw new RuntimeException("Busca Cidadao não encontrada para o id informado");
		}
		return optionalBuscaCidadao.get();
	}

}
