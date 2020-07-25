import 'package:flutter/material.dart';

class DialogoErroLiberarAcessoEmpresa extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Erro ao liberar o acesso para empresa'),
      content: SingleChildScrollView(
        child: ListBody(
          children: <Widget>[
            Text('Aconteceu algum erro no momento da liberação. Tente novamente mais tarde.'),
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