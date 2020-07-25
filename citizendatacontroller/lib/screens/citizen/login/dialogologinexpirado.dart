import 'package:flutter/material.dart';

class DialogoLoginExpirado extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Login Expirado'),
      content: SingleChildScrollView(
        child: ListBody(
          children: <Widget>[
            Text('O seu acesso esta expirado. Você será rediracionado para a tela inicial.'),
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