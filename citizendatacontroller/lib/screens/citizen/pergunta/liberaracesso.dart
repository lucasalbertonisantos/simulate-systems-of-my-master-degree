import 'package:citizendatacontroller/apiclient/cidadaoapiclient.dart';
import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/screens/citizen/login/logincidadao.dart';
import 'package:flutter/material.dart';

const _tituloAppBar = 'APP meus dados seguros - Liberação de acesso';
const _textoBotaoEntendi = 'Entendi';

class LiberarAcesso extends StatelessWidget {

  final LoginApiClient loginApiClient;
  final CidadaoApiClient cidadaoApiClient;
  LiberarAcesso(this.loginApiClient, this.cidadaoApiClient);

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
                  'Todas as informações foram confirmadas!',
                  style: TextStyle(fontWeight: FontWeight.bold,
                      fontSize: 25,
                      color: Colors.black),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Text(
                  'Nesse momento pedimos que você acesse o email cadastrado para confirmação do acesso.',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Text(
                  'Após a confirmação, seu acesso será liberado através da área de login do cidadão.',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Text(
                  'O token de confirmação enviado para o e-mail ficará aguardando até 9 minutos, sendo assim é aconselhável não perder tempo e já confirmá-lo.',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              RaisedButton(
                child: Text(_textoBotaoEntendi),
                onPressed: () => _navegarLogin(context),
              ),
            ],
          ),
        ));
  }

  void _navegarLogin(BuildContext context) {
    Navigator.popUntil(context, ModalRoute.withName(Navigator.defaultRouteName));
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      return LoginCidadao(loginApiClient, cidadaoApiClient);
    }));
  }
}
