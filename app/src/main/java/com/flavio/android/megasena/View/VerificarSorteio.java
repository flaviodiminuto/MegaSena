package com.flavio.android.megasena.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.sorteio.UltimoSorteioDTO;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.adapter.JogosAdapter;
import com.flavio.android.megasena.interfaces.Subscriber;
import com.flavio.android.megasena.service.SorteioService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerificarSorteio extends AppCompatActivity implements Subscriber<UltimoSorteioDTO> {
    private Aposta aposta;
    private TextView txtTitulo;
    private RecyclerView verificaSorteioRecycler;
    private EditText edtNum1, edtNum2, edtNum3, edtNum4, edtNum5, edtNum6;
    private ImageButton btnVerificar;
    private ImageView home, returnBack;
    private RecyclerView.Adapter adapter;
    private List<EditText> camposNumerosSorteados;
    private SorteioService sorteioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.verificar_sorteio );

/*--------------------------------------------------------------
    Inicializa os campos da tela
--------------------------------------------------------------*/
        //TextView Titulo, Sequencias e sorteado
        this.txtTitulo = findViewById ( R.id.txtApostaGeradaTitulo );

        //EditTexts 1 a 6
        this.edtNum1 = findViewById ( R.id.edtApostaGeradaNum1 );
        this.edtNum2 = findViewById ( R.id.edtApostaGeradaNum2 );
        this.edtNum3 = findViewById ( R.id.edtApostaGeradaNum3 );
        this.edtNum4 = findViewById ( R.id.edtApostaGeradaNum4 );
        this.edtNum5 = findViewById ( R.id.edtApostaGeradaNum5 );
        this.edtNum6 = findViewById ( R.id.edtApostaGeradaNum6 );
        this.camposNumerosSorteados = new ArrayList<>();
        this.camposNumerosSorteados.add(this.edtNum1);
        this.camposNumerosSorteados.add(this.edtNum2);
        this.camposNumerosSorteados.add(this.edtNum3);
        this.camposNumerosSorteados.add(this.edtNum4);
        this.camposNumerosSorteados.add(this.edtNum5);
        this.camposNumerosSorteados.add(this.edtNum6);
        //Image Verificar (atua como botão)
        this.btnVerificar = findViewById ( R.id.btnApostaGeradaVerificar );
        this.home = findViewById ( R.id.btnVerificarHome );
        this.returnBack = findViewById ( R.id.btnVerificarReturn );
        this.verificaSorteioRecycler = findViewById ( R.id.verifica_sorteio_recycler );
        this.sorteioService = new SorteioService();

        this.sorteioService.buscarUltimoSorteio(this);

/*--------------------------------------------------------------
    Recebe uma String JSON e inicializa um Objecto Aposta
--------------------------------------------------------------*/
        Bundle bund = getIntent ().getExtras ();
        Gson g = new Gson ();
        this.aposta = g.fromJson (bund.getString ( "aposta" ), Aposta.class );

        setTitulo();
        exibirSequencias();

        this.btnVerificar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                redirecionarParaSorteioVerificado();
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

    private void redirecionarParaSorteioVerificado() {
        Intent it = new Intent(VerificarSorteio.this, SorteioVerificado.class);
        if(aposta!=null) {
            setSorteio();
            it.putExtra("aposta_id", this.aposta.getId());
            startActivity(it);
        }
    }

    private void setSorteio() {
            UltimoSorteioDTO ultimoSorteioDTO = new UltimoSorteioDTO();
            ultimoSorteioDTO.listaDezenas = getValoresCampos();
            Validacao.setUltimoSorteioDTO(ultimoSorteioDTO);
    }

    private List<String> getValoresCampos() {
        return Arrays.asList(
                getNumeroFromEditText ( edtNum1 ),
                getNumeroFromEditText ( edtNum2 ),
                getNumeroFromEditText ( edtNum3 ),
                getNumeroFromEditText ( edtNum4 ),
                getNumeroFromEditText ( edtNum5 ),
                getNumeroFromEditText ( edtNum6 )
        );
    }

    private void exibirSequencias() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.verificaSorteioRecycler.setHasFixedSize(true);
        this.verificaSorteioRecycler.setLayoutManager(layoutManager);
        this.adapter = new JogosAdapter(this.aposta.getSequencias());
        this.verificaSorteioRecycler.setAdapter(this.adapter);
    }
    private void setTitulo() {
        final String titulo = "Quantidade de sequencias: " + this.aposta.getQuantidadeSequencias();
        this.txtTitulo.setText (titulo) ;
    }

    @SuppressLint("SetTextI18n")
    private String getNumeroFromEditText(EditText edt){
        String value = "0" + edt.getText().toString();
       try {
           return value;
       }catch (NumberFormatException nfe){
           edt.setText("0");
           return "0";
       }
    }

    public void alert(UltimoSorteioDTO ultimoSorteioDTO) {
        int i = 0;
        edtNum1.setText(ultimoSorteioDTO.listaDezenas.get(i++).substring(1,3));
        edtNum2.setText(ultimoSorteioDTO.listaDezenas.get(i++).substring(1,3));
        edtNum3.setText(ultimoSorteioDTO.listaDezenas.get(i++).substring(1,3));
        edtNum4.setText(ultimoSorteioDTO.listaDezenas.get(i++).substring(1,3));
        edtNum5.setText(ultimoSorteioDTO.listaDezenas.get(i++).substring(1,3));
        edtNum6.setText(ultimoSorteioDTO.listaDezenas.get(i).substring(1,3));

        if(this.aposta != null) exibirSequencias();
    }

    public Context context() {
        return this;
    }
}
