package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;

@Repository
public interface LoginRepository extends CrudRepository<Login, Long> {

	Login findByCpfCnpj(String cpfCnpj);
	boolean existsByCpfCnpj(String cpfCnpj);
	Login findByEmail(String username);
	boolean existsByEmail(String email);

}
