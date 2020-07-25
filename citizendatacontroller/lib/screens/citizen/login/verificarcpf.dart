import 'package:citizendatacontroller/apiclient/cidadaoapiclient.dart';
import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/apiclient/perguntaapiclient.dart';
import 'package:citizendatacontroller/models/logincheckstatus.dart';
import 'package:citizendatacontroller/screens/citizen/executarcomunicacaoapi.dart';
import 'package:citizendatacontroller/service/checkstatusproximopassoservice.dart';
import 'package:flutter/material.dart';

import 'dialogobuscandodados.dart';

const _tituloAppBar = 'APP meus dados seguros';
const _textoBotaoEnviar = 'Enviar';

class VerificarCpf extends StatelessWidget {
  final TextEditingController cpfController = TextEditingController();

  final LoginApiClient loginApiClient;
  final CidadaoApiClient cidadaoApiClient;
  final PerguntaApiClient perguntaApiClient;

  VerificarCpf(this.loginApiClient, this.cidadaoApiClient, this.perguntaApiClient);

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
                  'Bem-vindo!',
                  style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 25,
                      color: Colors.black),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Text(
                  'Esse é o aplicativo de controle de dados do cidadão.',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Text(
                  'Para iniciar precisamos que informe o seu CPF para que possamos encontrar seus dados e identificar se possuí cadastro.',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: TextField(
                  style: TextStyle(fontSize: 24.0),
                  decoration: InputDecoration(
                    labelText: 'CPF',
                    hintText: '99999999999',
                  ),
                  keyboardType: TextInputType.number,
                  maxLength: 11,
                  controller: cpfController,
                ),
              ),
              RaisedButton(
                child: Text(_textoBotaoEnviar),
                onPressed: () => _checarCpf(context),
              ),
            ],
          ),
        ));
  }

  void _checarCpf(BuildContext context) {
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      return ExecutarComunicacaoApi<LoginCheckStatus>(
        loginApiClient.checkStatusLogin(cpfController.text),
        DialogoBuscandoDados(),
        CheckStatusProximoPassoService(loginApiClient, cidadaoApiClient, perguntaApiClient, cpfController.text),
      );
    }));
  }
}
