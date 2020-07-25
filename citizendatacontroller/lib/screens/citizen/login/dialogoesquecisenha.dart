import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/models/logincheckstatus.dart';
import 'package:citizendatacontroller/models/recuperarsenhalogin.dart';
import 'package:citizendatacontroller/screens/citizen/executarcomunicacaoapi.dart';
import 'package:citizendatacontroller/service/recuperarsenhaproximopassoservice.dart';
import 'package:flutter/material.dart';

import 'dialogoprocesquecisenha.dart';

class DialogoEsqueciSenha extends StatelessWidget {
  final TextEditingController emailEsqeuciSenhaController =
      TextEditingController();
  final TextEditingController cpfEsqueciSenhaController =
      TextEditingController();

  final LoginApiClient loginApiClient;

  DialogoEsqueciSenha(this.loginApiClient);

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Esqueci minha senha'),
      content: SingleChildScrollView(
        child: ListBody(
          children: <Widget>[
            Text(
                'Com essa opção você receberá uma nova senha por e-mail, cancelando a anterior.'),
            Text('Você tem certeza que deseja seguir com o procedimento?'),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: TextField(
                style: TextStyle(
                  fontSize: 24.0,
                ),
                controller: emailEsqeuciSenhaController,
                decoration: InputDecoration(
                  labelText: 'Email',
                  hintText: 'exemplo@email.com',
                ),
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
                controller: cpfEsqueciSenhaController,
              ),
            ),
          ],
        ),
      ),
      actions: <Widget>[
        FlatButton(
          child: Text('Confirmar'),
          onPressed: () {
            _definirNovaSenha(context);
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

  void _definirNovaSenha(BuildContext context) {
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      return ExecutarComunicacaoApi<LoginCheckStatus>(
        loginApiClient.passwordRestore(RecuperarSenhaLogin(
            cpfEsqueciSenhaController.text, emailEsqeuciSenhaController.text)),
        DialogoProcEsqueciSenha(),
        RecuperarSenhaProximoPassoService(),
      );
    })).then((retorno) => Navigator.of(context).pop());
  }
}
