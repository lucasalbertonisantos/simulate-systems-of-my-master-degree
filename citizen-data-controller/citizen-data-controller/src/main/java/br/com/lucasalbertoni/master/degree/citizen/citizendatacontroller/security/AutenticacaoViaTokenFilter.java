package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.AutenticacaoService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenService;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter{
	
	private TokenService tokenService;
	private AutenticacaoService autenticacaoService;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService,
			AutenticacaoService autenticacaoService) {
		this.tokenService = tokenService;
		this.autenticacaoService = autenticacaoService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recuperarToken(request);
		if(tokenService.isTokenValido(token)) {
			autenticar(token);
		}
		filterChain.doFilter(request, response);
	}

	private void autenticar(String token) {
		String email = tokenService.getEmailUsuarioToken(token);
		UserDetails userDetails = autenticacaoService.loadUserByUsername(email);
		UsernamePasswordAuthenticationToken authToken = new 
				UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isBlank() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.replace("Bearer ", "");
	}

}
