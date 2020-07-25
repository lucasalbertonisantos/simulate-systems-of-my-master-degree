 # language: pt
  @LoginCidadao
Funcionalidade: Testar o Login do cidadão

	Cenario: Efetuar login do cidadão
		Dado que existe um login para o cidadão com o CPF 051.877.510-00
		E a senha do login desse cidadão é 123
		E esse login esteja cadastrado na base de dados
		E ele informe a senha 123
		Quando for feita a requisicao para efetuar o login
		Entao o código HTTP de resposta deve ser 200
		E o login deve ser feito corretamente

	Cenario: Tentativa de login de uma empresa pelo aplicativo
		Dado que existe um login para a empresa de CNPJ 61.805.858/0001-18
		E a senha do login dessa empresa é 123
		E esse login esteja cadastrado na base de dados
		E essa empresa informe a senha 123
		Quando for feita a requisicao para efetuar o login
		Entao o código HTTP de resposta deve ser 500
		E o login não deve ser permitido

	Cenario: Informe de senha incorreto
		Dado que existe um login para o cidadão com o CPF 051.877.510-00
		E a senha do login desse cidadão é 123
		E esse login esteja cadastrado na base de dados
		E ele informe a senha 234
		Quando for feita a requisicao para efetuar o login
		Entao o código HTTP de resposta deve ser 500
		E o login não deve ser permitido

