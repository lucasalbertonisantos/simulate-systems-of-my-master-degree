 # language: pt
  @ConsultaDadosCidadaoEmpresa
Funcionalidade: Testar a Consulta dos dados do cidadão pela empresa liberada

	Cenario: Empresa consulta um cidadão com acesso
		Dado que exista a permissão de acesso confirmada do CPF 810.761.450-00 para a empresa de CNPJ 94.820.838/0001-75
		E a empresa esteja autenticada
		E ela possua o código de acesso
		Quando for feita a requisição para busca dos dados do cidadão passando o código de acesso e o CPF do cidadão informado
		Entao o código HTTP de resposta deve ser 200
		E a empresa deve receber os dados do cidadão

	Cenario: Empresa tenta consultar um cidadão sem permissão
		Dado que não exista permissão do CPF 810.761.450-00 para a empresa de CNPJ 94.820.838/0001-75
		E existe permissão de acesso com confirmação de acesso para o CPF 522.084.140-80 para essa empresa
		E a empresa esteja autenticada
		E ela possua o código de acesso de um segundo cidadão
		Quando for feita a requisição para busca dos dados do cidadão passando o código de acesso do segundo cidadão e o CPF do primeiro cidadão
		Entao o código HTTP de resposta deve ser 500
		E a empresa não deve receber os dados do cidadão

	Cenario: Empresa tenta consultar um cidadão sem confirmação de permissão
		Dado que exista permissão sem confirmação do CPF 810.761.450-00 para a empresa de CNPJ 94.820.838/0001-75
		E existe permissão de acesso com confirmação de acesso para o CPF 522.084.140-80 para essa empresa
		E a empresa esteja autenticada
		E ela possua o código de acesso do primeiro cidadão
		E ela possua o código de acesso do segundo cidadão
		Quando for feita a requisição para busca dos dados do cidadão passando o código de acesso do primeiro cidadão e o CPF do primeiro cidadão
		Entao o código HTTP de resposta deve ser 500
		E a empresa não deve receber os dados do cidadão
