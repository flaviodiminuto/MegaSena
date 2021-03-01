package com.flavio.android.megasena.service;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flavio.android.megasena.Dao.DaoUltimoSorteio;
import com.flavio.android.megasena.Modelos.sorteio.UltimoSorteioDTO;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.interfaces.Subscriber;
import com.flavio.android.megasena.util.DataUtil;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SorteioService {

    private DaoUltimoSorteio dao = null;

    public void buscarUltimoSorteio(Subscriber<UltimoSorteioDTO> subscrito){
        Context context = subscrito.context();
        buscaNoBancoInterno(context);
        this.dao = new DaoUltimoSorteio(context);

        if(precisaAtualizarUltimoSorteio()) {
            buscaNaApi(context, subscrito);
        } else {
            subscrito.alert(Validacao.getUltimoSorteioDTO());
        }
    }

    public void buscaNaApi(Context context, Subscriber<UltimoSorteioDTO> subscrito){
        log("Realizando requisição à API para obter ultimo sorteio");
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0KO6H80AU71KG7J0072/res/id=buscaResultado/c=cacheLevelPage/?timestampAjax=";
        url += new Date().getTime();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Gson g = new Gson();
            UltimoSorteioDTO ultimoSorteioDTO =  g.fromJson(response, UltimoSorteioDTO.class);
            Validacao.setUltimoSorteioDTO(ultimoSorteioDTO);
            persistirSorteio();
            subscrito.alert(Validacao.getUltimoSorteioDTO());
        }, Throwable::printStackTrace){
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
        if(Validacao.getUltimoSorteioDTO() == null) return true;
        try {
            final Date agora = new Date();
            String dataString= Validacao.getUltimoSorteioDTO().dataProximoConcurso + " 21:00:00" ;
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
        log("Buscando ultimo sorteio no banco interno");
        Validacao.setUltimoSorteioDTO(dao.buscarUltimoSorteio());
    }

    public void persistirSorteio(){
        log("Persistindo sorteio obtido na busca da API");
        dao.persistir(Validacao.getUltimoSorteioDTO());
    }


    public void log(String log){
        System.out.println("**************************************************");
        System.out.println(log);
        System.out.println("**************************************************");
    }
}
