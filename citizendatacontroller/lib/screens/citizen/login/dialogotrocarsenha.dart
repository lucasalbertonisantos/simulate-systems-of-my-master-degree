import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/models/logincheckstatus.dart';
import 'package:citizendatacontroller/models/logintoken.dart';
import 'package:citizendatacontroller/models/trocarsenhalogin.dart';
import 'package:citizendatacontroller/screens/citizen/login/dialogoerrotrocarsenha.dart';
import 'package:citizendatacontroller/screens/citizen/executarcomunicacaoapi.dart';
import 'package:citizendatacontroller/service/trocarsenhaproximopassoservice.dart';
import 'package:flutter/material.dart';

class DialogoTrocarSenha extends StatelessWidget {
  final TextEditingController senhaAtualController =
  TextEditingController();
  final TextEditingController novaSenhaController =
  TextEditingController();

  final LoginApiClient loginApiClient;
  final LoginToken loginToken;
  DialogoTrocarSenha(this.loginApiClient, this.loginToken);

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Trocar senha'),
      content: SingleChildScrollView(
        child: ListBody(
          children: <Widget>[
            Text(
                'Favor digitar a senha atual e a nova senha desejada.'),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: TextField(
                obscureText: true,
                style: TextStyle(
                  fontSize: 24.0,
                ),
                controller: senhaAtualController,
                decoration: InputDecoration(
                  labelText: 'Senha Atual',
                  hintText: '123OI996a',
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
                controller: novaSenhaController,
                decoration: InputDecoration(
                  labelText: 'Nova Senha',
                  hintText: '123OI996a',
                ),
              ),
            ),
          ],
        ),
      ),
      actions: <Widget>[
        FlatButton(
          child: Text('Confirmar'),
          onPressed: () {
            _trocarSenha(context);
          },
        ),
        FlatButton(
          child: Text('Cancelar'),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
      ],
    );
  }

  void _trocarSenha(BuildContext context) {
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      return ExecutarComunicacaoApi<LoginCheckStatus>(
        loginApiClient.passwordChange(TrocarSenhaLogin(loginToken, senhaAtualController.text, novaSenhaController.text)),
        DialogoErroTrocarSenha(),
        TrocarSenhaProximoPassoService(),
      );
    }));
  }
}
