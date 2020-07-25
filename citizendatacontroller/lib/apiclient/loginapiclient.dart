import 'dart:convert';

import 'package:citizendatacontroller/apiclient/apiconfiguration.dart';
import 'package:citizendatacontroller/apiclient/logginginterceptor.dart';
import 'package:citizendatacontroller/models/login.dart';
import 'package:citizendatacontroller/models/logincheckstatus.dart';
import 'package:citizendatacontroller/models/loginstatusenum.dart';
import 'package:citizendatacontroller/models/logintoken.dart';
import 'package:citizendatacontroller/models/recuperarsenhalogin.dart';
import 'package:citizendatacontroller/models/trocarsenhalogin.dart';
import 'package:flutter/cupertino.dart';
import 'package:http/http.dart';
import 'package:http_interceptor/http_client_with_interceptor.dart';

class LoginApiClient{

  ApiConfiguration _configuration;

  LoginApiClient(this._configuration);

  Future<LoginCheckStatus> checkStatusLogin(String cpf) async {
    Client client = HttpClientWithInterceptor.build(
        interceptors: [LoggingInterceptor()]);
    final Response response = await client.get(
        _configuration.getBaseUrl() + 'login?cpf=$cpf');
    if (response.statusCode == 200) {
      final dynamic = jsonDecode(response.body);
      return LoginCheckStatus(dynamic['cpf'], _statusDefine(dynamic['status'].toString()));
    } else {
      throw Exception('Login não encontrado para o cpf informado!');
    }
  }

  Future<LoginToken> login(Login login) async {
    Client client = HttpClientWithInterceptor.build(
        interceptors: [LoggingInterceptor()]);
    final Map<String, dynamic> loginMap = {
      'usuario': login.usuario,
      'senha': login.senha
    };
    final Response response = await client.post(
      _configuration.getBaseUrl() + 'login', headers: {
      'Content-Type': 'application/json'
    }, body: jsonEncode(loginMap),);
    if (response.statusCode == 200) {
      final dynamic = jsonDecode(response.body);
      return LoginToken(dynamic['token'], dynamic['tipo']);
    } else {
      throw Exception('Login inválido!');
    }
  }

  Future<LoginCheckStatus> passwordRestore(RecuperarSenhaLogin recuperarSenhaLogin) async {
    Client client = HttpClientWithInterceptor.build(
        interceptors: [LoggingInterceptor()]);
    final Map<String, dynamic> loginMap = {
      'cpf': recuperarSenhaLogin.cpf,
      'email': recuperarSenhaLogin.email
    };

    final Response response = await client.put(
      _configuration.getBaseUrl() + 'login/recuperar-senha', headers: {
      'Content-Type': 'application/json'
    }, body: jsonEncode(loginMap),);
    if (response.statusCode == 200) {
      final dynamic = jsonDecode(response.body);
      return LoginCheckStatus(dynamic['cpf'], _statusDefine(dynamic['status'].toString()));
    } else {
      throw Exception('Login inválido!');
    }
  }

  Future<LoginCheckStatus> passwordChange(TrocarSenhaLogin trocarSenhaLogin) async {
    Client client = HttpClientWithInterceptor.build(
        interceptors: [LoggingInterceptor()]);
    final Map<String, dynamic> loginMap = {
      'token': {
        'token': trocarSenhaLogin.token.token,
        'tipo': trocarSenhaLogin.token.tipo
      },
      'senhaAtual': trocarSenhaLogin.senhaAtual,
      'novaSenha': trocarSenhaLogin.novaSenha,
    };

    final Response response = await client.put(
      _configuration.getBaseUrl() + 'login/trocar-senha', headers: {
      'Content-Type': 'application/json'
    }, body: jsonEncode(loginMap),);
    if (response.statusCode == 200) {
      final dynamic = jsonDecode(response.body);
      return LoginCheckStatus(dynamic['cpf'], _statusDefine(dynamic['status'].toString()));
    } else {
      throw Exception('Login inválido!');
    }
  }

  LoginStatusEnum _statusDefine(String status){
    if(status != null) {
      if (LoginStatusEnum.CREATED_LOGIN.toString().split('.').last == status) {
        return LoginStatusEnum.CREATED_LOGIN;
      } else if (LoginStatusEnum.PENDING_LOGIN.toString().split('.').last == status) {
        return LoginStatusEnum.PENDING_LOGIN;
      }
    }
    return LoginStatusEnum.SEARCHING_DATA;
  }
}