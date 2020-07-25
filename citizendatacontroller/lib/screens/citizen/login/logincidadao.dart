import 'package:citizendatacontroller/apiclient/cidadaoapiclient.dart';
import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/models/login.dart';
import 'package:citizendatacontroller/models/logintoken.dart';
import 'package:citizendatacontroller/screens/citizen/login/dialogoesquecisenha.dart';
import 'package:citizendatacontroller/screens/citizen/executarcomunicacaoapi.dart';
import 'package:citizendatacontroller/service/validarloginproximopassoservice.dart';
import 'package:flutter/material.dart';

import 'dialogousuariosenhaincorreto.dart';

const _tituloAppBar = 'APP meus dados seguros';
const _textoBotaoEnviar = 'Enviar';

class LoginCidadao extends StatelessWidget {
  final TextEditingController usuarioController = TextEditingController();
  final TextEditingController senhaController = TextEditingController();
  final TextEditingController emailRecuperarController =
      TextEditingController();

  final LoginApiClient loginApiClient;
  final CidadaoApiClient cidadaoApiClient;

  LoginCidadao(this.loginApiClient, this.cidadaoApiClient);

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
                  'Digite seu email ou CPF e senha para se conectar ao sistema.',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: TextField(
                  style: TextStyle(fontSize: 24.0),
                  controller: usuarioController,
                  decoration: InputDecoration(
                    labelText: 'Email ou CPF',
                    hintText: 'email ou cpf',
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: TextField(
                  obscureText: true,
                  style: TextStyle(
                    fontSize: 24.0,
                  ),
                  controller: senhaController,
                  decoration: InputDecoration(
                    labelText: 'Senha',
                    hintText: '123OI996a',
                  ),
                ),
              ),
              InkWell(
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: <Widget>[
                      Text(
                        'Esqueci a senha',
                        style: TextStyle(
                            color: Colors.blue,
                            decoration: TextDecoration.underline),
                      ),
                    ],
                  ),
                  onTap: () => _esqueciMinhaSenha(context)),
              Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: RaisedButton(
                    child: Text(_textoBotaoEnviar),
                    onPressed: () => _logar(context),
                  )),
            ],
          ),
        ));
  }

  void _logar(BuildContext context) {
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      return ExecutarComunicacaoApi<LoginToken>(
        loginApiClient.login(Login(usuarioController.text, senhaController.text)),
        DialogoUsuarioSenhaIncorreto(),
        ValidarLoginProximoPassoService(loginApiClient, cidadaoApiClient),
      );
    }));
  }

  void _esqueciMinhaSenha(BuildContext context) {
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      return DialogoEsqueciSenha(loginApiClient);
    }));
  }
}
