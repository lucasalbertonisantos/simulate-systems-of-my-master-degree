package br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.apiclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.builder.EmpresaDTOBuilder;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.SolicitarLiberacao;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.TokenLiberacaoAcesso;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.EmpresaDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.entity.citizendataconttroller.PermissaoDadosCidadaoEmpresaDTO;
import br.com.lucasalbertoni.master.degree.citizen.citizendatacontrollerbackendforfrontend.parse.PermissaoDadosCidadaoEmpresaDTOToTokenLiberacaoAcesso;

@Service
public class CidadaoApiClient {
	
	@Value("${citizen.controller.cidadao.liberar.acesso.empresa}")
	private String url;
	
	@Autowired
	private GenericApiClient genericApiClient;
	
	@Autowired
	private EmpresaDTOBuilder empresaDTOBuilder;
	
	@Autowired
	private PermissaoDadosCidadaoEmpresaDTOToTokenLiberacaoAcesso permissaoDadosCidadaoEmpresaDTOToTokenLiberacaoAcesso;
	
	public TokenLiberacaoAcesso solicitarLiberacaoDados(SolicitarLiberacao solicitarLiberacao) {
		if(solicitarLiberacao == null 
				|| solicitarLiberacao.getToken() == null) {
			throw new RuntimeException("Nem a liberação nem o token podêm ser nulos!");
		}
		EmpresaDTO empresa = empresaDTOBuilder.build(solicitarLiberacao.getCnpj());
		String token = solicitarLiberacao.getToken().getToken();
		PermissaoDadosCidadaoEmpresaDTO resposta = genericApiClient.postRequest(url, token, empresa, PermissaoDadosCidadaoEmpresaDTO.class);
		return permissaoDadosCidadaoEmpresaDTOToTokenLiberacaoAcesso.parse(resposta);
	}

}
