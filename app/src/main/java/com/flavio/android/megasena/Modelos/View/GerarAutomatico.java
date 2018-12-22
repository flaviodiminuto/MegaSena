package com.flavio.android.megasena.Modelos.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flavio.android.megasena.Modelos.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Recycler.Recycler2;
import com.flavio.android.megasena.R;

public class GerarAutomatico extends AppCompatActivity {
    private Aposta aposta;
    private EditText txt6, txt7, txt8, txt9, txt10, txt11, txt12, txt13, txt14, txt15;
    private Button  gerarSequencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_gerar_automatico );

/*--------------------------------------------------------------
    Inicializa os campos da tela
--------------------------------------------------------------*/
            txt6 = findViewById (R.id.edtGerar6);
            txt7 = findViewById ( R.id.edtGerar7 );
            txt8 = findViewById ( R.id.edtGerar8 );
            txt9 = findViewById ( R.id.edtGerar9 );
            txt10 = findViewById ( R.id.edtGerar10 );
            txt11 = findViewById ( R.id.edtGerar11 );
            txt12 = findViewById ( R.id.edtGerar12 );
            txt13 = findViewById ( R.id.edtGerar13 );
            txt14 = findViewById ( R.id.edtGerar14 );
            txt15 = findViewById ( R.id.edtGerar15 ) ;

            gerarSequencia = findViewById ( R.id.btnGerarAutomaticoGerarSequencia);
/*--------------------------------------------------------------
    Ação ao precionar o botão "Gerar Sequencias"
--------------------------------------------------------------*/
        gerarSequencia.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
               // adicionarSequencia ();
                Intent it = new Intent ( GerarAutomatico.this, Recycler2.class );
                startActivity ( it );
            }
        } );
    }

    private void adicionarSequencia(){
        this.aposta = new Aposta ();
/*--------------------------------------------------------------
    Adicionando as sequencias na aposta
--------------------------------------------------------------*/
        this.aposta.adicionaSequencia ( getNumero ( txt6 ),6 );
        this.aposta.adicionaSequencia ( getNumero ( txt7 ),7 );
        this.aposta.adicionaSequencia ( getNumero ( txt8 ),8 );
        this.aposta.adicionaSequencia ( getNumero ( txt9 ),9 );
        this.aposta.adicionaSequencia ( getNumero ( txt10 ),10 );
        this.aposta.adicionaSequencia ( getNumero ( txt11 ),11 );
        this.aposta.adicionaSequencia ( getNumero ( txt12 ),12 );
        this.aposta.adicionaSequencia ( getNumero ( txt13 ),13 );
        this.aposta.adicionaSequencia ( getNumero ( txt14 ),14 );
        this.aposta.adicionaSequencia ( getNumero ( txt15 ),15 );

        mensagem(this.aposta.toString ());
    }

    private int getNumero(EditText edt){
        if(edt.getText ().toString ().equals ( "" )){
            return 0;
        }
        return Integer.parseInt ( edt.getText ().toString () );
    }

    private void mensagem(String texto){
        Toast.makeText ( this, this.aposta.toString (), Toast.LENGTH_SHORT ).show ();
    }

}
