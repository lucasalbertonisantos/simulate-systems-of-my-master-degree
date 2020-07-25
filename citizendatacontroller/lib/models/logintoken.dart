
class LoginToken{
  final String token;
  final String tipo;

  LoginToken(this.token, this.tipo);

  @override
  String toString() {
    return '{\"token\":\"$token\", \"tipo\":\"$tipo\"}';
  }
}