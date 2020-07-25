package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Empresa;

@Repository
public interface EmpresaRepository extends CrudRepository<Empresa, Long> {

	boolean existsByCnpj(String cnpj);
	Empresa findByCnpj(String cnpj);

}
