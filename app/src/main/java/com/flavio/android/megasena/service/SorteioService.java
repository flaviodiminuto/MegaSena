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
import com.flavio.android.megasena.enumeradores.Periodo;
import com.flavio.android.megasena.enumeradores.Rota;
import com.flavio.android.megasena.interfaces.Subscriber;
import com.flavio.android.megasena.util.DataUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SorteioService {

    private DaoUltimoSorteio dao = null;

    public void buscaSorteiosAPartirDe(Subscriber subscrito, Periodo periodo, int quantidade){
        String queryString = String.format("quantidade=%d&data-inicial=%s".trim(), quantidade, periodo.dataString());
        buscaSorteiosAPartirDe(subscrito, queryString);
    }

    public void buscaSorteiosAPartirDe(Subscriber subscrito, String queryString){
        buscaNaApi(subscrito, Rota.ULTIMOS_POR_DATA, queryString);
    }

    public void buscarUltimoSorteio(Subscriber subscrito){
        Context context = subscrito.context();
        Sorteio sorteio = buscaNoBancoInterno(context);
        Validacao.setSorteio(sorteio);
        if(precisaAtualizarUltimoSorteio()) {
            buscaNaApi(subscrito, Rota.ULTIMO_SORTEIO, null);
        } else {
            subscrito.async_alert(Validacao.getSorteio());
        }
    }

    private void buscaNaApi( Subscriber subscrito, Rota rota, String queryString){
        Context context = subscrito.context();
        RequestQueue queue = Volley.newRequestQueue(context);
        String params = queryString == null || queryString.isEmpty() ? "" : "?".concat(queryString);
        String url = rota.getUrl().concat(params);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            subscrito.async_alert(rota.get(response));
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

    private Sorteio buscaNoBancoInterno(Context context){
        if(dao == null)
            dao = new DaoUltimoSorteio(context);
       return dao.buscarUltimoSorteio();
    }

    public void persistirSorteio(Sorteio sorteio){
        if(sorteio != null)
            dao.persistir(sorteio);
    }

    public void log(Object log){
        System.out.println("**************************************************");
        System.out.println(log);
        System.out.println("**************************************************");
    }


}
