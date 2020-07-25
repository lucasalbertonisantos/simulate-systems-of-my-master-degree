package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.BuscaCidadao;

@Repository
public interface BuscaCidadaoRepository extends CrudRepository<BuscaCidadao, Long> {

	Optional<BuscaCidadao> findByIdSolicitacao(String idSolicitacao);

}
