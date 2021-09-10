package com.flavio.android.megasena.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Sequencia;
import com.flavio.android.megasena.Modelos.sorteio.Sorteio;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.adapter.JogosAdapter;
import com.flavio.android.megasena.interfaces.Subscriber;
import com.flavio.android.megasena.service.ApostaService;
import com.flavio.android.megasena.service.SorteioService;
import com.flavio.android.megasena.service.grafico.GraficoBarraService;
import com.github.mikephil.charting.charts.BarChart;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;

public class SorteioVerificado extends AppCompatActivity implements Subscriber<Sorteio> {
    private TextView txtSequenciaComMaisAcertos;
    private RecyclerView recyclerView;
    private BarChart chart;
    private Aposta aposta;
    private ApostaService apostaService;
    private SorteioService sorteioService;
    private Adapter adapter;
    private LinearLayout linear;
    private Sorteio sorteio;
    private DecimalFormat decimalFormatter;
    private List<String> dezenasManuais = new ArrayList<>();;

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
        Bundle extras = getIntent ().getExtras ();
        int apostaId = extras != null ? extras.getInt ( "aposta_id" ) : 0;
        String dezena_01 = extras != null ? extras.getString ( "dezena_01" ) : "00";
        String dezena_02 = extras != null ? extras.getString ( "dezena_02" ) : "00";
        String dezena_03 = extras != null ? extras.getString ( "dezena_03" ) : "00";
        String dezena_04 = extras != null ? extras.getString ( "dezena_04" ) : "00";
        String dezena_05 = extras != null ? extras.getString ( "dezena_05" ) : "00";
        String dezena_06 = extras != null ? extras.getString ( "dezena_06" ) : "00";

        dezenasManuais.add(dezena_01);
        dezenasManuais.add(dezena_02);
        dezenasManuais.add(dezena_03);
        dezenasManuais.add(dezena_04);
        dezenasManuais.add(dezena_05);
        dezenasManuais.add(dezena_06);

        this.aposta = apostaService.getApostaCompletaById(apostaId, this);
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        decimalFormatter = (DecimalFormat)nf;
        decimalFormatter.applyPattern("###,##0.00");

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
        this.adapter = new JogosAdapter(list);
        this.recyclerView.setAdapter(this.adapter);
    }

    private void preencherDadosDoSorteio() {
        sorteioService.buscarUltimoSorteio(this);
    }

    private void addLinear(TextView textView){
        this.linear.addView(textView);
    }

    private void exibirNumeroEData() {
        String numero = String.valueOf(this.sorteio.concurso);
        TextView primeiraLinha = getTitulo("Número do sorteio");
        LinearLayout.LayoutParams params = getLayoutParamsBase();
        params.setMargins(30,80,0,0);
        primeiraLinha.setLayoutParams(params);

        addLinear(primeiraLinha);
        addLinear(getValue(numero));
        addLinear(getTitulo("Data do sorteio"));
        addLinear(getValue(this.sorteio.dataApuracao));
    }

    private void exibirSaidaDaMegaSena() {
        exibirSeAcumuou();
        if(this.sorteio == null || this.sorteio.listaMunicipioUFGanhadores == null) return;
        this.sorteio.listaMunicipioUFGanhadores.forEach(ganhador -> {
            addLinear(getTitulo("Cidade - Estado"));
            String value = ganhador.municipio + " - " + ganhador.uf;
            addLinear(getValue(value));
        });
    }

    @SuppressLint("SetTextI18n")
    private void exibirSeAcumuou() {
        TextView textView = getTitulo("");
        textView.setTextColor(Color.parseColor("#00ff00"));
        boolean sorteado = this.sorteio != null
                && this.sorteio.listaMunicipioUFGanhadores != null
                && this.sorteio.listaMunicipioUFGanhadores
                    .stream()
                    .anyMatch(ganhador -> ganhador.posicao == 1 && ganhador.ganhadores > 0);
        addMarginTop(textView);
        String value = sorteado ? "A MEGA SAIU!" : "A Mega Acumulou!";
        textView.setText(value);
        addLinear(textView);
    }

    private void addMarginTop(TextView textView) {
        LinearLayout.LayoutParams params = getLayoutParamsBase();
        params.setMargins(30,80, 0,0);
        textView.setLayoutParams(params);
    }

    @SuppressLint("DefaultLocale")
    private void exibirRateio(){
;        BiFunction<String, Integer, String> plural = (palavra, quantidade) ->
        quantidade == 1 ? palavra : palavra + "s";

        sorteio.listaRateioPremio.forEach(rateio -> {
            String value1 = String.format("%d %s %s",
                    rateio.numeroDeGanhadores,
                    plural.apply("aposta", rateio.numeroDeGanhadores),
                    plural.apply("ganhadora", rateio.numeroDeGanhadores));
            String value2 = String.format("Valor: R$ %s", decimalFormatter.format(rateio.valorPremio));

            TextView txtTitulo = getTitulo(String.format("%s com %d %s",
                    plural.apply("Aposta", rateio.numeroDeGanhadores),
                    7 - rateio.faixa,
                    plural.apply("acerto", rateio.numeroDeGanhadores)
                ));
            addMarginTop(txtTitulo);
            addLinear(txtTitulo);
            addLinear(getValue(value1));
            addLinear(getValue(value2));
        });
    }

    private void exibirPremioProximoCorcurso() {
        exibirProximoSorteioTitulo();
        String estimativaProximoConcurso = "Prêmio estimado para o concurso " + sorteio.numeroConcursoProximo;
        addLinear(getTitulo(estimativaProximoConcurso));
        String valorProximoConcurso = "RS " + decimalFormatter.format(sorteio.valorEstimadoProximoConcurso);
        addLinear(getValue(valorProximoConcurso));

        addLinear(getTitulo("Data do proximo sorteio"));
        String data = sorteio.dataProximoConcurso;
        String proxData = String.format("%s (%s)", data, getDiaDaSemana(data));
        addLinear(getValue(proxData));
    }

    @SuppressLint("SetTextI18n")
    private void exibirProximoSorteioTitulo() {
        TextView textView = getTitulo("");
        textView.setTextColor(Color.parseColor("#00ff00"));
        addMarginTop(textView);
        textView.setText("Próximo sorteio");
        addLinear(textView);
    }

    private TextView getTitulo(String titulo){
        TextView textView = getTextViewBase();
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(1,25.0f);
        textView.setText(titulo);
        return textView;
    }

    private TextView getValue(String value){
        TextView textView = getTextViewBase();
        textView.setTextSize(1,18.0f);
        textView.setText(value);
        return textView;
    }

    private TextView getTextViewBase(){
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = getLayoutParamsBase();
        params.setMarginStart(30);
        textView.setLayoutParams(params);
        textView.setPadding(30,0,0,0);
        textView.setTextColor(Color.WHITE);
        return textView;
    }

    private LinearLayout.LayoutParams getLayoutParamsBase(){
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        );
    }

    @SuppressLint("SimpleDateFormat")
    private String getDiaDaSemana(String dataStr){
        String dia = "";
        try {
            Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(data);
            int diaInt = calendar.get(Calendar.DAY_OF_WEEK);
            switch (diaInt){
                case 1: return "domingo";
                case 2: return "segunda-feira";
                case 3: return "terça-feira";
                case 4: return "quarta-feira";
                case 5: return "quinta-feira";
                case 6: return "sexta-feira";
                case 7: return "sábado";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dia;
    }

    @Override
    public void alert(Sorteio sorteioDTO) {
        this.sorteio = sorteioDTO;
        if(this.sorteio == null) return;
        this.sorteio.listaDezenas = this.dezenasManuais;
        exibirNumeroEData();
        exibirSaidaDaMegaSena();
        exibirRateio();
        exibirPremioProximoCorcurso();
    }

    @Override
    public Context context() {
        return this;
    }
}