package com.flavio.android.megasena.Modelos.Recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flavio.android.megasena.Modelos.Modelos.Sequencia;
import com.flavio.android.megasena.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter {

    private List<Sequencia> models = new ArrayList<> ();

    /**
     * Adapter constructor
     *
     * @param viewModels
     *         A collection of viewmodels which will contain the data that will be used in each ViewHolder
     */
    public SimpleAdapter(final List<Sequencia> viewModels) {
        if (viewModels != null) {
            this.models.addAll(viewModels);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((SimpleViewHolder) holder).bindData(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_simple_itemview;
    }
}
