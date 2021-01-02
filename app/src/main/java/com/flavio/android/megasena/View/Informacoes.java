package com.flavio.android.megasena.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.flavio.android.megasena.R;

public class Informacoes extends AppCompatActivity {
    private ImageView returnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_informacoes );

        this.returnBack = findViewById ( R.id.btnInformaReturn );

        this.returnBack.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                onBackPressed ();
            }
        } );
    }
}
