package com.flavio.android.megasena.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.flavio.android.megasena.Modelos.Sequencia;

import java.util.ArrayList;
import java.util.List;

public class DaoSequencia {

    private static final String tabelaSequencia = "sequencia";
    DaoGeneralista banco ;
    Context context;

/*--------------------------------------------------------------
    Inicializa o DaoSequencia e tenta criar a tabela Sequencia caso ainda não exista
--------------------------------------------------------------*/
    public DaoSequencia(Context context) {
        this.context = context;
        banco = new DaoGeneralista ( this.context );
        criarTabela ();
    }

/*--------------------------------------------------------------
    Criação da tabela chamada no construtor
--------------------------------------------------------------*/
    private boolean criarTabela(){
        String campos = " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tamanho INTEGER," +
                "valor DOUBLE, "+
                "sorteado BOOLEAN," +
                "campo0 INTEGER," +
                "campo1 INTEGER," +
                "campo2 INTEGER," +
                "campo3 INTEGER," +
                "campo4 INTEGER," +
                "campo5 INTEGER," +
                "campo6 INTEGER," +
                "campo7 INTEGER," +
                "campo8 INTEGER," +
                "campo9 INTEGER," +
                "campo10 INTEGER," +
                "campo11 INTEGER," +
                "campo12 INTEGER," +
                "campo13 INTEGER," +
                "campo14 INTEGER";
        //String campos = " id integer, tamanho integer, valor double ";
        return this.banco.criarTabela ( this.tabelaSequencia, campos);
    }

/*--------------------------------------------------------------
    Insere um registro de sequencia no banco
--------------------------------------------------------------*/
    public long inserirSequencia(Sequencia sequencia){
        sequencia.ordenar ();
        ContentValues cv = sequenciaToContentValues ( sequencia );
        return this.banco.inserir ( this.tabelaSequencia,cv );
    }

/*--------------------------------------------------------------
    Consulta e retorna a sequencia encontrada e null caso não encontre
--------------------------------------------------------------*/
    public Sequencia consultarSequenciaNumerica(Sequencia sequencia){
        ContentValues cv = sequenciaToContentValues ( sequencia );
        StringBuilder sql  = new StringBuilder("SELECT * FROM "+this.tabelaSequencia+" WHERE ");
        String campos ="";
        String key;
        for(int i = 0; i<cv.getAsInteger ( "tamanho" );i++){
            key = String.format ( "campo%d",i );
            campos += String.format ( "%s = %d ",key,cv.getAsInteger ( key ) );
            if(i< cv.getAsInteger ( "tamanho" )-1){ //adiciona a virgula depois de cada campo até o penultimo
                campos +=" AND ";
            }
        }
        sql .append(campos);
        Cursor cursor = this.banco.consulta (sql.toString());
        return cursorToSequencia ( cursor );
    }

    public Sequencia consultarSequenciaById(int id){
        String sql  = "SELECT * FROM "+this.tabelaSequencia+" WHERE id = "+id;
        Cursor cursor = this.banco.consulta (sql);
        return cursorToSequencia ( cursor );
    }

/*--------------------------------------------------------------
    Atualiza uma sequencia no banco de dados
--------------------------------------------------------------*/
    public boolean alterarSequencia(Sequencia sequencia){
        String where = " id = "+sequencia.getId ();
        sequencia.ordenar ();
        ContentValues cv = sequenciaToContentValues ( sequencia );
        return this.banco.atualiza ( this.tabelaSequencia,cv,where );
    }

/*--------------------------------------------------------------
    Deleta um registro de sequencia no banco de dados
--------------------------------------------------------------*/
    public boolean deletarsequencia(int id) {
        String where = " id = "+id ;
        return this.banco.deletar ( this.tabelaSequencia,where );
    }

/*--------------------------------------------------------------
    Deleta a tabela sequencia
--------------------------------------------------------------*/
    public boolean dropTabelaSequencia(){
        return this.banco.deletaTabela ( this.tabelaSequencia );
    }

/*--------------------------------------------------------------
    Converte um objeto Sequencia em um ContentValue
--------------------------------------------------------------*/
    public ContentValues sequenciaToContentValues(Sequencia sequencia) {
        ContentValues cv = new ContentValues (  );
        cv.put ( "tamanho",sequencia.getTamanho () );
        cv.put ( "valor",sequencia.getValor () );
        cv.put ( "sorteado",sequencia.istSorteado () );
        String key;
        for(int i = 0; i<sequencia.getTamanho ();i++){
            key = String.format ( "campo%d",i );
            cv.put ( key,sequencia.getNumeros ()[i] );
        }
        return cv;
    }

           /* 1- id INTEGER PRIMARY KEY AUTOINCREMENT," +
            2- "tamanho INTEGER," +
            3- "valor DOUBLE, "+
            4- "sorteado BOOLEAN," +*/
    public Sequencia cursorToSequencia(Cursor cursor){
        if(cursor!=null) {
            Sequencia sequencia = new Sequencia (  );
            sequencia.setId ( cursor.getInt ( 0 ) );
            sequencia.setTamanho ( cursor.getInt ( 1 ) );
            sequencia.setValor ( cursor.getDouble ( 2 ) );
            sequencia.setSorteado ( Boolean.parseBoolean (  cursor.getString ( 3 )) );
            int[] numeros = new int[sequencia.getTamanho ()];
            for(int i =0; i<sequencia.getTamanho ();i++){
                numeros[i]=cursor.getInt ( i+4 );
            }
            sequencia.setNumeros ( numeros );
            return sequencia;
        }
            return null;

    }

    private String getSqlFromSequencias(List<Sequencia> sequenciasList) {
        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tabelaSequencia);
        int c = 0; // náo dá pra perder a piada a seguir ;D
        sql.append(" (tamanho , valor, sorteado ")
            .append(" , campo").append(c++) //0
            .append(" , campo").append(c++) //1
            .append(" , campo").append(c++) //2
            .append(" , campo").append(c++) //3
            .append(" , campo").append(c++) //4
            .append(" , campo").append(c++) //5
            .append(" , campo").append(c++) //6
            .append(" , campo").append(c++) //7
            .append(" , campo").append(c++) //8
            .append(" , campo").append(c++) //9
            .append(" , campo").append(c++) //10
            .append(" , campo").append(c++) //11
            .append(" , campo").append(c++) //12
            .append(" , campo").append(c++) //13
            .append(" , campo").append(c) //14
            .append(")")
            .append(" VALUES ");

        for(Sequencia sequencia: sequenciasList){
            int tamanho = sequencia.getNumeros().length;
            sql.append("(")
                    .append(tamanho)
                    .append(", ")
                    .append(sequencia.getValor())
                    .append(", ")
                    .append(sequencia.istSorteado())
                    .append(", ");

            int i = 0;
            while (i < 15 ){
                if(i < tamanho){
                    sql.append(sequencia.getNumeros()[i]);
                } else {
                    sql.append("null");
                }
                sql.append(i == 14 ? " " : ", ");
                i++;
            }
            sql.append("),");
        }
        return sql.substring(0, sql.length()-1);
    }

    public  void inserirSequencia(List<Sequencia> sequencias) {
        String sql = getSqlFromSequencias(sequencias);
        this.banco.exec(sql);
    }


    public List<Integer> getIdsUltimasSequencias(int quantidade){
        String sql = "SELECT * FROM "+tabelaSequencia+" ORDER BY id DESC LIMIT "+quantidade;
        List<Integer> idsList = new ArrayList<>();
        Cursor cursor = this.banco.consulta(sql);
        if(cursor.moveToFirst())
            do{
                idsList.add(cursor.getInt(0));
            }while (cursor.moveToNext());

        return idsList;
    }
}
