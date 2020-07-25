package br.com.lucasalbertoni.master.degree.uf.sp.ufspdataapi.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lucasalbertoni.master.degree.uf.sp.ufspdataapi.entity.Cidadao;

public interface CidadaoRepository extends CrudRepository<Cidadao, Long> {
	
	Cidadao findByCpf(String cpf);

}
