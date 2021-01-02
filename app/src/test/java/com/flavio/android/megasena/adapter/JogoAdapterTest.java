package com.flavio.android.megasena.adapter;

import com.flavio.android.megasena.Modelos.Sequencia;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class JogoAdapterTest {

    @Test
    public void seisNumeros(){
        JogosAdapter adapter = new JogosAdapter(new ArrayList<Sequencia>());
        int quantidadeDeLinhas = adapter.quantidadeLinhas(6);

        Assert.assertEquals(2, quantidadeDeLinhas);
    }

    @Test
    public void dezNumeros(){
        JogosAdapter adapter = new JogosAdapter(new ArrayList<Sequencia>());
        int quantidadeDeLinhas = adapter.quantidadeLinhas(10);

        Assert.assertEquals(2, quantidadeDeLinhas);
    }

    @Test
    public void dozeNumeros(){
        JogosAdapter adapter = new JogosAdapter(new ArrayList<Sequencia>());
        int quantidadeDeLinhas = adapter.quantidadeLinhas(12);

        Assert.assertEquals(3, quantidadeDeLinhas);
    }

    @Test
    public void quinzeNumeros(){
        JogosAdapter adapter = new JogosAdapter(new ArrayList<Sequencia>());
        int quantidadeDeLinhas = adapter.quantidadeLinhas(15);

        Assert.assertEquals(3, quantidadeDeLinhas);
    }
}
