import 'package:citizendatacontroller/models/pergunta.dart';
import 'package:citizendatacontroller/models/resposta.dart';
import 'package:citizendatacontroller/models/statusperguntaflow.dart';
import 'dart:convert';

import 'package:citizendatacontroller/apiclient/logginginterceptor.dart';
import 'package:http/http.dart';
import 'package:http_interceptor/http_client_with_interceptor.dart';

import 'apiconfiguration.dart';

class PerguntaApiClient {

  final ApiConfiguration _configuration;

  PerguntaApiClient(this._configuration);

  Future<StatusPerguntaFlow> iniciarPerguntas(String cpf) async {
    Client client =
        HttpClientWithInterceptor.build(interceptors: [LoggingInterceptor()]);
    final Response response = await client.post(
      _configuration.getBaseUrl() + 'perguntas?cpf=$cpf',
      headers: {'Content-Type': 'application/json'},
    );
    if (response.statusCode == 200) {
      final dynamic = jsonDecode(response.body);
      return StatusPerguntaFlow(
        Pergunta(
          dynamic['pergunta']['id'],
          dynamic['pergunta']['pergunta'],
          dynamic['pergunta']['ordem'],
          dynamic['pergunta']['coluna'],
        ),
        dynamic['status'],
      );
    } else {
      throw Exception('Login inválido!');
    }
  }

  Future<StatusPerguntaFlow> proximPergunta(
      String cpf, Resposta resposta) async {
    Client client =
        HttpClientWithInterceptor.build(interceptors: [LoggingInterceptor()]);
    final Map<String, dynamic> respostaMap = {
      'resposta': resposta.resposta,
      'pergunta': {
        'id': resposta.pergunta.id,
        'pergunta': resposta.pergunta.pergunta,
        'ordem': resposta.pergunta.ordem,
        'coluna': resposta.pergunta.coluna,
      },
    };
    final Response response = await client.put(
      _configuration.getBaseUrl() + 'perguntas?cpf=$cpf',
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(respostaMap),
    );
    if (response.statusCode == 200) {
      final dynamic = jsonDecode(response.body);
      return StatusPerguntaFlow(
        Pergunta(
          dynamic['pergunta']['id'],
          dynamic['pergunta']['pergunta'],
          dynamic['pergunta']['ordem'],
          dynamic['pergunta']['coluna'],
        ),
        dynamic['status'],
      );
    } else {
      throw Exception('Login inválido!');
    }
  }

  Future<StatusPerguntaFlow> finalizarComEmail(String cpf, String email) async {
    Client client =
        HttpClientWithInterceptor.build(interceptors: [LoggingInterceptor()]);
    final Response response = await client.put(
      _configuration.getBaseUrl() + 'perguntas/email?cpf=$cpf&email=$email',
      headers: {'Content-Type': 'application/json'},
    );
    if (response.statusCode == 200) {
      final dynamic = jsonDecode(response.body);
      return StatusPerguntaFlow(
        Pergunta(
          dynamic['pergunta']['id'],
          dynamic['pergunta']['pergunta'],
          dynamic['pergunta']['ordem'],
          dynamic['pergunta']['coluna'],
        ),
        dynamic['status'],
      );
    } else {
      throw Exception('Login inválido!');
    }
  }
}
