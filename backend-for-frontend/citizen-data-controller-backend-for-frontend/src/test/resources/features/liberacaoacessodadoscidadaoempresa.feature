 # language: pt
  @LiberacaoAcessoCidadaoEmpresa
Funcionalidade: Testar a Liberação do acesso aos dados do cidadão, pelo cidadão para empresa

	Cenario: Fluxo de liberação concluído com sucesso
		Dado que exista um cidadão logado
		E ele informe o CNPJ 38.371.823/0001-04
		E essa empresa esteja cadastrada
		Quando for feita a requisição para o processo de liberação de dados para empresa
		Entao o código HTTP de resposta deve ser 200
		E o cidadão deve receber um e-mail com o token de confirmação
		E a empresa ainda não deve conseguir acessar os dados
		Quando o cidadão clicar no token
		Então será feito o procedimento de confirmação liberando o acesso para empresa
		Quando a empresa tentar buscar os dados do cidadão através de sua credencial e CPF do cidadão
		Entao ela deve conseguir acessar esses dados

	Cenario: Fluxo de liberação CNPJ não existe
		Dado que exista um cidadão logado
		E ele informe o CNPJ 38.371.823/0001-04
		E essa empresa não esteja cadastrada
		Quando for feita a requisição para o processo de liberação de dados para empresa
		Entao o código HTTP de resposta deve ser 500
		E o cidadão não deve receber um e-mail com o token de confirmação
