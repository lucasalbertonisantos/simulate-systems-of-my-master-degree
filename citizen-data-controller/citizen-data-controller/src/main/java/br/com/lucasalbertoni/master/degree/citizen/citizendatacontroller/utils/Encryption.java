package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encryption {
	
	public String encrypt(String valueToEncrypt) {
		return new BCryptPasswordEncoder().encode(valueToEncrypt);
	}
	
	public boolean check(String valueToCheck, String encodedPassword) {
		return new BCryptPasswordEncoder().matches(valueToCheck, encodedPassword);
	}

}
