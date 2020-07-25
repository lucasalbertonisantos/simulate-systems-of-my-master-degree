package br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Cidadao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Empresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.Login;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PerfilEnum;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.entity.PermissaoDadosCidadaoEmpresa;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.CidadaoRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.repository.PermissaoDadosCidadaoEmpresaRepository;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenLiberarDadosService;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontroller.security.service.TokenService;

@Service
public class CidadaoService {
	
	@Value("${citizen.controller.data.cidadao.liberacao.dados.url}")
	private String url;
	
	@Autowired
	private CidadaoRepository cidadaoRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PermissaoDadosCidadaoEmpresaRepository permissaoDadosCidadaoEmpresaRepository;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private TokenLiberarDadosService tokenLiberarDadosService;
	
	public boolean existsByCpf(String cpf) {
		return cidadaoRepository.existsByCpf(cpf);
	}

	public Cidadao save(Cidadao cidadao) {
		if(cidadao == null) {
			throw new RuntimeException("Cidadao não pode ser nulo");
		}
		if(cidadao.getId() != null) {
			throw new RuntimeException("Id deve ser nulo");
		}
		if(cidadao.getCpf() == null || cidadao.getCpf().isBlank()) {
			throw new RuntimeException("Cpf não pode ser nulo nem vazio");
		}
		cidadao.setDataUltimaAtualizacao(LocalDateTime.now());
		return cidadaoRepository.save(cidadao);
	}
	
	public Cidadao findByCpf(String cpf) {
		Cidadao cidadao = cidadaoRepository.findByCpf(cpf);
		if(cidadao == null) {
			throw new RuntimeException("Cidadao não encontrado!");
		}
		return cidadao;
	}

	public PermissaoDadosCidadaoEmpresa liberarDados(String token, Empresa empresa) {
		if(empresa == null 
				|| empresa.getCnpj() == null
				|| empresa.getCnpj().isBlank()) {
			throw new RuntimeException("Parametros inválidos!");
		}
		empresa = empresaService.findByCnpj(empresa.getCnpj());
		if(empresa == null) {
			throw new RuntimeException("Empresa não encontrada!");
		}
		if(token == null) {
			throw new RuntimeException("Token inválido");
		}
		String email = tokenService.getEmailUsuarioToken(token.replace("Bearer ", ""));
		Login login = loginService.findByUsername(email);
		if(login == null) {
			throw new RuntimeException("Login não encontrado");
		}
		Cidadao cidadao = cidadaoRepository.findByCpf(login.getCpfCnpj());
		PermissaoDadosCidadaoEmpresa permissao = new PermissaoDadosCidadaoEmpresa();
		permissao.setConfirmado(false);
		permissao.setCidadao(cidadao);
		permissao.setDataExpiracaoToken(LocalDateTime.now().plusHours(5));
		permissao.setEmpresa(empresa);
		permissao.setTokenAcesso(UUID.randomUUID().toString());
		permissao = permissaoDadosCidadaoEmpresaRepository.save(permissao);
		String tokenEnvio = tokenLiberarDadosService.gerarTokenEnvioEmail(permissao.getTokenAcesso());
		emailService.sendTokenLiberarAcessoDados(empresa.getNome(), 
				empresa.getCnpj(), 
				url, 
				tokenEnvio, 
				permissao.getTokenAcesso(), 
				email);
		return permissao;
	}

	public PermissaoDadosCidadaoEmpresa confirmar(String urlToken) {
		if(!tokenLiberarDadosService.isTokenValidoLiberarLogin(urlToken)) {
			throw new RuntimeException("Token inválido!");
		}
		String tokenPermissao = tokenLiberarDadosService.getIdPermissaoToken(urlToken);
		Optional<PermissaoDadosCidadaoEmpresa> optionalPermissao = permissaoDadosCidadaoEmpresaRepository.findByTokenAcesso(tokenPermissao);
		if(optionalPermissao.isPresent()) {
			PermissaoDadosCidadaoEmpresa permissao = optionalPermissao.get();
			permissao.setConfirmado(true);
			permissao.setDataConfirmacao(LocalDateTime.now());
			return permissaoDadosCidadaoEmpresaRepository.save(permissao);
		}
		throw new RuntimeException("Permissão não encontrada!");
	}

	public Cidadao findIfAllowed(String token, String cpf, String codigo) {
		if(token == null 
				|| token.isBlank() 
				|| cpf == null 
				|| cpf.isBlank() 
				|| codigo == null 
				|| codigo.isBlank()) {
			throw new RuntimeException("Faltam parâmetros na busca!");
		}
		String email = tokenService.getEmailUsuarioToken(token.replace("Bearer ", ""));
		if(email == null || email.isBlank()) {
			throw new RuntimeException("Erro ao buscar o email");
		}
		Login login = loginService.findByUsername(email);
		if(login == null || login.getPerfil() == null) {
			throw new RuntimeException("Erro ao buscar o login");
		}
		if(PerfilEnum.EMPRESA.equals(login.getPerfil())) {
			String cnpj = login.getCpfCnpj();
			Empresa empresa = empresaService.findByCnpj(cnpj);
			Cidadao cidadao = findByCpf(cpf);
			List<PermissaoDadosCidadaoEmpresa> permissoes= permissaoDadosCidadaoEmpresaRepository.findByEmpresaIdAndCidadaoId(empresa.getId(), cidadao.getId());
			if(permissoes == null || permissoes.isEmpty()) {
				throw new RuntimeException("Empresa sem permissao!");
			}
			for(PermissaoDadosCidadaoEmpresa permissao : permissoes) {
				if(permissao.isConfirmado() 
						&& permissao.getDataExpiracaoToken().isAfter(LocalDateTime.now())
						&& permissao.getTokenAcesso().equals(codigo)) {
					return cidadaoRepository.findByCpf(cpf);
				}
			}
			throw new RuntimeException("Empresa sem permissao!");
		}
		return cidadaoRepository.findByCpf(cpf);
	}

}
