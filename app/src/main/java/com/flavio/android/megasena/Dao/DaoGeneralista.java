package com.flavio.android.megasena.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.flavio.android.megasena.connectionfactory.ConnectionFactory;


/**
 * Created by Flávio on 29/05/2018.
 */

public class DaoGeneralista {
    private ConnectionFactory conexao;
    private Context context;

    public DaoGeneralista(Context context){
        this.context = context;
        conexao = new ConnectionFactory (context );
    }
    /**CREATE Tabela*/
    public boolean criarTabela(String nomeTabela, String campos){
        String sql = "CREATE TABLE IF NOT EXISTS "+nomeTabela+" ("+campos+");";
        conexao.openDB ();
        try{
            conexao.getWritableDatabase ().execSQL ( sql );
            conexao.getWritableDatabase ().execSQL ( "PRAGMA foreign_keys=ON;" );//Habilita chave estrangeira

            return true;
        }catch (Exception e){
            return false;
        }finally {
            conexao.close ();
        }
    }/**FIM CREATE*/

    /**READ Tabela*/
    public Cursor consulta(String selectQuery) {
        conexao.openDB ();
        Cursor cursor;
        try{
            cursor = conexao.getWritableDatabase ().rawQuery(selectQuery,null);
            cursor.moveToNext ();
            return cursor;
        }catch ( Exception e){
            return null;
        }
        finally {
            conexao.close ();
        }
    }

    public ConnectionFactory returnConexao (){
        return this.conexao;
    }

    public Cursor listaTeste (String query){

        Cursor cursor;
        try {
            cursor = conexao.getWritableDatabase ().rawQuery ( query, null );
            return cursor;
        }catch (Exception e){
            cursor = null;
            return cursor;
        }

    }

    /**UPDATE Campos*/
    public long inserir(String tabela, ContentValues cv){
        conexao.openDB ();
        try{
            return conexao.getWritableDatabase().insert(tabela, null, cv); //o método insert retorna -1 caso ocorra algum erro
        }catch (Exception e){
            e.printStackTrace (  );
            return -2;
        }finally {
            conexao.close ();
        }
    }
    public boolean atualiza(String tabela, ContentValues cv,String where){
        conexao.openDB ();
        try{
            if(conexao.getWritableDatabase ().update(tabela,cv,where,null)==0)
                return false;
            else
                return true;
        }catch (Exception e){
            e.printStackTrace (  );
            return false;
        }finally {
            conexao.close ();
        }
    }/**FIM UPDATE*/




    /**DELETE Tabela */
    public boolean deletaTabela(String tabela ){
        conexao.openDB ();
        String deleteTable= "DROP TABLE IF EXISTS "+tabela;
        try {
            conexao.getWritableDatabase ().execSQL ( deleteTable);
            return true;
        }catch (Exception e){
            e.printStackTrace (  );
            return false;
        }finally {
            conexao.close ();
        }
    }

    /**DELETE Campos*/
    public boolean deletar(String tabela, String where){
        conexao.openDB ();
        try{
            if((conexao.getWritableDatabase ().delete ( tabela,where,null ))==0)
                return false;
            else
                return true;
        }catch (Exception e){
            return false;
        }finally {
            conexao.close ();
        }

    } /**FIM DELETE*/

}
