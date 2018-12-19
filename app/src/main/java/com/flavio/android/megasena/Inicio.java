package com.flavio.android.megasena;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Inicio extends AppCompatActivity {
    private ImageView btnGerar, btnVerificar, btnRelatorio, btnInformacoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_inicio );

        btnGerar = findViewById ( R.id.btnInicioGerarJogo  );
        btnVerificar = findViewById ( R.id.btnInicioVerificarSorteio );
        btnRelatorio = findViewById ( R.id.btnInicioRelatorio );
        btnInformacoes = findViewById ( R.id.btnInicioInformacoes );

        btnGerar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( Inicio.this,GerarCartelasSelecionarModo.class );
                startActivity ( it );
            }
        } );

    }
}
