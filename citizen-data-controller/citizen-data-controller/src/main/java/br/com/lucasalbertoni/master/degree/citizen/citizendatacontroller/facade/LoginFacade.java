package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.apiclient.CidadaoDadosIntegradoApiClient;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.LoginStatusEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.CidadaoService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.LoginService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CpfValidator;

@Service
public class LoginFacade {
	
	@Autowired
	private CpfValidator cpfValidator;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired 
	private CidadaoService cidadaoService;
	
	@Autowired
	private CidadaoDadosIntegradoApiClient cidadaoDadosIntegradoApiClient;
	
	public Login checkStatus(String cpf) {
		cpfValidator.validate(cpf);
		Login login = new Login();
		login.setCpfCnpj(cpf);
		if(loginService.existsByCpfCnpj(cpf)) {
			login.setStatus(LoginStatusEnum.CREATED_LOGIN);
		}else if(!cidadaoService.existsByCpf(cpf)){
			try {
				Cidadao cidadao = cidadaoDadosIntegradoApiClient.findByCpf(cpf);
				cidadao.setId(null);
				login.setStatus(LoginStatusEnum.PENDING_LOGIN);
				cidadaoService.save(cidadao);
			}catch (Exception e) {
				e.printStackTrace();
				login.setStatus(LoginStatusEnum.SEARCHING_DATA);
			}
		}else {
			login.setStatus(LoginStatusEnum.PENDING_LOGIN);
		}
		return login;
	}

}
