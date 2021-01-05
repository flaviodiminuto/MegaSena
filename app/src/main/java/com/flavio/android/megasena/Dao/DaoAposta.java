package com.flavio.android.megasena.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.flavio.android.megasena.Modelos.Aposta;

import java.util.ArrayList;
import java.util.List;

public class DaoAposta {
    private static final String tabela = "aposta";
    private static final String tabelaIntermediaria = "aposta_sequencia";
    DaoGeneralista banco ;
    Context context;

/*--------------------------------------------------------------
    Inicializa o objeto DaoAposta e tenta criar a tabela "aposta" caso ainda não exista
--------------------------------------------------------------*/
    public DaoAposta(Context context) {
        this.context = context;
        banco = new DaoGeneralista ( this.context );
        this.criarTabela ();
    }
/*--------------------------------------------------------------
    Criação da tabela chamada no construtor
--------------------------------------------------------------*/
    private boolean criarTabela(){
        String campos;
        campos =" 'id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'premiado' BOOLEAN, " +
                "'valor' DOUBLE, " +
                "'quantidadesequencias' INTEGER ";
        return this.banco.criarTabela ( this.tabela, campos);
    }


/*--------------------------------------------------------------
    Insere um registro de Aposta no banco
--------------------------------------------------------------*/
    public long inserirAposta(Aposta aposta){
        ContentValues cv = apostaToContentValues ( aposta );
        return this.banco.inserir ( this.tabela,cv );
    }

/*--------------------------------------------------------------
    Consulta e retorna a aposta encontrada e null caso não encontre
--------------------------------------------------------------*/
    public Aposta consultarApostaById(int id){
        String sql;
        sql  = "SELECT * FROM "+this.tabela+" WHERE id = "+id ;

        Cursor cursor = this.banco.consulta (sql);


        return cursorToAposta ( cursor );
    }
/*--------------------------------------------------------------
    Retorna uma lista com todas as apostas
--------------------------------------------------------------*/
    public List<Aposta> listaApostas(){
        List<Aposta> apostas = new ArrayList <Aposta> (  );
        Cursor cursor = this.banco.consulta ( "select * from aposta;" );
        if(cursor.moveToFirst ()){
            do{
                apostas.add ( cursorToAposta ( cursor ) );
            }while(cursor.moveToNext ());
        }
        return apostas;
    }


/*--------------------------------------------------------------
    Atualiza uma aposta no banco de dados
--------------------------------------------------------------*/
    public boolean alterarAposta(Aposta aposta){
        String where = " id = "+aposta.getId ();
        ContentValues cv = apostaToContentValues ( aposta );
        return this.banco.atualiza ( this.tabela,cv,where );
    }


/*--------------------------------------------------------------
    Deleta um registro de aposta no banco de dados
--------------------------------------------------------------*/
    public boolean deletarAposta(int id) {
        String where = " id = "+id ;
        return this.banco.deletar ( this.tabela,where );
    }

/*--------------------------------------------------------------
    Converte um objeto Aposta em um ContentValue
--------------------------------------------------------------*/
    public ContentValues apostaToContentValues(Aposta aposta) {
        ContentValues cv = new ContentValues (  );
        cv.put ( "premiado",aposta.isPremiado () );
        cv.put ( "valor",aposta.getValor () );
        cv.put ( "quantidadesequencias",aposta.getQuantidadeSequencias () );
        return cv;

    }

    public Aposta cursorToAposta(Cursor cursor){
        if(cursor!=null) {
            Aposta aposta = new Aposta ();
            aposta.setId ( cursor.getInt ( 0 ) );
            aposta.setPremiado ( Boolean.parseBoolean (  cursor.getString ( 1)) );
            aposta.setValor ( cursor.getDouble ( 2 ) );
            aposta.setQuantidadeSequencias ( cursor.getInt ( 3 ) );
            return aposta;
        }
        return null;
    }

}
