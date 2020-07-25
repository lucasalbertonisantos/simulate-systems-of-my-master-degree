package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PermissaoDadosCidadaoEmpresa;

@Repository
public interface PermissaoDadosCidadaoEmpresaRepository extends CrudRepository<PermissaoDadosCidadaoEmpresa, Long> {

	Optional<PermissaoDadosCidadaoEmpresa> findByTokenAcesso(String tokenAcesso);
	List<PermissaoDadosCidadaoEmpresa> findByEmpresaIdAndCidadaoId(Long empresaId, Long cidadaoId);
}
