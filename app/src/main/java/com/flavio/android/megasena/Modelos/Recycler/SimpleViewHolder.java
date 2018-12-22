package com.flavio.android.megasena.Modelos.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.flavio.android.megasena.Modelos.Modelos.Sequencia;
import com.flavio.android.megasena.R;

public class SimpleViewHolder extends RecyclerView.ViewHolder {

    private TextView simpleTextView;

    /**
     * The ViewHolder that will be used to display the data in each item shown
     * in the RecyclerView
     *
     * @param itemView
     *         The layout view group used to display the data
     */
    public SimpleViewHolder(final View itemView) {
        super(itemView);
        simpleTextView = (TextView) itemView.findViewById( R.id.simple_text);
    }

    /**
     * Method that is used to bind the data to the ViewHolder
     *
     * @param viewModel
     *         The viewmodel that contains the data
     */
    public void bindData(final Sequencia viewModel) {
        simpleTextView.setText(viewModel.toString ());

    }
}