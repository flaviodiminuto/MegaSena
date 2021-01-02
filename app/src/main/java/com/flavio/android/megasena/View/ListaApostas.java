package com.flavio.android.megasena.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flavio.android.megasena.Dao.DaoAposta;
import com.flavio.android.megasena.Dao.DaoApostaSequencia;
import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.R;

import java.util.ArrayList;
import java.util.List;

public class ListaApostas extends AppCompatActivity {
    private List<Aposta> apostas;
    private DaoAposta da;
    private TextView titulo, conteudo;
    private EditText apostaEscolhida;
    private ImageView btnVerificar,returnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_lista_apostas );
        this.apostas = new ArrayList <Aposta> (  );
        titulo = findViewById ( R.id.txtListaApostaTitulo );
        conteudo = findViewById ( R.id.txtListaApostaConteudo );
        da = new DaoAposta ( getApplicationContext () );
        apostaEscolhida = findViewById ( R.id.edtListaApostaNumSelecionado );
        btnVerificar = findViewById ( R.id.btnListaApostaSelecionaAposta );
        returnBack = findViewById ( R.id.btnListaReturn );

        this.apostas= da.listaApostas ();
        if(this.apostas!=null) {
            titulo.setText ( String.format ( "%d Apostas encontradas",apostas.size ()   ) );
            String texto = "";
            for (Aposta aposta : apostas) {
                texto += String.format ( "\n\nAposta: %d\t\tValor: %.2f\nQuantidade de Sequencias: %d", aposta.getId (), aposta.getValor (), aposta.getQuantidadeSequencias () );
                if (aposta.isPremiado ()) {
                    texto += "\nAposta Sorteada!";
                }
            }
            conteudo.setText ( texto );
        }else Toast.makeText ( this, "Apostas não encontradas", Toast.LENGTH_SHORT ).show ();

        btnVerificar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( ListaApostas.this, VerificarSorteio.class );
                Aposta aposta = preencheAposta ();
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

    private Aposta preencheAposta(){
        DaoApostaSequencia das = new DaoApostaSequencia ( getApplicationContext () );
        if(!apostaEscolhida.getText ().toString ().equals ( "" ) && Integer.parseInt ( apostaEscolhida.getText ().toString () )>=0 && Integer.parseInt ( apostaEscolhida.getText ().toString () )<=this.apostas.size ()){
            return das.consultaApostaCompletaById ( Integer.parseInt ( apostaEscolhida.getText ().toString () ) );
        }else
            Toast.makeText ( this, "Selecione uma aposta da lista", Toast.LENGTH_SHORT ).show ();
    return null;
    }
}
