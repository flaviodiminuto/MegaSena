package com.flavio.android.megasena.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.flavio.android.megasena.Modelos.adapter.TopDezModel;
import com.flavio.android.megasena.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.Holder> {
    List<TopDezModel> topList;

    public RankingAdapter(List<TopDezModel> topList) {
        this.topList = topList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.top_dez_quadro,parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        TopDezModel top = topList.get(position);
        TextView titulo = holder.titulo;

        titulo.setText(top.getTitulo());
        int menor = Math.min(top.getRanking().sorteados.size(), holder.dezenas.size());
        List<Integer> keys = new ArrayList<>(top.getRanking().sorteados.keySet());
        for (int i = 0; i < menor; i++) {
            Integer chave = keys.get(i);
            String dezena = Objects.requireNonNull(top.getRanking().sorteados.get(chave)).toString();
            String text = String.format("Dezena = %s: Quantidade = %s", chave.toString(), dezena);
            holder.dezenas.get(i).setText(text);
        }

        holder.dezenasArea.setVisibility(top.getRanking().sorteados.size() > 0 && top.isVisivel() ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return topList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView titulo;
        List<TextView> dezenas;
        ConstraintLayout dezenasArea;
        public Holder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.top_dez_titulo);
            dezenasArea = itemView.findViewById(R.id.top_10_dezenas_constraint);
            dezenas = new ArrayList<>();
            dezenas.add(itemView.findViewById(R.id.top_dez_1));
            dezenas.add(itemView.findViewById(R.id.top_dez_2));
            dezenas.add(itemView.findViewById(R.id.top_dez_3));
            dezenas.add(itemView.findViewById(R.id.top_dez_4));
            dezenas.add(itemView.findViewById(R.id.top_dez_5));
            dezenas.add(itemView.findViewById(R.id.top_dez_6));
            dezenas.add(itemView.findViewById(R.id.top_dez_7));
            dezenas.add(itemView.findViewById(R.id.top_dez_8));
            dezenas.add(itemView.findViewById(R.id.top_dez_9));
            dezenas.add(itemView.findViewById(R.id.top_dez_10));

            titulo.setOnClickListener(view ->{
                TopDezModel top = topList.get(getAdapterPosition());
                top.setVisivel(!top.isVisivel());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
