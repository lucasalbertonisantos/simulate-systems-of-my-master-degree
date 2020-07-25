import 'package:citizendatacontroller/apiclient/apiconfiguration.dart';
import 'package:citizendatacontroller/apiclient/cidadaoapiclient.dart';
import 'package:citizendatacontroller/apiclient/loginapiclient.dart';
import 'package:citizendatacontroller/apiclient/perguntaapiclient.dart';
import 'package:citizendatacontroller/apiclient/logginginterceptor.dart';
import 'package:citizendatacontroller/models/pergunta.dart';
import 'package:citizendatacontroller/screens/citizen/login/verificarcpf.dart';
import 'package:citizendatacontroller/screens/citizen/pergunta/perguntadatanascimento.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(CitizenDataControllerApp());
}

class CitizenDataControllerApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    ApiConfiguration apiConfiguration = ApiConfiguration();
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: VerificarCpf(LoginApiClient(apiConfiguration), CidadaoApiClient(apiConfiguration), PerguntaApiClient(apiConfiguration))
    );
  }
}