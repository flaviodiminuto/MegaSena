package com.flavio.android.megasena.Modelos;

public class Validacao {
    private static int[] numerosSorteados = new int[0];

    public static int[] getNumerosSorteados() {
        return numerosSorteados;
    }

    public static void setNumerosSorteados(int[] numerosSorteados) {
        Validacao.numerosSorteados = numerosSorteados;
    }
}
