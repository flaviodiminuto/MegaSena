package com.flavio.android.megasena.Modelos.adapter;

import java.util.List;

public class TopDezModel {
    private final String titulo;
    private final List<String> dezenas;
    private boolean visivel;

    public TopDezModel(String titulo, List<String> dezenas, boolean visivel) {
        this.titulo = titulo;
        this.dezenas = dezenas;
        this.visivel = visivel;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<String> getDezenas() {
        return dezenas;
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }
}
