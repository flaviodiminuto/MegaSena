package com.flavio.android.megasena.service;

import android.content.Context;

import com.flavio.android.megasena.Dao.DaoApostaSequencia;
import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Sequencia;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApostaService {
    private List<Sequencia> sequenciasComMaisAcertos = new ArrayList<>();
    private List<Sequencia> sequenciasComZeroAcertos = new ArrayList<>();
    private List<Sequencia> sequenciasComUmAcerto = new ArrayList<>();
    private List<Sequencia> sequenciasComDoisAcertos = new ArrayList<>();
    private List<Sequencia> sequenciasComTresAcertos = new ArrayList<>();
    private List<Sequencia> sequenciasComQuatroAcertos = new ArrayList<>();
    private List<Sequencia> sequenciasComCincoAcertos = new ArrayList<>();
    private List<Sequencia> sequenciasComSeisAcertos = new ArrayList<>();

    private DaoApostaSequencia daoApostaSequencia;

    /**
     * Gera e adiciona na lista de sequencias uma quantidade de sequencias de mesmo tamanho
     * @param quantidade de sequencias a serem adicionadas
     * @param tamanho da sequencia a ser gerada e adicionada
     */
    public void adicionaSequencia(Aposta aposta, int quantidade,int tamanho){
        Sequencia s;
        for(int i = 0; i<quantidade; i++){
    /*-------------------------------------------------------------
      O limite para o número de sequencias é 1.000 sequencias
    -------------------------------------------------------------*/
            if(aposta.getSequencias().size()<1000){
                boolean repetido;
                do{
                    repetido=false;
                    s = new Sequencia(tamanho);

    //Caso a sequencia já tenha sido sorteada não adiciona na lista e sortea novamente.
                    for(Sequencia sequencia: aposta.getSequencias()){
                        if (sequenciaEncontrada(sequencia, s)){
                            repetido = true;
                            break;
                        }
                    }
    //Caso a sequencia numérica seja inédita na aposta, adiciona na lista de sequencias
                    if(!repetido) {
                        aposta.getSequencias().add(s);
                        aposta.setQuantidadeSequencias(aposta.getSequencias().size());
                    }
                }while(repetido);
            }else{
                break;
            }
        }
        calculaValor (aposta);
    }

    /**
     * Adiciona uma sequencia a lista com a se quencia de números recebidos
     * <b>OBS: Permite adicionar sequencia repetida</b>
     * @param numeros
     */
    public void adicionaSequencia(Aposta aposta, int[] numeros){
        Sequencia seq = new Sequencia(numeros);
        aposta.getSequencias().add(seq);
        aposta.setQuantidadeSequencias(aposta.getSequencias().size());
    }

    /*-------------------------------------------------------------
          Remove uma sequencia da aposta
    /*-------------------------------------------------------------*/
    public void removerSequencia(Aposta aposta,int id){
        aposta.getSequencias().remove ( id );
        aposta.setQuantidadeSequencias(aposta.getSequencias().size());
        calculaValor(aposta);
    }

    /**
     * Compara se a sequencia numérica recebida está registrada nesta aposta
     * Serve tanto para preencher sem repetir sequencias quanto para
     * verificar se a numeração desta sequencia foi sorteada
     * @param desejada
     * @return boolean
     */
    public boolean sequenciaEncontrada(Sequencia verificada, Sequencia desejada){
        desejada.ordenar();
    /*-------------------------------------------------------------
      Caso as duas sequencias tenham o mesmo tamanho e  números retorna verdadeiro
    -------------------------------------------------------------*/
        if(Arrays.equals(verificada.getNumeros(), desejada.getNumeros()))return true;
    /*-------------------------------------------------------------
      Verifica se a sequencia sorteada está contida na sequencia verificada
    //Compara cada número da sequencia sorteada com cada número da sequencia verificada
    -------------------------------------------------------------*/
        for(int i = 0; i<desejada.getTamanho(); i++){
            for(int j=0; j<verificada.getTamanho(); j++){
    /*-------------------------------------------------------------
      Caso o número seja igual então podemos verificar o próximo número da sequencia recebida
    -------------------------------------------------------------*/
                if(desejada.getNumeros()[i]==verificada.getNumeros()[j])
                    break; //interrompe a iteracao do for interno
    /*-------------------------------------------------------------
      Caso o verifiquemos toda a sequencia e não encontrarmos este número da sequencia sorteada
      então a sequencia sorteada não está contida na verificada e retorna false
    -------------------------------------------------------------*/
                if(j==(verificada.getTamanho()-1)
                        && desejada.getNumeros()[i]!=verificada.getNumeros()[j])
                    return false;
            }
        }
        return true;
    /*-------------------------------------------------------------
      Fim do método sequenciaEncontrada
    -------------------------------------------------------------*/
    }

    public int quantidadeAcertos(Sequencia verificada, Sequencia desejada) {
        verificada.ordenar();
        desejada.ordenar();
        int acertos = 0;

        if (Arrays.equals(verificada.getNumeros(), desejada.getNumeros()))
            return verificada.getNumeros().length;

        for (int i = 0; i < desejada.getTamanho(); i++) {
            for (int j = 0; j < verificada.getTamanho(); j++) {
                if (desejada.getNumeros()[i] == verificada.getNumeros()[j]) {
                    acertos++;
                    break; //interrompe a iteracao do for interno
                }
            }
        }
        return acertos;
    }

    private void calculaValor(Aposta aposta){
        double valor = 0;
        for(Sequencia sequencia : aposta.getSequencias()){
            valor += sequencia.getValor();
        }
        aposta.setValor(valor);
    }

    /**
     * Verifica se a sequencia informada está contida na aposta
     * @param sorteada contendo os números a serem verificados
     * @return true caso a sequencia esta contida na aposta
     */
    public boolean verificaSorteio(Aposta aposta, Sequencia sorteada){

        for (Sequencia sequenciaVerificada : aposta.getSequencias()) {
            switch (quantidadeAcertos(sequenciaVerificada,sorteada)){
                case 0: sequenciasComZeroAcertos.add(sequenciaVerificada);break;
                case 1: sequenciasComUmAcerto.add(sequenciaVerificada); break;
                case 2: sequenciasComDoisAcertos.add(sequenciaVerificada); break;
                case 3: sequenciasComTresAcertos.add(sequenciaVerificada); break;
                case 4: sequenciasComQuatroAcertos.add(sequenciaVerificada); break;
                case 5: sequenciasComCincoAcertos.add(sequenciaVerificada); break;
                default: sequenciasComSeisAcertos.add(sequenciaVerificada); break;
            }
        }

        return !sequenciasComQuatroAcertos.isEmpty()
                || !sequenciasComCincoAcertos.isEmpty()
                || !sequenciasComSeisAcertos.isEmpty();
    }

    public String getJson(Aposta aposta){
        Gson g = new Gson();
        return g.toJson ( aposta );
    }

    public Map<Integer, Integer> quantidadeAcertosMap() {
        Map<Integer, Integer> quantidadesSorteadas = new HashMap<>();
        quantidadesSorteadas.put(0, sequenciasComZeroAcertos.size());
        quantidadesSorteadas.put(1, sequenciasComUmAcerto.size());
        quantidadesSorteadas.put(2, sequenciasComDoisAcertos.size());
        quantidadesSorteadas.put(3, sequenciasComTresAcertos.size());
        quantidadesSorteadas.put(4, sequenciasComQuatroAcertos.size());
        quantidadesSorteadas.put(5, sequenciasComCincoAcertos.size());
        quantidadesSorteadas.put(6, sequenciasComSeisAcertos.size());
        return quantidadesSorteadas;
    }

    public List<Sequencia> getSequenciaComMaisAcertos(int quantidade) {
        List<Sequencia> sequenciaList = new ArrayList<>();
        sequenciaList.addAll(sequenciasComSeisAcertos);
        sequenciaList.addAll(sequenciasComCincoAcertos);
        sequenciaList.addAll(sequenciasComQuatroAcertos);
        sequenciaList.addAll(sequenciasComTresAcertos);
        sequenciaList.addAll(sequenciasComDoisAcertos);
        sequenciaList.addAll(sequenciasComUmAcerto);
        sequenciaList.addAll(sequenciasComZeroAcertos);

        return sequenciaList.subList(0,quantidade);
    }

    public Aposta getApostaCompletaById(int apostaId, Context context){
        this.daoApostaSequencia = new DaoApostaSequencia(context);
        return this.daoApostaSequencia.consultaApostaCompletaById(apostaId);
    }

    public List<Sequencia> getSequenciasComZeroAcertos() {
        return sequenciasComZeroAcertos;
    }

    public List<Sequencia> getSequenciasComUmAcerto() {
        return sequenciasComUmAcerto;
    }

    public List<Sequencia> getSequenciasComDoisAcertos() {
        return sequenciasComDoisAcertos;
    }

    public List<Sequencia> getSequenciasComTresAcertos() {
        return sequenciasComTresAcertos;
    }

    public List<Sequencia> getSequenciasComQuatroAcertos() {
        return sequenciasComQuatroAcertos;
    }

    public List<Sequencia> getSequenciasComCincoAcertos() {
        return sequenciasComCincoAcertos;
    }

    public List<Sequencia> getSequenciasComSeisAcertos() {
        return sequenciasComSeisAcertos;
    }
}
