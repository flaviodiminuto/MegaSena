

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


    public Sequencia getSequencia(int indice){
        return this.sequencias.get(indice);
    }

    public List<Sequencia> getSequencias(){
        return this.sequencias;
    }
 
    /**
     * Preenche uma sequencia já definida e adiciona na sequencia da lista de indice informado
     * @param indice da sequenecia na lista de sequencias que será preenchida
     * @param numeros que serão atribuídos a sequencia
     */
    public void setSequencia(int indice, int[] numeros){
        this.sequencias.get(indice).setNumeros(numeros);
    }
    
    /**
     * Adiciona uma sequencia a lista com a se quencia de números recebidos
     * @param numeros 
     */
    public void adicionaSequencia(int[] numeros){
        Sequencia seq = new Sequencia(numeros);
        this.sequencias.add(seq);
        quantidadeSequencias++;
    }
    /**
     * Gera e adiciona na lista de sequencias uma quantidade de sequencias de mesmo tamanho
     * @param quantidade de sequencias a serem adicionadas
     * @param tamanho da sequencia a ser gerada e adicionada
     */
    public void adicionaSequencia(int quantidade,int tamanho){
        Sequencia s;
        for(int i = 0; i<quantidade; i++){
/*-------------------------------------------------------------
  O limite para o número de sequencias é 1.000 sequencias
-------------------------------------------------------------*/
            if(sequencias.size()<1000){
                boolean repetido;
                do{
                    repetido=false;
                    s = new Sequencia(tamanho);

//Caso a sequencia já tenha sido sorteada não adiciona na lista e sortea novamente.
                        for(Sequencia sequencia: this.sequencias){
                            if (sequencia.sequenciaEncontrada(s)){
                                repetido = true;
                                break;
                            }
                        }
//Caso a sequencia numérica seja inédita na aposta, adiciona na lista de sequencias
                    if(!repetido) {
                        this.sequencias.add(s);
                        quantidadeSequencias++;
                    }
                }while(repetido);
            }else{
                break;
            }
        }

/*-------------------------------------------------------------
      Remove uma sequencia da aposta
/*-------------------------------------------------------------*/
      calculaValor ();
    }

    public void removerSequencia(int id){
        this.sequencias.remove ( id );
        this.quantidadeSequencias--;
        calculaValor ();
    }

    public boolean isPremiado() {
        return premiado;
    }

    public void setPremiado(boolean premiado) {
        this.premiado = premiado;
    }

    private void calculaValor(){
        this.valor=0;
        for(Sequencia seq : this.sequencias){
            this.valor+=seq.getValor();
        }
    }

    public void setSequencias(ArrayList <Sequencia> sequencias) {
        this.sequencias = sequencias;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * 
     * @return valor total da aposta
     */
    public double getValor(){
        return this.valor;
    }

    /**
     * Verifica se a sequencia informada está contida na aposta
     * @param seq contendo os números a serem verificados
     * @return true caso a sequencia esta contida na aposta
     */
    public boolean verificaSorteio(Sequencia seq){
        for(Sequencia sequencia : this.sequencias){
            if(sequencia.sequenciaEncontrada(seq)) return true;
        }
        return false;
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
    public String getJson(){
        Gson g = new Gson();
        return g.toJson ( this );
    }

}
