package com.flavio.android.megasena.View;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flavio.android.megasena.Dao.DaoGeneralista;
import com.flavio.android.megasena.Modelos.sorteio.Sorteio;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.enumeradores.Rota;
import com.flavio.android.megasena.interfaces.Subscriber;
import com.flavio.android.megasena.service.ProcessamentoHistoricoService;
import com.flavio.android.megasena.service.SorteioService;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Relatorio extends AppCompatActivity implements Subscriber<List<Sorteio>> {
    private final SorteioService sorteioService = new SorteioService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_relatorio );

/*--------------------------------------------------------------
    Configura o icone "retornar"
--------------------------------------------------------------*/
        ImageView returnBack = findViewById(R.id.btnRelatorioReturn);
        returnBack.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                onBackPressed ();
            }
        } );

        sorteioService.buscaSorteiosAPartirDe(this, "data-inicial=2021-01-01&quantidade=1000");
    }

    @Override
    public void alert(List<Sorteio> sorteios) {
        Map<Integer, Integer> sorteados = ProcessamentoHistoricoService.NumerosMaisSorteados(sorteios);
        Map<Integer, Integer> top10 = new LinkedTreeMap<>();
        int contador = 0;
        for(Integer key : sorteados.keySet()){
            top10.put(key, sorteados.get(key));
            if(++contador == 10) break;
        }

        //todo - Preparar tela para receber o top10 do ultimo mes
        Toast.makeText(this, top10.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return this;
    }
}
