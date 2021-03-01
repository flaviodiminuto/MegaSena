package com.flavio.android.megasena.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ProcessamentoHistoricoServiceTest {

    ProcessamentoHistoricoService service ;

    @Before
    public void setup(){
        this.service = new ProcessamentoHistoricoService();
    }

    @After
    public void tearDown(){
        this.service = null;
    }

    @Test
    public void testOrdenarCrescente(){
        Integer[][] numeros = {
                {2, 5},
                {5, 10},
                {3, 1}
        };
        service.ordenarCrescente(numeros);

        int quantidade = numeros[0][1];
        assertEquals(1, quantidade);

        quantidade = numeros[1][1];
        assertEquals(5, quantidade);

        quantidade = numeros[2][1];
        assertEquals(10, quantidade);
    }


    @Test
    public void testOrdenarDecrescente(){
        Integer[][] numeros = {
                {2, 5},
                {5, 10},
                {3, 1}
        };
        service.ordenarDecrescente(numeros);

        int quantidade = numeros[0][1];
        assertEquals(10, quantidade);

        quantidade = numeros[1][1];
        assertEquals(5, quantidade);

        quantidade = numeros[2][1];
        assertEquals(1, quantidade);
    }

    @Test
    public void testMapToArray(){
        Integer[][] array = {
                {2, 5},
                {5, 10},
                {3, 1}
        };
        Map<Integer, Integer> map = new LinkedHashMap<>();
        map.put(2,5);
        map.put(5,10);
        map.put(3,1);

        Integer[][] arrayRetorno = service.mapToArray(map);

        assertEquals(arrayRetorno[0][0], array[0][0]);
        assertEquals(arrayRetorno[0][1], array[0][1]);

        assertEquals(arrayRetorno[1][0], array[1][0]);
        assertEquals(arrayRetorno[1][1], array[1][1]);

        assertEquals(arrayRetorno[2][0], array[2][0]);
        assertEquals(arrayRetorno[2][1], array[2][1]);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAdicionarNumero() {
        Map<Integer,Integer> map = new HashMap<>();

        service.incrementarQuantidade(map, 1);
        service.incrementarQuantidade(map,2);
        service.incrementarQuantidade(map,3);
        service.incrementarQuantidade(map, 1);

        int quantidade;
        quantidade = map.get(1);
        assertEquals(2, quantidade);

        quantidade = map.get(2);
        assertEquals(1, quantidade);

        quantidade = map.get(3);
        assertEquals(1, quantidade);
    }
}
