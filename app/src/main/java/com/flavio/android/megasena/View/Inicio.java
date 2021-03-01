package com.flavio.android.megasena.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.flavio.android.megasena.Dao.DaoAposta;
import com.flavio.android.megasena.Dao.DaoApostaSequencia;
import com.flavio.android.megasena.Dao.DaoSequencia;
import com.flavio.android.megasena.R;

public class Inicio extends AppCompatActivity {
    private ImageView btnGerar, btnVerificarSorteio, btnRelatorio, btnInformacoes, btnFechar;
    DaoSequencia ds;
    DaoAposta da;
    DaoApostaSequencia das;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        this.btnGerar = findViewById(R.id.btnInicioGerarJogo);
        this.btnVerificarSorteio = findViewById(R.id.btnInicioVerificarSorteio);
        this.btnRelatorio = findViewById(R.id.btnInicioRelatorio);
        this.btnInformacoes = findViewById(R.id.btnInicioInformacoes);
        this.btnFechar = findViewById(R.id.btn_fechar);

/*--------------------------------------------------------------
    Inicializa as tabelas do banco de dados caso ainda não existam (sequencia, aposta, aposta_sequencia)
--------------------------------------------------------------*/
        this.ds = new DaoSequencia ( getApplicationContext ()  );
        this.da = new DaoAposta ( getApplicationContext () );
        this.das = new DaoApostaSequencia ( getApplicationContext () );


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
        btnFechar.setOnClickListener(clickListener ->{
            this.finishAffinity();
        });
    }

    @Override
    public void onBackPressed() {
        //Na tela inicial esta tecla não funcionará
    }
}
