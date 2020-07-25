import 'package:citizendatacontroller/apiclient/cidadaoapiclient.dart';
import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/apiclient/perguntaapiclient.dart';
import 'package:citizendatacontroller/models/pergunta.dart';
import 'package:citizendatacontroller/models/resposta.dart';
import 'package:citizendatacontroller/models/statusperguntaflow.dart';
import 'package:citizendatacontroller/screens/citizen/executarcomunicacaoapi.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/respostaerrada.dart';
import 'package:citizendatacontroller/service/perguntaproximopassoservice.dart';
import 'package:flutter/material.dart';

const _tituloAppBar = 'Pergunta';
const _textoBotaoEnviar = 'Enviar';

class PerguntaNomeMae extends StatelessWidget {
  final Pergunta pergunta;
  final LoginApiClient loginApiClient;
  final CidadaoApiClient cidadaoApiClient;
  final PerguntaApiClient perguntaApiClient;
  final String cpf;

  PerguntaNomeMae(this.pergunta, this.loginApiClient, this.cidadaoApiClient,
      this.perguntaApiClient, this.cpf);

  final TextEditingController respostaController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text(_tituloAppBar),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: <Widget>[
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Text(
                  'Qual é o nome da sua mãe?',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: TextField(
                  style: TextStyle(fontSize: 24.0),
                  controller: respostaController,
                  decoration: InputDecoration(
                    labelText: 'Digite o nome da mãe completo',
                  ),
                ),
              ),
              RaisedButton(
                child: Text(_textoBotaoEnviar),
                onPressed: () => _enviarResposta(context),
              ),
            ],
          ),
        ));
  }

  void _enviarResposta(BuildContext context) {
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      return ExecutarComunicacaoApi<StatusPerguntaFlow>(
        perguntaApiClient.proximPergunta(cpf, Resposta(respostaController.text, pergunta)),
        RespostaErrada(),
        PerguntaProximoPassoService(
            loginApiClient, cidadaoApiClient, perguntaApiClient, cpf),
      );
    }));
  }
}
