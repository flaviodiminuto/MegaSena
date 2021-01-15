package com.flavio.android.megasena.Modelos;

import com.flavio.android.megasena.Modelos.sorteio.Sorteio;

public class Validacao {
    private static Sorteio sorteio = null;

    public static Sorteio getSorteio() {
        return sorteio;
    }

    public static void setSorteio(Sorteio sorteio) {
        Validacao.sorteio = sorteio;
    }
}
