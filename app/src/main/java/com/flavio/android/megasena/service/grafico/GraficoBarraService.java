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
import com.github.mikephil.charting.renderer.LegendRenderer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraficoBarraService {

    public static void getBarChart(Map<Integer,Integer> values,
                                   BarChart chart,
                                   String label){
        configchart(chart);
        configLegend(chart,values);
        setBarChartValue(chart, values, label);
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

    private static void configLegend(BarChart chart, Map<Integer,Integer> values){
        Legend legend = chart.getLegend();
        legend.mNeededHeight = 15f;
        legend.mTextHeightMax = 20f;
        legend.setTextSize(10f);
        legend.setTextColor(Color.WHITE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setCustom(getLegendEntries(values));
    }

    private static void setBarChartValue(BarChart chart,Map<Integer,Integer> values,  String label) {
        List<BarEntry> data = getBrEntry(values);
        BarDataSet barDataSet = new BarDataSet(data,label);
        barDataSet.setColors(getColors(values.size()), 255);
        BarData barData = new BarData(barDataSet);
        barData.setValueFormatter(new MyValueFormatter());
        barData.setValueTextColor(Color.WHITE);
        barData.setValueTextSize(15f);
        chart.setData(barData);
        chart.invalidate();
    }

    private static List<LegendEntry> getLegendEntries(Map<Integer,Integer> values){
        List<LegendEntry> entriesList = new ArrayList<>();
        String[] labels = getLabels(values);
        int[] colors = getColors(values.size());

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

    private static int[] getColors(int tamanho) {
        int i = 0;
        int[] colors = new int[tamanho];
        while(i < tamanho){
            colors[i] = i % 2 == 0 ? Color.parseColor("#394A4A") :  Color.WHITE;
            i++;
        }
        return colors;
    }

    @SuppressLint("DefaultLocale")
    private static String[] getLabels(Map<Integer,Integer> values){
        String[] labels = new String[values.size()];
        Set<Integer> chaves = values.keySet();
        int i = 0;
        for ( Integer chave : chaves) {
            labels[i] = String.format("n°%d", chave);
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
