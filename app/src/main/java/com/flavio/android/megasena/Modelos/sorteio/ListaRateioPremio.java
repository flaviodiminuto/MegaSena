package com.flavio.android.megasena.Modelos.sorteio;

import com.google.gson.annotations.SerializedName;

public class ListaRateioPremio {
    public int faixa;
    @SerializedName("numero_de_ganhadores")
    public int numeroDeGanhadores;
    @SerializedName("valor_premio")
    public double valorPremio;
    @SerializedName("descricao_faixa")
    public String descricaoFaixa;
}
