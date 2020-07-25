import 'package:citizendatacontroller/models/logintoken.dart';

class TrocarSenhaLogin{
  final LoginToken token;
  final String senhaAtual;
  final String novaSenha;
  TrocarSenhaLogin(this.token, this.senhaAtual, this.novaSenha);
}