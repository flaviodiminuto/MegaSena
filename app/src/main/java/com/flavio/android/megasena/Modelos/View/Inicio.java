package com.flavio.android.megasena.Modelos.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.flavio.android.megasena.Modelos.Recycler.Recycler2;
import com.flavio.android.megasena.R;

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
