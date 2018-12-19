
package Modelos;

import java.text.DecimalFormat;


public class MegaSena {

    public static void main(String[] args) {
        // TODO code application logic here
        Aposta aposta = new Aposta();
        Sequencia seq = new Sequencia();
        int[] numeros = {10,12,13,14,18,31};
        int[] numeros2 = {10,12,13,14,18,31,4,1,5};
/*-------------------------------------------------------
Gera sequencias e exibe sequencias geradas na aposta
-------------------------------------------------------*/
        aposta.adicionaSequencia(60, 10);
        
        seq.setNumeros(numeros);
        aposta.setSequencia(0, numeros2);
        
        System.out.println(aposta.toString());
        
/*-------------------------------------------------------
Formata e exibe o valor com a soma das sequencias desta aposta
-------------------------------------------------------*/
        DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
        System.out.printf("Valor total da aposta: %s%n",df.format(aposta.getValor()));
        
        if(seq.sequenciaEncontrada(aposta.getSequencia(0))){
            System.out.printf("%s Sequencia encontrada\t%s%n",seq,aposta.getSequencia(0));
        }
        
        if(aposta.verificaSorteio(seq)){
            System.out.println("Parabéns, você GANHOU na MEGA SENA!");
        }
        
    }
    
}
