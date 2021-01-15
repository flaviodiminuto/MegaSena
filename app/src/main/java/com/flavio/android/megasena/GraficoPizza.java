package com.flavio.android.megasena;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.flavio.android.megasena.service.grafico.GraficoPizzaService;
import com.github.mikephil.charting.charts.PieChart;

import java.util.HashMap;
import java.util.Map;

public class GraficoPizza extends AppCompatActivity {

    private PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_pizza);

        this.chart = findViewById(R.id.chart);

        Map<String, Integer> values = new HashMap<>();
        values.put("35",30);
        values.put("44",8);
        values.put("53", 31);
        values.put("56", 10);
        values.put("12",30);
        values.put("37",8);
        GraficoPizzaService.getGraficoDePizza(values,
                this,
                "Números mais sorteados",
                "");
    }
}