package br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.entity.PessoaFisica;

@Repository
public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica, Long> {

	PessoaFisica findByCpf(String cpf);

}
