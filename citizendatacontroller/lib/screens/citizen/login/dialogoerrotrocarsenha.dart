import 'package:flutter/material.dart';

class DialogoErroTrocarSenha extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Erro ao alterar a senha'),
      content: SingleChildScrollView(
        child: ListBody(
          children: <Widget>[
            Text('A senha esta incorreta, favor tente novamente.'),
          ],
        ),
      ),
      actions: <Widget>[
        FlatButton(
          child: Text('Fechar'),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
      ],
    );
  }

}