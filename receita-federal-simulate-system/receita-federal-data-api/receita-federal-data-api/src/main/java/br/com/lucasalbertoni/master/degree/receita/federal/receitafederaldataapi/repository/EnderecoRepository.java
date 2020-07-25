package br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.entity.Endereco;

public interface EnderecoRepository extends CrudRepository<Endereco, Long> {
	
	@Modifying
	@Query(value = "DELETE FROM ENDERECO where id=?1",
			nativeQuery = true)
	void deleteById(Long id);

	Set<Endereco> findByPessoaFisicaId(Long id);
	
}
