package com.flavio.android.megasena.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flavio.android.megasena.Dao.DaoUltimoSorteio;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.Modelos.sorteio.Sorteio;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.interfaces.Subscriber;
import com.flavio.android.megasena.util.DataUtil;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.Date;


public class SorteioService {

    private DaoUltimoSorteio dao = null;

    private void buscaNaApi(Subscriber subscrito) {
        Gson gson = new Gson();
        Context context = subscrito.context();
        String megaSenaUrl = context.getString(R.string.megasena_api);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, megaSenaUrl, response -> {
            subscrito.async_alert(gson.fromJson(response, Sorteio.class));
        }, volleyError -> {
            Toast.makeText(context, "Não foi possivel obter as informações atualizadas, verifique sua conexão", Toast.LENGTH_LONG).show();
            volleyError.printStackTrace();
        });
        queue.add(stringRequest);
    }

    public void buscarUltimoSorteio(Subscriber subscrito){
        Context context = subscrito.context();
        Sorteio sorteio = buscaNoBancoInterno(context);
        Validacao.setSorteio(sorteio);
        if(precisaAtualizarUltimoSorteio()) {
            buscaNaApi(subscrito);
        } else {
            subscrito.async_alert(Validacao.getSorteio());
        }
    }

    private boolean precisaAtualizarUltimoSorteio() {
        if (Validacao.getSorteio() == null
                || Validacao.getSorteio().id == null
                || Validacao.getSorteio().dataProximoConcurso == null) return true;
        try {
            final Date agora = new Date();
            String dataString = Validacao.getSorteio().dataProximoConcurso + " 21:00:00";
            final Date dataHoraSorteio = DataUtil.toDataHoraBr(dataString);
            return dataHoraSorteio.before(agora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Sorteio buscaNoBancoInterno(Context context) {
        if (dao == null)
            dao = new DaoUltimoSorteio(context);
        return dao.buscarUltimoSorteio();
    }

    private void persistirSorteio(Sorteio sorteio) {
        if (sorteio != null)
            dao.persistir(sorteio);
    }
}
