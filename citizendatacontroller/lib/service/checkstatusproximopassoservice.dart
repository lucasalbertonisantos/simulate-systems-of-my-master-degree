import 'package:citizendatacontroller/apiclient/cidadaoapiclient.dart';
import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/apiclient/perguntaapiclient.dart';
import 'package:citizendatacontroller/models/logincheckstatus.dart';
import 'package:citizendatacontroller/models/loginstatusenum.dart';
import 'package:citizendatacontroller/models/statusperguntaflow.dart';
import 'package:citizendatacontroller/screens/citizen/executarcomunicacaoapi.dart';
import 'package:citizendatacontroller/screens/citizen/login/dialogobuscandodados.dart';
import 'package:citizendatacontroller/screens/citizen/login/logincidadao.dart';
import 'package:citizendatacontroller/service/definirproximopassoservice.dart';
import 'package:citizendatacontroller/service/perguntaproximopassoservice.dart';
import 'package:flutter/src/widgets/framework.dart';

class CheckStatusProximoPassoService
    extends DefinirProximoPassoService<LoginCheckStatus> {
  final LoginApiClient loginApiClient;
  final CidadaoApiClient cidadaoApiClient;
  final PerguntaApiClient perguntaApiClient;
  final String cpf;

  CheckStatusProximoPassoService(this.loginApiClient, this.cidadaoApiClient, this.perguntaApiClient, this.cpf);

  @override
  Widget definir(LoginCheckStatus checkStatus) {
    if (checkStatus == null) {
      return null;
    }
    switch (checkStatus.status) {
      case LoginStatusEnum.CREATED_LOGIN:
        return LoginCidadao(loginApiClient, cidadaoApiClient);
        break;
      case LoginStatusEnum.SEARCHING_DATA:
        return DialogoBuscandoDados();
        break;
      case LoginStatusEnum.PENDING_LOGIN:
        return ExecutarComunicacaoApi<StatusPerguntaFlow>(
          perguntaApiClient.iniciarPerguntas(cpf),
          DialogoBuscandoDados(),
          PerguntaProximoPassoService(loginApiClient, cidadaoApiClient, perguntaApiClient, cpf),
        );
        break;
    }
  }
}
