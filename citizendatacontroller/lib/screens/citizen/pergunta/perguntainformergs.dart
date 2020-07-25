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

class PerguntaInformaRgs extends StatelessWidget {
  final Pergunta pergunta;
  final LoginApiClient loginApiClient;
  final CidadaoApiClient cidadaoApiClient;
  final PerguntaApiClient perguntaApiClient;
  final String cpf;
  final List<TextEditingController> rgsController;
  final List<TextEditingController> ufsRgsController;

  PerguntaInformaRgs(
      this.pergunta,
      this.loginApiClient,
      this.cidadaoApiClient,
      this.perguntaApiClient,
      this.cpf,
      this.rgsController,
      this.ufsRgsController);

  @override
  Widget build(BuildContext context) {
    List<Widget> rows = _gerarList(context);
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
                  'Quais são os RGs?',
                  style: TextStyle(fontSize: 25, color: Colors.grey),
                ),
              ),
              for(var item in rows ) item,
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
      String resposta = '';
      for(int i=0; i<rgsController.length; i++){
        resposta += (rgsController[i].text + '__'+ufsRgsController[i].text);
        if(i!=rgsController.length-1){
          resposta += '___';
        }
      }
      return ExecutarComunicacaoApi<StatusPerguntaFlow>(
        perguntaApiClient.proximPergunta(
            cpf, Resposta(resposta, pergunta)),
        RespostaErrada(),
        PerguntaProximoPassoService(
            loginApiClient, cidadaoApiClient, perguntaApiClient, cpf),
      );
    }));
  }

  List<Widget> _gerarList(BuildContext context) {
    List<Widget> list = new List<Widget>();
    for(var i = 0; i < rgsController.length; i++){
      list.add(Row(
        children: <Widget>[
          SizedBox(
            width: 250,
            child: TextField(
              controller: rgsController[i],
              style: TextStyle(fontSize: 24.0),
              decoration: InputDecoration(
                labelText: 'Numero RG',
                hintText: '99999999999',
              ),
              maxLength: 10,
            ),
          ),
          SizedBox(width: 50),
          Expanded(
            child: TextField(
              controller: ufsRgsController[i],
              style: TextStyle(fontSize: 24.0),
              decoration: InputDecoration(
                labelText: 'UF RG',
                hintText: 'AC',
              ),
              maxLength: 2,
            ),
          ),
        ],
      ));
    }
    return list;
  }
}
