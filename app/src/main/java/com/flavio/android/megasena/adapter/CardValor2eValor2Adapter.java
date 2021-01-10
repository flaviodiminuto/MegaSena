package com.flavio.android.megasena.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.flavio.android.megasena.R;

import java.util.Comparator;
import java.util.List;

public class CardValor2eValor2Adapter extends RecyclerView.Adapter<CardValor2eValor2Adapter.CardValor2eValor2ViewHolder> {
    private List<Integer[]> chaveValor;
    private int maiorOcorrencia=0;

    public class CardValor2eValor2ViewHolder extends RecyclerView.ViewHolder {
        public CardValor2eValor2ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public CardValor2eValor2Adapter(List<Integer[]> chaveValor) {
        chaveValor.sort(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] v1, Integer[] v2) {
                return v2[1] - v1[1];
            }
        });
        maiorOcorrencia = chaveValor.get(0)[1];
        this.chaveValor = chaveValor;
    }

    
    @Override
    public CardValor2eValor2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_valor1_e_valor2,parent,false);
        return new CardValor2eValor2ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder( CardValor2eValor2ViewHolder holder, int position) {
        TextView valor1 = holder.itemView.findViewById(R.id.card_cave_valor1);
        TextView valor2 = holder.itemView.findViewById(R.id.card_chave_valor2);
        valor1.setText(String.format("%d", this.chaveValor.get(position)[0]));
        valor2.setText(String.format("%d", this.chaveValor.get(position)[1]));

        if(this.chaveValor.get(position)[1] == this.maiorOcorrencia){
            valor1.setTextColor(Color.parseColor("#00ff00"));
            valor2.setTextColor(Color.parseColor("#00ff00"));
        }
    }

    @Override
    public int getItemCount() {
        return chaveValor.size();
    }
}
