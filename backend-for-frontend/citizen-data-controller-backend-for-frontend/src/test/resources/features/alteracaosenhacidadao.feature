 # language: pt
  @AlteracaoSenhaCidadao
Funcionalidade: Testar a Alteração de senha do cidadão 

	Cenario: Fluxo de alteração de senha concluído com sucesso
		Dado que exista um cidadão logado
		E sua senha seja 123
		E esse login esteja cadastrado na base de dados
		E ele informe como senha atual 123
		E ele informe como nova senha 234
		Quando for feita a requisição para o processo de alteração de senha
		Entao o código HTTP de resposta deve ser 200
		E o cidadão deve ter a senha alterada
		E o cidadão deve voltar para tela de login quando confirmar

	Cenario: Fluxo de alteração com senha atual divergente
		Dado que exista um cidadão logado
		E sua senha seja 123
		E esse login esteja cadastrado na base de dados
		E ele informe como senha atual 234
		E ele informe como nova senha 123
		Quando for feita a requisição para o processo de alteração de senha
		Entao o código HTTP de resposta deve ser 500
		E o cidadão não deve ter a senha alterada
