package com.flavio.android.megasena.service;

import com.flavio.android.megasena.Modelos.sorteio.Sorteio;
import com.google.gson.internal.LinkedTreeMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ProcessamentoHistoricoService {
    public static Map<Integer, Integer> NumerosMaisSorteados(List<Sorteio> historicoList) {
        Map<Integer, Integer>  sorteados= new HashMap<>();
        historicoList.forEach(sorteio -> {
            sorteio.listaDezenas.forEach(dezenaStr -> {
                    Integer dezena = Integer.parseInt(dezenaStr);
                    incrementarQuantidade(sorteados, dezena);
            });
        });
        return ordenarDecrescente(sorteados);
    }

    public static void incrementarQuantidade(Map<Integer, Integer> sorteados, Integer numero){
        if(sorteados == null) return;
        int novaQuantidade = (sorteados.get(numero) == null) ? 1 : sorteados.get(numero) + 1;
        sorteados.put(numero,novaQuantidade);
    }

    public static void ordenarCrescente(Integer[][] numerosSorteados){
        //booblesort
        for (int i = 0; i < numerosSorteados.length; i++) {
            for (int j = 0; j < numerosSorteados.length; j++) {
                if(numerosSorteados[i][1] < numerosSorteados[j][1]){
                    //inverter posicao
                    Integer[][] auxiliar = new Integer[1][2];
                    auxiliar[0] = numerosSorteados[i];
                    numerosSorteados[i] = numerosSorteados[j];
                    numerosSorteados[j] = auxiliar[0];
                }
            }
        }
    }

    public static Map<Integer, Integer> ordenarDecrescente(Map<Integer, Integer> numerosSorteados){
        Integer[][] numerosArray = mapToArray(numerosSorteados);
        ordenarDecrescente(numerosArray);
        return arrayToMap(numerosArray);
    }

    private static Map<Integer, Integer> arrayToMap(Integer[][] numerosArray) {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        Arrays.stream(numerosArray).forEach(linha -> {
            map.put(linha[0], linha[1]);
        });
        return map;
    }

    public static void ordenarDecrescente(Integer[][] numerosSorteados){
        //booblesort
        for (int i = 0; i < numerosSorteados.length; i++) {
            for (int j = 0; j < numerosSorteados.length; j++) {
                if(numerosSorteados[i][1] > numerosSorteados[j][1]){
                    //inverter posicao
                    Integer[][] auxiliar = new Integer[1][2];
                    auxiliar[0] = numerosSorteados[i];
                    numerosSorteados[i] = numerosSorteados[j];
                    numerosSorteados[j] = auxiliar[0];
                }
            }
        }
    }

    public static Integer[][] mapToArray(Map<Integer, Integer> numerosSorteados){
        Integer[][] arrayList = new Integer[numerosSorteados.size()][2];
        Set<Integer> chaveSet = numerosSorteados.keySet();
        int i = 0;
        for (Integer chave : chaveSet) {
            arrayList[i][0] = chave;
            arrayList[i][1] = numerosSorteados.get(chave);
            i++;
        }
        return arrayList;
    }
}
