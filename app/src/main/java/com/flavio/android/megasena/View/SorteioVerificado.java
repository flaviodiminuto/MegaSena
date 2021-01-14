package com.flavio.android.megasena.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Sequencia;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.Modelos.sorteio.Sorteio;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.adapter.JogosAdapter;
import com.flavio.android.megasena.service.ApostaService;
import com.flavio.android.megasena.service.SorteioService;
import com.flavio.android.megasena.service.grafico.GraficoBarraService;
import com.github.mikephil.charting.charts.BarChart;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SorteioVerificado extends AppCompatActivity {
    private TextView txtSequenciaComMaisAcertos;
    private RecyclerView recyclerView;
    private BarChart chart;
    private Aposta aposta;
    private ApostaService apostaService;
    private SorteioService sorteioService;
    private Adapter adapter;
    private LinearLayout linear;
    private Sorteio sorteio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorteio_verificado);

        this.txtSequenciaComMaisAcertos = findViewById(R.id.txt_titulo_sequencia_com_mais_acertos);
        this.recyclerView = findViewById(R.id.sorteio_verificado_recycler);
        this.chart = findViewById(R.id.sorteio_verificado_barchart);
        this.linear = findViewById(R.id.sorteio_verificado_linear);
        this.apostaService = new ApostaService();
        this.sorteioService = new SorteioService();
        Bundle bund = getIntent ().getExtras ();
        int apostaId = bund != null ? bund.getInt ( "aposta_id" ) : 0;
        this.aposta = apostaService.getApostaCompletaById(apostaId, this);

        apostaService.verificaSorteio(aposta);
        preencheApostaComMaisAcertos();
        preencheGraficoAcertos();
        preencherDadosDoSorteio();

    }

    private void preencheGraficoAcertos() {
        int[] colunasDeDestaque = {4,5,6};
        Map<Integer, Integer> quantidadesSorteadas = apostaService.quantidadeAcertosMap();
        GraficoBarraService.getBarChart(quantidadesSorteadas,chart,"acertos", colunasDeDestaque);
    }

    private void preencheApostaComMaisAcertos() {
        List<Sequencia> list = apostaService.getSequenciaComMaisAcertos(1);
        if(list.isEmpty()){
            txtSequenciaComMaisAcertos.setText("");
            return;
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(layoutManager);
        this.adapter = new JogosAdapter(apostaService.getSequenciaComMaisAcertos(1));
        this.recyclerView.setAdapter(this.adapter);
    }

    private void preencherDadosDoSorteio() {
        if(!sorteioService.sorteioValido()) return;
        this.sorteio = Validacao.getSorteio();
        exibirNumeroEData();
        exibirSaidaDaMegaSena();
        exibirSaidaDaQuina();
        exibirSaidaDaQuadra();
        exibirPremioProximoCorcurso();
        exibirDataProximoSorteio();


    }

    private void exibirNumeroEData() {
        String numero = String.valueOf(this.sorteio.numero);

        this.linear.addView(getTitulo("Número do sorteio"));
        this.linear.addView(getValue(numero));
        this.linear.addView(getTitulo("Data do sorteio"));
        this.linear.addView(getValue(this.sorteio.dataApuracao));
    }

    private void exibirSaidaDaMegaSena() {
        String uf = Validacao.getSorteio().listaMunicipioUFGanhadores.get(0).uf;
        String municipio  = Validacao.getSorteio().listaMunicipioUFGanhadores.get(0).municipio;
    }

    private void exibirSaidaDaQuina() {

    }

    private void exibirSaidaDaQuadra() {

    }

    private void exibirDataProximoSorteio(){

    }

    private void exibirPremioProximoCorcurso() {

    }

    private TextView getTitulo(String titulo){
        TextView textView = textViewBase();
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(1,25.0f);
        textView.setText(titulo);
        return textView;
    }

    private TextView getValue(String value){
        TextView textView = textViewBase();
        textView.setTextSize(1,18.0f);
        textView.setText(value);
        return textView;
    }

    private TextView textViewBase(){
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        params.setMarginStart(30);
        textView.setLayoutParams(params);
        textView.setPadding(30,0,0,0);
        textView.setTextColor(Color.WHITE);
        return textView;
    }

}