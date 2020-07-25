
import 'package:citizendatacontroller/models/loginstatusenum.dart';

class LoginCheckStatus {

  final String cpf;
  final LoginStatusEnum status;

  LoginCheckStatus(this.cpf, this.status);

  @override
  String toString() {
    return '{\"cps\":$cpf, \"status\":$status}';
  }

}