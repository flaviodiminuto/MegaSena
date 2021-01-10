package com.flavio.android.megasena.service.grafico;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraficoLinearService {

    public void preencheGraficoLinear(Map<Float, Float> values, LineChart chart, String  label){
        List<Entry> data = getEntry(values);
        List<ILineDataSet> dataSets = getLineDataSetList(data,label);
        chart.setData(new LineData(dataSets));
        chart.invalidate();
    }

    private List<Entry> getEntry(Map<Float, Float> values) {
        List<Entry> data = new ArrayList<>();
        Set<Float> chaves = values.keySet();
        for (Float chave : chaves) {
            data.add(new Entry(chave, values.get(chave)));
        }
        return data;
    }

    public List<ILineDataSet> getLineDataSetList(List<Entry> data, String label){
        LineDataSet lineDataSet = new LineDataSet(data,label);
        List<ILineDataSet> dataSetList = new ArrayList<>();
        dataSetList.add(lineDataSet);
        return  dataSetList;
    }
}
