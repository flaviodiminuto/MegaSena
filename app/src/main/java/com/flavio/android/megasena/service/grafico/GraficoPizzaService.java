package com.flavio.android.megasena.service.grafico;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GraficoPizzaService {

    public static void getGraficoDePizza(Map<String,Integer> values,
                                         Context context,
                                         String centerText,
                                         String label){
        PieChart chart = new PieChart(context);
        configChart(chart,centerText);
        setChartValue(values,chart,label);
    }

    private static void configChart(PieChart chart, String centerText) {
        Legend legend = chart.getLegend();

        //Cores
        chart.setBackgroundColor(Color.BLACK);
        chart.setHoleColor(Color.BLACK);
        chart.setCenterTextColor(Color.GRAY);
        chart.setEntryLabelColor(Color.BLACK);
        legend.setTextColor(Color.WHITE);

        //texto
        chart.setCenterText(centerText);
        chart.setUsePercentValues(true);

        //legenda
        legend.setEnabled(true);
        legend.setTextSize(20f);

        //tamanhos
        chart.setHoleRadius(30f);
        chart.setTransparentCircleRadius(90f);
        chart.setCenterTextSize(20f);
        chart.setEntryLabelTextSize(30f);

    }

    private static void setChartValue(Map<String, Integer> values, PieChart chart, String label) {
        List<PieEntry> data = getPieEntryList(values);
        PieDataSet pieDataSet = new PieDataSet(data,label);
        int[] colors = getColors(values.size());
        pieDataSet.setColors(colors,255);
        PieData pieData = new PieData(pieDataSet);
        chart.setData(pieData);
        chart.invalidate();
    }

    private static int[] getColors(int tamanho) {
        int i = 0;
        Random random = new Random(System.currentTimeMillis());
        int[] colors = new int[tamanho];
        while(i < tamanho){
            colors[i] = Color.argb(255,
                    random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
            );
            i++;
        }
        return colors;
    }

    public static List<PieEntry> getPieEntryList(Map<String, Integer> values){
        Set<String> chaves = values.keySet();
        List<PieEntry> data = new ArrayList<>();
        for (String chave : chaves) {
            data.add(new PieEntry(values.get(chave), chave));
        }
        return data;
    }
}
