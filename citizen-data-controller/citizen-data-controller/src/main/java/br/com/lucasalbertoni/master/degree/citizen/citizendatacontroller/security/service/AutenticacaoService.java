package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service.LoginService;

@Service
public class AutenticacaoService implements UserDetailsService{
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Login login = loginService.findByUsername(username);
		if(login != null) {
			return login;
		}
		throw new UsernameNotFoundException("Login inválido!");
	}
	
	public void validateUsuarioPerfil(Authentication auth, String perfil) {
		if(perfil != null 
				&& !perfil.isBlank()
				&& auth != null
				&& auth.getAuthorities() != null
				&& !auth.getAuthorities().isEmpty()) {
			boolean encontrou = false;
			for(GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if(perfil.equals(grantedAuthority.getAuthority())) {
					encontrou=true;
				}
			}
			if(!encontrou) {
				throw new RuntimeException("Perfil diferente do esperado!");
			}
		}
	}

}
