package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;

@Repository
public interface CidadaoRepository extends CrudRepository<Cidadao, Long> {

	boolean existsByCpf(String cpf);
	Cidadao findByCpf(String cpf);

}
