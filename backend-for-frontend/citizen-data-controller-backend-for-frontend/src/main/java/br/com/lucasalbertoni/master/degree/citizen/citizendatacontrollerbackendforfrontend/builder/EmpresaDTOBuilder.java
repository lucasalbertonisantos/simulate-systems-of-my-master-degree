package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder;

import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.EmpresaDTO;

@Component
public class EmpresaDTOBuilder {
	
	public EmpresaDTO build(String cnpj) {
		EmpresaDTO empresa = new EmpresaDTO();
		empresa.setCnpj(cnpj);
		return empresa;
	}

}
