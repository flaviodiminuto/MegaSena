package com.flavio.android.megasena.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flavio.android.megasena.R;
import com.flavio.android.megasena.adapter.CardValor2eValor2Adapter;
import com.flavio.android.megasena.service.grafico.GraficoBarraService;
import com.github.mikephil.charting.charts.BarChart;
import com.google.gson.internal.LinkedHashTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class NumerosMaisSorteados extends AppCompatActivity {

    private BarChart graficoBarra;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeros_mais_sorteados);
        this.graficoBarra = findViewById(R.id.mais_sorteados_grafico_barra);
        this.recycler = findViewById(R.id.mais_sorteados_recycler);
        Map<Integer, Integer> values = getvalues();
        List<Integer[]> chaveValorList = getChaveValorArray(values);

        GraficoBarraService.getBarChart(values,graficoBarra, "", new int[0]);
        preencheTabelaInferior(chaveValorList);
    }

    private void preencheTabelaInferior(List<Integer[]> chaveValorList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        CardValor2eValor2Adapter adapter = new CardValor2eValor2Adapter(chaveValorList);
        this.recycler.setHasFixedSize(true);
        this.recycler.setLayoutManager(layoutManager);
        this.recycler.setAdapter(adapter);
    }

    private List<Integer[]> getChaveValorArray(Map<Integer, Integer> values){
        List<Integer[]> chaveValorList = new ArrayList<>();
        Set<Integer> chaves = values.keySet();
        for(Integer chave : chaves){
            Integer[] chaveValor = {chave, values.get(chave)};
            chaveValorList.add(chaveValor);
        }
        return chaveValorList;
    }

    public Map<Integer,Integer> getvalues(){
        Map<Integer, Integer> values = new LinkedHashTreeMap<>();
        Random random = new Random(System.currentTimeMillis());
        while(values.size() < 10) {
            int valor = random.nextInt(60);
            int quantidade = random.nextInt(1000);
            values.put(valor, quantidade);
        }
        return new TreeMap<>(values); //ordenar
    }
}