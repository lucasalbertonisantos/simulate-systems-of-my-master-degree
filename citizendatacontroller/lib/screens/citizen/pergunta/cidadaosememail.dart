import 'package:citizendatacontroller/apiclient/cidadaoapiclient.dart';
import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/apiclient/perguntaapiclient.dart';
import 'package:citizendatacontroller/models/statusperguntaflow.dart';
import 'package:citizendatacontroller/screens/citizen/executarcomunicacaoapi.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/liberaracesso.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/respostaerrada.dart';
import 'package:citizendatacontroller/service/perguntaproximopassoservice.dart';
import 'package:flutter/material.dart';

const _tituloAppBar = 'APP meus dados seguros - Liberação de acesso';
const _textoBotaoEnviar = 'Enviar';

class CidadaoSemEmail extends StatelessWidget {
  final TextEditingController emailController = TextEditingController();

  final LoginApiClient loginApiClient;
  final CidadaoApiClient cidadaoApiClient;
  final PerguntaApiClient perguntaApiClient;
  final String cpf;

  CidadaoSemEmail(this.loginApiClient, this.cidadaoApiClient, this.perguntaApiClient, this.cpf);

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
                  'Precisamos de mais uma informação para confirmar suas respostas',
                  style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 25,
                      color: Colors.black),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(8),
                child: Text(
                  'Não conseguimos encontrar um e-mail em seu cadastro.',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(8),
                child: Text(
                  'Pedimos que informe um e-mail para que possamos confirmar as respostas.',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: TextField(
                  style: TextStyle(fontSize: 24.0),
                  controller: emailController,
                  decoration: InputDecoration(
                    labelText: 'Email',
                    hintText: 'email',
                  ),
                ),
              ),
              RaisedButton(
                child: Text(_textoBotaoEnviar),
                onPressed: () => _navegarLogin(context),
              ),
            ],
          ),
        ));
  }

  void _navegarLogin(BuildContext context) {
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      return ExecutarComunicacaoApi<StatusPerguntaFlow>(
        perguntaApiClient.finalizarComEmail(cpf, emailController.text),
        RespostaErrada(),
        PerguntaProximoPassoService(
            loginApiClient, cidadaoApiClient, perguntaApiClient, cpf),
      );
      return LiberarAcesso(loginApiClient, cidadaoApiClient);
    }));
  }
}
