package com.flavio.android.megasena.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.R;

public class GerarAutomatico extends AppCompatActivity {
    private Aposta aposta;
    private EditText txt6, txt7, txt8, txt9, txt10, txt11, txt12, txt13, txt14, txt15;
    private ImageView gerarSequencia,returnBack;

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

/*--------------------------------------------------------------
    Inicializa os botões
--------------------------------------------------------------*/
            gerarSequencia = findViewById ( R.id.btnGerarAutomaticoGerarSequencia);
            returnBack = findViewById ( R.id.btnAutomaticoReturn );

/*--------------------------------------------------------------
    Ação ao precionar o botão "Gerar Sequencias"
--------------------------------------------------------------*/
        gerarSequencia.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( GerarAutomatico.this, ApostaCarregada.class );
                String resposta = adicionarSequencia ();
                if(!resposta.equals ( "vazio" )) {
                    it.putExtra ( "aposta", resposta );
                    startActivity ( it );
                }else{
                    mensagem ( "Adicione a quantidade de sequencias que deseja Gerar" );
                }
            }
        } );

/*--------------------------------------------------------------
    Ação ao precionar o botão "home"
--------------------------------------------------------------*/
        returnBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                onBackPressed ();
            }
        } );

/*--------------------------------------------------------------
    Ação ao clicar em OK do teclado ao estar selecionada a ultima editText
--------------------------------------------------------------*/
        txt15.setOnEditorActionListener ( new TextView.OnEditorActionListener () {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handle = false;
                if(actionId == EditorInfo.IME_ACTION_DONE)
                    handle = gerarSequencia.callOnClick ();

                return handle;
            }
        } );
    }


/*--------------------------------------------------------------
    Adicionando as sequencias na aposta
--------------------------------------------------------------*/
    private String adicionarSequencia(){
        this.aposta = new Aposta ();
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

        if(this.aposta.getQuantidadeSequencias ()==0){
            return "vazio";
        }
        return aposta.getJson ();
    }

    private int getNumero(EditText edt){
        if(edt.getText ().toString ().equals ( "" )){
            return 0;
        }
        return Integer.parseInt ( edt.getText ().toString () );
    }

    private void mensagem(String texto){
        Toast.makeText ( this, texto, Toast.LENGTH_SHORT ).show ();
    }

}
