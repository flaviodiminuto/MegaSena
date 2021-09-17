package com.flavio.android.megasena.enumeradores;

import com.flavio.android.megasena.Modelos.sorteio.Sorteio;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public enum Rota {
    ULTIMOS_POR_DATA("/sorteios"){
        @Override
        public List<Sorteio> get(String json){
            Type tipoDaLista = new TypeToken<List<Sorteio>>(){}.getType();
            return gson.fromJson(json, tipoDaLista);
        }
    },
    ULTIMO_SORTEIO("/sorteios/ultimo"){
        @Override
        public Sorteio get(String json) {
            return gson.fromJson(json, Sorteio.class);
        }
    };

    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public abstract <T> T get(String json);
    String url = "https://super-megasena.herokuapp.com";
    private String rota;

    Rota(String rota) {
        this.rota = rota;
    }

    public String getUrl() {
        return url.concat(rota);
    }
}
