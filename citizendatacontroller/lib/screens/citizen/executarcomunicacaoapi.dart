import 'package:citizendatacontroller/service/definirproximopassoservice.dart';
import 'package:flutter/material.dart';

import 'carregando.dart';

class ExecutarComunicacaoApi<T> extends StatelessWidget {
  final Future<T> execucao;
  final Widget errorPage;
  final DefinirProximoPassoService definirProximoPassoService;

  ExecutarComunicacaoApi(
    this.execucao,
    this.errorPage,
    this.definirProximoPassoService,
  );

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<T>(
      future: execucao,
      builder: (context, snapshot) {
        if (snapshot.hasError) {
          return errorPage;
        }
        switch (snapshot.connectionState) {
          case ConnectionState.none:
            break;
          case ConnectionState.waiting:
            return Carregando();
            break;
          case ConnectionState.active:
            break;
          case ConnectionState.done:
            return definirProximoPassoService.definir(snapshot.data);
            break;
        }
        return Text('Erro desconhecido');
      },
    );
  }
}
