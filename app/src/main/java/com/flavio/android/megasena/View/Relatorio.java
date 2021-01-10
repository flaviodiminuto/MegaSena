package com.flavio.android.megasena.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flavio.android.megasena.Dao.DaoGeneralista;
import com.flavio.android.megasena.R;

public class Relatorio extends AppCompatActivity {
    private DaoGeneralista banco;
    private int apostasTotais, apostasPremiadas, sequenciasSorteadas;
    private TextView total, premiadas,sorteadas;
    private ImageView returnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_relatorio );

        banco = new DaoGeneralista ( getApplicationContext () );
        total = findViewById ( R.id.txtRelatorioQuantidadeValor );
        premiadas = findViewById ( R.id.txtRelatorioPremiadasValor );
        sorteadas = findViewById ( R.id.txtRelatorioSorteadasValor );
        returnBack = findViewById ( R.id.btnRelatorioReturn );

        String selectApostas = "select * from aposta";
        String selectPremiadas = selectApostas+" where premiado = 1";
        String selectSorteadas = "select * from sequencia where sorteado = 1";

        apostasTotais = banco.consulta ( selectApostas ).getCount ();
        total.setText ( apostasTotais+" Apostas geradas" );

        sequenciasSorteadas = banco.consulta ( selectSorteadas ).getCount ();
        sorteadas.setText ( sequenciasSorteadas + " Sequencias sorteadas" );

        apostasPremiadas = banco.consulta ( selectPremiadas ).getCount ();
        float pc = ((float)apostasPremiadas/(float)apostasTotais)*100;
        premiadas.setText ( String.format("%d Apostas premiadas %.2f %c das apostas",apostasPremiadas,pc,'%') );

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

}
