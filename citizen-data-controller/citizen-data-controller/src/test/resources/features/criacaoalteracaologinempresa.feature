 # language: pt
  @CriacaoAlteracaoLoginEmpresa
Funcionalidade: Testes de Criação e alteração de Login de Empresa

	Cenario: Cadastrar e Alterar login da empresa
		Dado uma empresa de CNPJ 34.154.345/0001-11
		E nome da empresa EMPRESA_1
		E que essa empresa possua cadastro no sistema 
		Quando for necessário o cadastro de login dessa empresa
		E esse login deva ter e-mail testes@email.com
		E esse login deva ter a senha 123
		E for feita a requisição para criação de login
		Entao o código HTTP de resposta deve ser 200
		E o login deve ter sido cadastrado com a mesma senha
		E o login deve possuir o mesmo e-mail
		E o ID desse login deve ter sido gerado
		Quando for necessária a alteração desse login da empresa
		E seja utilizado o ID desse login
		E esse login deva ter a senha 234 
		E esse login deva ter o e-mail alteracao@email.com
		Quando for enviada uma alteração para esse login
		Entao o código HTTP de resposta deve ser 200
		E a senha desse login deve ser a alterada
		E o e-mail desse login deve ser o alterado

	Cenario: Cadastrar e Alterar login da empresa com um CNPJ já existente para outro login
		Dado uma empresa de CNPJ 34.154.345/0001-11
		E nome da empresa EMPRESA_1
		E que essa empresa possua cadastro no sistema
		E que essa empresa possua cadastro de login com e-mail testes@email.com
		E for feita a requisição para criação de login
		Entao o código HTTP de resposta deve ser 500
		Dado uma empresa de CNPJ 46.670.785/0001-00
		E nome da empresa EMPRESA_2
		E que essa empresa possua cadastro no sistema
		E que essa empresa possua cadastro de login com e-mail testes_2@email.com
		E essa empresa deseja alterar o CNPJ para 34.154.345/0001-11
		E seja utilizado o ID desse login
		Quando for enviada uma alteração para esse login
		Entao o código HTTP de resposta deve ser 500

		

