package com.flavio.android.megasena.Modelos;

public class Validacao {
    private static Sorteio sorteio = null;

    public static Sorteio getSorteio() {
        return sorteio;
    }

    public static void setSorteio(Sorteio sorteio) {
        Validacao.sorteio = sorteio;
    }
}
