package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Sistema;

@Repository
public interface SistemaRepository extends CrudRepository<Sistema, Long> {
	
	Optional<Sistema> findByNome(String nome);

}
