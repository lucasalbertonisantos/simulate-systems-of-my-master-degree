class Login{
  final String usuario;
  final String senha;

  Login(this.usuario, this.senha);

  @override
  String toString() {
    return 'Pergunta{_usuario: $usuario, _senha: $senha}';
  }
}