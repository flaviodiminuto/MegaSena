package com.flavio.android.megasena;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.flavio.android.megasena.Modelos.Aposta;

public class GerarAutomatico extends AppCompatActivity {
    private Aposta aposta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_gerar_automatico );
        this.aposta = new Aposta ();
        this.aposta.adicionaSequencia(10,6);
        Toast.makeText ( this, this.aposta.toString (), Toast.LENGTH_SHORT ).show ();


    }
}
