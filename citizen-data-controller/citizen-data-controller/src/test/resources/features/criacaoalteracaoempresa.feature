 # language: pt
  @CriacaoAlteracaoEmpresa
Funcionalidade: Testes de Criação e alteração de Empresa

	Cenario: Cadastro de empresa
		Dado uma empresa de CNPJ 34.154.345/0001-11
		E nome da empresa EMPRESA_1
		Quando for feita a requisição para o cadastro dessa empresa 
		Entao o código HTTP de resposta deve ser 200
		E a empresa deve ter as mesmas informações passadas
		E o ID de cadastro dessa empresa deve ter sido gerado

	Cenario: Alteração de Empresa
		Dado uma empresa de CNPJ 34.154.345/0001-11
		E nome da empresa EMPRESA_1
		E que essa empresa possua cadastro no sistema
		Quando for necessário alterar o nome da empresa para EMPRESA_2
		E CNPJ dessa empresa para 48.154.630/0001-47
		E seja utilizado o ID de cadastro dessa empresa
		E for feita a requisição para alteração dessa empresa
		Entao o código HTTP de resposta deve ser 200
		E a empresa deve ter nome de EMPRESA_2
		E o CNPJ dessa empresa deve ser 48.154.630/0001-47

