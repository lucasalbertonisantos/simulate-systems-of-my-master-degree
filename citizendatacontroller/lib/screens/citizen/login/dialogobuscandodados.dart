
import 'package:flutter/material.dart';

class DialogoBuscandoDados extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return SimpleDialog(
      title: const Text('Buscando os dados'),
      children: <Widget>[
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: Text(
            'Estamos tentando encontrar seus dados.',
            style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 25,
                color: Colors.black),
          ),
        ),
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: Text(
            'Certifique-se de que informou o CPF corretamente.',
            style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 25,
                color: Colors.black),
          ),
        ),
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: Text(
            'Caso tenha digitado corretamente, tente novamente mais tarde.',
            style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 25,
                color: Colors.black),
          ),
        ),
        RaisedButton(
          child: Text('Concordar'),
          onPressed: () => Navigator.pop(context, this),
        ),
      ],
    );
  }

}