import 'dart:convert';

import 'package:citizendatacontroller/apiclient/apiconfiguration.dart';
import 'package:citizendatacontroller/apiclient/logginginterceptor.dart';
import 'package:citizendatacontroller/models/logintoken.dart';
import 'package:citizendatacontroller/models/permissao.dart';
import 'package:citizendatacontroller/models/solicitarliberacao.dart';
import 'package:citizendatacontroller/models/tokenliberacaoacesso.dart';
import 'package:http/http.dart';
import 'package:http_interceptor/http_client_with_interceptor.dart';

class CidadaoApiClient {

  final ApiConfiguration _configuration;

  CidadaoApiClient(this._configuration);

  Future<Permissao> solicitarLiberacaoDados(
      SolicitarLiberacao solicitarLiberacao) async {
    Client client =
        HttpClientWithInterceptor.build(interceptors: [LoggingInterceptor()]);
    final Map<String, dynamic> cidadaoMap = {
      'token': {
        'token': solicitarLiberacao.loginToken.token,
        'tipo': solicitarLiberacao.loginToken.tipo,
      },
      'cnpj': solicitarLiberacao.cnpj,
    };
    final Response response = await client.post(
      _configuration.getBaseUrl() + 'cidadaos/liberar-dados-empresa',
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(cidadaoMap),
    );
    if (response.statusCode == 200) {
      final dynamic = jsonDecode(response.body);
      return Permissao(
          dynamic['cnpj'],
          TokenLiberacaoAcesso(dynamic['tokenLiberacaoAcesso']['tokenAcesso'],
              dynamic['tokenLiberacaoAcesso']['dataExpiracaoToken']),
          LoginToken(
              dynamic['loginToken']['token'], dynamic['loginToken']['tipo']));
    } else {
      throw Exception('Login inválido!');
    }
  }
}
