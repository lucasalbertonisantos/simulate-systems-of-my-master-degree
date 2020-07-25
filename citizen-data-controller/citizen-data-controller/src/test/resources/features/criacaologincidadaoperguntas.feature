 # language: pt
  @CriacaoLoginCidadaoPerguntas
Funcionalidade: Testar a criação de login através de perguntas e respostas para o cidadão

	Cenario: Fluxo de perguntas com respostas corretas
		Dado que existe o cidadão com o CPF 051.877.510-00 cadastrado como cidadão, mas sem login
		E que esse cidadão interessado em criar o login tenha o nome de João
		E que esse cidadão interessado em criar o login tenha o e-mail testes@email.com
		E que a mãe desse cidadão interessado em criar o login tenha o nome de Joana
		E que o pai desse cidadão interessado em criar o login tenha o nome de José
		E que esse cidadão interessado em criar o login tenha os rgs
		| 78745879-8 |
		| 555-6      |
		E que esse cidadão interessado em criar o login tenha os celulares 
		| 55_11_987654425 |
		E que a data de nascimento desse cidadão interessado em criar o login seja 20/01/1980
		Quando for feita a solicitação do início das perguntas
		Entao o código HTTP de resposta deve ser 200
		E deve conter todas as 8 perguntas no corpo da resposta
		E deve ser executado o processo de resposta das 8 perguntas informando os dados corretamente 
		Quando for feita a resposta de todas as perguntas
		Entao o código HTTP de resposta deve ser 200
		E o cidadão deve receber um e-mail
		E o login deve ter sido criado
		E o login deve estar como inativo
		E a senha deve estar como gerada automaticamente
		E ele não deve conseguir logar sem antes confirmar o e-mail
		Quando o cidadão clicar no token do e-mail recebido
		Entao o login deve ser liberado

	Cenario: Fluxo de perguntas com uma resposta incorreta
		Dado que existe o cidadão com o CPF 051.877.510-00 cadastrado como cidadão, mas sem login
		E que esse cidadão interessado em criar o login tenha o nome de João
		E que esse cidadão interessado em criar o login tenha o e-mail testes@email.com
		E que a mãe desse cidadão interessado em criar o login tenha o nome de Joana
		E que o pai desse cidadão interessado em criar o login tenha o nome de José
		E que esse cidadão interessado em criar o login tenha os rgs
		| 78745879-8 |
		E que esse cidadão interessado em criar o login tenha os celulares 
		| 55_11_987654425 |
		E que a data de nascimento desse cidadão interessado em criar o login seja 20/01/1980
		Quando for feita a solicitação do início das perguntas
		Entao o código HTTP de resposta deve ser 200
		Entao deve conter todas as 8 perguntas no corpo da resposta
		Entao deve ser respondido a data de nascimento como 20/02/1980
		Entao deve ser executado o processo de resposta das outras 7 perguntas informando os dados corretamente 
		Quando for feita a resposta de todas as perguntas
		Entao o código HTTP de resposta deve ser 500
		E o cidadão não deve ser receber um e-mail
		E o login não deve ter sido criado

	Cenario: Fluxo de perguntas sem nenhum RG cadastrado
		Dado que existe o cidadão com o CPF 051.877.510-00 cadastrado como cidadão, mas sem login
		E que esse cidadão interessado em criar o login tenha o nome de João
		E que esse cidadão interessado em criar o login tenha o e-mail testes@email.com
		E que a mãe desse cidadão interessado em criar o login tenha o nome de Joana
		E que o pai desse cidadão interessado em criar o login tenha o nome de José
		E que esse cidadão interessado em criar o login tenha os celulares
		| 55_11_987654425 |
		E que a data de nascimento desse cidadão interessado em criar o login seja 20/01/1980
		Quando for feita a solicitação do início das perguntas
		Entao o código HTTP de resposta deve ser 200
		E deve conter todas as 6 perguntas no corpo da resposta
		E deve ser executado o processo de resposta das 6 perguntas informando os dados corretamente 
		Quando for feita a resposta de todas as perguntas
		Entao o código HTTP de resposta deve ser 200
		E o cidadão deve receber um e-mail
		E o login deve ter sido criado
		E o login deve estar como inativo
		E a senha deve estar como gerada automaticamente
		E ele não deve conseguir logar sem antes confirmar o e-mail
		Quando o cidadão clicar no token do e-mail recebido
		Entao o login deve ser liberado

