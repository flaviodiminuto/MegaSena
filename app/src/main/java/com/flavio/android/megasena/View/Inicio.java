package com.flavio.android.megasena.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.flavio.android.megasena.Dao.DaoAposta;
import com.flavio.android.megasena.Dao.DaoApostaSequencia;
import com.flavio.android.megasena.Dao.DaoSequencia;
import com.flavio.android.megasena.R;

public class Inicio extends AppCompatActivity {
    private ImageView btnGerar, btnVerificarSorteio, btnRelatorio, btnInformacoes;
    DaoSequencia ds;
    DaoAposta da;
    DaoApostaSequencia das;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_inicio );

        btnGerar = findViewById ( R.id.btnInicioGerarJogo  );
        btnVerificarSorteio = findViewById ( R.id.btnInicioVerificarSorteio );
        btnRelatorio = findViewById ( R.id.btnInicioRelatorio );
        btnInformacoes = findViewById ( R.id.btnInicioInformacoes );

/*--------------------------------------------------------------
    Inicializa as tabelas do banco de dados caso ainda não existam (sequencia, aposta, aposta_sequencia)
--------------------------------------------------------------*/
        ds = new DaoSequencia ( getApplicationContext ()  );
        da = new DaoAposta ( getApplicationContext () );
        das = new DaoApostaSequencia ( getApplicationContext () );


        btnGerar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( Inicio.this,GerarAutomatico.class );
                startActivity ( it );
            }
        } );

        btnVerificarSorteio.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( Inicio.this,ListaApostas.class );
                startActivity ( it );
            }
        } );

        btnRelatorio.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( Inicio.this, Relatorio.class );
                startActivity ( it );
            }
        } );
        btnInformacoes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( Inicio.this,Informacoes.class );
                startActivity ( it );
            }
        } );


    }

    @Override
    public void onBackPressed() {
        //Na tela inicial esta tecla não funcionará
    }
}
