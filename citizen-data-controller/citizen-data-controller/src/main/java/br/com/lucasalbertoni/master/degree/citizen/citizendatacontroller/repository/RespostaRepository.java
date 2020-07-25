package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Resposta;

@Repository
public interface RespostaRepository extends CrudRepository<Resposta, Long> {

	List<Resposta> findByCpfAndAtiva(String cpf, boolean ativa);

}
