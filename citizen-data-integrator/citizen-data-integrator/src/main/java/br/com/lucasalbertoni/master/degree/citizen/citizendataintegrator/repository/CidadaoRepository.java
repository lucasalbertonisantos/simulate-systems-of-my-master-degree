package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Cidadao;

@Repository
public interface CidadaoRepository extends CrudRepository<Cidadao, Long> {
	
	Boolean existsByCpfAndPrincipal(String cpf, boolean principal);
	Boolean existsByCpf(String cpf);
	List<Cidadao> findByCpf(String cpf);
	Cidadao findByCpfAndPrincipal(String cpf, boolean principal);

}
