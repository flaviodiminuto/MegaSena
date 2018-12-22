package com.flavio.android.megasena.Modelos.Recycler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.flavio.android.megasena.Modelos.Modelos.Aposta;
import com.flavio.android.megasena.Modelos.Modelos.Sequencia;
import com.flavio.android.megasena.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Recycler2 extends AppCompatActivity {
    Aposta aposta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_recycler2 );

        aposta = new Aposta ();
        aposta.adicionaSequencia ( 20,6 );
        aposta.adicionaSequencia ( 10,10 );
        aposta.adicionaSequencia ( 3,15 );

        SimpleAdapter adapter = new SimpleAdapter(generateSimpleList());

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.simple_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager (this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        Toast.makeText ( this, aposta.toString (), Toast.LENGTH_SHORT ).show ();
    }

    private List<Sequencia> generateSimpleList() {
        List<Sequencia> simpleViewModelList = new ArrayList<> ();

        for (int i = 0; i < this.aposta.getQuantidadeSequencias (); i++) {
            simpleViewModelList.add(aposta.getSequencia( i ));
        }

        return simpleViewModelList;
    }
}
