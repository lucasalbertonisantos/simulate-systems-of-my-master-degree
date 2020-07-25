import 'package:citizendatacontroller/models/logincheckstatus.dart';
import 'package:citizendatacontroller/screens/citizen/login/dialogoerrotrocarsenha.dart';
import 'package:citizendatacontroller/screens/citizen/login/dialogosucessotrocarsenha.dart';
import 'package:citizendatacontroller/service/definirproximopassoservice.dart';
import 'package:flutter/src/widgets/framework.dart';

class TrocarSenhaProximoPassoService extends DefinirProximoPassoService<LoginCheckStatus>{
  @override
  Widget definir(LoginCheckStatus retorno) {
    if(retorno != null) {
      return DialogoSucessoTrocarSenha();
    }else{
      return DialogoErroTrocarSenha();
    }
  }

}