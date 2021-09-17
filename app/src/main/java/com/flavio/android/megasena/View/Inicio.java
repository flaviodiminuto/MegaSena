package com.flavio.android.megasena.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flavio.android.megasena.Dao.DaoAposta;
import com.flavio.android.megasena.Dao.DaoApostaSequencia;
import com.flavio.android.megasena.Dao.DaoSequencia;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.Modelos.sorteio.Sorteio;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.interfaces.Subscriber;
import com.flavio.android.megasena.service.SorteioService;

public class Inicio extends AppCompatActivity implements Subscriber {
    private ImageView btnGerar, btnVerificarSorteio, btnRelatorio, btnInformacoes, btnFechar;
    private DaoSequencia ds;
    private DaoAposta da;
    private DaoApostaSequencia das;
    private SorteioService sorteioService;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        this.btnGerar = findViewById(R.id.btnInicioGerarJogo);
        this.btnVerificarSorteio = findViewById(R.id.btnInicioVerificarSorteio);
        this.btnRelatorio = findViewById(R.id.btnInicioRelatorio);
        this.btnInformacoes = findViewById(R.id.btnInicioInformacoes);
        this.btnFechar = findViewById(R.id.btn_fechar);
        this.sorteioService = new SorteioService();

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
        this.sorteioService.buscarUltimoSorteio(this);
    }

    @Override
    public void onBackPressed() {
        //Na tela inicial esta tecla não funcionará
    }

    @Override
    public void async_alert(Object obj) {
        if(obj instanceof Sorteio) {
            Sorteio sorteio = (Sorteio) obj;
            sorteioService.persistirSorteio((Sorteio) sorteio);
            Validacao.setSorteio(sorteio);
        }
    }

    @Override
    public Context context() {
        return this;
    }
}
