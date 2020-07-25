import 'package:flutter/material.dart';

class DialogoSucessoTrocarSenha extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Senha alterada com sucesso!'),
      content: SingleChildScrollView(
        child: ListBody(
          children: <Widget>[
            Text('A sua senha foi alterada. Por esse motivo você será redirecionado para tela inicial para fazer um novo login.'),
          ],
        ),
      ),
      actions: <Widget>[
        FlatButton(
          child: Text('Fechar'),
          onPressed: () {
            Navigator.popUntil(context, ModalRoute.withName(Navigator.defaultRouteName));
          },
        ),
      ],
    );
  }

}