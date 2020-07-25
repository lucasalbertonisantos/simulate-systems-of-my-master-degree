package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.LoginCheckStatus;

@Repository
public interface LoginCheckStatusRepository extends CrudRepository<LoginCheckStatus, String> {

}
