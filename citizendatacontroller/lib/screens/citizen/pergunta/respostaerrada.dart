import 'package:citizendatacontroller/screens/citizen/login/verificarcpf.dart';
import 'package:flutter/material.dart';

const _tituloAppBar = 'Respostas erradas';
const _textoBotaoEnviar = 'Retornar a tela inicial';

class RespostaErrada extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text(_tituloAppBar),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: <Widget>[
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Text(
                  'Infelizmente identificamos que uma ou mais respostas estão divergindo de nossas informações.',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Text(
                  'Pedimos que responda as perguntas com atenção para que possamos liberar o acesso.',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              RaisedButton(
                child: Text(_textoBotaoEnviar),
                onPressed: () => _checarCpf(context),
              ),
            ],
          ),
        ));
  }

  void _checarCpf(BuildContext context) {
    Navigator.popUntil(context, ModalRoute.withName(Navigator.defaultRouteName));
  }
}