import 'package:flutter/material.dart';

class DialogoProcEsqueciSenha extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Procedimento executado'),
      content: SingleChildScrollView(
        child: ListBody(
          children: <Widget>[
            Text(
                'Caso o e-mail digitado esteja correto, você deverá receber um e-mail nos próximos minutos.')
          ],
        ),
      ),
      actions: <Widget>[
        FlatButton(
          child: Text('Aceitar'),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
      ],
    );
  }
}
