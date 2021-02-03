package com.flavio.android.megasena.Modelos;

import com.flavio.android.megasena.Modelos.sorteio.UltimoSorteioDTO;

public class Validacao {
    private static UltimoSorteioDTO ultimoSorteioDTO = null;

    public static UltimoSorteioDTO getUltimoSorteioDTO() {
        return ultimoSorteioDTO;
    }

    public static void setUltimoSorteioDTO(UltimoSorteioDTO ultimoSorteioDTO) {
        Validacao.ultimoSorteioDTO = ultimoSorteioDTO;
    }
}
