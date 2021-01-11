package com.flavio.android.megasena.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.os.Bundle;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Sequencia;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.adapter.JogosAdapter;
import com.flavio.android.megasena.service.ApostaService;
import com.flavio.android.megasena.service.grafico.GraficoBarraService;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SorteioVerificado extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BarChart chart;
    private Aposta aposta;
    private ApostaService apostaService;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorteio_verificado);

        this.recyclerView = findViewById(R.id.sorteio_verificado_recycler);
        this.chart = findViewById(R.id.sorteio_verificado_barchart);
        this.apostaService = new ApostaService();
        Bundle bund = getIntent ().getExtras ();
        int apostaId = bund != null ? bund.getInt ( "aposta_id" ) : 0;
        this.aposta = apostaService.getApostaCompletaById(apostaId, this);

        apostaService.verificaSorteio(aposta, new Sequencia(Validacao.getNumerosSorteados()));
        preencheApostaComMaisAcertos();
        preencheGraficoAcertos();

    }

    private void preencheGraficoAcertos() {
        int[] colunasDeDestaque = {4,5,6};
        Map<Integer, Integer> quantidadesSorteadas = apostaService.quantidadeAcertosMap();
        GraficoBarraService.getBarChart(quantidadesSorteadas,chart,"acertos", colunasDeDestaque);
    }

    private void preencheApostaComMaisAcertos() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(layoutManager);
        this.adapter = new JogosAdapter(apostaService.getSequenciaComMaisAcertos(1));
        this.recyclerView.setAdapter(this.adapter);
    }
}