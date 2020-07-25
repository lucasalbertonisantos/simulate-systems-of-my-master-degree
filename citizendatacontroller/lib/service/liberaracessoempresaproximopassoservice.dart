import 'package:citizendatacontroller/models/permissao.dart';
import 'package:citizendatacontroller/screens/citizen/cidadao/dialogoconfirmacaopermissao.dart';
import 'package:citizendatacontroller/screens/citizen/cidadao/dialogoerroliberaracessoempresa.dart';
import 'package:citizendatacontroller/service/definirproximopassoservice.dart';
import 'package:flutter/src/widgets/framework.dart';

class LiberarAcessoEmpresaProximoPassoService extends DefinirProximoPassoService<Permissao>{
  @override
  Widget definir(Permissao permissao) {
    if(permissao != null && permissao.loginToken != null && permissao.loginToken.token != null) {
      return DialogoConfirmacaoPermissao(permissao.tokenLiberacaoAcesso.tokenAcesso);
    }else{
      return DialogoErroLiberarAcessoEmpresa();
    }
  }

}