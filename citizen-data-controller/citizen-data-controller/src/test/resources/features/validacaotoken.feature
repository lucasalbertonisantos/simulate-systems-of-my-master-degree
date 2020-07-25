 # language: pt
  @ValidacaoToken
Funcionalidade: Validação do Token

	Cenario: Usuário com acesso a funcionalidade
		Dado que o usuário de e-mail testes@email.com tenha um token válido
		Quando for feita a requisição para uma funcionalidade que ele possua acesso
		Entao o código HTTP de resposta deve ser 200
		E o corpo da resposta deve conter o que é esperado da funcionalidade
		
	Cenario: Usuário sem acesso a funcionalidade
		Dado que o usuário de e-mail testes2@email.com tenha um token válido
		Quando for feita a requisição para uma funcionalidade que ele não possua acesso
		Entao o código HTTP de resposta deve ser 403


