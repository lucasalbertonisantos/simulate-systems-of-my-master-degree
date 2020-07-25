package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.Endereco;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {

}
