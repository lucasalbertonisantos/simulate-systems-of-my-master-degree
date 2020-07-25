package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.LoginStatusEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PerfilEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.dto.TrocaSenhaLoginDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.LoginRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenConfirmarCadastroService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.utils.Encryption;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CnpjValidator;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.validate.CpfValidator;

@Service
public class LoginService {
	
	@Value("${citizen.controller.data.cidadao.liberacao.acesso.url}")
	private String url;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private LoginCidadaoEmpresaService loginCidadaoEmpresaService;
	
	@Autowired
	private LoginSistemaInternoService loginSistemaInternoService;
	
	@Autowired
	private Encryption encryption;
	
	@Autowired
	private TokenConfirmarCadastroService tokenConfirmarCadastroService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private GeradorSenhaService geradorSenhaService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private CpfValidator cpfValidator;
	
	@Autowired
	private CnpjValidator cnpjValidator;
	
//	@PostConstruct
//	public void init() {
//		Login login = new Login();
//		login.setDataCriacao(LocalDateTime.now());
//		login.setEmail("aaa@aaa.com");
//		login.setPerfil(PerfilEnum.SISTEMA_INTERNO);
//		login.setSenha("$2a$10$YtdjXwLhSjnns0OAqRu0/O/eHnEwsoI.iicONo2drS.9UCJcjFRVi");
//		login.setStatus(LoginStatusEnum.CREATED_LOGIN);
//		loginRepository.save(login);
//	}
	
	public boolean existsByCpfCnpj(String cpfCnpj) {
		return loginRepository.existsByCpfCnpj(cpfCnpj);
	}
	
	public Login create(Login login) {
		if(login == null) {
			throw new RuntimeException("Login não pode ser nulo!");
		}
		if(login.getId() != null) {
			throw new RuntimeException("Login id deve ser nulo!");
		}
		if(login.getPerfil() == null) {
			throw new RuntimeException("Login deve ter um perfil!");
		}
		if(login.getEmail() == null || login.getEmail().isBlank()) {
			throw new RuntimeException("Login deve ter um email!");
		}
		if(loginRepository.existsByEmail(login.getEmail())) {
			throw new RuntimeException("Email já cadastrado!");
		}
		if(PerfilEnum.SISTEMA_INTERNO.equals(login.getPerfil())){
			return loginSistemaInternoService.create(login);
		}else {
			return loginCidadaoEmpresaService.create(login);
		}
	}
	
	public Login findByUsername(String username) {
		if(username == null) {
			throw new RuntimeException("Não foi possível logar!");
		}
		Login loginRetornado = loginRepository.findByCpfCnpj(username);
		if(loginRetornado == null) {
			loginRetornado = loginRepository.findByEmail(username);
		}
		return loginRetornado;
	}
	
	public Login gerarLoginCidadaoRespostas(String cpf,
			String email, String senha) {
		if(cpf == null || cpf.isBlank()) {
			throw new RuntimeException("O CPF é obrigatório");
		}
		if(email == null || email.isBlank()) {
			throw new RuntimeException("O e-mail é obrigatório");
		}
		if(senha == null || senha.isBlank()) {
			throw new RuntimeException("O senha é obrigatório");
		}
		cpf = cpfValidator.validate(cpf);
		Login login = new Login();
		if(loginRepository.existsByCpfCnpj(cpf)) {
			login = loginRepository.findByCpfCnpj(cpf);
		}
		login.setDataCriacao(LocalDateTime.now());
		login.setCpfCnpj(cpf);
		login.setEmail(email);
		login.setLoginAtivo(false);
		login.setPerfil(PerfilEnum.CIDADAO);
		login.setSenha(encryption.encrypt(senha));
		login.setSenhaGeradaAutomaticamente(true);
		login.setStatus(LoginStatusEnum.CREATED_LOGIN);
		return loginRepository.save(login);
	}

	public Login liberarLoginCidadao(String token) {
		if(!tokenConfirmarCadastroService.isTokenValidoLiberarLogin(token)) {
			throw new RuntimeException("Token inválido!");
		}
		String cpf = tokenConfirmarCadastroService.getCpfUsuarioToken(token);
		Login login = loginRepository.findByCpfCnpj(cpf);
		if(login == null || !PerfilEnum.CIDADAO.equals(login.getPerfil())) {
			throw new RuntimeException("Login inválido!");
		}
		login.setLoginAtivo(true);
		Login loginSalvo = loginRepository.save(login);
		loginSalvo.setSenha(null);
		return loginSalvo;
	}

	public Login trocarSenha(TrocaSenhaLoginDTO trocaSenhaLoginDTO, String token) {
		if(trocaSenhaLoginDTO == null 
				|| trocaSenhaLoginDTO.getNovaSenha() == null
				|| trocaSenhaLoginDTO.getNovaSenha().isBlank()
				|| trocaSenhaLoginDTO.getSenha() == null
				|| trocaSenhaLoginDTO.getSenha().isBlank()
				|| token == null
				|| token.isBlank()) {
			throw new RuntimeException("Todos os campos são obrigatórios para trocar a senha!");
		}
		String email = tokenService.getEmailUsuarioToken(token.replace("Bearer ", ""));
		String novaSenhaC = encryption.encrypt(trocaSenhaLoginDTO.getNovaSenha());
		Login login = loginRepository.findByEmail(email);
		if(!encryption.check(trocaSenhaLoginDTO.getSenha(), login.getSenha())) {
			throw new RuntimeException("Proibido a alteração de senha!");
		}
		login.setSenha(novaSenhaC);
		login.setSenhaGeradaAutomaticamente(false);
		Login loginSalvo = loginRepository.save(login);
		loginSalvo.setSenha(null);
		return loginSalvo;
	}
	
	public Login recuperarSenha(Login loginRecebido) {
		if(loginRecebido == null 
				|| loginRecebido.getCpfCnpj() == null
				|| loginRecebido.getCpfCnpj().isBlank()
				|| loginRecebido.getEmail() == null
				|| loginRecebido.getEmail().isBlank()) {
			throw new RuntimeException("Não foi possível enviar a nova senha.");
		}
		loginRecebido.setCpfCnpj(cpfValidator.validate(loginRecebido.getCpfCnpj()));
		Login login = loginRepository.findByCpfCnpj(loginRecebido.getCpfCnpj());
		if(login == null 
				|| login.getEmail() == null
				|| login.getEmail().isBlank()
				|| !login.getEmail().equals(loginRecebido.getEmail())) {
			throw new RuntimeException("Não foi possível enviar a nova senha.");
		}
		String senhaGerada = geradorSenhaService.gerarSenha();
		String token = tokenConfirmarCadastroService.gerarTokenEnvioEmail(login.getCpfCnpj());
		emailService.sendToken(url, token, senhaGerada, login.getEmail());
		login.setSenha(encryption.encrypt(senhaGerada));
		login.setSenhaGeradaAutomaticamente(true);
		login.setLoginAtivo(false);
		login = loginRepository.save(login);
		login.setSenha(null);
		return login;
	}

	public Login createEmpresa(Login login) {
		if(login == null) {
			throw new RuntimeException("Login não pode ser nulo!");
		}
		if(login.getId() != null) {
			throw new RuntimeException("Login id deve ser nulo!");
		}
		if(login.getPerfil() == null) {
			throw new RuntimeException("Login deve ter um perfil!");
		}
		if(login.getEmail() == null || login.getEmail().isBlank()) {
			throw new RuntimeException("Login deve ter um email!");
		}
		if(loginRepository.existsByEmail(login.getEmail())) {
			throw new RuntimeException("Email já cadastrado!");
		}
		if(!PerfilEnum.EMPRESA.equals(login.getPerfil())){
			throw new RuntimeException("Perfil deve ser de empresa");
		}else {
			login.setCpfCnpj(cnpjValidator.validate(login.getCpfCnpj()));
			if(loginRepository.existsByCpfCnpj(login.getCpfCnpj())) {
				throw new RuntimeException("Documento já cadastrado!");
			}
			return loginCidadaoEmpresaService.create(login);
		}
	}

	public Login updateEmpresa(Login login) {
		if(login == null || login.getId() == null) {
			throw new RuntimeException("Login ou ID nulo");
		}
		if(!loginRepository.existsById(login.getId())) {
			throw new RuntimeException("Login não existe!");
		}
		if(login.getPerfil() == null) {
			throw new RuntimeException("Login deve ter um perfil!");
		}
		if(login.getEmail() == null || login.getEmail().isBlank()) {
			throw new RuntimeException("Login deve ter um email!");
		}
		Login empresaEncontrada = loginRepository.findByEmail(login.getEmail());
		if(empresaEncontrada != null && !empresaEncontrada.getId().equals(login.getId())) {
			throw new RuntimeException("Email já existe para outra empresa!");
		}
		if(!PerfilEnum.EMPRESA.equals(login.getPerfil())){
			throw new RuntimeException("Só é permitida para login de empresa");
		}else {
			login.setCpfCnpj(cnpjValidator.validate(login.getCpfCnpj()));
			empresaEncontrada = loginRepository.findByCpfCnpj(login.getCpfCnpj());
			if(empresaEncontrada != null && !empresaEncontrada.getId().equals(login.getId())) {
				throw new RuntimeException("Documento já existe para outra empresa!");
			}
			return loginCidadaoEmpresaService.update(login);
		}
	
	}

}
