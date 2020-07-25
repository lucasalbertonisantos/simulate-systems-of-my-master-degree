package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value="LoginCheckStatus", timeToLive=300)
public class LoginCheckStatus implements Serializable{
	
	private static final long serialVersionUID = -4800848952170220708L;
	@Id
	private String cpf;
	private LoginStatusEnum status;
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public LoginStatusEnum getStatus() {
		return status;
	}
	public void setStatus(LoginStatusEnum status) {
		this.status = status;
	}

}
