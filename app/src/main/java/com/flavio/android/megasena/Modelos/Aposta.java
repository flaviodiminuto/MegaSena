

package com.flavio.android.megasena.Modelos;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Aposta  {
    private int id;
    private ArrayList<Sequencia> sequencias = new ArrayList<Sequencia>();
    private boolean premiado;
    private double valor;
    private int quantidadeSequencias;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidadeSequencias(){
        return this.quantidadeSequencias;
    }

    public void setQuantidadeSequencias(int quantidadeSequencias) {
        this.quantidadeSequencias = quantidadeSequencias;
    }

    public List<Sequencia> getSequencias(){
        return this.sequencias;
    }

    public boolean isPremiado() {
        return premiado;
    }

    public void setPremiado(boolean premiado) {
        this.premiado = premiado;
    }

    public void setSequencias(ArrayList <Sequencia> sequencias) {
        this.sequencias = sequencias;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValor(){
        return this.valor;
    }

    /**
     * 
     * @return uma string, da lista de todas as sequencais de uma aposta
     */
    @Override
    public String toString() {
        String resposta="";
        for(Sequencia montante1 : this.sequencias) {
            resposta +="Jogo "+ sequencias.indexOf(montante1)+": \t"+ montante1.toString()+"\n\n";
        }
        return resposta;
    }
}
