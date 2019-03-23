package com.flavio.android.megasena.Modelos.connectionfactory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionFactory extends SQLiteOpenHelper {

    private static final String nomeDatabase= "megasorteio";
    private static final int versaoDatabase = 1;
    private static final String pathDatabase = "/data/user/0/com.flavio.android.megasorteio/database"+nomeDatabase;
    private Context context;

    public ConnectionFactory(Context context) {
        super ( context, nomeDatabase, null, versaoDatabase );
        this.context = context;
    }
    public void openDB(){
        if(!getWritableDatabase ().isOpen ()){
            this.context.openOrCreateDatabase ( pathDatabase ,context.MODE_ENABLE_WRITE_AHEAD_LOGGING,null );
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}