 # language: pt
  @RecuperacaoSenhaCidadao
Funcionalidade: Testar a Recuperação de senha do cidadão

	Cenario: Recuperação de senha realizado com sucesso
		Dado que existe um login para o cidadão com o CPF 051.877.510-00
		E esse login tenha e-mail teste@email.com
		E esse login esteja cadastrado na base de dados
		E ele informe o e-mail teste@email.com
		E ele informe o CPF 051.877.510-00
		Quando for feita a requisição para o processo de recuperação de senha
		Entao o código HTTP de resposta deve ser 200
		E o login deve ter a senha alterada
		E o login deve ficar inativo
		E o login deve ficar como geração de senha automático
		E o cidadão deve receber um e-mail de confirmação
		Quando o cidadão clicar no token que recebeu por e-mail
		Então o login deve ser liberado
		E o cidadão poderá logar utilizando a senha recebida

	Cenario: Recuperação de senha com e-mail divergente
		Dado que existe um login para o cidadão com o CPF 051.877.510-00
		E esse login tenha e-mail teste@email.com 
		E esse login esteja cadastrado na base de dados
		E ele informe o e-mail testando@email.com
		E ele informe o CPF 051.877.510-00
		Quando for feita a requisição para o processo de recuperação de senha
		Entao o código HTTP de resposta deve ser 500
		E o login não deve ter a senha alterada
		E o login não deve ficar inativo
		E o login não deve ficar como geração de senha automático
		E o cidadão não deve receber um e-mail de confirmação

	Cenario: Recuperação de senha com CPF divergente
		Dado que existe um login para o cidadão com o CPF 051.877.510-00
		E esse login tenha e-mail teste@email.com 
		E esse login esteja cadastrado na base de dados
		E ele informe o e-mail teste@email.com
		E ele informe o CPF 369.848.820-52
		Quando for feita a requisição para o processo de recuperação de senha
		Entao o código HTTP de resposta deve ser 500
		E o login não deve ter a senha alterada
		E o login não deve ficar inativo
		E o login não deve ficar como geração de senha automático
		E o cidadão não deve receber um e-mail de confirmação


