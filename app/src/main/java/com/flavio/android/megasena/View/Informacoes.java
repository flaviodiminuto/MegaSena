package com.flavio.android.megasena.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Sequencia;
import com.flavio.android.megasena.R;

import java.util.ArrayList;
import java.util.List;

public class Informacoes extends AppCompatActivity {
    private ImageView returnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_informacoes );
        this.returnBack = findViewById ( R.id.btnInformaReturn );
        List<Sequencia> sequenciasList = getSequencias();
        List<TextView> camposList = getCampos();
        String valor;

        for (int i = 0; i <= 9; i++) {
            valor = String.format("%.2f", sequenciasList.get(i).getValor());
            camposList.get(i).setText(valor);
        }

        this.returnBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                onBackPressed ();
            }
        } );
    }

    private List<Sequencia> getSequencias() {
        int tamanho = 6;
        Aposta aposta = new Aposta();
        while(tamanho <= 15){
            aposta.adicionaSequencia(1,tamanho);
            tamanho++;
        }
        return aposta.getSequencias();
    }

    private List<TextView> getCampos(){
        List<TextView> campos = new ArrayList<>();
        campos.add((TextView)this.findViewById(R.id.info_valor_6));
        campos.add((TextView)this.findViewById(R.id.info_valor_7));
        campos.add((TextView)this.findViewById(R.id.info_valor_8));
        campos.add((TextView)this.findViewById(R.id.info_valor_9));
        campos.add((TextView)this.findViewById(R.id.info_valor_10));
        campos.add((TextView)this.findViewById(R.id.info_valor_11));
        campos.add((TextView)this.findViewById(R.id.info_valor_12));
        campos.add((TextView)this.findViewById(R.id.info_valor_13));
        campos.add((TextView)this.findViewById(R.id.info_valor_14));
        campos.add((TextView)this.findViewById(R.id.info_valor_15));
        return campos;
    }
}
