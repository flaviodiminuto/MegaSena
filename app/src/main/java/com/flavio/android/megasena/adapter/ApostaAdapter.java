package com.flavio.android.megasena.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flavio.android.megasena.Dao.DaoApostaSequencia;
import com.flavio.android.megasena.Modelos.Aposta;
import com.flavio.android.megasena.R;
import com.flavio.android.megasena.View.VerificarSorteio;

import java.util.List;

public class ApostaAdapter extends RecyclerView.Adapter<ApostaAdapter.ApostaViewHolder> {
    private List<Aposta> apostaList;
    private DaoApostaSequencia dao;
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
        this.dao = new DaoApostaSequencia(view.getContext());
        return new ApostaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ApostaViewHolder holder, @SuppressLint("RecyclerView") final int position) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carregaAposta(holder.itemView, position);
            }
        });
    }

    private void carregaAposta(View view, int position){
        Intent it = new Intent( view.getContext(), VerificarSorteio.class );
        Aposta aposta = dao.consultaApostaCompletaById(apostaList.get(position).getId());
        it.putExtra ("aposta", aposta.getJson());
        view.getContext().startActivity( it );
    }

    @Override
    public int getItemCount() {
        return this.apostaList.size();
    }

}
