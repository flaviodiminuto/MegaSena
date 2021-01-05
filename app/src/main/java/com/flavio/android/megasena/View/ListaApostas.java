package com.flavio.android.megasena.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flavio.android.megasena.Dao.DaoAposta;
import com.flavio.android.megasena.Dao.DaoApostaSequencia;
import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.adapter.ApostaAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaApostas extends AppCompatActivity {
    private List<Aposta> apostas;
    private DaoAposta da;
    private TextView titulo;
    private EditText apostaEscolhida;
    private ImageView btnVerificar,returnBack;
    private RecyclerView apostaRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_lista_apostas );
        this.apostas = new ArrayList <Aposta> (  );
        this.titulo = findViewById ( R.id.txtListaApostaTitulo );
        this.da = new DaoAposta ( getApplicationContext () );
        this.apostaEscolhida = findViewById ( R.id.edtListaApostaNumSelecionado );
        this.btnVerificar = findViewById ( R.id.btnListaApostaSelecionaAposta );
        this.returnBack = findViewById ( R.id.btnListaReturn );
        this.apostaRecycler = findViewById(R.id.lista_aposta_recycler);


        this.apostas= da.listaApostas ();
        if(this.apostas!=null) {
            titulo.setText ( String.format ( "%d Apostas em seu histórico",apostas.size ()   ) );
            preencherApostas();
        }else Toast.makeText ( this, "Apostas não encontradas", Toast.LENGTH_SHORT ).show ();

        btnVerificar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( ListaApostas.this, VerificarSorteio.class );
                Aposta aposta = consultarAposta();
                if(aposta!=null) {
                    it.putExtra ( "aposta", aposta.getJson () );
                    startActivity ( it );
                }
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

    private void preencherApostas() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter adapter = new ApostaAdapter(this.apostas);
        apostaRecycler.setHasFixedSize(true);
        apostaRecycler.setLayoutManager(layoutManager);
        apostaRecycler.setAdapter(adapter);
    }

    private Aposta consultarAposta(){
        DaoApostaSequencia das = new DaoApostaSequencia ( getApplicationContext () );
        if(!apostaEscolhida.getText ().toString ().equals ( "" ) && Integer.parseInt ( apostaEscolhida.getText ().toString () )>=0 && Integer.parseInt ( apostaEscolhida.getText ().toString () )<=this.apostas.size ()){
            return das.consultaApostaCompletaById ( Integer.parseInt ( apostaEscolhida.getText ().toString () ) );
        }else
            Toast.makeText ( this, "Selecione uma aposta da lista", Toast.LENGTH_SHORT ).show ();
    return null;
    }
}
