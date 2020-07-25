import 'package:citizendatacontroller/apiclient/cidadaoapiclient.dart';
import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/apiclient/perguntaapiclient.dart';
import 'package:citizendatacontroller/models/statusperguntaflow.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/liberaracesso.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/perguntacelular.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/perguntadatanascimento.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/perguntaemail.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/perguntaflow.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/perguntainformergs.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/perguntanome.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/perguntanomemae.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/perguntanomepai.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/perguntaquantosrgspossui.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/respostaerrada.dart';
import 'package:citizendatacontroller/service/definirproximopassoservice.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';

class PerguntaProximoPassoService
    extends DefinirProximoPassoService<StatusPerguntaFlow> {
  final LoginApiClient loginApiClient;
  final CidadaoApiClient cidadaoApiClient;
  final PerguntaApiClient perguntaApiClient;
  final String cpf;
  final int quantidadeRgs;

  PerguntaProximoPassoService(this.loginApiClient, this.cidadaoApiClient,
      this.perguntaApiClient, this.cpf, {this.quantidadeRgs});

  @override
  Widget definir(StatusPerguntaFlow retorno) {
    if(retorno.status == 'SUCESSO'){
      return LiberarAcesso(loginApiClient, cidadaoApiClient);
    } else if(retorno.pergunta.coluna == 'DATA_NASCIMENTO'){
      return PerguntaDataNascimento(
          retorno.pergunta, loginApiClient, cidadaoApiClient, perguntaApiClient,
          cpf);
    } else if(retorno.pergunta.coluna == 'NOME'){
      return PerguntaNome(
          retorno.pergunta, loginApiClient, cidadaoApiClient, perguntaApiClient,
          cpf);
    } else if(retorno.pergunta.coluna == 'NOME_MAE'){
      return PerguntaNomeMae(
          retorno.pergunta, loginApiClient, cidadaoApiClient, perguntaApiClient,
          cpf);
    } else if(retorno.pergunta.coluna == 'NOME_PAI'){
      return PerguntaNomePai(
          retorno.pergunta, loginApiClient, cidadaoApiClient, perguntaApiClient,
          cpf);
    } else if(retorno.pergunta.coluna == 'QUANTOS_RGS_POSSUI'){
      return PerguntaQuantosRgsPossui(
          retorno.pergunta, loginApiClient, cidadaoApiClient, perguntaApiClient,
          cpf);
    } else if(retorno.pergunta.coluna == 'QUAIS_RGS_UFS'){
      if(quantidadeRgs == null){
        return RespostaErrada();
      }
      List<TextEditingController> rgs = [];
      List<TextEditingController> ufs = [];
      for(int i=0; i<quantidadeRgs; i++){
        rgs.add(TextEditingController());
        ufs.add(TextEditingController());
      }
      return PerguntaInformaRgs(
          retorno.pergunta, loginApiClient, cidadaoApiClient, perguntaApiClient,
          cpf, rgs, ufs);
    } else if(retorno.pergunta.coluna == 'EMAIL'){
      return PerguntaEmail(
          retorno.pergunta, loginApiClient, cidadaoApiClient, perguntaApiClient,
          cpf);
    } else if(retorno.pergunta.coluna == 'CELULAR'){
      return PerguntaCelular(
          retorno.pergunta, loginApiClient, cidadaoApiClient, perguntaApiClient,
          cpf);
    }
    return PerguntaFlow(
        retorno.pergunta, loginApiClient, cidadaoApiClient, perguntaApiClient,
        cpf);
  }

}