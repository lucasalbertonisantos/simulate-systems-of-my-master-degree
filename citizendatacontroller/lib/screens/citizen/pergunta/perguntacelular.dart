import 'package:citizendatacontroller/apiclient/cidadaoapiclient.dart';
import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/apiclient/perguntaapiclient.dart';
import 'package:citizendatacontroller/models/pergunta.dart';
import 'package:citizendatacontroller/models/resposta.dart';
import 'package:citizendatacontroller/models/statusperguntaflow.dart';
import 'package:citizendatacontroller/screens/citizen/executarcomunicacaoapi.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/respostaerrada.dart';
import 'package:citizendatacontroller/service/perguntaproximopassoservice.dart';
import 'package:flutter/material.dart';

const _tituloAppBar = 'Pergunta';
const _textoBotaoEnviar = 'Enviar';

class PerguntaCelular extends StatelessWidget {
  final Pergunta pergunta;
  final LoginApiClient loginApiClient;
  final CidadaoApiClient cidadaoApiClient;
  final PerguntaApiClient perguntaApiClient;
  final String cpf;

  PerguntaCelular(this.pergunta, this.loginApiClient, this.cidadaoApiClient,
      this.perguntaApiClient, this.cpf);

  final TextEditingController ddiController = TextEditingController();
  final TextEditingController dddController = TextEditingController();
  final TextEditingController numeroController = TextEditingController();

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
                  'Qual seu celular para contato?',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              Row(
                children: <Widget>[
                  SizedBox(
                    width: 60,
                    child: TextField(
                      controller: ddiController,
                      style: TextStyle(fontSize: 24.0),
                      decoration: InputDecoration(
                        labelText: 'DDI',
                        hintText: '123',
                      ),
                      keyboardType: TextInputType.number,
                      maxLength: 3,
                    ),
                  ),
                  SizedBox(width: 20),
                  SizedBox(
                    width: 60,
                    child: TextField(
                      controller: dddController,
                      style: TextStyle(fontSize: 24.0),
                      decoration: InputDecoration(
                        labelText: 'DDD',
                        hintText: '99',
                      ),
                      keyboardType: TextInputType.number,
                      maxLength: 2,
                    ),
                  ),
                  SizedBox(width: 20),
                  Expanded(
                    child: TextField(
                      controller: numeroController,
                      style: TextStyle(fontSize: 24.0),
                      decoration: InputDecoration(
                        labelText: 'Número',
                        hintText: '999999999',
                      ),
                      keyboardType: TextInputType.number,
                      maxLength: 9,
                    ),
                  ),
                ],
              ),
              RaisedButton(
                child: Text(_textoBotaoEnviar),
                onPressed: () => _enviarResposta(context),
              ),
            ],
          ),
        ));
  }

  void _enviarResposta(BuildContext context) {
    Navigator.push(context, MaterialPageRoute(builder: (context) {
      String delimiter = '___';
      String resposta = ddiController.text + delimiter + dddController.text + delimiter + numeroController.text;
      return ExecutarComunicacaoApi<StatusPerguntaFlow>(
        perguntaApiClient.proximPergunta(
            cpf, Resposta(resposta, pergunta)),
        RespostaErrada(),
        PerguntaProximoPassoService(
            loginApiClient, cidadaoApiClient, perguntaApiClient, cpf),
      );
    }));
  }
}
