package com.flavio.android.megasena.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flavio.android.megasena.Dao.DaoApostaSequencia;
import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.adapter.JogosAdapter;
import com.google.gson.Gson;

import java.util.Arrays;

public class ApostaCarregada extends AppCompatActivity {
    private TextView titulo;
    private RecyclerView jogosRecycler;
    private Aposta aposta;
    private ImageView btnSalvar,home, returnBack;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_aposta_carregada );

        this.titulo = findViewById ( R.id.txtApostaCarregadaTitulo );
        this.jogosRecycler = findViewById(R.id.jogos_recycler);
        this.btnSalvar = findViewById ( R.id.btnApostaCarregadaSalvar );

        this.home = findViewById ( R.id.btnGeradaHome );
        this.returnBack = findViewById ( R.id.btnGeradaReturn );
/*--------------------------------------------------------------
    Recebe uma String JSON e inicializa um Objecto Aposta
--------------------------------------------------------------*/

        Bundle bund = getIntent ().getExtras ();
        Gson g = new Gson ();
        this.aposta = g.fromJson (bund.getString ( "aposta" ),Aposta.class );

        this.titulo.setText ( "Aposta Carregada" );
        String texto = String.format ( "%s %nValor: R$ %.2f" ,this.aposta.toString () ,this.aposta.getValor ());
        if(this.aposta.isPremiado ()) {
            texto += "\nPARABÉNS!\nAPOSTA SORTEADA!" ;
        }

        configJogosRecycler();
        this.btnSalvar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                if(salvar()>=0)
                    showMessage ( "Aposta Salva!" );
                else
                    showMessage ( "Aposta não pôde ser salva" );

                Intent it = new Intent ( ApostaCarregada.this, Inicio.class );
                startActivity ( it );
            }
        } );

/*--------------------------------------------------------------
    Ação ao precionar o botão "home"
--------------------------------------------------------------*/
        home.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( ApostaCarregada.this, Inicio.class );
                startActivity ( it );
            }
        } );

/*--------------------------------------------------------------
    Ação ao precionar o botão "returnBack"
--------------------------------------------------------------*/
        returnBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                onBackPressed ();
            }
        } );
    }

    private void configJogosRecycler() {
        this.jogosRecycler.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.jogosRecycler.setLayoutManager(this.layoutManager);
        this.adapter = new JogosAdapter(aposta.getSequencias());
        this.jogosRecycler.setAdapter(this.adapter);
    }


    private long salvar(){
        DaoApostaSequencia das = new DaoApostaSequencia ( getApplicationContext () );
        return das.inserirApostaCompleta ( this.aposta ) ;
    }
    private void showMessage(String texto){
        Toast.makeText ( this, texto, Toast.LENGTH_SHORT ).show ();
    }

}
