 # language: pt
  @BuscaStatusLoginCpf
Funcionalidade: Testar o busca de status do login pelo CPF

	Cenario: Login já existe no cache do M1
		Dado que exista um status de login dentro do serviço de cache do M1 com o valor de status CREATED_LOGIN para o CPF 051.877.510-00 
		Quando for feita a requisição de busca de status passando o CPF informado
		Entao o código HTTP de resposta deve ser 200
		E o status retornado deve ser CREATED_LOGIN

	Cenario: Login não existe no cache do M1, porém existente na base de dados do M2
		Dado que não exista um status de login dentro do serviço de cache do M1 para o CPF 309.944.860-57
		E exista um login cadastrado no M2 com esse CPF
		Quando for feita a requisição de busca de status passando o CPF informado
		Entao o código HTTP de resposta deve ser 200
		E o status retornado deve ser CREATED_LOGIN

	Cenario: Login não existe no cache do M1, não existe na base de dados do M2, porém existe um cidadão com o CPF informado na base de dados do M2
		Dado que não exista um status de login dentro do serviço de cache do M1 para o CPF 807.452.330-66
		E não exista um login cadastrado no M2 com esse CPF
		E exista um cidadão cadastrado no M2 com esse CPF
		Quando for feita a requisição de busca de status passando o CPF informado
		Entao o código HTTP de resposta deve ser 200
		E o status retornado deve ser PENDING_LOGIN

	Cenario: Login não existe no cache do M1, não existe na base de dados do M2, não existe um cidadão com o CPF informado na base de dados do M2, porém existe um cidadão principal na base de dados do M3
		Dado que não exista um status de login dentro do serviço de cache do M1 para o CPF 477.852.520-50
		E não exista um login cadastrado no M2 com esse CPF
		E não exista um cidadão cadastrado no M2 com esse CPF 
		E exista um cidadão cadastrado no M3 como principal com esse CPF
		Quando for feita a requisição de busca de status passando o CPF informado
		Entao o código HTTP de resposta deve ser 200
		E o M2 deve persistir esse cidadão na base de dados
		E o status retornado deve ser PENDING_LOGIN

	Cenario: Login não existe no cache do M1, não existe na base de dados do M2, não existe um cidadão com o CPF informado na base de dados do M2, não existe um cidadão principal na base de dados do M3, porém existe um ou mais cidadão não principal na base de dados
		Dado que não exista um status de login dentro do serviço de cache do M1 para o CPF 568.458.810-73
		E não exista um login cadastrado no M2 com esse CPF
		E não exista um cidadão cadastrado no M2 com esse CPF 
		E não exista um cidadão cadastrado no M3 como principal esse CPF 
		E existam 3 cidadãos cadastrados no M3 para esse CPF vindos de diferentes órgãos
		E o cidadão do órgão de tipo FEDERAL tenha nome João José da Silva
		E o cidadão do órgão de tipo ESTADUAL tenha nome João José da Silva 2
		E o cidadão do órgão de tipo MUNICIPAL tenha nome João José da Silva 3
		E não tenha nome da mãe cadastrada para o órgão FEDERAL
		E a mãe se chame Maria Josefina da Silva 2 no órgão ESTADUAL
		E a mãe se chame Maria Josefina da Silva 3 no órgão MUNICIPAL
		E não exista o nome do pai cadastrado para o órgão FEDERAL
		E não exista o nome do pai cadastrado para o órgão ESTADUAL
		E o pai se chame João José da Silva no órgão MUNICIPAL
		Quando for feita a requisição de busca de status passando o CPF informado
		Entao o código HTTP de resposta deve ser 200
		E o M3 deve ter executado o algoritmo de unificação
		E o cidadão principal com o CPF deve ter o nome igual o passado pelo órgão FEDERAL
		E o cidadão principal com o CPF deve ter o nome da mãe igual o passado pelo órgão ESTADUAL
		E o cidadão principal com o CPF deve ter o nome do pai igual o passado pelo órgão MUNICIPAL
		E o M2 deve persistir esse cidadão na base de dados
		E o status retornado deve ser PENDING_LOGIN
		
	Cenario: Login não existe no cache do M1, não existe na base de dados do M2, não existe um cidadão com o CPF informado na base de dados do M2, não existe nenhum cidadão com esse CPF na base de dados do M3
		Dado que não exista um status de login dentro do serviço de cache do M1 para o CPF 791.963.380-08
		E não exista um login cadastrado no M2 com esse CPF
		E não exista um cidadão cadastrado no M2 com esse CPF
		E não exista um cidadão cadastrado no M3 como principal esse CPF
		E não exista nenhum cidadão cadastrado no M3
		E exista três sistemas órgãos dentro da solução integrada
		| 1 |  FEDERAL   |
		| 2 | ESTADUAL |
		| 3 | ESTADUAL |
		E o nome desse cidadão dentro do órgão 1 é João José da Silva Federal
		E o nome desse cidadão dentro do órgão 2 é João José da Silva Estadual
		E o RG desse cidadão dentro do órgão 2 é 5555
		E o órgão 3 não possua esse cidadão cadastrado
		Quando for feita a requisição de busca de status passando o CPF informado
		Entao o código HTTP de resposta deve ser 500
		E deve conter o código solicitação de busca
		Quando os órgãos existentes na solução integrada retornarem os dados de seus cidadãos
		Entao o M3 deve persistir cada cidadão retornado
		E os dados devem ser iguais aos existentes em cada órgão
		E a quantidade de cidadãos cadastradas devem ser iguais aos de cidadãos retornados
		E não deve existir nenhum cidadão como principal


