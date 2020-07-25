import 'package:citizendatacontroller/models/logincheckstatus.dart';
import 'package:citizendatacontroller/screens/citizen/login/dialogoprocesquecisenha.dart';
import 'package:citizendatacontroller/service/definirproximopassoservice.dart';
import 'package:flutter/src/widgets/framework.dart';

class RecuperarSenhaProximoPassoService extends DefinirProximoPassoService<LoginCheckStatus>{
  @override
  Widget definir(LoginCheckStatus retorno) {
    return DialogoProcEsqueciSenha();
  }

}