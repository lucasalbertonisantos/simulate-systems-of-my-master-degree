import 'package:flutter/material.dart';

class DialogoUsuarioSenhaIncorreto extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Usuário ou senha incorreta'),
      content: SingleChildScrollView(
        child: ListBody(
          children: <Widget>[
            Text('A senha ou o usuário não está correto.'),
            Text('Tente novamente,'),
            Text('Caso tenha esquecido, siga o procedimento de  esquecimento de senha.'),
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