package com.flavio.android.megasena.service;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Sequencia;
import com.flavio.android.megasena.Modelos.sorteio.Sorteio;
import com.flavio.android.megasena.Modelos.Validacao;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ApostaServiceTest {

    ApostaService apostaService = new ApostaService();

    @Test
    public void adicionarSequenciaQuantidadeTamanho(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,1,6);

        assertEquals(1, aposta.getQuantidadeSequencias());
        assertEquals(6, aposta.getSequencias().get(0).getTamanho());
    }

    @Test
    public void adicionarSequenciaTamanhoMenorQueSeis(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,1,5);

        assertEquals(6, aposta.getSequencias().get(0).getTamanho());
    }

    @Test
    public void adicionarSequenciaTamanhoMaiorQueQuinze(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,1,16);

        assertEquals(15, aposta.getSequencias().get(0).getTamanho());
    }

    @Test
    public void adicionarSequenciaTamanhoIgualSeis(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,1,6);

        assertEquals(6, aposta.getSequencias().get(0).getTamanho());
    }

    @Test
    public void adicionarSequenciaTamanhoIgualQuinze(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,1,15);

        assertEquals(15, aposta.getSequencias().get(0).getTamanho());
    }

    @Test
    public void adicionarDezSequenciasQuantidadeTamanho(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,10,1);

        assertEquals(10, aposta.getSequencias().size());
        assertEquals(10,aposta.getQuantidadeSequencias());
    }

    @Test
    public void adicionarSequenciaPredefinida(){
        Aposta aposta = new Aposta();
        int[] numeros = {1,2,3,4,5,6};
        int[] verificados = {1,2,3,4,5,6};
        apostaService.adicionaSequencia(aposta,numeros);

        assertArrayEquals(verificados, aposta.getSequencias().get(0).getNumeros());
    }

    @Test
    public void verificarSequenciaFluxoOtimo(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,10,6);
        int[] numeros = {1,2,3,4,5,6};
        apostaService.adicionaSequencia(aposta,numeros);
        Validacao.setSorteio(new Sorteio());
        Validacao.getSorteio().listaDezenas = Arrays.asList("1", "2", "3", "4", "5", "6");

        assertTrue(apostaService.verificaSorteio(aposta));
    }

    @Test
    public void verificaSorteioApostaVazia(){
        Aposta aposta = new Aposta();
        Validacao.setSorteio(new Sorteio());
        Validacao.getSorteio().listaDezenas = Arrays.asList("1", "2", "3", "4", "5", "6");

        assertFalse(apostaService.verificaSorteio(aposta));
    }

    @Test
    public void verificaSorteioSequenciaComCincoAcertos(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,10,6);
        int[] numeros = {1,2,3,4,5,6,7,8,9,10,11};
        apostaService.adicionaSequencia(aposta,numeros);
        Validacao.setSorteio(new Sorteio());
        Validacao.getSorteio().listaDezenas = Arrays.asList("1", "2", "3", "4", "5", "12");

        assertTrue(apostaService.verificaSorteio(aposta));
    }

    @Test
    public void verificaSorteioSequenciaComQuatroAcertos(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,10,6);
        int[] numeros = {1,2,3,4,5,6,7,8,9,10,11};
        apostaService.adicionaSequencia(aposta,numeros);
        Validacao.setSorteio(new Sorteio());
        Validacao.getSorteio().listaDezenas = Arrays.asList("1", "2", "3", "4", "13", "12");

        assertTrue(apostaService.verificaSorteio(aposta));
    }

    @Test
    public void verificaSorteioSequenciaComSeisAcertos(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,10,6);
        int[] numeros = {1,2,3,4,5,6,7,8,9,10,11};
        apostaService.adicionaSequencia(aposta,numeros);
        int[] numerosVerificados = {1,2,3,4,5,11};
        Validacao.setSorteio(new Sorteio());
        Validacao.getSorteio().listaDezenas = Arrays.asList("1", "2", "3", "4", "5", "11");

        assertTrue(apostaService.verificaSorteio(aposta));
    }

    @Test
    public void verificaSorteioSequenciaComQuinze(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,10,6);
        int[] numeros = {1,2,3,4,5,6,7,8,9,10,11};
        apostaService.adicionaSequencia(aposta,numeros);
        int[] numerosVerificados = {11,5,4,3,2,6};
        Sequencia sequenciaVerificada = new Sequencia(numerosVerificados);

        assertTrue(apostaService.verificaSorteio(aposta));
    }

    @Test
    public void verificarRemoverSequencia(){
        Aposta aposta = new Aposta();
        int[] numeros = {2,3,4,5,6,11};
        apostaService.adicionaSequencia(aposta,numeros);
        int[] numerosVerificados = {11,5,4,3,2,6};
        Sequencia sequenciaVerificada = new Sequencia(numerosVerificados);
        apostaService.adicionaSequencia(aposta,2,6);

        apostaService.removerSequencia(aposta,0);

        assertFalse(apostaService.verificaSorteio(aposta));
        assertEquals(2, aposta.getQuantidadeSequencias());
        assertEquals(2, aposta.getSequencias().size());
    }

    @Test
    public void calcularValorAposta(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,5,6);
        apostaService.adicionaSequencia(aposta,5,15);

        Sequencia sequenciaComSeisNumeros = new Sequencia(6);
        Sequencia sequenciaComQuinzeNumeros = new Sequencia(15);

        Double valorDesejado = (5 * sequenciaComQuinzeNumeros.getValor())
        + ( 5 * sequenciaComSeisNumeros.getValor());

        assertEquals(valorDesejado, aposta.getValor(), 0.0);
    }

    @Test
    public void VerificaQuantidadeDeAcertos(){
        int[] numeros = {1,2,3,4,5,6,7,8,9,10,11};
        Sequencia verificada = new Sequencia(numeros);

        int[] numerosSorteados1 = {2,12,13,14,15,16};
        int[] numerosSorteados2 = {2,9,13,14,15,16};
        int[] numerosSorteados3 = {2,9,1,14,15,16};
        int[] numerosSorteados4 = {2,9,1,14,15,11};
        int[] numerosSorteados5 = {2,9,1,7,15,11};
        int[] numerosSorteados6 = {11,5,4,3,2,6};

        Sequencia umacerto = new Sequencia(numerosSorteados1);
        Sequencia doisAcertos = new Sequencia(numerosSorteados2);
        Sequencia tresAcertos = new Sequencia(numerosSorteados3);
        Sequencia quatroAcertos = new Sequencia(numerosSorteados4);
        Sequencia cincoAcertos = new Sequencia(numerosSorteados5);
        Sequencia seisAcertos = new Sequencia(numerosSorteados6);

        assertEquals(1, apostaService.quantidadeAcertos(verificada,umacerto));
        assertEquals(2, apostaService.quantidadeAcertos(verificada,doisAcertos));
        assertEquals(3, apostaService.quantidadeAcertos(verificada,tresAcertos));
        assertEquals(4, apostaService.quantidadeAcertos(verificada,quatroAcertos));
        assertEquals(5, apostaService.quantidadeAcertos(verificada,cincoAcertos));
        assertEquals(6, apostaService.quantidadeAcertos(verificada,seisAcertos));
    }

}
