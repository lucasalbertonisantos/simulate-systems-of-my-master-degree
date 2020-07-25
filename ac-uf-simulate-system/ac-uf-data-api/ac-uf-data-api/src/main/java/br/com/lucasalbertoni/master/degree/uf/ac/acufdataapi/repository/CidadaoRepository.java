package br.com.lucasalbertoni.master.degree.uf.ac.acufdataapi.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lucasalbertoni.master.degree.uf.ac.acufdataapi.entity.Cidadao;

public interface CidadaoRepository extends CrudRepository<Cidadao, Long> {
	
	Cidadao findByCpf(String cpf);

}
