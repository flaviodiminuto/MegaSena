package com.flavio.android.megasena.adapter;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Sequencia;
import com.flavio.android.megasena.Modelos.Validacao;

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

    @Test
    public void numerosSorteados(){
        Aposta aposta = new Aposta();
        int[] numeros = {1,2,3,4,5,6};
        aposta.adicionaSequencia(numeros);
        JogosAdapter adapter = new JogosAdapter(aposta.getSequencias());
        Validacao.setNumerosSorteados(numeros);
        int i = 0;
        while (++i < 7) {
            Assert.assertEquals(true, adapter.isNumeroSorteado(i));
        }
    }

    @Test
    public void numerosNaoSorteados(){
        Aposta aposta = new Aposta();
        int[] numeros = {1,2,3,4,5,6};
        aposta.adicionaSequencia(numeros);
        JogosAdapter adapter = new JogosAdapter(aposta.getSequencias());
        Validacao.setNumerosSorteados(numeros);
        Assert.assertEquals(false, adapter.isNumeroSorteado(7));
        Assert.assertEquals(false, adapter.isNumeroSorteado(8));
    }
}
