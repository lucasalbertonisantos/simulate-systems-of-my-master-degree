
import 'package:citizendatacontroller/models/tokenliberacaoacesso.dart';

import 'logintoken.dart';

class Permissao{
  final String cnpj;
  final TokenLiberacaoAcesso tokenLiberacaoAcesso;
  final LoginToken loginToken;

  Permissao(this.cnpj, this.tokenLiberacaoAcesso, this.loginToken);
}