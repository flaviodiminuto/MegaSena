package com.flavio.android.megasena.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.service.ApostaService;
import com.flavio.android.megasena.service.InputFilterMinMax;

public class GerarAutomatico extends AppCompatActivity {
    private Aposta aposta;
    private EditText txt6, txt7, txt8, txt9, txt10, txt11, txt12, txt13, txt14, txt15;
    private ImageView gerarSequencia, home, returnBack;
    private ApostaService apostaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_gerar_automatico );

/*--------------------------------------------------------------
    Inicializa os campos da tela
--------------------------------------------------------------*/
            this.txt6 = findViewById (R.id.edtGerar6);
            this.txt6.setFilters(new InputFilter[]{new InputFilterMinMax("1", "1000")});
            this.txt7 = findViewById ( R.id.edtGerar7 );
            this.txt7.setFilters(new InputFilter[]{new InputFilterMinMax("1", "1000")});
            this.txt8 = findViewById ( R.id.edtGerar8 );
            this.txt8.setFilters(new InputFilter[]{new InputFilterMinMax("1", "1000")});
            this.txt9 = findViewById ( R.id.edtGerar9 );
            this.txt9.setFilters(new InputFilter[]{new InputFilterMinMax("1", "1000")});
            this.txt10 = findViewById ( R.id.edtGerar10 );
            this.txt10.setFilters(new InputFilter[]{new InputFilterMinMax("1", "1000")});
            this.txt11 = findViewById ( R.id.edtGerar11 );
            this.txt11.setFilters(new InputFilter[]{new InputFilterMinMax("1", "1000")});
            this.txt12 = findViewById ( R.id.edtGerar12 );
            this.txt12.setFilters(new InputFilter[]{new InputFilterMinMax("1", "1000")});
            this.txt13 = findViewById ( R.id.edtGerar13 );
            this.txt13.setFilters(new InputFilter[]{new InputFilterMinMax("1", "1000")});
            this.txt14 = findViewById ( R.id.edtGerar14 );
            this.txt14.setFilters(new InputFilter[]{new InputFilterMinMax("1", "1000")});
            this.txt15 = findViewById ( R.id.edtGerar15 ) ;
            this.txt15.setFilters(new InputFilter[]{new InputFilterMinMax("1", "1000")});

/*--------------------------------------------------------------
    Inicializa os botões
--------------------------------------------------------------*/
           this.gerarSequencia = findViewById ( R.id.btnGerarAutomaticoGerarSequencia);
           this.home = findViewById(R.id.btnAutomaticoHome);
           this.returnBack = findViewById ( R.id.btnAutomaticoReturn );

/*--------------------------------------------------------------
    Inicializa o aposta service
--------------------------------------------------------------*/
            this.apostaService = new ApostaService();

/*--------------------------------------------------------------
    Ação ao precionar o botão "Gerar Sequencias"
--------------------------------------------------------------*/
        this.gerarSequencia.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( GerarAutomatico.this, ApostaCarregada.class );
                adicionarSequenciaNaAposta();
                if(aposta.getQuantidadeSequencias() > 0) {
                    Validacao.setSorteio(null);
                    it.putExtra ( "aposta", apostaService.getJson(aposta) );
                    startActivity ( it );
                }else{
                    mensagem ( "Adicione a quantidade de sequencias que deseja Gerar" );
                }
            }
        } );

        this.home.setOnClickListener(listener -> {
            Intent it = new Intent(GerarAutomatico.this, Inicio.class);
            startActivity(it);
        });

/*--------------------------------------------------------------
    Ação ao precionar o botão "home"
--------------------------------------------------------------*/
        this.returnBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                onBackPressed ();
            }
        } );

/*--------------------------------------------------------------
    Ação ao clicar em OK do teclado ao estar selecionada a ultima editText
--------------------------------------------------------------*/
        this.txt15.setOnEditorActionListener ( new TextView.OnEditorActionListener () {
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
    private void adicionarSequenciaNaAposta(){
        int soma = getNumero(this.txt6) +
        getNumero(this.txt7) +
        getNumero(this.txt8) +
        getNumero(this.txt9) +
        getNumero(this.txt10) +
        getNumero(this.txt11) +
        getNumero(this.txt12) +
        getNumero(this.txt13) +
        getNumero(this.txt14) +
        getNumero(this.txt15) ;

        if(soma > 1000)
            Toast.makeText(this, "O limite para geração é de 1.000 sequencias", Toast.LENGTH_LONG).show();

        this.aposta = new Aposta ();
        this.apostaService.adicionaSequencia(this.aposta, getNumero(this.txt6),6);
        this.apostaService.adicionaSequencia(this.aposta, getNumero(this.txt7),7);
        this.apostaService.adicionaSequencia(this.aposta, getNumero(this.txt8),8);
        this.apostaService.adicionaSequencia(this.aposta, getNumero(this.txt9),9);
        this.apostaService.adicionaSequencia(this.aposta, getNumero(this.txt10),10);
        this.apostaService.adicionaSequencia(this.aposta, getNumero(this.txt11),11);
        this.apostaService.adicionaSequencia(this.aposta, getNumero(this.txt12),12);
        this.apostaService.adicionaSequencia(this.aposta, getNumero(this.txt13),13);
        this.apostaService.adicionaSequencia(this.aposta, getNumero(this.txt14),14);
        this.apostaService.adicionaSequencia(this.aposta, getNumero(this.txt15),15);
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
