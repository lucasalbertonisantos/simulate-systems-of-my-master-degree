package br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.builder;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.Header;
import br.com.lucasalbertoni.master.degree.citizen.citizendataintegrator.entity.citizenrequesttopic.TipoOrgao;

@Component
public class HeaderBuilder {
	
	@Value("${nome.sistema}")
	private String nomeSistema;
	
	public Header build() {
		Header novoHeader = new Header();
		novoHeader.setData(LocalDateTime.now());
		novoHeader.setId(UUID.randomUUID().toString());
		novoHeader.setNomeSistema(nomeSistema);
		novoHeader.setTipoOrgao(TipoOrgao.FEDERAL);
		return novoHeader;
	}

}
