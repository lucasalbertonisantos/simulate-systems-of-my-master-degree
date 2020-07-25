package br.com.lucasalbertoni.master.degree.uf.sp.ufspdataapi.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.lucasalbertoni.master.degree.uf.sp.ufspdataapi.entity.Endereco;

public interface EnderecoRepository extends CrudRepository<Endereco, Long> {
	
	Set<Endereco> findByCidadaoId(Long id);
	
	@Modifying
	@Query(value = "DELETE FROM ENDERECO where id=?1",
			nativeQuery = true)
	void deleteById(Long id);

}
