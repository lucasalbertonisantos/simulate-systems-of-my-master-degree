
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class Carregando extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          CircularProgressIndicator(),
          Text('Carregando'),
        ],
      ),
    );
  }

}