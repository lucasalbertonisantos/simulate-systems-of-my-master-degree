 # language: pt
  @ServicoAutenticacao
Funcionalidade: Testar o serviço de autenticacao

	Cenario: Sucesso no retorno do token
		Dado que exista um login cadastrado cujo cpf é 12345678910
		E o e-mail é teste@email.com
		E a senha é 123
		Quando for feita a requisição da autenticação passando o cpf 12345678910 
		E utilizando a senha 123
		Entao o código HTTP de resposta deve ser 200
		E o tipo do token deve ser Bearer
		E o token deve estar preenchido no formato JWT
		
	Cenario: Usuário ou senha inválido
		Dado que exista um login cadastrado cujo cpf é 12345678910
		E o e-mail é teste@email.com
		E a senha é 123
		Quando for feita a requisição da autenticação passando o cpf 12345678910
		E utilizando a senha 856
		Entao o código HTTP de resposta deve ser 400

