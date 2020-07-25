package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Pergunta;

@Repository
public interface PerguntaRepository extends CrudRepository<Pergunta, Long> {

	List<Pergunta> findByCpfAndAtivaOrderByOrdemAsc(String cpf, boolean ativa);

}
