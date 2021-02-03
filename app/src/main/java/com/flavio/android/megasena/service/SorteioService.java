package com.flavio.android.megasena.service;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flavio.android.megasena.Modelos.sorteio.UltimoSorteioDTO;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.interfaces.subscriber;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SorteioService {
    public void buscarUltimoSorteio(subscriber<UltimoSorteioDTO> subscrito){
        if (sorteioValido()) {
            subscrito.alert(Validacao.getUltimoSorteioDTO());
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(subscrito.context());
        String url = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0KO6H80AU71KG7J0072/res/id=buscaResultado/c=cacheLevelPage/?timestampAjax=";
        url += new Date().getTime();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Gson g = new Gson();
            UltimoSorteioDTO ultimoSorteioDTO =  g.fromJson(response, UltimoSorteioDTO.class);
            Validacao.setUltimoSorteioDTO(ultimoSorteioDTO);
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

    public boolean sorteioValido() {
        return Validacao.getUltimoSorteioDTO() != null &&
                Validacao.getUltimoSorteioDTO().numero != 0;
    }
}
