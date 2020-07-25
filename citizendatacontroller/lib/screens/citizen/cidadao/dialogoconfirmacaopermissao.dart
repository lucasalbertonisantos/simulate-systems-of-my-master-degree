import 'package:flutter/material.dart';

class DialogoConfirmacaoPermissao extends StatelessWidget {
  final String token;

  DialogoConfirmacaoPermissao(this.token);

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Token gerado '),
      content: SingleChildScrollView(
        child: ListBody(
          children: <Widget>[
            Text('Token: $token'),
            Text(
                'Para validar esse token é necessário confirmá-lo pelo e-mail.'),
            Text(
                'Após a confirmação é só informar na confirmação do cadastro na empresa informada.'),
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
