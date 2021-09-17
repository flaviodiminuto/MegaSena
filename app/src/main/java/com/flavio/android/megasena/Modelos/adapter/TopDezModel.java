package com.flavio.android.megasena.Modelos.adapter;

import com.flavio.android.megasena.service.ProcessamentoHistoricoService.Ranking;

public class TopDezModel {
    private final String titulo;
    private Ranking ranking;
    private boolean visivel;

    public TopDezModel(String titulo, Ranking ranking, boolean visivel) {
        this.titulo = titulo;
        this.ranking = ranking;
        this.visivel = visivel;
    }


    public String getTitulo() {
        return titulo;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }
}
