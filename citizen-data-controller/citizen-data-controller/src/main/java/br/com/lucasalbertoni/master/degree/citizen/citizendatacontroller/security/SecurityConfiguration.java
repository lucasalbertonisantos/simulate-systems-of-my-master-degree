package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PerfilEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.AutenticacaoService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.PUT, "/perguntas").hasAuthority(PerfilEnum.SISTEMA_INTERNO.getAuthority())
		
		.antMatchers(HttpMethod.PUT, "/respostas").hasAuthority(PerfilEnum.SISTEMA_INTERNO.getAuthority())
		
		.antMatchers(HttpMethod.POST, "/empresas").hasAuthority(PerfilEnum.SISTEMA_INTERNO.getAuthority())
		.antMatchers(HttpMethod.PUT, "/empresas").hasAuthority(PerfilEnum.SISTEMA_INTERNO.getAuthority())
		.antMatchers(HttpMethod.GET, "/empresas").hasAuthority(PerfilEnum.SISTEMA_INTERNO.getAuthority())
		.antMatchers(HttpMethod.GET, "/empresas/**").hasAuthority(PerfilEnum.SISTEMA_INTERNO.getAuthority())
		
		.antMatchers(HttpMethod.POST, "/cidadaos/liberar-dados").hasAuthority(PerfilEnum.CIDADAO.getAuthority())
		.antMatchers(HttpMethod.GET, "/cidadaos/confirmar-liberacao-dados").permitAll()
		.antMatchers(HttpMethod.GET, "/cidadaos").authenticated()
		.antMatchers(HttpMethod.GET, "/cidadaos/*").authenticated()
		.antMatchers(HttpMethod.GET, "/email").permitAll()
		
		.antMatchers(HttpMethod.GET, "/login/situacao").hasAuthority(PerfilEnum.SISTEMA_INTERNO.getAuthority())
		.antMatchers(HttpMethod.PUT, "/login/recuperar-senha").hasAuthority(PerfilEnum.SISTEMA_INTERNO.getAuthority())
		.antMatchers(HttpMethod.PUT, "/login/trocar-senha").permitAll()
		.antMatchers(HttpMethod.POST, "/login").hasAuthority(PerfilEnum.SISTEMA_INTERNO.getAuthority())
		.antMatchers(HttpMethod.POST, "/login/empresa").hasAuthority(PerfilEnum.SISTEMA_INTERNO.getAuthority())
		.antMatchers(HttpMethod.PUT, "/login/empresa").hasAuthority(PerfilEnum.SISTEMA_INTERNO.getAuthority())
		.antMatchers(HttpMethod.GET, "/login/liberar-cidadao").permitAll()
		.antMatchers(HttpMethod.POST, "/login/trocar-senha").authenticated()

		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		
		//MOCKS
		.antMatchers(HttpMethod.GET, "/cidadaom1apimock").permitAll()
		
		.anyRequest().denyAll()
		.and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, autenticacaoService), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	}

}
