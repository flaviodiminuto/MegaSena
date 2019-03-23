package com.flavio.android.megasena.Modelos.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flavio.android.megasena.Modelos.Dao.DaoAposta;
import com.flavio.android.megasena.Modelos.Dao.DaoSequencia;
import com.flavio.android.megasena.Modelos.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Modelos.Sequencia;
import com.flavio.android.megasena.R;
import com.google.gson.Gson;

import java.util.Arrays;

public class VerificarSorteio extends AppCompatActivity {
    private Aposta aposta;
    private TextView txtSequencias,txtTitulo,txtSorteado,preenchaCampos;
    private EditText edtNum1, edtNum2, edtNum3, edtNum4, edtNum5, edtNum6;
    private ImageButton btnVerificar;
    private ImageView home, returnBack;
    private boolean verificado;
    private LinearLayout campos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.verificar_sorteio );

/*--------------------------------------------------------------
    Inicializa os campos da tela
--------------------------------------------------------------*/
        //TextView Titulo, Sequencias e sorteado
        txtTitulo = findViewById ( R.id.txtApostaGeradaTitulo );
        txtSequencias = findViewById ( R.id.txtApostaGeradaSequencias );
        txtSorteado = findViewById ( R.id.txtApostaGeradaPreencha );

        //EditTexts 1 a 6
        edtNum1 = findViewById ( R.id.edtApostaGeradaNum1 );
        edtNum2 = findViewById ( R.id.edtApostaGeradaNum2 );
        edtNum3 = findViewById ( R.id.edtApostaGeradaNum3 );
        edtNum4 = findViewById ( R.id.edtApostaGeradaNum4 );
        edtNum5 = findViewById ( R.id.edtApostaGeradaNum5 );
        edtNum6 = findViewById ( R.id.edtApostaGeradaNum6 );

        //Image Verificar (atua como botão)
        btnVerificar = findViewById ( R.id.btnApostaGeradaVerificar );
        home = findViewById ( R.id.btnVerificarHome );
        returnBack = findViewById ( R.id.btnVerificarReturn );

        campos = findViewById ( R.id.linearCampos );
        preenchaCampos = findViewById ( R.id.txtApostaGeradaPreencha );

/*--------------------------------------------------------------
    Recebe uma String JSON e inicializa um Objecto Aposta
--------------------------------------------------------------*/
        Bundle bund = getIntent ().getExtras ();
        Gson g = new Gson ();
        this.aposta = g.fromJson (bund.getString ( "aposta" ),Aposta.class );

        if(!this.aposta.isPremiado ())verificado = false;

        preencheTextos ();

        this.btnVerificar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                    verificarSorteio ();
                    preencheTextos ();

            }
        } );

/*--------------------------------------------------------------
    Ação ao precionar o botão "home"
--------------------------------------------------------------*/
        this.home.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( VerificarSorteio.this, Inicio.class );
                startActivity ( it );
            }
        } );

/*--------------------------------------------------------------
    Ação ao precionar o botão "returnBack"
--------------------------------------------------------------*/
        this.returnBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                onBackPressed ();
            }
        } );

    }
    private void preencheTextos(){
        this.txtTitulo.setText ( "Sequencia(s): "+String.valueOf ( this.aposta.getId () ) );
        String texto = String.format ( "%s %nValor: R$ %.2f" ,this.aposta.toString () ,this.aposta.getValor ());
        if(this.aposta.isPremiado ()) {
           txtSorteado.setText ( String.format ( "Aposta sorteada\nParabéns!" ) );
        }else {
            if (verificado)
                txtSorteado.setText ( "Aposta NÃO sorteada!" );
        }
        txtSequencias.setText ( texto );
    }

/*--------------------------------------------------------------
    Verifica se a sequencia apresentada nos editibox está na Aposta atual
--------------------------------------------------------------*/
    private void verificarSorteio(){
        /*Desenvolver mecanismo de verificação de número repetido nos campos e solicitar novo número caso já encontrado*/
        int[] numeros = {getNumeroFromEditText ( edtNum1 ), getNumeroFromEditText ( edtNum2 ), getNumeroFromEditText ( edtNum3 ), getNumeroFromEditText ( edtNum4 ), getNumeroFromEditText ( edtNum5 ), getNumeroFromEditText ( edtNum6 )};
        Sequencia sequencia = new Sequencia ( numeros );
        sequencia.ordenar ();
        numeros = sequencia.getNumeros ();
        //this.aposta.setPremiado ( this.aposta.verificaSorteio ( sequencia ) );
       // if(this.aposta.isPremiado ()){
            DaoAposta da = new DaoAposta ( getApplicationContext () );
            DaoSequencia ds = new DaoSequencia ( getApplicationContext () );

            for(Sequencia seq : this.aposta.getSequencias ()){
                if(Arrays.equals (seq.getNumeros (),numeros)){
                    this.aposta.setPremiado ( true );
                    sequencia = seq;
                    sequencia.setSorteado ( true );
                    ds.alterarSequencia ( sequencia );
                    da.alterarAposta ( this.aposta );
                    break;
                }
            }
        //}
        this.verificado = true;
    }
    private int getNumeroFromEditText(EditText edt){
        if(edt.getText ().toString ().equals ( "" )) return 0;
        return Integer.parseInt ( edt.getText ().toString () );
    }


}
