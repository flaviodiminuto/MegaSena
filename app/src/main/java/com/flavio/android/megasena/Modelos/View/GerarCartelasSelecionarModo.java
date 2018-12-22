package com.flavio.android.megasena.Modelos.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.flavio.android.megasena.R;

public class GerarCartelasSelecionarModo extends AppCompatActivity {
        private ImageView btnAutomatico, btnCarregar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_gerar_cartelas_selecionar_modo );
        btnAutomatico = findViewById (R.id.btnSelecionarModoGerarAutomaticamente);
        btnCarregar = findViewById ( R.id.btnSelecionarModoCarregarAnteriores );


        btnAutomatico.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent it = new Intent ( GerarCartelasSelecionarModo.this, GerarAutomatico.class );
                startActivity ( it );
            }
        } );
    }
}
