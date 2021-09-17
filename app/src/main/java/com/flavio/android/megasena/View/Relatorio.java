package com.flavio.android.megasena.View;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flavio.android.megasena.Modelos.adapter.TopDezModel;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.adapter.RankingAdapter;
import com.flavio.android.megasena.enumeradores.Periodo;
import com.flavio.android.megasena.interfaces.Subscriber;
import com.flavio.android.megasena.service.ProcessamentoHistoricoService;
import com.flavio.android.megasena.service.ProcessamentoHistoricoService.Ranking;

import java.util.ArrayList;
import java.util.List;

public class Relatorio extends AppCompatActivity implements Subscriber<Ranking> {
    private RecyclerView recycler;
    private RankingAdapter adapter;

    private TopDezModel topMes;
    private TopDezModel topTrimestre;
    private TopDezModel topSemestre;
    private TopDezModel topAno;
    ProcessamentoHistoricoService historico = new ProcessamentoHistoricoService();

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

/*--------------------------------------------------------------
    Configura  a lista de "RecyclerViews top 10"
--------------------------------------------------------------*/
        //todo - obter a lista atravez do servico historico
        Ranking rankingMes = historico.buscaTopDez(this, Periodo.MES_ATUAL);
        Ranking rankingTrimestre = historico.buscaTopDez(this, Periodo.ULTIMO_TRIMESTRE);
        Ranking rankingSemestre = historico.buscaTopDez(this, Periodo.ULTIMO_SEMESTRE);
        Ranking rankingAno = historico.buscaTopDez(this, Periodo.ULTIMO_ANO);

        topMes = new TopDezModel("30 dias", rankingMes, false);
        topTrimestre = new TopDezModel("90 dias", rankingTrimestre, false);
        topSemestre = new TopDezModel("180 Dias", rankingSemestre, false);
        topAno = new TopDezModel("360 dias", rankingAno, false);

        List<TopDezModel> topList = new ArrayList<>();
        topList.add(topMes);
        topList.add(topTrimestre);
        topList.add(topSemestre);
        topList.add(topAno);

        adapter = new RankingAdapter(topList);
        recycler = findViewById(R.id.top_10_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);


    }

    @Override
    public void async_alert(Ranking ranking) {
        switch (ranking.periodo){
            case MES_ATUAL: topMes.setRanking(ranking);
            case ULTIMO_TRIMESTRE: topTrimestre.setRanking(ranking);
            case ULTIMO_SEMESTRE: topSemestre.setRanking(ranking);
            case ULTIMO_ANO: topAno.setRanking(ranking);
        }
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public Context context() {
        return this;
    }
}
