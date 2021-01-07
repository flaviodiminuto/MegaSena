package com.flavio.android.megasena.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flavio.android.megasena.Dao.DaoAposta;
import com.flavio.android.megasena.Dao.DaoSequencia;
import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Sequencia;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.adapter.JogosAdapter;
import com.google.gson.Gson;

import java.util.Arrays;

public class VerificarSorteio extends AppCompatActivity {
    private Aposta aposta;
    private TextView txtTitulo,txtSorteado,preenchaCampos;
    private RecyclerView verificaSorteioRecycler;
    private EditText edtNum1, edtNum2, edtNum3, edtNum4, edtNum5, edtNum6;
    private ImageButton btnVerificar;
    private ImageView home, returnBack;
    private boolean verificado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.verificar_sorteio );

/*--------------------------------------------------------------
    Inicializa os campos da tela
--------------------------------------------------------------*/
        //TextView Titulo, Sequencias e sorteado
        this.txtTitulo = findViewById ( R.id.txtApostaGeradaTitulo );
        this.txtSorteado = findViewById ( R.id.txtApostaGeradaPreencha );

        //EditTexts 1 a 6
        this.edtNum1 = findViewById ( R.id.edtApostaGeradaNum1 );
        this.edtNum2 = findViewById ( R.id.edtApostaGeradaNum2 );
        this.edtNum3 = findViewById ( R.id.edtApostaGeradaNum3 );
        this.edtNum4 = findViewById ( R.id.edtApostaGeradaNum4 );
        this.edtNum5 = findViewById ( R.id.edtApostaGeradaNum5 );
        this.edtNum6 = findViewById ( R.id.edtApostaGeradaNum6 );

        //Image Verificar (atua como botão)
        this.btnVerificar = findViewById ( R.id.btnApostaGeradaVerificar );
        this.home = findViewById ( R.id.btnVerificarHome );
        this.returnBack = findViewById ( R.id.btnVerificarReturn );
        this.verificaSorteioRecycler = findViewById ( R.id.verifica_sorteio_recycler );

/*--------------------------------------------------------------
    Recebe uma String JSON e inicializa um Objecto Aposta
--------------------------------------------------------------*/
        Bundle bund = getIntent ().getExtras ();
        Gson g = new Gson ();
        this.aposta = g.fromJson (bund.getString ( "aposta" ), Aposta.class );

        if(!this.aposta.isPremiado ())verificado = false;

        exibirJogos();

        this.btnVerificar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                    verificarSorteio ();
                    exibirJogos();

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

    private void exibirJogos(){
        setTitulo();
        exibirSequencias();
        setResultadoVerificacao(this.aposta.isPremiado());
        setValor();
    }

    private void exibirSequencias() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.verificaSorteioRecycler.setHasFixedSize(true);
        this.verificaSorteioRecycler.setLayoutManager(layoutManager);
        JogosAdapter adapter = new JogosAdapter(aposta.getSequencias());
        this.verificaSorteioRecycler.setAdapter(adapter);
    }

    private void setResultadoVerificacao(boolean premiado) {
        if(premiado) {
            this.txtSorteado.setText ( "Aposta sorteada\nParabéns!"  );
        }else {
            if (this.verificado) {
                this.txtSorteado.setText ( "Aposta NÃO sorteada!" );
            }
        }
    }

    private void setValor() {
        final String valorText = String.format("%nValor: R$ %.2f", aposta.getValor());
    }

    private void setTitulo() {
        final String titulo = "Jogo(s): " + this.aposta.getQuantidadeSequencias();
        this.txtTitulo.setText (titulo) ;
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
        String value = "0" + edt.getText().toString();
       try {
           return Double.valueOf(value).intValue();
       }catch (NumberFormatException nfe){
           edt.setText(0);
           return 0;
       }
    }
}
