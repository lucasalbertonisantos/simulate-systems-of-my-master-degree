package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.CidadaoSistemas;

public interface CidadaoSistemasRepository extends CrudRepository<CidadaoSistemas, Long> {

	List<CidadaoSistemas> findByCpf(String cpf);

}
