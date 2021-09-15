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

public class TopDezAdapter extends RecyclerView.Adapter<TopDezAdapter.Holder> {
    List<TopDezModel> topList;

    public TopDezAdapter(List<TopDezModel> topList) {
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
        int menor = Math.min(top.getDezenas().size(), holder.dezenas.size());
        for (int i = 0; i < menor; i++) {
            String dezena = top.getDezenas().get(i);
            holder.dezenas.get(i).setText(dezena != null ? dezena : "");
        }

        holder.dezenasArea.setVisibility(top.isVisivel() ? View.VISIBLE : View.GONE);

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
            dezenas.add(itemView.findViewById(R.id.top_10_primeiro));
            dezenas.add(itemView.findViewById(R.id.top_10_segundo));


            titulo.setOnClickListener(view ->{
                TopDezModel top = topList.get(getAdapterPosition());
                top.setVisivel(!top.isVisivel());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
