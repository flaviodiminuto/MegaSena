package com.flavio.android.megasena.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flavio.android.megasena.Modelos.Sequencia;
import com.flavio.android.megasena.Modelos.Validacao;
import com.flavio.android.megasena.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JogosAdapter extends RecyclerView.Adapter<JogosAdapter.JogoViewHolder> {
    private List<Sequencia> sequencias;
    public JogoViewHolder holder;

    public static class JogoViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public List<TextView> numerosTextView;
        public boolean preenchido;
        public List<LinearLayout> linhas;
        public JogoViewHolder(View view) {
            super(view);
            this.view = view;
            this.numerosTextView = new ArrayList<>();
            this.linhas = new ArrayList<>();
        }
    }

    public JogosAdapter(List<Sequencia> sequencias) {
        this.sequencias = sequencias;
    }

    @NonNull
    @Override
    public JogoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card_sequencia, parent, false);
        this.holder = new JogoViewHolder(view);
        return  this.holder;
    }

/*--------------------------------------------------------------------------------------
    PERFORMANCE: o onBindViewHolder é chamado toda vez que rolamos a tela (scrolling)
    é portanto os processos contidos devem evitar consumir muito recurso
--------------------------------------------------------------------------------------*/
    @Override
    public void onBindViewHolder(@NonNull JogoViewHolder holder, int position) {
        if(holder.preenchido) return;

        View view = holder.view;
        CardView card = view.findViewById(R.id.sequencias_card_view);
        setTituloCard(card, position);
        preparaCamposNumericosCard(holder, sequencias.get(position).getNumeros().length);
        setValorJogoCard(card,position);
        setNumerosCard(holder.numerosTextView,position);
        holder.preenchido = true;
    }

    @Override
    public int getItemCount() {
        return this.sequencias.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void setTituloCard(CardView card, int position){
        TextView textView = card.findViewById(R.id.txt_sequencia_identificador);
        String titulo = "Sequencia: " + (position+1);
        textView.setText(titulo);
    }

    private void setValorJogoCard(CardView card, int position){
        TextView textView = card.findViewById(R.id.txt_sequencia_valor);
        @SuppressLint("DefaultLocale") String valorTexto = String.format("Valor: R$ %.2f", this.sequencias.get(position).getValor());
        textView.setText(valorTexto);
    }

    private void setNumerosCard(List<TextView> numerosTextView, int position){
        for (int i = 0; i < sequencias.get(position).getNumeros().length; i++) {
            int value = sequencias.get(position).getNumeros()[i];
            numerosTextView.get(i).setText(String.valueOf(value));
            if(isNumeroSorteado(value)){
                changeColorNumeroSorteado(numerosTextView.get(i));
            }
        }
    }

    private void preparaCamposNumericosCard(JogoViewHolder holder, int tamanho){
        if(holder.linhas.size() > 0 ) return;

        int quantidadeLinhas = quantidadeLinhas(tamanho);
        View view = holder.view;

        LinearLayout layoutPrincipal = view.findViewById(R.id.layout_horizontal_sequencias);
        for (int i = 0; i < quantidadeLinhas; i++) {
        //PASSOS - inicializar um LinearLayout (Horizontal)
        //inserir 5 textViews no LinearLayout
        //(mesmo que alguns náo sejam preenchidos futuramente para adequar o tamanho do textView)
        //adicionar o novo LinearLayout ao "layoutPrincipal"
            LinearLayout novaLinha = new LinearLayout(view.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(7,7,7,7);
            novaLinha.setLayoutParams(layoutParams);

            int contador = 0;
            while (contador++ < 5){
                TextView textView = new TextView(view.getContext());
                configTextView(textView, view.getContext());

                novaLinha.addView(textView, layoutParams);
                holder.numerosTextView.add(textView);
            }
            layoutPrincipal.addView(novaLinha);
            holder.linhas.add(novaLinha);
        }
    }

    public int quantidadeLinhas(int quantidadeDeNumeros){
        boolean exato = quantidadeDeNumeros % 5 == 0;
        int retorno ;
        if(exato){
            retorno = quantidadeDeNumeros / 5;
        }else{
            retorno =  (quantidadeDeNumeros / 5) + 1;
        }
        return retorno;
    }

    private void configTextView(TextView textView, Context context){
        Drawable background = AppCompatResources.getDrawable(context, R.drawable.bordas_arredondadas_claro);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        textView.setHeight(100);
        textView.setWidth(170);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setPadding(10,10,10,0);
        textView.setBackground(background);
    }

    private void changeColorNumeroSorteado(TextView textView){
        //"#1DFF04"
        textView.setTextColor(Color.parseColor("#1DFF04"));
    }

    public boolean isNumeroSorteado(Integer numero){
        return Arrays.binarySearch(Validacao.getNumerosSorteados(), numero) > -1;
    }
}
