import 'package:citizendatacontroller/apiclient/cidadaoapiclient.dart';
import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/models/logintoken.dart';
import 'package:citizendatacontroller/models/permissao.dart';
import 'package:citizendatacontroller/models/solicitarliberacao.dart';
import 'package:citizendatacontroller/screens/citizen/cidadao/dialogoerroliberaracessoempresa.dart';
import 'package:citizendatacontroller/screens/citizen/login/dialogotrocarsenha.dart';
import 'package:citizendatacontroller/screens/citizen/executarcomunicacaoapi.dart';
import 'package:citizendatacontroller/service/liberaracessoempresaproximopassoservice.dart';
import 'package:flutter/material.dart';

import 'dialogoconfirmacaopermissao.dart';

const _tituloAppBar = 'APP meus dados seguros';
const _textoBotaoEnviar = 'Enviar';

class LiberarAcessoEmpresa extends StatelessWidget {
  final TextEditingController cnpjController = TextEditingController();
  final TextEditingController senhaController = TextEditingController();
  final TextEditingController emailRecuperarController =
      TextEditingController();

  final LoginApiClient loginApiClient;
  final LoginToken loginToken;
  final CidadaoApiClient cidadaoApiClient;

  LiberarAcessoEmpresa(this.loginApiClient, this.loginToken, this.cidadaoApiClient);

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
                  'Digite o cnpj da empresa que deseja liberar o acesso de seus dados.',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: TextField(
                  style: TextStyle(fontSize: 24.0),
                  controller: cnpjController,
                  decoration: InputDecoration(
                    labelText: 'CNPJ da empresa',
                    hintText: '12345678000194',
                  ),
                  keyboardType: TextInputType.number,
                  maxLength: 14,
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: RaisedButton(
                  child: Text(_textoBotaoEnviar),
                  onPressed: () => _liberarAcesso(context),
                ),
              ),
              InkWell(
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: <Widget>[
                      Text(
                        'Deseja trocar sua senha? Clique aqui.',
                        style: TextStyle(
                          color: Colors.blue,
                          decoration: TextDecoration.underline,
                          fontSize: 22,
                        ),
                      ),
                    ],
                  ),
                  onTap: () => _trocarSenha(context)),
            ],
          ),
        ));
  }

  void _trocarSenha(BuildContext context) {
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      return DialogoTrocarSenha(loginApiClient, loginToken);
    }));
  }

  void _liberarAcesso(BuildContext context) {
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      return ExecutarComunicacaoApi<Permissao>(
        cidadaoApiClient.solicitarLiberacaoDados(SolicitarLiberacao(loginToken, cnpjController.text)),
        DialogoErroLiberarAcessoEmpresa(),
        LiberarAcessoEmpresaProximoPassoService(),
      );
    }));
  }
}
