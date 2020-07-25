package br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.lucasalbertoni.master.degree.receita.federal.receitafederaldataapi.entity.EmpresaPagadora;

public interface EmpresaPagadoraRepository extends CrudRepository<EmpresaPagadora, Long> {
	
	@Modifying
	@Query(value = "DELETE FROM EMPRESA_PAGADORA where id=?1",
			nativeQuery = true)
	void deleteById(Long id);

	Set<EmpresaPagadora> findByPessoaFisicaId(Long id);
	
}
