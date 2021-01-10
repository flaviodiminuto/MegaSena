package com.flavio.android.megasena.service;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Sequencia;

import org.junit.Test;

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
        int[] numerosVerificados = {1,2,3,4,5,6};
        Sequencia sequenciaVerificada = new Sequencia(numerosVerificados);

        assertTrue(apostaService.verificaSorteio(aposta,sequenciaVerificada));
    }

    @Test
    public void verificaSorteioApostaVazia(){
        Aposta aposta = new Aposta();
        int[] numerosVerificados = {1,2,3,4,5,6};
        Sequencia sequenciaVerificada = new Sequencia(numerosVerificados);

        assertFalse(apostaService.verificaSorteio(aposta,sequenciaVerificada));
    }

    @Test
    public void verificaSorteioSequenciaSemelhante(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,10,6);
        int[] numeros = {1,2,3,4,5,6,7,8,9,10,11};
        apostaService.adicionaSequencia(aposta,numeros);
        int[] numerosVerificados = {1,2,3,4,5,12};
        Sequencia sequenciaVerificada = new Sequencia(numerosVerificados);

        assertFalse(apostaService.verificaSorteio(aposta,sequenciaVerificada));
    }

    @Test
    public void verificaSorteioSequenciaComQuinze(){
        Aposta aposta = new Aposta();
        apostaService.adicionaSequencia(aposta,10,6);
        int[] numeros = {1,2,3,4,5,6,7,8,9,10,11};
        apostaService.adicionaSequencia(aposta,numeros);
        int[] numerosVerificados = {11,5,4,3,2,6};
        Sequencia sequenciaVerificada = new Sequencia(numerosVerificados);

        assertTrue(apostaService.verificaSorteio(aposta,sequenciaVerificada));
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

        assertFalse(apostaService.verificaSorteio(aposta,sequenciaVerificada));
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

}
