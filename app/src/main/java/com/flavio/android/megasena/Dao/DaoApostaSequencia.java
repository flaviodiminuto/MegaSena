package com.flavio.android.megasena.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Sequencia;

import java.util.ArrayList;
import java.util.List;

public class DaoApostaSequencia {

    private static final String tabela = "aposta_sequencia";
    DaoGeneralista banco ;
    Context context;

/*--------------------------------------------------------------
    Inicializa o objeto DaoApostaSequencia e tenta criar a tabela "aposta" caso ainda não exista
--------------------------------------------------------------*/
    public DaoApostaSequencia(Context context) {
        this.context = context;
        banco = new DaoGeneralista ( this.context );
        this.criarTabela ();
    }

/*--------------------------------------------------------------
    Criação da tabela aposta_sequencia, chamada no construtor
--------------------------------------------------------------*/
    private boolean criarTabela(){
        String campos;
        campos =" 'aposta_id' integer , " +
                " 'sequencia_id' integer , " +
                " PRIMARY KEY('aposta_id','sequencia_id'), " +
                " FOREIGN KEY('aposta_id') REFERENCES 'aposta'('id'), " +
                " FOREIGN KEY('sequencia_id') REFERENCES 'sequencia'('id')" ;
        return this.banco.criarTabela ( this.tabela, campos);
    }

    public Aposta consultaApostaCompletaById(int id){
        String sql = "select aposta.*,sequencia.* from aposta_sequencia " +
                " inner join aposta On aposta.id = aposta_sequencia.aposta_id " +
                " inner join sequencia on sequencia.id = aposta_sequencia.sequencia_id " +
                " where aposta.id = "+id;
        Cursor cursor = this.banco.consulta ( sql );

        return cursorToApostaCompleta ( cursor );
    }

    public long inserirApostaCompleta(Aposta aposta){
        DaoAposta da = new DaoAposta ( this.context );
        DaoSequencia ds = new DaoSequencia ( this.context );
        long apostaId = da.inserirAposta ( aposta );
        long sequenciaId;
        if(apostaId>=0){
            ds.inserirSequencia(aposta.getSequencias());
            String sql = getSqlFromApostaSequenciaList(apostaId, ds.getIdsUltimasSequencias(aposta.getQuantidadeSequencias()));
            try {
                this.banco.exec(sql);
            }catch (Exception e){
                System.out.println ("Falha a inserir sequencia em DaoApostaCompleta");
                return -2;
            }
        }
        return apostaId;
    }

    private String getSqlFromApostaSequenciaList(long apostaId, List<Integer> sequencias) {
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tabela);
        sql.append(" (aposta_id, sequencia_id) VALUES ");
        for (Integer sequenciaId : sequencias) {
            sql.append("( ").append(apostaId).append(", ");
            sql.append(sequenciaId).append("),");
        }
        return sql.substring(0,sql.length()-1);
    }

    public Aposta cursorToApostaCompleta(Cursor cursor){
        if(cursor.moveToFirst ()) {
            Aposta aposta = new Aposta ();
            List<Sequencia> sequencias = new ArrayList <Sequencia> (  );
            Sequencia sequencia;
            int[] numeros;

            aposta.setId ( cursor.getInt ( 0 ) );
            aposta.setPremiado ( Boolean.parseBoolean (  cursor.getString ( 1)) );
            aposta.setValor ( cursor.getDouble ( 2 ) );
            aposta.setQuantidadeSequencias ( cursor.getInt ( 3 ) );

            do {
                    sequencia = new Sequencia ();
                    sequencia.setId ( cursor.getInt ( 4 ) );
                    sequencia.setTamanho ( cursor.getInt ( 5 ) );
                    sequencia.setValor ( cursor.getDouble ( 6 ) );
                    sequencia.setSorteado ( Boolean.parseBoolean ( cursor.getString ( 8 ) ) );
                    numeros = new int[sequencia.getTamanho ()];
                    for (int j = 0; j < sequencia.getTamanho (); j++) {
                        numeros[j] = cursor.getInt ( j + 8 );
                    }
                    sequencia.setNumeros ( numeros );
                    sequencias.add ( sequencia );
            }while(cursor.moveToNext ());
            aposta.setSequencias ( (ArrayList <Sequencia>) sequencias );
            return aposta;
        }
        return null;
    }



/*--------------------------------------------------------------
    Deleta um registro de  sequencia da aposta no banco de dados
--------------------------------------------------------------*/
    public boolean removerSequencia(int sequenciaId){
        DaoSequencia ds = new DaoSequencia ( this.context );
        DaoAposta da = new DaoAposta ( this.context );
        int apostaId;
        Aposta aposta;
        String sqlAposta= "select aposta_id from "+this.tabela+" where sequencia_id = "+sequenciaId;
        String sqlSequencia = "delete from "+this.tabela+ "where sequencia_id = "+sequenciaId;
        if(ds.deletarsequencia ( sequenciaId )) {
            if( this.banco.deletar ( this.tabela, sqlSequencia)){
                apostaId = this.banco.consulta ( sqlAposta ).getInt ( 0 );
                aposta = consultaApostaCompletaById ( apostaId );
                aposta.setQuantidadeSequencias ( aposta.getQuantidadeSequencias ()-1 );
                return da.alterarAposta ( aposta );
            }
        }
        return false;
    }

}
