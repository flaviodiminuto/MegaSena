package com.flavio.android.megasena.Modelos.sorteio;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Sorteio {
    public String tipoJogo;
    @SerializedName("concurso")
    public Integer concurso;
    @SerializedName("nome_municipio_uf_sorteio")
    public String nomeMunicipioUFSorteio;
    @SerializedName("data_apuracao")
    public String dataApuracao;
    @SerializedName("valor_arrecadado")
    public BigDecimal valorArrecadado;
    @SerializedName("valor_estimado_proximo_concurso")
    public BigDecimal valorEstimadoProximoConcurso;
    @SerializedName("valor_acumulado_proximo_concurso")
    public BigDecimal valorAcumuladoProximoConcurso;
    @SerializedName("valor_acumulado_concurso_especial")
    public BigDecimal valorAcumuladoConcursoEspecial;
    @SerializedName("valor_acumulado_concurso_05")
    public BigDecimal valorAcumuladoConcurso05;
    @SerializedName("is_acumulado")
    public Boolean acumulado;
    @SerializedName("is_concurso_especial")
    public Boolean indicadorConcursoEspecial;
    @SerializedName("dezenasSorteadasOrdemSorteio")
    public List<String> dezenasSorteadasOrdemSorteio = new ArrayList<String>();
    @SerializedName("numero_jogo")
    public Integer numeroJogo;
    @SerializedName("tipo_publicacao")
    public Integer tipoPublicacao;
    @SerializedName("observacao")
    public String observacao;
    @SerializedName("local_sorteio")
    public String localSorteio;
    @SerializedName("data_proximo_concurso")
    public String dataProximoConcurso;
    @SerializedName("numero_concurso_anterior")
    public Integer numeroConcursoAnterior;
    @SerializedName("numero_concurso_proximo")
    public Integer numeroConcursoProximo;
    @SerializedName("valor_total_premio_faixa_um")
    public BigDecimal valorTotalPremioFaixaUm;
    @SerializedName("numero_concurso_final_05")
    public Integer numeroConcursoFinal05;
    @SerializedName("lista_municipio_uf_ganhadores")
    public List<MuniciipUFGanhadores> listaMunicipioUFGanhadores = new ArrayList<>();
    @SerializedName("lista_rateio_premio")
    public List<ListaRateioPremio> listaRateioPremio = new ArrayList<ListaRateioPremio>();
    @SerializedName("lista_dezenas")
    public List<String> listaDezenas = new ArrayList<String>();
    public List<String> listaDezenasSegundoSorteio;
    public Long id;
}