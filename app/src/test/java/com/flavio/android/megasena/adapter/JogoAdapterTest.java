package com.flavio.android.megasena.adapter;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Sequencia;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.Modelos.sorteio.UltimoSorteioDTO;
import com.flavio.android.megasena.service.ApostaService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class JogoAdapterTest {
    ApostaService apostaService = new ApostaService();

    @Before
    public void setup(){
        Validacao.setUltimoSorteioDTO(new UltimoSorteioDTO());
        Validacao.getUltimoSorteioDTO().listaDezenas = Arrays.asList("01", "02", "03", "04", "05", "06");
    }

    @After
    public void tearDown(){
        Validacao.setUltimoSorteioDTO(null);
    }

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
        apostaService.adicionaSequencia(aposta,numeros);
        JogosAdapter adapter = new JogosAdapter(aposta.getSequencias());
        int i = 0;
        while (++i < 7) {
            Assert.assertTrue(adapter.isNumeroSorteado(i));
        }
    }

    @Test
    public void numerosNaoSorteados(){
        Aposta aposta = new Aposta();
        int[] numeros = {1,2,3,4,5,6};
        apostaService.adicionaSequencia(aposta,numeros);
        JogosAdapter adapter = new JogosAdapter(aposta.getSequencias());
        Assert.assertFalse(adapter.isNumeroSorteado(7));
        Assert.assertFalse(adapter.isNumeroSorteado(8));
    }
}
