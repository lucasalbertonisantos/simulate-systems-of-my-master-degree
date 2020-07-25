package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PerfilEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.LoginRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CnpjValidator;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CpfValidator;

@Service
public class LoginCidadaoEmpresaService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private LoginCidadaoService loginCidadaoService;
	
	@Autowired
	private LoginEmpresaService loginEmpresaService;
	
	@Autowired
	private CpfValidator cpfValidator;
	
	@Autowired
	private CnpjValidator cnpjValidator;
	
	public Login create(Login login) {
		if(login == null) {
			throw new RuntimeException("Login não pode ser nulo!");
		}
		if(login.getCpfCnpj() == null || login.getCpfCnpj().isBlank()) {
			throw new RuntimeException("Documento não pode ser nulo nem vazio");
		}
		if(loginRepository.existsByCpfCnpj(login.getCpfCnpj())) {
			throw new RuntimeException("Login já existe para esse documento!");
		}
		if(PerfilEnum.CIDADAO.equals(login.getPerfil())){
			login.setCpfCnpj(cpfValidator.validate(login.getCpfCnpj()));
			if(loginRepository.existsByCpfCnpj(login.getCpfCnpj())) {
				throw new RuntimeException("Documento já cadastrado!");
			}
			return loginCidadaoService.create(login);
		}else if(PerfilEnum.EMPRESA.equals(login.getPerfil())){
			login.setCpfCnpj(cnpjValidator.validate(login.getCpfCnpj()));
			if(loginRepository.existsByCpfCnpj(login.getCpfCnpj())) {
				throw new RuntimeException("Documento já cadastrado!");
			}
			return loginEmpresaService.create(login);
		}
		throw new RuntimeException("Perfil inválido!");
	}
	
	public Login update(Login login) {
		if(login == null) {
			throw new RuntimeException("Login não pode ser nulo!");
		}
		if(login.getCpfCnpj() == null || login.getCpfCnpj().isBlank()) {
			throw new RuntimeException("Documento não pode ser nulo nem vazio");
		}
		if(PerfilEnum.CIDADAO.equals(login.getPerfil())){
			login.setCpfCnpj(cpfValidator.validate(login.getCpfCnpj()));
			Login loginEncontrado = loginRepository.findByCpfCnpj(login.getCpfCnpj());
			if(loginEncontrado == null) {
				throw new RuntimeException("Login desse cidadão não existe!");
			}
			if(!loginEncontrado.getId().equals(login.getId())) {
				throw new RuntimeException("Login já existe para esse documento!");
			}
			return loginCidadaoService.create(login);
		}else if(PerfilEnum.EMPRESA.equals(login.getPerfil())){
			login.setCpfCnpj(cnpjValidator.validate(login.getCpfCnpj()));
			Login loginEncontrado = loginRepository.findByCpfCnpj(login.getCpfCnpj());
			if(loginEncontrado == null) {
				throw new RuntimeException("Login dessa empresa não existe!");
			}
			if(!loginEncontrado.getId().equals(login.getId())) {
				throw new RuntimeException("Login já existe para esse documento!");
			}
			return loginEmpresaService.create(login);
		}
		throw new RuntimeException("Perfil inválido!");
	}

}
