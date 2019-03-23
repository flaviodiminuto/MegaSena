
package com.flavio.android.megasena.Modelos.Modelos;

import java.util.Arrays;
import java.util.Random;

public class Sequencia {
    private int id;
    private int tamanho;
    private int[] numeros;  
    private double valor;
    private boolean sorteado;
    
    /**
     * Gera uma sequencia de tamanho padrão 6 números
     * 
     * Exemplo:
     * Sequencia seq = new Sequencia();
     */
    public Sequencia(){
        this.tamanho = 6;
        numeros = new int[this.tamanho];
        this.sorteado= false;
        gerarSequencia();
    }
    
    /**
     * Gera uma sequencia do tamalho passado como parametro
     * 
     * Exemplo:
     * Sequencia seq = new Sequencia(7);
     * 
     * @param tamanho 
     */
    public Sequencia(int tamanho){
        if(tamanho<6) this.tamanho=6;
        if(tamanho>15) this.tamanho=15;
        this.tamanho = tamanho;
        this.numeros = new int[this.tamanho];
        this.sorteado= false;
        gerarSequencia();
    }
    
    /**
     * Gera uma sequencia com os números informados de 6 a 15 números
     * 
     * Exemplo: 
     * int[] numeros = {1,2,5,12,15,60};
     * Sequencia seq = new Sequencia(numeros);
     * 
     * @param numeros 
     */
    public Sequencia(int[] numeros){
        int quantidade = numeros.length;
        if(quantidade<6) this.tamanho=6;
        if(quantidade>15) this.tamanho=15;
        this.tamanho = quantidade;
        this.numeros = new int[this.tamanho];
        this.sorteado= false;
        System.arraycopy(numeros, 0, this.numeros, 0, this.tamanho);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int[] getNumeros() {
        return numeros;
    }

    public void setNumeros(int[] numeros) {
        this.numeros = numeros;
        this.tamanho = numeros.length;
    }

    public boolean istSorteado() {
        return sorteado;
    }

    public void setSorteado(boolean sorteado) {
        this.sorteado = sorteado;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    /*-------------------------------------------------------------
       O valor sempre será preenchido com a geração da sequencia
    -------------------------------------------------------------*/
    private void setValor(){
        switch(this.tamanho){
            case 6:
                this.valor = 3.5;
                break;
            case 7:
                this.valor = 24.5;
                break;
            case 8:
                this.valor = 98;
                break;
            case 9:
                this.valor = 294;
                break;
            case 10:
                this.valor = 3735;
                break;
            case 11:
                this.valor = 1617;
                break;
            case 12:
                this.valor = 3234;
                break;
            case 13:
                this.valor = 6006;
                break;
            case 14:
                this.valor = 10510.5;
                break;
            case 15:
                this.valor = 17517.5;
                break;
        }
    }
    

    public void gerarSequencia(){
        Random random = new Random();
        int sorteado;
        boolean repetido;
        for(int i = 0; i<this.tamanho; i++){
/*-------------------------------------------------------------
   Verifica se o número sorteado não é repetido
-------------------------------------------------------------*/            
            do{
                repetido=false;
                sorteado = random.nextInt(60)+1;
                for(int j =0;j<i;j++){
                    if(sorteado == this.numeros[j]){
                        repetido =true;                        
                    }
                }
            }while(repetido);
/*-------------------------------------------------------------
   Adiciona o valor inedito na sequencia
-------------------------------------------------------------*/
            this.numeros[i] = sorteado;
        }
        ordenar();
        setValor();
    }
    
    /**
     * Ordena a sequencia de números em ordem crescente
     */
    public void ordenar(){
        int aux;
        for(int i = 0; i <this.tamanho; i++){
           for(int j = 0; j<this.tamanho; j++){
               if(this.numeros[j]>this.numeros[i]){
                   aux = this.numeros[j];
                   this.numeros[j] = this.numeros[i];
                   this.numeros[i]=aux;
               }
           } 
        }
    }

    /**
     * Compara se a sequencia numérica recebida está registrada nesta aposta
     * Serve tanto para preencher sem repetir sequencias quanto para
     * verificar se a numeração desta sequencia foi sorteada
     * @param seq
     * @return boolean
     */
    public boolean sequenciaEncontrada(Sequencia seq){
        seq.ordenar();
/*-------------------------------------------------------------
  Caso as duas sequencias tenham o mesmo tamanho e  números retorna verdadeiro
-------------------------------------------------------------*/
        if(Arrays.equals(this.numeros, seq.getNumeros()))return true;
/*-------------------------------------------------------------
  Caso a sequencia recebida seja maior que a sequencia atual
  retorna utilizando o metodo sequenciaEncontrada da sequencia maior
-------------------------------------------------------------*/
        if(seq.tamanho>this.tamanho) return seq.sequenciaEncontrada(this);//Recursivo
/*-------------------------------------------------------------
  Verifica se a sequencia enviada está contida na sequencia atual

//Compara cada número da sequencia recebida com cada número da sequencia atual
-------------------------------------------------------------*/
        for(int i = 0; i<seq.tamanho; i++){
            for(int j=0; j<this.tamanho; j++){
/*-------------------------------------------------------------
  Caso o número seja igual então podemos verificar o próximo número da sequencia recebida
-------------------------------------------------------------*/
                if(seq.getNumeros()[i]==this.numeros[j]){
                    break;
                }
/*-------------------------------------------------------------
  Caso o verifiquemos toda a sequencia e não encontrarmos este número da sequencia recebida
  então a sequencia recebida não está contida nesta e retorna false
-------------------------------------------------------------*/
                if(j==(this.tamanho-1) && seq.getNumeros()[i]!=this.numeros[j]) return false;
            }
        }
        return true;
/*-------------------------------------------------------------
  Fim do método sequenciaEncontrada
-------------------------------------------------------------*/ 
    }

    @Override
    public String toString() {
/*-------------------------------------------------------------
  *Inicia uma string vazia e preenche
  *preenche a string com os números da sequencia
  *retorna a string preenchida
-------------------------------------------------------------*/ 
        String resposta="";
        for(int i = 0; i < this.tamanho; i++){
            resposta += this.numeros[i] +"\t ";
        }
        return resposta;
/*-------------------------------------------------------------
  Fim do método sequenciaEncontrada
-------------------------------------------------------------*/ 
    }
    
}