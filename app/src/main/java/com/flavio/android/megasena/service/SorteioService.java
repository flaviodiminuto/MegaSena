package com.flavio.android.megasena.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flavio.android.megasena.Dao.DaoUltimoSorteio;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.Modelos.sorteio.Sorteio;
import com.flavio.android.megasena.enumeradores.Rota;
import com.flavio.android.megasena.interfaces.Subscriber;
import com.flavio.android.megasena.util.DataUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SorteioService {

    private DaoUltimoSorteio dao = null;

    public void buscaSorteiosAPartirDe(Subscriber<Sorteio> subscrito, String parameters){
        buscaNaApi(subscrito, Rota.ULTIMOS_POR_DATA, parameters);
    }

    public void buscarUltimoSorteio(Subscriber<Sorteio> subscrito){
        Context context = subscrito.context();
        buscaNoBancoInterno(context);
        this.dao = new DaoUltimoSorteio(context);

        if(precisaAtualizarUltimoSorteio()) {
            buscaNaApi(subscrito, Rota.ULTIMO_SORTEIO, null);
        } else {
            subscrito.alert(Validacao.getSorteio());
        }
    }

    public void buscaNaApi( Subscriber<Sorteio> subscrito, Rota rota, String parameters){
        Context context = subscrito.context();
        RequestQueue queue = Volley.newRequestQueue(context);
        String params = parameters == null || parameters.isEmpty() ? "" : "?".concat(parameters);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, rota.getUrl().concat(params), response -> {
            subscrito.alert(rota.get(response));
            persistirSorteio();
        }, volleyError -> {
            Toast.makeText ( context, "Não foi possivel obter as informações atualizadas, verifique sua conexão", Toast.LENGTH_LONG ).show ();
            volleyError.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept-Language", "*/*");
                params.put("Cookie", "security=true");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public boolean precisaAtualizarUltimoSorteio(){
        if(Validacao.getSorteio() == null
        || Validacao.getSorteio().id == null
        || Validacao.getSorteio().dataProximoConcurso == null) return true;
        try {
            final Date agora = new Date();
            String dataString= Validacao.getSorteio().dataProximoConcurso + " 21:00:00" ;
            final Date dataHoraSorteio = DataUtil.toDataHoraBr(dataString);
            return dataHoraSorteio.before(agora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void buscaNoBancoInterno(Context context){
        if(dao == null)
            dao = new DaoUltimoSorteio(context);
        Validacao.setSorteio(dao.buscarUltimoSorteio());
    }

    public void persistirSorteio(){
        dao.persistir(Validacao.getSorteio());
    }

    public void log(Object log){
        System.out.println("**************************************************");
        System.out.println(log);
        System.out.println("**************************************************");
    }


}
