package com.flavio.android.megasena.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.R;

import java.util.List;

public class ApostaAdapter extends RecyclerView.Adapter<ApostaAdapter.ApostaViewHolder> {
    private List<Aposta> apostaList;
    public class ApostaViewHolder extends RecyclerView.ViewHolder {
        public ApostaViewHolder(View view) {
            super(view);
            setIsRecyclable(false);
        }
    }

    public ApostaAdapter(List<Aposta> apostasList) {
        this.apostaList = apostasList;
    }

    @NonNull
    @Override
    public ApostaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_aposta,parent,false);
        return new ApostaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApostaViewHolder holder, int position) {
        TextView txtApostaIdentificador = holder.itemView.findViewById(R.id.txt_card_aposta_identificador);
        TextView txtApostaValor = holder.itemView.findViewById(R.id.txt_card_aposta_valor);
        TextView txtQuantidadeSequencias = holder.itemView.findViewById(R.id.txt_card_aposta_quantidade);
        Aposta aposta = apostaList.get(position);

        String identificador = String.format("Aposta: %d", aposta.getId() );
        String valor = String.format("Valor: R$ %.2f", aposta.getValor());
        String singularOuPlural = aposta.getQuantidadeSequencias() == 1 ? "Sequencia" : "Sequencias";
        String quantidade = String.format("%d %s", aposta.getQuantidadeSequencias(), singularOuPlural);

        txtApostaIdentificador.setText(identificador);
        txtApostaValor.setText(valor);
        txtQuantidadeSequencias.setText(quantidade);
    }

    @Override
    public int getItemCount() {
        return this.apostaList.size();
    }

}
