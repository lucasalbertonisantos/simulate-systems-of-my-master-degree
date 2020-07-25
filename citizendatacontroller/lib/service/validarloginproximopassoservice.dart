import 'package:citizendatacontroller/apiclient/cidadaoapiclient.dart';
import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/models/logintoken.dart';
import 'package:citizendatacontroller/screens/citizen/login/dialogousuariosenhaincorreto.dart';
import 'package:citizendatacontroller/screens/citizen/cidadao/liberaracessoempresa.dart';
import 'package:flutter/src/widgets/framework.dart';

import 'definirproximopassoservice.dart';

class ValidarLoginProximoPassoService extends DefinirProximoPassoService<LoginToken>{

  final LoginApiClient loginApiClient;
  final CidadaoApiClient cidadaoApiClient;
  ValidarLoginProximoPassoService(this.loginApiClient, this.cidadaoApiClient);

  @override
  Widget definir(LoginToken loginToken) {
    if (loginToken != null &&
        loginToken.tipo != null &&
        loginToken.token != null) {
      return LiberarAcessoEmpresa(loginApiClient, loginToken, cidadaoApiClient);
    } else {
      return DialogoUsuarioSenhaIncorreto();
    }
  }

}