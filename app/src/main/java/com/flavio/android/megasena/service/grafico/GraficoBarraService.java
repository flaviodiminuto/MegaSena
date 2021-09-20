package com.flavio.android.megasena.service.grafico;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraficoBarraService {

    public static void getBarChart(Map<Integer,Integer> values,
                                   BarChart chart,
                                   String legendLabel,
                                   int[] colunasDeDestaque){
        configchart(chart);
        configLegend(chart,values,legendLabel, colunasDeDestaque);
        setBarChartValue(chart, values, legendLabel, colunasDeDestaque);
    }

    private static void configchart(BarChart chart) {
        YAxis eixoEsquerdo = chart.getAxisLeft();
        eixoEsquerdo.setTextColor(Color.WHITE);
        eixoEsquerdo.setGranularity(1f);

        YAxis eixoDireito = chart.getAxisRight();
        eixoDireito.setTextColor(Color.WHITE);
        eixoDireito.setGranularity(1f);

        XAxis eixoX = chart.getXAxis();
        eixoX.setTextColor(Color.WHITE);
        eixoX.setGranularity(1f);
        eixoX.setDrawGridLines(false);

        Description description = chart.getDescription();
        description.setEnabled(false);

        chart.setBackgroundColor(Color.parseColor("#506060"));
        chart.setDrawBorders(false);
        chart.setFitBars(true);
    }

    private static void configLegend(BarChart chart, Map<Integer,Integer> values,String legendLabel, int[] colunasDeDestaque){
        Legend legend = chart.getLegend();
        legend.mNeededHeight = 15f;
        legend.mTextHeightMax = 20f;
        legend.setTextSize(9f);
        legend.setTextColor(Color.WHITE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setCustom(getLegendEntries(values,legendLabel, colunasDeDestaque));
    }

    private static void setBarChartValue(BarChart chart,Map<Integer,Integer> values,
                                         String legendLabel,
                                         int[] colunasDeDestaque) {
        List<BarEntry> data = getBrEntry(values);
        BarDataSet barDataSet = new BarDataSet(data,legendLabel);
        barDataSet.setColors(getColors(values.size(), colunasDeDestaque), 255);
        BarData barData = new BarData(barDataSet);
        barData.setValueFormatter(new MyValueFormatter());
        barData.setValueTextColor(Color.WHITE);
        barData.setValueTextSize(15f);
        chart.setData(barData);
        chart.invalidate();
    }

    private static List<LegendEntry> getLegendEntries(Map<Integer,Integer> values,
                                                      String label,
                                                      int[] colunasDeDestaque){
        List<LegendEntry> entriesList = new ArrayList<>();
        String[] labels = getLabels(values, label);
        int[] colors = getColors(values.size(), colunasDeDestaque);

        for (int i = 0; i < values.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.label = labels[i];
            entry.formColor = colors[i];
            entry.formSize = 8f;
            entry.form = Legend.LegendForm.CIRCLE;
            entriesList.add(entry);
        }
        return entriesList;
    }

    private static int[] getColors(int tamanho, int[] colunasDeDestaque) {
        int i = 0;
        int[] colors = new int[tamanho];
        int anterior = -1;
        while(i < tamanho){
            int busca = Arrays.binarySearch(colunasDeDestaque, i);
            if( busca > anterior)
                colors[i] =  Color.parseColor("#00FF00");
            else
                colors[i] = i % 2 == 0 ? Color.parseColor("#394A4A") :  Color.WHITE;
            anterior = busca;
            i++;
        }
        return colors;
    }

    @SuppressLint("DefaultLocale")
    private static String[] getLabels(Map<Integer,Integer> values, String label){
        String[] labels = new String[values.size()];
        Set<Integer> chaves = values.keySet();
        int i = 0;
        for ( Integer chave : chaves) {
                labels[i] = String.format("%d %s", chave, label);
            i++;
        }
        return labels;
    }

    private static List<BarEntry> getBrEntry(Map<Integer,Integer> values) {
        List<BarEntry> data = new ArrayList<>();
        int i = 0;
        Set<Integer> chaves = values.keySet();
        for ( Integer chave : chaves) {
            data.add(new BarEntry(i, values.get(chave)));
            i++;
        }
        return data;
    }

    private static class MyValueFormatter extends ValueFormatter {
        private DecimalFormat format = new DecimalFormat("0");
        @Override
        public String getBarLabel(BarEntry barEntry) {
            return format.format(barEntry.getY());
        }
    }
}
